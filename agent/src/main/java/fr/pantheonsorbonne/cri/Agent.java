package fr.pantheonsorbonne.cri;

import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javaparser.utils.Log;

import fr.pantheonsorbonne.cri.requirements.Empty;
import fr.pantheonsorbonne.cri.requirements.ReqCollectorGrpc;
import fr.pantheonsorbonne.cri.requirements.ReqCollectorGrpc.ReqCollectorStub;
import fr.pantheonsorbonne.cri.requirements.Requirement;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

/**
 * Hello world!
 *
 */
public class Agent {

	public static final Logger LOG = LoggerFactory.getLogger(Agent.class);
	public static final String INSTRUMENTED_PACKAGE = "fr.pantheonsorbonne.ufr27";
	public static final String REPO_ADDRESS = "file:///home/nherbaut/workspace/basic-cli-uni.git";
	public static final String GRPC_ENDPOINT_HOST = "localhost";
	public static final Integer GRPC_ENDPOINT_PORT = 8081;
	public static final Set<ReqMatcher> reqMatchers = new HashSet<>();
	private static StreamObserver<Requirement> requirementObserver;

	public static void main(String args[]) throws InterruptedException {

	}

	public static void sendReq(String reqId) {
		if (requirementObserver != null) {
			sendReqGrPC(reqId);
		}
		sendReqLog(reqId);

	}

	private static void sendReqGrPC(String reqId) {
		requirementObserver.onNext(Requirement.newBuilder().setId(reqId).build());
	}

	private static void sendReqLog(String reqId) {
		LOG.info("new requirement" + reqId);
	}

	public static void initGrpcChannel() {
		ManagedChannel channel = ManagedChannelBuilder.forAddress(GRPC_ENDPOINT_HOST, GRPC_ENDPOINT_PORT).usePlaintext()
				.build();

		ReqCollectorStub stub = ReqCollectorGrpc.newStub(channel);

		requirementObserver = stub.pushRequirement(new StreamObserver<Empty>() {

			@Override
			public void onNext(Empty value) {
				System.out.println("None");

			}

			@Override
			public void onError(Throwable t) {
				requirementObserver = null;

			}

			@Override
			public void onCompleted() {
				requirementObserver = null;

			}
		});

		try {
			channel.awaitTermination(1000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			channel.shutdown();
		}

	}

	public static void premain(String arg, Instrumentation instZ) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				initGrpcChannel();

			}
		}).start();
		
		Log.info("GRPC client started");
		Log.info("Requirement extraction Started");
		initReqDB();
		Log.info("Requirement extraction Finished");

		new AgentBuilder.Default().type(ElementMatchers.nameStartsWith(INSTRUMENTED_PACKAGE))
				.transform((Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader,
						JavaModule module) -> builder.method(ElementMatchers.any())
								.intercept(MethodDelegation.to(Target.class))

				).installOn(instZ);

	}

	private static void initReqDB() {
		try {
			Path tempFolder = Files.createTempDirectory("nhe-agent");
			LOG.info("cloning in" + tempFolder);
			Git repo = Git.cloneRepository().setURI(REPO_ADDRESS).setDirectory(tempFolder.toFile()).call();

			RepoFileVisitor visitor = new RepoFileVisitor(repo);
			Files.walkFileTree(tempFolder, visitor);
			reqMatchers.addAll(visitor.getReqMatcher());

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}

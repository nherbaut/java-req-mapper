package fr.pantheonsorbonne.cri;

import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.AbstractScheduledService.CustomScheduler;
import com.google.common.util.concurrent.AbstractScheduledService.Scheduler;

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
	public static final HashSet<ReqMatcher> reqMatchers = new HashSet<>();
	
	public static final Scheduler scheduler = CustomScheduler.newFixedRateSchedule(0, 5, TimeUnit.SECONDS);

	public static void premain(String arg, Instrumentation instZ) {
		
		

		initReqDB();

		new AgentBuilder.Default().type(ElementMatchers.nameStartsWith(INSTRUMENTED_PACKAGE))
				.transform((Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader,
						JavaModule module) -> builder.method(ElementMatchers.any())
								// .intercept(FixedValue.value("transformed"))
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

package fr.pantheonsorbonne.cri.net;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import fr.pantheonsorbonne.cri.configuration.AppConfigurationVariables;
import fr.pantheonsorbonne.cri.net.grpc.DummyObserver;
import fr.pantheonsorbonne.cri.net.grpc.GrPCRequirementPublisher;
import fr.pantheonsorbonne.cri.req.RequirementPublisher;
import fr.pantheonsorbonne.cri.requirements.Empty;
import fr.pantheonsorbonne.cri.requirements.ReqCollectorGrpc;
import fr.pantheonsorbonne.cri.requirements.Requirement;
import fr.pantheonsorbonne.cri.requirements.ReqCollectorGrpc.ReqCollectorStub;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.Stream;
import io.grpc.stub.StreamObserver;

public class PublisherConfiguration extends AbstractModule {

	@Override
	protected void configure() {
		super.configure();
		this.bind(RequirementPublisher.class).to(GrPCRequirementPublisher.class);
		bind(new TypeLiteral<StreamObserver<Empty>>() {
		}).to(new TypeLiteral<DummyObserver>() {
		});

	}

	@Provides
	@Singleton
	@Inject
	public ReqCollectorStub getStud(ManagedChannel chan) {
		return ReqCollectorGrpc.newStub(chan);
	}

	@Provides
	@Singleton
	@Inject
	public ManagedChannel getChannel(AppConfigurationVariables vars) {
		return ManagedChannelBuilder.forAddress(vars.getGRPEndpointHost(), vars.getGRPCEndpointPort()).usePlaintext()
				.build();

	}

	@Inject
	@Singleton
	@Provides
	public StreamObserver<Requirement> initGrpcChannel(ManagedChannel channel, StreamObserver<Empty> obs) {

		ReqCollectorStub stub = ReqCollectorGrpc.newStub(channel);

		return stub.pushRequirement(obs);

	}
}

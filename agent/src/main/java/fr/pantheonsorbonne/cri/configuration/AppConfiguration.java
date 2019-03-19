package fr.pantheonsorbonne.cri.configuration;

import java.lang.instrument.Instrumentation;
import java.util.Collection;
import java.util.HashSet;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

import fr.pantheonsorbonne.cri.inst.InstrumentationClient;
import fr.pantheonsorbonne.cri.inst.bbuddy.BBudyConfiguration;
import fr.pantheonsorbonne.cri.inst.bbuddy.ByteBuddyInstrumentationClient;
import fr.pantheonsorbonne.cri.req.ReqMatcher;
import fr.pantheonsorbonne.cri.requirements.Empty;
import fr.pantheonsorbonne.cri.requirements.ReqCollectorGrpc;
import fr.pantheonsorbonne.cri.requirements.ReqCollectorGrpc.ReqCollectorStub;
import fr.pantheonsorbonne.cri.requirements.Requirement;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class AppConfiguration extends AbstractModule {

	protected Instrumentation instrumentation;

	public AppConfiguration(Instrumentation instZ) {
		this.instrumentation = instZ;
	}

	@Override
	protected void configure() {
		super.configure();

		bind(ConfigurationVariableProvider.class).to(AppConfigurationVariables.class);
		
		install(new BBudyConfiguration());

	}

	@Provides
	public Instrumentation getInstrumentation() {
		return this.instrumentation;
	}

}

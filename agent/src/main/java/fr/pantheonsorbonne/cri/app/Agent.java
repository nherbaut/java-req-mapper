package fr.pantheonsorbonne.cri.app;

import java.lang.instrument.Instrumentation;
import java.util.Set;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import fr.pantheonsorbonne.cri.configuration.AppConfiguration;
import fr.pantheonsorbonne.cri.instrumentation.InstrumentationClient;
import fr.pantheonsorbonne.cri.instrumentation.configuration.InstrumentationConfiguration;
import fr.pantheonsorbonne.cri.mapping.configuration.RequirementMappingConfiguration;
import fr.pantheonsorbonne.cri.publisher.grpc.configuration.GRPCPublisherConfiguration;

public class Agent {

	@Inject
	Set<InstrumentationClient> instrumentatinClients;

	public static void premain(String arg, Instrumentation instZ) {

		// gather all modules to be used in the IC
		Module applicationConfiguraiton = new AppConfiguration();
		Module publisherConfiguration = new GRPCPublisherConfiguration();
		Module requirementMappingConfiguration = new RequirementMappingConfiguration();
		Module instrumentationConfiguration = new InstrumentationConfiguration(instZ);

		// consolidate modules
		Module conf = Modules.combine(applicationConfiguraiton, requirementMappingConfiguration,
				instrumentationConfiguration, publisherConfiguration);

		// create the agent a hook instrumentation directives
		Injector injector = Guice.createInjector(conf);
		Agent agent = injector.getInstance(Agent.class);
		agent.run();

	}

	private void run() {

		for (InstrumentationClient ic : instrumentatinClients) {
			ic.registerClient();
		}

	}

}

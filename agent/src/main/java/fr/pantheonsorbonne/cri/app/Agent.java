package fr.pantheonsorbonne.cri.app;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

import fr.pantheonsorbonne.cri.configuration.AppConfiguration;
import fr.pantheonsorbonne.cri.inst.InstrumentationClient;
import fr.pantheonsorbonne.cri.inst.bbuddy.BBudyConfiguration;
import fr.pantheonsorbonne.cri.net.PublisherConfiguration;
import fr.pantheonsorbonne.cri.net.grpc.GrPCRequirementPublisher;
import fr.pantheonsorbonne.cri.req.ReqConfiguration;
import fr.pantheonsorbonne.cri.req.RequirementPublisher;

public class Agent {

	@Inject
	java.util.logging.Logger logger;

	public static void premain(String arg, Instrumentation instZ) {

		PublisherConfiguration publisher = new PublisherConfiguration();
		AppConfiguration instrumenter = new AppConfiguration(instZ);
		ReqConfiguration reqGrabber = new ReqConfiguration();
		com.google.inject.Module conf = Modules.combine(publisher, instrumenter, reqGrabber);
		Injector injector = Guice.createInjector(conf);

		Agent agent = injector.getInstance(Agent.class);
		agent.run();

	}

	@Inject
	Instrumentation inst;

	@Inject
	Set<InstrumentationClient> instrumentatinClients;

	@Inject
	RequirementPublisher publisher;

	private void run() {

		for (InstrumentationClient ic : instrumentatinClients) {
			ic.registerClient();
		}

	}

}

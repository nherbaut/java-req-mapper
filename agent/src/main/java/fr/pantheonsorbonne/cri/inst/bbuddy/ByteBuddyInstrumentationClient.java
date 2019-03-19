package fr.pantheonsorbonne.cri.inst.bbuddy;

import java.lang.instrument.Instrumentation;
import java.util.Set;

import com.google.inject.Inject;

import fr.pantheonsorbonne.cri.inst.InstrumentationClient;
import net.bytebuddy.agent.builder.AgentBuilder.Identified.Extendable;

public class ByteBuddyInstrumentationClient implements InstrumentationClient {

	@Inject
	Set<Extendable> extendables;

	@Inject
	Instrumentation inst;

	@Override
	public void registerClient() {
		for (Extendable ext : extendables) {
			ext.installOn(inst);
		}

	}

}

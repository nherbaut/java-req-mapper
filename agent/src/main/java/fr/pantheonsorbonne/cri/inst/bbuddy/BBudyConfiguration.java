package fr.pantheonsorbonne.cri.inst.bbuddy;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import fr.pantheonsorbonne.cri.inst.InstrumentationClient;
import net.bytebuddy.agent.builder.AgentBuilder.Identified.Extendable;

public class BBudyConfiguration extends AbstractModule {

	@Override
	protected void configure() {
		super.configure();

		requestStaticInjection(ByteBuddyInstrumentationClient.class);

		Multibinder<InstrumentationClient> multibinder = Multibinder.newSetBinder(binder(),
				InstrumentationClient.class);
		multibinder.addBinding().to(ByteBuddyInstrumentationClient.class);

		Multibinder<Extendable> multibinderExtendable = Multibinder.newSetBinder(binder(), Extendable.class);
		multibinderExtendable.addBinding().toProvider(MethodExtractorProvider.class);

	}

}

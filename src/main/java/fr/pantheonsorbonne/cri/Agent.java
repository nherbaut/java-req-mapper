package fr.pantheonsorbonne.cri;

import static net.bytebuddy.matcher.ElementMatchers.named;

import java.lang.instrument.Instrumentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

/**
 * Hello world!
 *
 */
public class Agent {

	private static final Logger LOG = LoggerFactory.getLogger(Agent.class);
	public static final String INSTRUMENTED_PACKAGE = "fr.pantheonsorbonne.ufr27";

	public static void premain(String arg, Instrumentation instZ) {

		new AgentBuilder.Default().type(ElementMatchers.nameStartsWith(INSTRUMENTED_PACKAGE))
				.transform((Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader,
						JavaModule module) -> 
				builder
					.method(ElementMatchers.any())
					//.intercept(FixedValue.value("transformed"))
					.intercept(MethodDelegation.to(Target.class))

				).installOn(instZ);

	}
}

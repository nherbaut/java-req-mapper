package fr.pantheonsorbonne.cri.inst.bbuddy;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.google.inject.Inject;

import fr.pantheonsorbonne.cri.configuration.AppConfigurationVariables;
import fr.pantheonsorbonne.cri.req.ReqMatcher;
import fr.pantheonsorbonne.cri.req.RequirementPublisher;
import fr.pantheonsorbonne.cri.req.StackTraceParser;
import fr.pantheonsorbonne.cri.requirements.Requirement;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class MethodCallInterceptor {

	@Inject
	public RequirementPublisher publisher;

	@Inject
	AppConfigurationVariables vars;

	@Inject
	Collection<ReqMatcher> reqMatchers;

	@RuntimeType
	public Object intercept(@SuperCall Callable<?> zuper, @AllArguments Object... args) throws Exception {
		Set<String> reqs = new StackTraceParser(Thread.getAllStackTraces().get(Thread.currentThread()), vars,
				reqMatchers).getReqs();
		if (!reqs.isEmpty()) {
			reqs.stream()
				.collect(Collectors.toSet()).stream()
				.map((String req) -> Requirement.newBuilder().setId(req).build())
				.forEach((Requirement req) -> publisher.publish(req));

		}
		return zuper.call();
	}
}
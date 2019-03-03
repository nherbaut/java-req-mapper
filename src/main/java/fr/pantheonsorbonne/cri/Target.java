package fr.pantheonsorbonne.cri;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class Target {

	private static final Logger LOG = LoggerFactory.getLogger(Target.class);

	@RuntimeType
	public static Object intercept(@SuperCall Callable<?> zuper, @AllArguments Object... args) throws Exception {
		LOG.info(new StackTraceParser(Thread.getAllStackTraces().get(Thread.currentThread())).toString());
		return zuper.call();
	}
}
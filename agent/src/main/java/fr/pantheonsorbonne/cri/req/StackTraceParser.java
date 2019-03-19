package fr.pantheonsorbonne.cri.req;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.inject.Inject;

import fr.pantheonsorbonne.cri.app.Agent;
import fr.pantheonsorbonne.cri.configuration.AppConfigurationVariables;

public class StackTraceParser {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	private StackTraceElement[] elements;
	private AppConfigurationVariables vars;
	private Collection<ReqMatcher> reqMatchers;

	public StackTraceParser(StackTraceElement[] elements, AppConfigurationVariables vars,
			Collection<ReqMatcher> reqMatchers) {
		this.elements = elements;
		this.vars = vars;
		this.reqMatchers = reqMatchers;
	}

	public Set<String> getReqs() {

		Set<String> res = new HashSet<>();
		for (StackTraceElement elt : elements) {
			if (elt.getClassName().startsWith(this.vars.getInstrumentedPackage()) && elt.getLineNumber() != -1) {
				String classNAme = elt.getClassName();
				String methodName = elt.getMethodName().split("\\$")[0];
				Integer lineNumber = elt.getLineNumber();// can we use that?

				for (ReqMatcher m : reqMatchers) {
					if (m.getClassName().equals(classNAme)) {
						if (m.getMethodName().equals(methodName)) {

							for (String reqStr : m.getReq()) {
								if (!Strings.isNullOrEmpty(reqStr)) {
									res.add(reqStr);
								}
							}

						}
					}
				}

			}
		}
		return res;

	}
}

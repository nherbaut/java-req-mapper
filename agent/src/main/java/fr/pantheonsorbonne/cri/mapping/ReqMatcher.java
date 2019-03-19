package fr.pantheonsorbonne.cri.mapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReqMatcher {
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public Set<String> getReq() {
		return req;
	}

	public void setReq(Set<String> req) {
		this.req = req;
	}

	private String className;
	private String methodName;
	private Integer line;
	private Set<String> req = new HashSet<>();

	public ReqMatcher(String className, String methodName, Integer line, String... reqs) {
		this.className = className;
		this.methodName = methodName;
		this.line = line;
		req.addAll(Arrays.asList(reqs));

	}

}

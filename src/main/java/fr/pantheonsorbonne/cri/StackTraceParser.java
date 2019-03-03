package fr.pantheonsorbonne.cri;

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

	public StackTraceParser(StackTraceElement[] elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder(ANSI_GREEN).append("\n");
		for (StackTraceElement elt : elements) {
			if (elt.getClassName().startsWith(Agent.INSTRUMENTED_PACKAGE) && elt.getLineNumber()!=-1) {
				sb.append(elt.getClassName());
				sb.append("\t");
				sb.append(elt.getMethodName().split("\\$")[0]);
				sb.append("\t").append(elt.getLineNumber());
				sb.append("\n");
			}
		}
		sb.append(ANSI_RESET);
		return sb.toString();

	}
}

package fr.pantheonsorbonne.cri.configuration;

public interface ConfigurationVariableProvider {

	public abstract Integer getGRPCEndpointPort();

	public abstract String getGRPEndpointHost();

	public abstract String getRepoAddress();

	public abstract String getInstrumentedPackage();

	public abstract String getSourceRootDir();
	
	public abstract RequirementIssueDecorator getIssueBaseURL();

}

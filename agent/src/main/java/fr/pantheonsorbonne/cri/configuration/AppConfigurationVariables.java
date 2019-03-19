package fr.pantheonsorbonne.cri.configuration;

public class AppConfigurationVariables implements ConfigurationVariableProvider {

	public enum SourceRootDIR {
		MAVEN("src/main/java");

		String str;

		SourceRootDIR(String str) {
			this.str = str;
		}

		public String getPath() {
			return this.str;
		}
	}

	public String getInstrumentedPackage() {
		return "fr.pantheonsorbonne.ufr27";
	}

	public final String getRepoAddress() {
		return "file:///home/nherbaut/workspace/basic-cli-uni.git";
	}

	public final String getGRPEndpointHost() {
		return "localhost";
	}

	public final Integer getGRPCEndpointPort() {
		return 8081;
	}

	@Override
	public String getSourceRootDir() {
		return SourceRootDIR.MAVEN.getPath();
	}

	@Override
	public RequirementIssueDecorator getIssueBaseURL() {
		return new GitHubRequirementIssueDecorator("https://github.com/nherbaut/basic-cli-uni/issues");
	}

}

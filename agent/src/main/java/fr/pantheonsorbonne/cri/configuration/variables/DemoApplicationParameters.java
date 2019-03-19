package fr.pantheonsorbonne.cri.configuration.variables;

import com.google.inject.Inject;

import fr.pantheonsorbonne.cri.configuration.RequirementIssueDecorator;
import fr.pantheonsorbonne.cri.mapping.GitHubRequirementIssueDecorator;

public class DemoApplicationParameters implements ApplicationParameters {

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
	public RequirementIssueDecorator getRequirementIssueDecorator() {
		return new GitHubRequirementIssueDecorator("http://github.com/nherbaut/basic-cli-uni/issues/");
	}

}

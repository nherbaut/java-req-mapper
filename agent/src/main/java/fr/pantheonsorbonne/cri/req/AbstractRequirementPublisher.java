package fr.pantheonsorbonne.cri.req;

import java.util.Collection;

import com.google.inject.Inject;

import fr.pantheonsorbonne.cri.configuration.AppConfigurationVariables;
import fr.pantheonsorbonne.cri.configuration.ConfigurationVariableProvider;
import fr.pantheonsorbonne.cri.requirements.Requirement;

public abstract class AbstractRequirementPublisher implements RequirementPublisher {

	@Inject
	ConfigurationVariableProvider vars;

	@Override
	final public void publish(Requirement req) {
		this.publishLinkedRequirement(vars.getIssueBaseURL().getIssueLink(req));
	}

	protected abstract void publishLinkedRequirement(Requirement req);

	@Override
	final public void publish(Collection<Requirement> reqToPublish) {
		for (Requirement r : reqToPublish) {
			this.publish(r);
		}

	}

}

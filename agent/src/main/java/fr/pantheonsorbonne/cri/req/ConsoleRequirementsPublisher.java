package fr.pantheonsorbonne.cri.req;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;

import fr.pantheonsorbonne.cri.requirements.Requirement;

public class ConsoleRequirementsPublisher implements RequirementPublisher {

	@Inject
	Logger logger;

	@Override
	public void publish(Requirement req) {
		logger.log(Level.ALL, req.getId());

	}

	@Override
	public void publish(Collection<Requirement> reqToPublish) {
		for (Requirement r : reqToPublish) {
			this.publish(r);
		}

	}

}

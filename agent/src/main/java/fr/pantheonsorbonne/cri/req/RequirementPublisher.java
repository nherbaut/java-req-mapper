package fr.pantheonsorbonne.cri.req;

import java.util.Collection;
import java.util.Set;

import fr.pantheonsorbonne.cri.requirements.Requirement;

public interface RequirementPublisher {

	void publish(Requirement req);

	void publish(Collection<Requirement> reqToPublish);

}

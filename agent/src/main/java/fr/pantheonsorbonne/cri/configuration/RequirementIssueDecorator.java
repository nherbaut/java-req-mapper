package fr.pantheonsorbonne.cri.configuration;

import java.net.URI;

import fr.pantheonsorbonne.cri.requirements.Requirement;

public interface RequirementIssueDecorator {

	public Requirement getIssueLink(Requirement req);

}
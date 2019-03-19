package fr.pantheonsorbonne.cri.req;

import java.util.Collection;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import fr.pantheonsorbonne.cri.configuration.ConfigurationVariableProvider;
import fr.pantheonsorbonne.cri.versioning.RepoFileVisitor;

public class ReqConfiguration extends AbstractModule {

	

	@Override
	protected void configure() {

		super.configure();

	}

	@Provides
	@Singleton
	public Collection<ReqMatcher> getMatcher(ConfigurationVariableProvider vars) {
		RepoFileVisitor repo = new RepoFileVisitor(vars);
		return repo.getReqMatcher();

	}
}

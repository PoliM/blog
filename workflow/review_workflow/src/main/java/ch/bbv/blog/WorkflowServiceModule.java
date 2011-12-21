package ch.bbv.blog;

import com.google.inject.AbstractModule;

public class WorkflowServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(WorkflowService.class).to(WorkflowServiceImpl.class);
	}
}

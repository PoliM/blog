package ch.bbv.blog;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class WorkflowServiceFactory {

	public static void sayHello() {
		System.out.println("Hello World");
	}

	private static Injector injector;

	public static WorkflowService getWorkflowService() {
		return getInjector().getInstance(WorkflowService.class);
	}

	private static Injector getInjector() {
		if (injector == null) {
			injector = Guice.createInjector(new WorkflowServiceModule());
		}
		return injector;
	}

	public static void setInjector(final Injector injector) {
		WorkflowServiceFactory.injector = injector;
	}
}

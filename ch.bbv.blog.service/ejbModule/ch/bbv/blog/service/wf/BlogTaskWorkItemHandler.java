package ch.bbv.blog.service.wf;

import java.util.UUID;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import ch.bbv.blog.service.BlogAdminServiceImpl;

public class BlogTaskWorkItemHandler implements WorkItemHandler {


	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		UUID blogEntryId = (UUID)workItem.getParameter("blogId");
		BlogAdminServiceImpl.createBlogTask(workItem.getId(), blogEntryId);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}
}

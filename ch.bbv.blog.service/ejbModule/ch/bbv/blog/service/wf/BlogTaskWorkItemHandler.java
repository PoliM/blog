package ch.bbv.blog.service.wf;

import java.util.ArrayList;
import java.util.List;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

public class BlogTaskWorkItemHandler implements WorkItemHandler {

	private final List<WorkItem> workItems = new ArrayList<WorkItem>();

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		workItems.add(workItem);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}

	public WorkItem getWorkItem() {
		if (workItems.size() == 0) {
			return null;
		}
		if (workItems.size() == 1) {
			WorkItem result = workItems.get(0);
			this.workItems.clear();
			return result;
		} else {
			throw new IllegalArgumentException("More than one work item active");
		}
	}

	public List<WorkItem> getWorkItems() {
		List<WorkItem> result = new ArrayList<WorkItem>(workItems);
		workItems.clear();
		return result;
	}

}

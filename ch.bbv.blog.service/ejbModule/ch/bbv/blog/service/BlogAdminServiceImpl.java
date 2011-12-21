package ch.bbv.blog.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.ejb.Stateless;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.WorkItemHandler;
import org.jbpm.workflow.instance.WorkflowProcessInstance;

import ch.bbv.blog.service.model.BlogEntry;
import ch.bbv.blog.service.model.BlogTask;
import ch.bbv.blog.service.wf.BlogTaskWorkItemHandler;

/**
 * Session Bean implementation class TestServiceImpl
 */
@Stateless(mappedName = "BlogAdminService")
public class BlogAdminServiceImpl implements BlogAdminService {

	private static Map<UUID, BlogEntry> blogEntries = new HashMap<UUID, BlogEntry>();
	private static Map<UUID, BlogTask> blogTasks = new HashMap<UUID, BlogTask>();

	public static void publishBlog(UUID id) {
		blogEntries.get(id).setState(BlogEntry.State.Published);
	}

	public static void rejectBlog(UUID id) {
		blogEntries.get(id).setState(BlogEntry.State.Rejected);
	}

	public static void createBlogTask(long workItemId, UUID blogEntryId) {
		BlogTask blogTask = new BlogTask(workItemId, blogEntryId);
		blogTasks.put(blogTask.getId(), blogTask);
	}

	private StatefulKnowledgeSession ksession;

	/**
	 * Default constructor.
	 */
	public BlogAdminServiceImpl() {
	}

	@Override
	public void create(BlogEntry blogEntry) {
		blogEntries.put(blogEntry.getId(), blogEntry);

		// Start workflow
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("blogId", blogEntry.getId());

		WorkflowProcessInstance w = (WorkflowProcessInstance) getKSession()
				.startProcess("ch.bbv.blog.review", params);
	}

	@Override
	public Set<BlogTask> getBlogTasks() {
		Set<BlogTask> result = new HashSet<BlogTask>();
		for (BlogTask e : blogTasks.values()) {
			if (e.getState().equals(BlogTask.State.Open)) {
				result.add(e);
			}
		}
		return result;
	}

	@Override
	public Set<BlogEntry> getPublishedBlogEntries() {
		Set<BlogEntry> result = new HashSet<BlogEntry>();
		for (BlogEntry e : blogEntries.values()) {
			if (e.getState().equals(BlogEntry.State.Published)) {
				result.add(e);
			}
		}
		return result;
	}

	@Override
	public void accept(UUID blogTaskId) {
		blogTasks.get(blogTaskId).setState(BlogTask.State.Accepted);
	}

	@Override
	public void reviewedOk(UUID blogTaskId) {
		BlogTask blogTask = blogTasks.get(blogTaskId);
		Map<String, Object> taskResult = new HashMap<String, Object>();
		taskResult.put("rejected", Boolean.FALSE);
		ksession.getWorkItemManager().completeWorkItem(
				blogTask.getWorkItemId(), taskResult);
	}

	@Override
	public void reviewedFailed(UUID blogTaskId) {
		BlogTask blogTask = blogTasks.get(blogTaskId);
		Map<String, Object> taskResult = new HashMap<String, Object>();
		taskResult.put("rejected", Boolean.TRUE);
		ksession.getWorkItemManager().completeWorkItem(
				blogTask.getWorkItemId(), taskResult);
	}

	private StatefulKnowledgeSession getKSession() {
		if (ksession == null) {
			System.out.println("Loading process... ");
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
					.newKnowledgeBuilder();
			kbuilder.add(ResourceFactory.newClassPathResource("ch/bbv/blog/service/review.bpmn"),
					ResourceType.BPMN2);
			KnowledgeBase knowledgeBase = kbuilder.newKnowledgeBase();
			ksession = knowledgeBase.newStatefulKnowledgeSession();

			WorkItemHandler workItemHandler = new BlogTaskWorkItemHandler();
			ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
					workItemHandler);
		}
		return ksession;
	}
}

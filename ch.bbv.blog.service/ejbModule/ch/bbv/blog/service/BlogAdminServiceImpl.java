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

import ch.bbv.blog.service.model.BlogEntry;
import ch.bbv.blog.service.model.BlogTask;
import ch.bbv.blog.service.model.User;
import ch.bbv.blog.service.wf.BlogTaskWorkItemHandler;

/**
 * Session Bean implementation class TestServiceImpl
 */
@Stateless(mappedName = "BlogAdminService")
public class BlogAdminServiceImpl implements BlogAdminService {

	private static Set<User> users = new HashSet<User>();
	private static Map<UUID, BlogEntry> blogEntries = new HashMap<UUID, BlogEntry>();
	private static Map<UUID, BlogTask> blogTasks = new HashMap<UUID, BlogTask>();

	public static void publishBlog(UUID id) {
		blogEntries.get(id).setState(BlogEntry.State.Published);
	}

	public static void rejectBlog(UUID id) {
		blogEntries.get(id).setState(BlogEntry.State.Rejected);
	}

	/**
	 * Default constructor.
	 */
	public BlogAdminServiceImpl() {
		users.add(new User("Marco"));
		users.add(new User("Lukas"));
		users.add(new User("Adrian"));
	}

	@Override
	public void create(BlogEntry blogEntry) {
		blogEntries.put(blogEntry.getId(), blogEntry);
		
		// Start workflow
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("blogId", blogEntry.getId());
		
		System.out.println("Loading process... ");
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("review.bpmn"), ResourceType.BPMN2);
		KnowledgeBase knowledgeBase = kbuilder.newKnowledgeBase();
		StatefulKnowledgeSession ksession = knowledgeBase.newStatefulKnowledgeSession();

		WorkItemHandler workItemHandler = new BlogTaskWorkItemHandler();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", workItemHandler);

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

	}

	@Override
	public void reviewedFailed(UUID blogTaskId) {

	}

}

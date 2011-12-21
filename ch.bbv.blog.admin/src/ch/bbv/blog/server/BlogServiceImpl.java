package ch.bbv.blog.server;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import javax.ejb.EJB;

import ch.bbv.blog.client.BlogService;
import ch.bbv.blog.service.BlogAdminService;
import ch.bbv.blog.service.model.BlogEntry;
import ch.bbv.blog.service.model.BlogTask;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class BlogServiceImpl extends RemoteServiceServlet implements
		BlogService {

	@EJB
	private BlogAdminService blogAdminService;

	@Override
	public String post(String username, String blogValue) {
		blogAdminService.create(new BlogEntry(username, blogValue));
		return "Entry Added";
	}

	/**
	 * @return liste der BlogTask.Id
	 */
	@Override
	public Collection<String> getAllBlogsToReview() {
		Collection<String> result = new LinkedList<String>();
		for (BlogTask tasks : blogAdminService.getBlogTasks()) {
			result.add(tasks.getId().toString());
		}
		return result;
	}

	@Override
	public String getNextEntryForUser(String blogTaskId) {
		BlogEntry blogForTask = blogAdminService.getBlogForTask(UUID.fromString(blogTaskId));
		return blogForTask.getText();
	}

	@Override
	public boolean accept(String user, String text, String reviewerName) {
		blogAdminService.reviewedOk(UUID.fromString(user));
		return true;
	}

	@Override
	public boolean deny(String user, String text, String reviewerName) {
		blogAdminService.reviewedFailed(UUID.fromString(user));
		return true;
	}

	@Override
	public Collection<String> getAllEntries() {
		Collection<String> result = new LinkedList<String>();
		for (BlogEntry entry : blogAdminService.getPublishedBlogEntries()) {
			result.add(entry.getText());
		}
		return result;
	}
}

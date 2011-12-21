package ch.bbv.blog.service;

import java.util.Set;
import java.util.UUID;

import javax.ejb.Local;

import ch.bbv.blog.service.model.BlogEntry;
import ch.bbv.blog.service.model.BlogTask;

@Local
public interface BlogAdminService {

	void create(BlogEntry blogEntry);

	Set<BlogEntry> getPublishedBlogEntries();

	Set<BlogTask> getBlogTasks();

	void accept(UUID blogTaskId);

	void reviewedOk(UUID blogTaskId);

	void reviewedFailed(UUID blogTaskId);

}

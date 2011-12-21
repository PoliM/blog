package ch.bbv.blog;

public interface WorkflowService {

	public void publishBlog(long id);

	public void rejectBlog(long id);
}

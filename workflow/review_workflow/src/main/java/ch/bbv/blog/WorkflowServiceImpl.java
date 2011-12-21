package ch.bbv.blog;

public class WorkflowServiceImpl implements WorkflowService {

	@Override
	public void publishBlog(long id) {
		System.out.println("publishBlog: " + id);
	}

	@Override
	public void rejectBlog(long id) {
		System.out.println("revokeBlog: " + id);
	}

}

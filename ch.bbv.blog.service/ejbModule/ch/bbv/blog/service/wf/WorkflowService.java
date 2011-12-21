package ch.bbv.blog.service.wf;

import java.util.UUID;

public interface WorkflowService {

	public void publishBlog(UUID id );

	public void rejectBlog(UUID id );
}

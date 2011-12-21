package ch.bbv.blog.service.wf;

import java.util.UUID;

import ch.bbv.blog.service.BlogAdminServiceImpl;

public class WorkflowServiceImpl implements WorkflowService {

	@Override
	public void publishBlog(UUID id) {
		BlogAdminServiceImpl.publishBlog(id);
	}

	@Override
	public void rejectBlog(UUID id ) {
		BlogAdminServiceImpl.rejectBlog(id);
	}

}

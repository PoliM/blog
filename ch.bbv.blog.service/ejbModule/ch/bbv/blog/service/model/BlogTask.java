package ch.bbv.blog.service.model;

import java.util.UUID;

public class BlogTask {

	public enum State {
		Open, Accepted, Closed;
	}

	private State state;

	private UUID id;

	private long workItemId;
	
	private UUID blogEntryId;

	public BlogTask(long workItemId, UUID blogEntryId) {
		id = UUID.randomUUID();
		this.workItemId = workItemId;
		this.blogEntryId = blogEntryId;
		state = State.Open;
	}

	public long getWorkItemId() {
		return workItemId;
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public UUID getId() {
		return id;
	}
}

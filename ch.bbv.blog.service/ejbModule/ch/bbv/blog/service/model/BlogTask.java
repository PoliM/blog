package ch.bbv.blog.service.model;

import java.util.UUID;

public class BlogTask {

	public enum State {
		Open, Accepted, Closed;
	}

	private State state;

	private UUID id;

	private UUID blogEntryId;

	public BlogTask(UUID blogEntryId) {
		id = UUID.randomUUID();
		this.blogEntryId = blogEntryId;
		state = State.Open;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}

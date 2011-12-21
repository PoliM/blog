package ch.bbv.blog.service.model;

import java.io.Serializable;
import java.util.UUID;

public class BlogEntry implements Serializable {

	public enum State {
		Draft,
		ToBeReviewed,
		Published,
		Rejected;
	}

	private static final long serialVersionUID = 1L;

	private UUID id;
	
	private User author;

	private String text;

	private State state;

	public BlogEntry(User author, String text) {
		this.author = author;
		this.text = text;
		this.state = State.Draft;
		this.id = UUID.randomUUID();
	}

	public UUID getId() {
		return id;
	}

	public User getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
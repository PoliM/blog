package ch.bbv.blog.service.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

	private static final long serialVersionUID = -9210230106887969177L;

	private UUID id;

	private String name;

	public User(String name) {
		this.name = name;
		this.id = UUID.randomUUID();
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}

package org.mule.burster;

import java.util.UUID;

public class TaskId {

	private final UUID uuid;

	private TaskId(UUID uuid) {

		this.uuid = uuid;
	}

	public static TaskId create() {
		return new TaskId(UUID.randomUUID());
	}

	public static TaskId of(String uuid) {
		return new TaskId(UUID.fromString(uuid));
	}

	public String value() {
		return uuid.toString();
	}
}

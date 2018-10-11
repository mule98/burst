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

	public static TaskId of(UUID uuid) {
		return new TaskId(uuid);
	}

	public String value() {
		return uuid.toString();
	}

    @Override
    public String toString() {
        return value();
    }
}

package org.mule.burster;

import java.util.Objects;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TaskId taskId = (TaskId) o;
		return Objects.equals(uuid, taskId.uuid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}
}

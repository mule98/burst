package org.mule.burster;

public class TaskDescription {
	private final TaskId taskId;

	public TaskDescription(TaskId taskId) {this.taskId = taskId;}

	public TaskId taskId() {
		return taskId;
	}

    @Override
    public String toString() {
        return "TaskDescription{" + "taskId=" + taskId + '}';
    }
}

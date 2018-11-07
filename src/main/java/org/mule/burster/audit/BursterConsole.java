package org.mule.burster.audit;

import org.mule.burster.TaskDescription;
import org.mule.burster.TaskId;

import java.util.HashMap;
import java.util.logging.Logger;

public class BursterConsole implements BurstListener {

	Logger logger = Logger.getLogger(BursterConsole.class.getCanonicalName());

	private HashMap<TaskId, TaskDescription> queued = new HashMap<>();
	private HashMap<TaskId, TaskDescription> running = new HashMap<>();
	private HashMap<TaskId, TaskDescription> finished = new HashMap<>();

	public int getRunningSize() {
		return running.values().size();
	}

	@Override
	public void taskAppended(TaskDescription description) {
		System.out.println("Queued " + description);
		queued.put(description.taskId(), description);
	}

	@Override
	public void taskFinished(TaskDescription description) {
		System.out.println("Finished " + description);
		TaskDescription element = running.remove(description.taskId());
		if (element == null) {
			element = queued.remove(description.taskId());
		}
		if (element == null) {
			throw new RuntimeException("No started element found:" + description);
		}
		finished.put(description.taskId(), element);
	}

	@Override
	public void taskStarted(TaskDescription description) {
		System.out.println("Started " + description);
		TaskDescription element = queued.remove(description.taskId());
		if (element == null) {
			throw new RuntimeException("No started element found:" + description);
		}
		running.put(description.taskId(), element);
	}

	public int getQueuedSize() {
		return queued.size();
	}

	public int getFinishedSize() {
		return finished.size();
	}
}

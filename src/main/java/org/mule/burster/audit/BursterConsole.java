package org.mule.burster.audit;

import org.mule.burster.TaskDescription;
import org.mule.burster.TaskId;

import java.util.HashMap;
import java.util.logging.Logger;

public class BursterConsole implements BurstListener {

	Logger logger = Logger.getLogger(BursterConsole.class.getCanonicalName());

	private HashMap<TaskId, TaskDescription> queued = new HashMap();
	private HashMap<TaskId, TaskDescription> running = new HashMap();
	private HashMap<TaskId, TaskDescription> finished = new HashMap();

	public int getRunningSize() {
		return running.values().size();
	}

	@Override
	public void taskAppended(TaskDescription description) {
		System.out.println("Queued " + description.taskId());
		queued.put(description.taskId(), description);
	}

	@Override
	public void taskFinished(TaskId id) {
		System.out.println("Finished " + id);
		TaskDescription element = running.remove(id);
		if (element == null) {
			element = queued.remove(id);
		}
		if (element == null) {
			throw new RuntimeException("No started element found:" + id);
		}
		finished.put(id, element);
	}

	@Override
	public void taskStarted(TaskId id) {
		System.out.println("Started " + id);
		TaskDescription element = queued.remove(id);
		if (element == null) {
			throw new RuntimeException("No started element found:" + id);
		}
		running.put(id, element);
	}

	public int getQueuedSize() {
		return queued.size();
	}

	public int getFinishedSize() {
		return finished.size();
	}
}

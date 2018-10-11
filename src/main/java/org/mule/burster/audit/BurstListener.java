package org.mule.burster.audit;

import org.mule.burster.TaskDescription;
import org.mule.burster.TaskId;

public interface BurstListener {

	void taskAppened(TaskDescription description);

	void taskFinished(TaskId id);

	void taskStarted(TaskId id);

}

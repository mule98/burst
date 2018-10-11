package org.mule.burster.audit;

import org.mule.burster.TaskDescription;
import org.mule.burster.TaskId;

public interface BurstListener {

	void taskAppended(TaskDescription description);

	void taskFinished(TaskId id);

	void taskStarted(TaskId id);

}

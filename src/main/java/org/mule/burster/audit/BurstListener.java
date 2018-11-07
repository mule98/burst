package org.mule.burster.audit;

import org.mule.burster.TaskDescription;

public interface BurstListener {

	void taskAppended(TaskDescription description);

	void taskFinished(TaskDescription description);

	void taskStarted(TaskDescription description);

}

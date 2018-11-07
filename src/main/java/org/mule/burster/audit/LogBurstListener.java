package org.mule.burster.audit;

import org.mule.burster.TaskDescription;

public class LogBurstListener implements BurstListener {
    @Override
    public void taskAppended(TaskDescription description) {
        System.out.println(taskPrefix() + " Appended " + description);
    }

    private long taskPrefix() {
        return Thread.currentThread().getId();
    }

    @Override
    public void taskFinished(TaskDescription id) {
        System.out.println(taskPrefix() + " Finished " + id);

    }

    @Override
    public void taskStarted(TaskDescription id) {
        System.out.println(taskPrefix() + " Started " + id);

    }
}

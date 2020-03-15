package dev.alexengrig.intellij.resttime.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import dev.alexengrig.intellij.resttime.ui.RestTimeDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.SECONDS;

@State(name = "MyRestImeService", storages = @Storage("rest-time.xml"))
public class MyRestTimeService implements RestTimeService, PersistentStateComponent<MyRestTimeService.MyState> {
    private static final Logger LOGGER = Logger.getLogger(MyRestTimeService.class.getName());

    private final RestTimeDialog myDialog;
    private final ScheduledExecutorService myExecutor;
    private final FutureTask<?> myTask;

    private MyState myState;

    public MyRestTimeService() {
        this.myDialog = new RestTimeDialog();
        this.myExecutor = Executors.newSingleThreadScheduledExecutor();
        this.myTask = new FutureTask<>(() -> {
            ApplicationManager.getApplication().invokeAndWait(myDialog::show);
            scheduleTask();
        }, 0);
        this.myState = new MyState();
        scheduleTask();
    }

    private void scheduleTask() {
        if (!myTask.isDone()) {
            LOGGER.info("Cancel task.");
            myTask.cancel(true);
        }
        LOGGER.info("Schedule task.");
        myExecutor.schedule(myTask, getWorkTime(), SECONDS);
    }

    @Override
    public int getWorkTime() {
        return myState.workTime;
    }

    @Override
    public void setWorkTime(int time) {
        myState.workTime = time;
    }

    @Nullable
    @Override
    public MyState getState() {
        LOGGER.info("Return state: " + myState);
        return myState;
    }

    @Override
    public void loadState(@NotNull MyState state) {
        LOGGER.info("Get state: " + state);
        myState = state;
    }

    static class MyState {
        int workTime = 45;

        @Override
        public String toString() {
            return "MyState{" +
                    "workTime=" + workTime +
                    '}';
        }
    }
}

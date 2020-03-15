package dev.alexengrig.intellij.resttime.service;

import com.intellij.openapi.application.ApplicationManager;

public interface RestTimeService {
    static RestTimeService getInstance() {
        return ApplicationManager.getApplication().getService(RestTimeService.class);
    }

    int getWorkTime();

    void setWorkTime(int time);
}

package dev.wittek.tc.jakarta.time;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalTime;

@ApplicationScoped
public class MockTimeService implements TimeService {

    private LocalTime fixedTime;

    @PostConstruct
    void init() {
        setTimeToNow();
    }

    public void setTimeToNow() {
        fixedTime = LocalTime.now();
    }

    @Override
    public LocalTime now() {
        return fixedTime;
    }
}

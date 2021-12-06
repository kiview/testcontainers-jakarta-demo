package dev.wittek.tc.jakarta.time;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalTime;

@Produces(MediaType.APPLICATION_JSON)
@Path("time")
public class TimeController {

    @Inject
    private TimeService timeService;

    @GET
    public LocalTime now() {
        return timeService.now();
    }

}

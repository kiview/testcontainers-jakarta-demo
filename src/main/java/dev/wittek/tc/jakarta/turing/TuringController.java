package dev.wittek.tc.jakarta.turing;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Path("turing")
public class TuringController {

    @Inject
    private TuringAwardService turingAwardService;

    @GET
    public String fetchTuringAwardWinner(@DefaultValue("2020") @QueryParam("year") int year) {
        return turingAwardService.fetchTuringAwardWinner(year);
    }

}

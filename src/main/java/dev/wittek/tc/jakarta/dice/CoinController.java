package dev.wittek.tc.jakarta.dice;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Produces(MediaType.APPLICATION_JSON)
@Path("coin")
public class CoinController {

    @GET
    public List<String> throwCoins(@DefaultValue("1") @QueryParam("numberOfThrows") int numberOfThrows) {
        ArrayList<String> results = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < numberOfThrows; i++) {
            String singleThrow = (random.nextInt(2) == 0) ? "head" : "tail";
            results.add(singleThrow);
        }

        return results;
    }


}

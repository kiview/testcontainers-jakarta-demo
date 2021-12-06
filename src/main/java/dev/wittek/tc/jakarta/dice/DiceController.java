package dev.wittek.tc.jakarta.dice;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Random;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("dice")
public class DiceController {

    @POST
    public DiceRollResult rollDice(Dice d) {
        return d.roll();
    }
    
    public static class Dice {

        public int sides;

        private final Random random = new Random();

        public Dice(int sides) {
            this.sides = sides;
        }

        public Dice() {
        }

        public DiceRollResult roll() {

            return new DiceRollResult(sides, random.nextInt(sides) + 1);
        }

    }

    public static class DiceRollResult {
        public int sides;
        public int value;

        public DiceRollResult(int sides, int value) {
            this.sides = sides;
            this.value = value;
        }

        public DiceRollResult() {
        }
    }

}

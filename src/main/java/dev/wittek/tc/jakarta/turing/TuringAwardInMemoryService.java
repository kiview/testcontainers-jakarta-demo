package dev.wittek.tc.jakarta.turing;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class TuringAwardInMemoryService implements TuringAwardService {

    private Map<Integer, String> turingAwardWinner;

    @PostConstruct
    void init() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("turing.csv");
        InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(is));
        BufferedReader bufferedReader = new BufferedReader(isr);
        turingAwardWinner = bufferedReader.lines()
                .map(s -> s.split(","))
                .collect(Collectors.toMap(
                        s -> Integer.parseInt(s[0]),
                        s -> s[1],
                        (winner1, winner2) -> winner1 + ", " + winner2
                ));
    }


    @Override
    public String fetchTuringAwardWinner(int year) {
        return turingAwardWinner.get(year);
    }
}

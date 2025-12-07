import aoc.Day;
import util.Coordinate;
import util.DenseGrid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static util.Direction.DN;
import static util.Direction.LT;
import static util.Direction.RT;

@SuppressWarnings("unused")
public class Day07 extends Day {

    @Override
    protected Long partOne(List<String> input) {
        DenseGrid<Character> grid = DenseGrid.fromInput(input).getFirst();
        Coordinate start = grid.find(c -> c == 'S');

        Set<Coordinate> beams = new HashSet<>();
        beams.add(start.move(DN));
        long splits = 0;

        for (int row = 2; row < input.size(); row++) {
            Set<Coordinate> newBeams = new HashSet<>();

            for (var beam : beams) {
                if (grid.get(beam.move(DN)) == '^') {
                    newBeams.add(beam.move(DN).move(LT));
                    newBeams.add(beam.move(DN).move(RT));
                    ++splits;
                } else {
                    newBeams.add(beam.move(DN));
                }
            }

            beams = newBeams;
        }

        return splits;
    }

    @Override
    protected Long partTwo(List<String> input) {
        DenseGrid<Character> grid = DenseGrid.fromInput(input).getFirst();
        Coordinate start = grid.find(c -> c == 'S');

        Map<Coordinate, Long> beams = new HashMap<>();
        beams.put(start.move(DN), 1L);

        for (int row = 2; row < input.size(); row++) {
            Map<Coordinate, Long> newBeams = new HashMap<>();

            for (var beam : beams.entrySet()) {
                if (grid.get(beam.getKey().move(DN)) == '^') {
                    newBeams.merge(beam.getKey().move(DN).move(LT), beam.getValue(), Long::sum);
                    newBeams.merge(beam.getKey().move(DN).move(RT), beam.getValue(), Long::sum);
                } else {
                    newBeams.merge(beam.getKey().move(DN), beam.getValue(), Long::sum);
                }
            }

            beams = newBeams;
        }

        return beams.values().stream().mapToLong(n -> n).sum();
    }

}

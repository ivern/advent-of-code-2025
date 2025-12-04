import aoc.Day;
import util.Coordinate;
import util.DenseGrid;

import java.util.List;

@SuppressWarnings("unused")
public class Day04 extends Day {

    @Override
    protected Long partOne(List<String> input) {
        DenseGrid<Character> grid = DenseGrid.fromInput(input).getFirst();

        return grid.mapReduce(
                (g, r, c) -> isAccessibleRoll(g, r, c) ? 1L : 0L
                , Long::sum,
                0L);
    }

    boolean isAccessibleRoll(DenseGrid<Character> grid, int row, int col) {
        return isRoll(grid, row, col) && countTrue(grid.mapAllNeighbors(row, col, this::isRoll)) < 4;
    }

    boolean isRoll(DenseGrid<Character> grid, int row, int col) {
        return grid.get(row, col) == '@';
    }

    long countTrue(List<Boolean> list) {
        return list.stream().filter(b -> b == true).count();
    }

    @Override
    protected Long partTwo(List<String> input) {
        DenseGrid<Character> grid = DenseGrid.fromInput(input).getFirst();
        long removed = 0;

        while (true) {
            List<Coordinate> removable = grid.findAll((c) -> isAccessibleRoll(grid, c.row(), c.col()));
            if (removable.isEmpty()) {
                break;
            }

            removed += removable.size();

            for (Coordinate coordinate : removable) {
                grid.put(coordinate, '.');
            }
        }

        return removed;
    }

}

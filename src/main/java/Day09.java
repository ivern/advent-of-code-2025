import aoc.Day;
import util.Coordinate;

import java.util.List;

@SuppressWarnings("unused")
public class Day09 extends Day {

    @Override
    protected Long partOne(List<String> input) {
        List<Coordinate> redTiles = input.stream()
                .map(line -> line.split(","))
                .map(parts -> new Coordinate(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])))
                .toList();

        long maxArea = 0;

        for (int i = 0; i < redTiles.size(); i++) {
            for (int j = i + 1; j < redTiles.size(); j++) {
                long area = area(redTiles, i, j);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }

        return maxArea;
    }

    long area(List<Coordinate> redTiles, int i, int j) {
        long width = Math.abs(redTiles.get(i).col() - redTiles.get(j).col()) + 1;
        long height = Math.abs(redTiles.get(i).row() - redTiles.get(j).row()) + 1;
        return width * height;
    }

}

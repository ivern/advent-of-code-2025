import aoc.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class Day08 extends Day {

    @Override
    protected Long partOne(List<String> input) {
        JunctionBox[] junctionBoxes = new JunctionBox[input.size()];
        for (int i = 0; i < input.size(); i++) {
            junctionBoxes[i] = JunctionBox.fromString(input.get(i));
        }

        List<Connection> possibleConnections = new ArrayList<>();
        for (int i = 0; i < junctionBoxes.length; i++) {
            for (int j = i + 1; j < junctionBoxes.length; j++) {
                possibleConnections.add(new Connection(i, j, junctionBoxes[i].distance(junctionBoxes[j])));
            }
        }
        possibleConnections.sort(Comparator.comparingLong(Connection::distance));

        List<Set<Integer>> circuits = new ArrayList<>();

        int nextConnectionIndex = 0;
        int connectionsMade = 0;
        while (connectionsMade < 1000) {
            var next = possibleConnections.get(nextConnectionIndex);

            int idx1 = -1;
            int idx2 = -1;
            for (int i = 0; i < circuits.size() && (idx1 == -1 || idx2 == -1); ++i) {
                if (circuits.get(i).contains(next.box1)) {
                    idx1 = i;
                } else if (circuits.get(i).contains(next.box2)) {
                    idx2 = i;
                }
            }

            if (idx1 == -1 && idx2 == -1) {
                var newSet = new HashSet<Integer>();
                newSet.add(next.box1);
                newSet.add(next.box2);
                circuits.add(newSet);
                ++connectionsMade;
            } else if (idx1 == -1) {
                circuits.get(idx2).add(next.box1);
                ++connectionsMade;
            } else if (idx2 == -1) {
                circuits.get(idx1).add(next.box2);
                ++connectionsMade;
            } else if (idx1 != idx2) {
                circuits.get(idx1).addAll(circuits.remove(idx2));
                ++connectionsMade;
            }

            ++nextConnectionIndex;
        }

        var circuitSizes = circuits.stream().map(Set::size).sorted(Comparator.reverseOrder()).toList();

        return (long) circuitSizes.get(0) * circuitSizes.get(1) * circuitSizes.get(2);
    }

    record Connection(int box1, int box2, long distance) {
    }

    record JunctionBox(int x, int y, int z) {
        static JunctionBox fromString(String line) {
            String[] parts = line.split(",");
            return new JunctionBox(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        }

        long distance(JunctionBox other) {
            long dx = x - other.x;
            long dy = y - other.y;
            long dz = z - other.z;
            return dx * dx + dy * dy + dz * dz;
        }
    }

    @Override
    protected Long partTwo(List<String> input) {
        JunctionBox[] junctionBoxes = new JunctionBox[input.size()];
        for (int i = 0; i < input.size(); i++) {
            junctionBoxes[i] = JunctionBox.fromString(input.get(i));
        }

        List<Connection> possibleConnections = new ArrayList<>();
        for (int i = 0; i < junctionBoxes.length; i++) {
            for (int j = i + 1; j < junctionBoxes.length; j++) {
                possibleConnections.add(new Connection(i, j, junctionBoxes[i].distance(junctionBoxes[j])));
            }
        }
        possibleConnections.sort(Comparator.comparingLong(Connection::distance));

        List<Set<Integer>> circuits = new ArrayList<>();

        int nextConnectionIndex = 0;
        long result = 0;
        do {
            var next = possibleConnections.get(nextConnectionIndex);

            int idx1 = -1;
            int idx2 = -1;
            for (int i = 0; i < circuits.size() && (idx1 == -1 || idx2 == -1); ++i) {
                if (circuits.get(i).contains(next.box1)) {
                    idx1 = i;
                } else if (circuits.get(i).contains(next.box2)) {
                    idx2 = i;
                }
            }

            boolean connected = false;

            if (idx1 == -1 && idx2 == -1) {
                var newSet = new HashSet<Integer>();
                newSet.add(next.box1);
                newSet.add(next.box2);
                circuits.add(newSet);
                connected = true;
            } else if (idx1 == -1) {
                circuits.get(idx2).add(next.box1);
                connected = true;
            } else if (idx2 == -1) {
                circuits.get(idx1).add(next.box2);
                connected = true;
            } else if (idx1 != idx2) {
                circuits.get(idx1).addAll(circuits.remove(idx2));
                connected = true;
            }

            if (connected) {
                result = (long) junctionBoxes[next.box1].x() * junctionBoxes[next.box2].x();
            }

            ++nextConnectionIndex;
        } while (circuits.stream().mapToInt(Set::size).max().getAsInt() < junctionBoxes.length);

        return result;
    }

}

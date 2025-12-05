import aoc.Day;
import util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Day05 extends Day {

    @Override
    protected Long partOne(List<String> input) {
        List<Pair<Long, Long>> fresh = new ArrayList<>();
        List<Long> available = new ArrayList<>();
        boolean rangesAreDone = false;

        for (var line : input) {
            if (rangesAreDone) {
                available.add(Long.parseLong(line));
            } else if (line.isEmpty()) {
                rangesAreDone = true;
            } else {
                String[] parts = line.split("-");
                fresh.add(new Pair<>(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
            }
        }

        long result = 0;

        for (long id : available) {
            for (var range : fresh) {
                if (id >= range.first() && id <= range.second()) {
                    ++result;
                    break;
                }
            }
        }

        return result;
    }

    @Override
    protected Long partTwo(List<String> input) {
        List<Pair<Long, Long>> fresh = new ArrayList<>();

        for (var line : input) {
            if (line.isEmpty()) {
                break;
            }

            String[] parts = line.split("-");
            fresh.add(new Pair<>(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
        }

        fresh = fresh.stream().sorted(Comparator.comparingLong(Pair::first)).collect(Collectors.toList());

        long result = 0;

        while (!fresh.isEmpty()) {
            var next = fresh.removeFirst();
            while (!fresh.isEmpty() && overlap(next, fresh.getFirst())) {
                next = merge(next, fresh.removeFirst());
            }
            result += next.second() - next.first() + 1;
        }

        return result;
    }

    boolean overlap(Pair<Long, Long> p1, Pair<Long, Long> p2) {
        return contains(p1, p2.first())
                || contains(p1, p2.second())
                || p1.second() + 1 == p2.first()
                || p2.second() + 1 == p1.first();
    }

    boolean contains(Pair<Long, Long> p, long n) {
        return p.first() <= n && n <= p.second();
    }

    Pair<Long, Long> merge(Pair<Long, Long> p1, Pair<Long, Long> p2) {
        return new Pair<>(Math.min(p1.first(), p2.first()), Math.max(p1.second(), p2.second()));
    }

}

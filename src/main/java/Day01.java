import aoc.Day;

import java.util.List;

@SuppressWarnings("unused")
public class Day01 extends Day {

    @Override
    protected Long partOne(List<String> input) {
        int accumulator = 50;
        long result = 0;

        for (var line : input) {
            int clicks = Integer.parseInt(line.substring(1));

            if (line.charAt(0) == 'L') {
                accumulator = mod100(accumulator - clicks);
            } else {
                accumulator = mod100(accumulator + clicks);
            }

            if (accumulator == 0) {
                ++result;
            }
        }

        return result;
    }

    @Override
    protected Long partTwo(List<String> input) {
        int accumulator = 50;
        long result = 0;

        for (var line : input) {
            int clicks = Integer.parseInt(line.substring(1));

            while (clicks-- > 0) {
                if (line.charAt(0) == 'L') {
                    accumulator = mod100(accumulator - 1);
                } else {
                    accumulator = mod100(accumulator + 1);
                }

                if (accumulator == 0) {
                    ++result;
                }
            }
        }

        return result;
    }

    int mod100(int n) {
        if (n >= 0) {
            return n % 100;
        } else {
            while (n < 0) {
                n += 100;
            }
            return n;
        }
    }

}

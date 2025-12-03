import aoc.Day;

import java.util.List;

@SuppressWarnings("unused")
public class Day03 extends Day {

    @Override
    protected Long partOne(List<String> input) {
        long result = 0;

        for (var line : input) {
            long max1 = 0;
            long max2 = 0;
            int pos = 0;
            char[] chars = line.toCharArray();

            for (int i = 0; i < chars.length - 1; i++) {
                int c = Character.getNumericValue(chars[i]);
                if (c > max1) {
                    max1 = c;
                    pos = i;
                }
            }

            for (int i = pos + 1; i < chars.length; i++) {
                int c = Character.getNumericValue(chars[i]);
                if (c > max2) {
                    max2 = c;
                }
            }

            result += max1 * 10 + max2;
        }

        return result;
    }

    @Override
    protected Long partTwo(List<String> input) {
        long result = 0;

        for (var line : input) {
            int left = 12;

            long maxJoltage = 0;
            int pos = -1;
            char[] chars = line.toCharArray();

            while (--left >= 0) {
                long max = 0;

                for (int i = pos + 1; i < chars.length - left; i++) {
                    int c = Character.getNumericValue(chars[i]);
                    if (c > max) {
                        max = c;
                        pos = i;
                    }
                }

                maxJoltage = maxJoltage * 10 + max;
            }

            result += maxJoltage;
        }

        return result;
    }

}

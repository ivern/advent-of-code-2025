import aoc.Day;

import java.util.List;

@SuppressWarnings("unused")
public class Day02 extends Day {

    @Override
    protected Long partOne(List<String> input) {
        String[] parts = input.get(0).split(",");
        long result = 0;

        for (String part : parts) {
            String[] ends = part.split("-");
            long start = Long.parseLong(ends[0]);
            long end = Long.parseLong(ends[1]);

            for (long i = start; i <= end; i++) {
                if (isInvalid(i)) {
                    result += i;
                }
            }
        }

        return result;
    }

    boolean isInvalid(long n) {
        int numDigits = (int) Math.log10(n) + 1;

        if (numDigits % 2 == 1) {
            return false;
        }

        long rightSide = 0;
        long multiplier = 1;
        for (int i = 0; i < numDigits / 2; ++i) {
            rightSide += multiplier * (n % 10);
            multiplier *= 10;
            n /= 10;
        }

        return n == rightSide;
    }

    @Override
    protected Long partTwo(List<String> input) {
        String[] parts = input.get(0).split(",");
        long result = 0;

        for (String part : parts) {
            String[] ends = part.split("-");
            long start = Long.parseLong(ends[0]);
            long end = Long.parseLong(ends[1]);

            for (long i = start; i <= end; i++) {
                if (isInvalid2(i)) {
                    result += i;
                    System.out.println(part + " " + i);
                }
            }
        }

        return result;
    }

    boolean isInvalid2(long n) {
        int numDigits = (int) Math.log10(n) + 1;

        for (int i = 2; i <= numDigits; ++i) {
            if (numDigits % i != 0) {
                continue;
            }

            long length = numDigits / i;
            long sequence = 0;
            long multiplier = 1;
            long num = n;

            for (int j = 0; j < length; ++j) {
                sequence += multiplier * (num % 10);
                multiplier *= 10;
                num /= 10;
            }

            boolean invalid = true;

            for (int j = 0; j < i - 1; ++j) {
                long tail = num % multiplier;
                if (tail != sequence) {
                    invalid = false;
                    break;
                }
                num /= multiplier;
            }

            if (invalid) {
                return true;
            }
        }

        return false;
    }

}

import aoc.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Day06 extends Day {

    @Override
    protected Long partOne(List<String> input) {
        List<List<Long>> numbers = new ArrayList<>();
        List<String> operations = null;

        for (var line : input) {
            var elements =
                    Arrays.stream(line.split(" "))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .toList();

            if (Character.isDigit(elements.get(0).charAt(0))) {
                for (int i = 0; i < elements.size(); i++) {
                    if (numbers.size() <= i) {
                        numbers.add(new ArrayList<>());
                    }
                    numbers.get(i).add(Long.parseLong(elements.get(i)));
                }
            } else {
                operations = elements;
            }
        }

        long total = 0;

        for (int i = 0; i < numbers.size(); i++) {
            long acc = operations.get(i).equals("+") ? 0L : 1L;
            for (int j = 0; j < numbers.get(i).size(); j++) {
                if (operations.get(i).equals("+")) {
                    acc += numbers.get(i).get(j);
                } else {
                    acc *= numbers.get(i).get(j);
                }
            }
            total += acc;
        }

        return total;
    }

    @Override
    protected Long partTwo(List<String> input) {
        List<List<String>> originals = new ArrayList<>();
        List<String> operations = new ArrayList<>();
        int opRow = input.size() - 1;

        int col = 0;
        int equation = -1;
        int num = 0;

        while (true) {
            boolean done = true;

            if (col < input.get(opRow).length() && input.get(opRow).charAt(col) != ' ') {
                operations.add(input.get(opRow).substring(col, col + 1));
                originals.add(new ArrayList<>());
                done = false;
                ++equation;
                num = 0;
            }

            List<String> nums = originals.get(equation);

            for (int j = 0; j < input.size() - 1; j++) {
                if (num >= opRow) {
                    done = false;
                    break;
                }
                if (nums.size() <= j) {
                    nums.add("");
                }
                if (col < input.get(j).length()) {
                    nums.set(num, nums.get(num) + input.get(j).charAt(col));
                    done = false;
                }
            }

            if (col > input.stream().mapToInt(String::length).max().getAsInt()) {
                done = true;
            }

            if (done) {
                break;
            }

            ++col;
            ++num;
        }

        List<List<Long>> numbers = new ArrayList<>();

        for (int j = 0; j < originals.size(); j++) {
            numbers.add(new ArrayList<>());
            for (int k = 0; k < originals.get(j).size(); k++) {
                var s = originals.get(j).get(k).trim();
                if (s.isEmpty()) {
                    if (operations.get(j).equals("+")) {
                        numbers.get(j).add(0L);
                    } else {
                        numbers.get(j).add(1L);
                    }
                } else {
                    numbers.get(j).add(Long.parseLong(s));
                }
            }
        }

        long total = 0;

        for (int i = 0; i < numbers.size(); i++) {
            long acc = operations.get(i).equals("+") ? 0L : 1L;
            for (int j = 0; j < numbers.get(i).size(); j++) {
                if (operations.get(i).equals("+")) {
                    acc += numbers.get(i).get(j);
                } else {
                    acc *= numbers.get(i).get(j);
                }
            }
            total += acc;
        }

        return total;
    }

}

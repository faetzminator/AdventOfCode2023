package ch.faetzminator.aoc2023;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.faetzminator.aocutil.PuzzleUtil;
import ch.faetzminator.aocutil.ScannerUtil;
import ch.faetzminator.aocutil.Timer;

public class Day15b {

    public static void main(final String[] args) {
        final Day15b puzzle = new Day15b();

        final String input = ScannerUtil.readNonBlankLine();
        final Timer timer = PuzzleUtil.start();
        puzzle.parseInput(input);
        final long solution = puzzle.calculateFocusingPower();
        PuzzleUtil.end(solution, timer);
    }

    private static final Pattern LABEL_PATTERN = Pattern.compile("(\\w+)(?:-|=(\\d+))");

    @SuppressWarnings("unchecked")
    private final Map<String, Integer>[] boxes = new Map[256];

    public void parseInput(final String str) {
        for (final String value : str.split(",")) {
            final Matcher matcher = LABEL_PATTERN.matcher(value);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("value: " + value);
            }
            final String key = matcher.group(1);
            final int box = calculateHash(key);
            if (boxes[box] == null) {
                boxes[box] = new LinkedHashMap<>();
            }
            if (value.endsWith("-")) {
                boxes[box].remove(key);
            } else {
                boxes[box].put(key, Integer.parseInt(matcher.group(2)));
            }
        }
    }

    private int calculateHash(final String str) {
        int hash = 0;
        for (final char c : str.toCharArray()) {
            hash += c;
            hash *= 17;
            hash = hash % 256;
        }
        return hash;
    }

    public long calculateFocusingPower() {
        long focusingPower = 0;
        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i] != null) {
                int slot = 1;
                for (final Entry<String, Integer> entry : boxes[i].entrySet()) {
                    focusingPower += (i + 1) * (slot++) * entry.getValue();
                }
            }
        }
        return focusingPower;
    }
}

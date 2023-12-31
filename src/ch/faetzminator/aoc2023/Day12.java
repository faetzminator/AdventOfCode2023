package ch.faetzminator.aoc2023;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.faetzminator.aocutil.PuzzleUtil;
import ch.faetzminator.aocutil.ScannerUtil;
import ch.faetzminator.aocutil.Timer;

public class Day12 {

    public static void main(final String[] args) {
        final Day12 puzzle = new Day12();

        final List<String> lines = ScannerUtil.readNonBlankLines();
        final Timer timer = PuzzleUtil.start();
        for (final String line : lines) {
            puzzle.parseConditionRecord(line);
        }
        final long solution = puzzle.getArrangementSum();
        PuzzleUtil.end(solution, timer);
    }

    private long arrangementSum;

    private final Pattern linePattern = Pattern.compile("([.?#]+) ((\\d+,)+\\d+)");

    public void parseConditionRecord(final String line) {
        final Matcher matcher = linePattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("line: " + line);
        }
        final String record = matcher.group(1);
        final String[] partsStr = matcher.group(2).split(",");
        final int[] parts = new int[partsStr.length];
        for (int i = 0; i < partsStr.length; i++) {
            parts[i] = Integer.parseInt(partsStr[i]);
        }
        arrangementSum += calculateArrangements(record, parts);
    }

    private long calculateArrangements(final String record, final int[] parts) {
        int partsLength = parts.length - 1;
        for (final int part : parts) {
            partsLength += part;
        }
        return calculateArrangements(record, parts, 0, partsLength, 0);
    }

    private boolean substrDoesNotContain(final String record, final int pos, final int len, final char c) {
        for (int i = 0; i < len; i++) {
            final int x = pos + i;
            if (x >= 0 && x < record.length() && record.charAt(x) == c) {
                return false;
            }
        }
        return true;
    }

    private long calculateArrangements(final String record, final int[] parts, final int atPart, final int partsLength,
            final int startIndex) {
        long arrangements = 0;
        final int partsLen = partsLength - parts[atPart] - 1;
        for (int pos = startIndex; pos < record.length() - partsLength + 1; pos++) {
            if (substrDoesNotContain(record, startIndex - 1, pos - startIndex + 1, '#')
                    && substrDoesNotContain(record, pos, parts[atPart], '.')
                    && substrDoesNotContain(record, pos + parts[atPart], 1, '#')) {

                long combo = 1;
                final int nextStartIndex = pos + parts[atPart] + 1;
                if (atPart < parts.length - 1) {
                    combo = calculateArrangements(record, parts, atPart + 1, partsLen, nextStartIndex);
                } else if (!substrDoesNotContain(record, nextStartIndex, record.length() - nextStartIndex, '#')) {
                    combo = 0;
                }
                arrangements += combo;
            }
        }
        return arrangements;
    }

    public long getArrangementSum() {
        return arrangementSum;
    }
}

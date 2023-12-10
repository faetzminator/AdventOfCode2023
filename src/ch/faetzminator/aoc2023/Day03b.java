package ch.faetzminator.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day03b {

    public static void main(String[] args) {
        Day03b puzzle = new Day03b();

        List<String> input = new ArrayList<>();
        try (Scanner scanner = new Scanner(System.in)) {
            String line;
            while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
                input.add(line);
            }
            scanner.close();
        }

        System.out.println("Calculating...");
        for (String line : input) {
            puzzle.addLine(line);
        }
        System.out.println("Solution: " + puzzle.calculatePartsSum());
    }

    private List<Number> numbers = new ArrayList<>();
    private List<Symbol> symbols = new ArrayList<>();
    private int lineNumber = 0;

    public void addLine(String str) {
        int y = lineNumber++, x;
        int number = 0, count = 0;
        for (x = 0; x < str.length(); x++) {
            char c = str.charAt(x);
            if (c >= '0' && c <= '9') {
                if (count == 0) {
                    number = 0;
                }
                count++;
                number = number * 10 + (c - '0');
            } else {
                if (count > 0) {
                    numbers.add(new Number(number, x - count, y, count));
                    count = 0;
                }
                if (c == '*') {
                    symbols.add(new Symbol(x, y));
                }
            }
        }
        if (count > 0) {
            numbers.add(new Number(number, x - count, y, count));
        }
    }

    public long calculatePartsSum() {
        long sum = 0;

        for (Symbol symbol : symbols) {
            List<Number> matchingNumbers = new ArrayList<>();
            for (Number number : numbers) {
                boolean yMatch = number.getY() <= symbol.getY() + 1 && number.getY() >= symbol.getY() - 1;
                boolean xMatch = number.getX() <= symbol.getX() + 1 && number.getEndX() >= symbol.getX() - 1;
                if (yMatch && xMatch) {
                    matchingNumbers.add(number);
                }
            }
            if (matchingNumbers.size() >= 2) {
                long product = 1;
                for (Number number : matchingNumbers) {
                    product *= number.getNumber();
                }
                sum += product;
            }
        }
        return sum;
    }

    class Number {

        final int number;

        final int x;
        final int y;
        final int length;

        public Number(int number, int x, int y, int length) {
            this.number = number;
            this.x = x;
            this.y = y;
            this.length = length;
        }

        public int getNumber() {
            return number;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getEndX() {
            return x + length - 1;
        }
    }

    class Symbol {

        final int x;
        final int y;

        public Symbol(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
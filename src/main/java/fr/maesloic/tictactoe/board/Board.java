package fr.maesloic.tictactoe.board;

import fr.maesloic.tictactoe.entities.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Board {
    private final int size = 4;
    private final char defaultChar = '·';
    private final Player x, o;
    private Player current;
    private char[][] playground;
    private boolean running;

    public Board(final Player x, final Player o) {
        this.x = x;
        this.o = o;
        this.current = x;
        this.playground = new char[this.size][this.size];
        this.fill();
        this.running = true;
    }

    public Board(final String x, final String o) {
        this(new Player('∆', x), new Player('ß', o));
    }

    private void fill() {
        for (int x = 0; x < this.size; x ++)
            for (int y = 0; y < this.size; y++)
                this.playground[x][y] = this.defaultChar;
    }

    public void display() {
        final StringBuilder header = new StringBuilder(" ");
        for (int i = 1; i <= this.size; i++)
            header.append(" ").append(i);
        System.out.println(header.toString());

        for (int x = 0; x < this.size; x++) {
            final StringBuilder line = new StringBuilder("%d".formatted(x + 1));
            for (int y = 0; y < this.size; y++)
                line.append(" ").append(this.placement(x, y));
            System.out.println(line.toString());
        }

        System.out.printf("Current Player: %s (%c)%n", this.current.name(), this.current.icon());
    }

    public void ask(final BufferedReader reader) throws IOException {
        System.out.print("Choose a pos (rc) ");
        final String pos = reader.readLine();

        if (pos.length() != 2) {
            this.ask(reader);
            return;
        }

        final int r = Integer.parseInt(String.valueOf(pos.charAt(0)));
        final int c = Integer.parseInt(String.valueOf(pos.charAt(1)));

        if (!this.place(r - 1, c - 1)) {
            this.ask(reader);
            return;
        }

        if (this.hasWinner()) {
            this.running = false;
            this.display();
            System.out.printf("%s (%c) won!%n", this.current.name(), this.current.icon());
            return;
        }

        this.current = this.current.equals(this.x) ? this.o : this.x;
    }

    public boolean place(final int x, final int y) {
        if (!this.canPlace(x, y))
            return false;
        this.playground[x][y] = this.current.icon();
        return true;
    }

    private boolean canPlace(final int x, final int y) {
        return 0 <= x && x < this.size
                && 0 <= y && y < this.size
                && this.playground[x][y] == this.defaultChar;
    }

    private char placement(int x, int y) {
        return this.playground[x][y];
    }

    private boolean hasWinner() {
        for (int i = 0; i < this.size; i++) {
            if (this.checkRow(i)) return true;
            if (this.checkColumn(i)) return true;
        }

        if (this.checkDiag(1)) return true;
        return this.checkDiag(-1);
    }

    private boolean checkRow(final int x) {
        final char p = this.placement(x, 0);
        if (p == this.defaultChar) return false;

        for (int y = 0; y < this.size; y++)
            if (this.placement(x, y) != p)
                return false;

        return true;
    }

    private boolean checkColumn(final int y) {
        final char p = this.placement(0, y);
        if (p == this.defaultChar) return false;

        for (int x = 0; x < this.size; x++)
            if (this.placement(x, y) != p)
                return false;

        return true;
    }

    private boolean checkDiag(final int multiplier) {
        if (multiplier == 1) {
            final char p = this.placement(0, 0);
            if (p == this.defaultChar) return false;

            for (int i = 0; i < this.size; i++)
                if (this.placement(i, i) != p)
                    return false;

            return true;
        }

        if (multiplier == -1) {
            final char p = this.placement(0, this.size - 1);
            if (p == this.defaultChar) return false;

            for (int i = 0; i < this.size; i++)
                if (this.placement(i, this.size - 1 - i) != p)
                    return false;

            return true;
        }

        return false;
    }

    public Player x() {
        return this.x;
    }

    public Player o() {
        return this.o;
    }

    public Player current() {
        return this.current;
    }

    public boolean running() {
        return this.running;
    }
}

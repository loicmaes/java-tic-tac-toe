package fr.maesloic.tictactoe;

import fr.maesloic.tictactoe.board.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    public static void main(String args[]) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("What's your name player one? ");
        final String p1 = reader.readLine();
        System.out.print("What's your name player two? ");
        final String p2 = reader.readLine();

        final Board board = new Board(p1, p2);

        while (board.running()) {
            board.display();
            board.ask(reader);
        }
    }
}

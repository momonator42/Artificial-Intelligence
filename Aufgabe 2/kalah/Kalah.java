package kalah;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Hauptprogramm für KalahMuster.
 *
 * @since 29.3.2021
 * @author oliverbittel
 */
public class Kalah {

    private static final String ANSI_BLUE = "\u001B[34m";

    /**
     *
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {
        //testExample();
        //testHHGame();
        //testMiniMaxAndAlphaBetaWithGivenBoard();
        //testHumanMiniMax();
        //testHumanMiniMaxAndAlphaBeta();
        playAgainstAI();
    }

    /**
     * Beispiel von https://de.wikipedia.org/wiki/Kalaha
     */
    public static void testExample() {
        KalahBoard kalahBd = new KalahBoard(new int[]{5, 3, 2, 1, 2, 0, 0, 4, 3, 0, 1, 2, 2, 0}, 'B');
        kalahBd.print();

        System.out.println("B spielt Mulde 11");
        kalahBd.move(11);
        kalahBd.print();

        System.out.println("B darf nochmals ziehen und spielt Mulde 7");
        kalahBd.move(7);
        kalahBd.print();
    }


    public static void playAgainstAI() {
        KalahBoard kalahBd = new KalahBoard();
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action;
            if (kalahBd.getCurPlayer() == 'A') {
                action = miniMaxDecision(kalahBd); //AI
                System.out.println("KI hat seinen Zug: " + action);
            }
            else {
                action = kalahBd.readAction(); //HUMAN
                System.out.println("Mensch hat seinen Zug: " + action);
            }
            kalahBd.move(action);
            kalahBd.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
        if (kalahBd.getAKalah() > kalahBd.getBKalah()) {
            System.out.println("KI hat gewonnen");
        }
        else {
            System.out.println("Du hast gewonnen");
        }
    }
    /**
     * Mensch gegen Mensch
     */
    public static void testHHGame() {
        KalahBoard kalahBd = new KalahBoard();
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action = kalahBd.readAction();
            kalahBd.move(action);
            kalahBd.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }

    public static void testMiniMaxAndAlphaBetaWithGivenBoard() {
        KalahBoard kalahBd = new KalahBoard(new int[]{2, 0, 4, 3, 2, 0, 0, 1, 0, 1, 3, 2, 1, 0}, 'A');
        // A ist am Zug und kann aufgrund von Bonuszügen 8-mal hintereinander ziehen!
        // A muss deutlich gewinnen!
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action;
            if (kalahBd.getCurPlayer() == 'A') {
                action = miniMaxDecision(kalahBd);
                System.out.println("Action of AI: " + action);
            } else {
                action = kalahBd.readAction();
                System.out.println("Action of Human: " + action);
            }
            kalahBd.move(action);
            kalahBd.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }

    private static int miniMaxDecision(KalahBoard board) {
        int bestAction = 0;
        int bestValue = Integer.MIN_VALUE;

        List<KalahBoard> possibleActions = board.possibleActions();
        Collections.shuffle(possibleActions); // Zufällige Reihenfolge, um gleiche Werte zu durchmischen
        possibleActions.sort(Comparator.comparingInt(a -> -evaluate(a))); // Sortieren absteigend basierend auf Bewertung

        for (KalahBoard action : board.possibleActions()) {
            int value = minValue(action, Integer.MIN_VALUE, Integer.MAX_VALUE, 5); // Depth limit of 5
            if (value > bestValue) {
                bestValue = value;
                bestAction = action.getLastPlay();
            }
        }
        return bestAction;
    }

    private static int maxValue(KalahBoard board, int alpha, int beta, int depth) {
        if (depth == 0 || board.isFinished()) {
            return evaluate(board);
        }
        int value = Integer.MIN_VALUE;
        for (KalahBoard action : board.possibleActions()) {
            value = Math.max(value, minValue(action, alpha, beta, depth - 1));
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    private static int minValue(KalahBoard board, int alpha, int beta, int depth) {
        if (depth == 0 || board.isFinished()) {
            return evaluate(board);
        }
        int value = Integer.MAX_VALUE;
        for (KalahBoard action : board.possibleActions()) {
            value = Math.min(value, maxValue(action, alpha, beta, depth - 1));
            if (value <= alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value;
    }

    public static int evaluate(KalahBoard board) {
        int scoreA = board.getAKalah();
        int scoreB = board.getBKalah();
        return scoreA - scoreB;
    }
}

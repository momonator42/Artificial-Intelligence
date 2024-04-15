package kalah;

import kalah.impl.MiniMaxAlphaBeta;
import kalah.impl.MiniMaxAlphaWithHeuristic;
import kalah.impl.MiniMaxNormal;

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
        testMiniMaxAndAlphaBetaWithGivenBoard();
        //testHumanMiniMax();
        //testHumanMiniMaxAndAlphaBeta();
        //playAgainstAI();
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
        MinMax maxAlphaWithHeuristic = new MiniMaxAlphaWithHeuristic();
        KalahBoard kalahBd = new KalahBoard();
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action;
            if (kalahBd.getCurPlayer() == 'A') {
                action = maxAlphaWithHeuristic.miniMaxDecision(kalahBd); //AI
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
                MinMax maxAlphaWithHeuristic = new MiniMaxAlphaWithHeuristic();
                MinMax MinMaxAlphaBeta = new MiniMaxAlphaBeta();
                MinMax MinMaxNormal = new MiniMaxNormal();
                action = maxAlphaWithHeuristic.miniMaxDecision(kalahBd);
                action = MinMaxAlphaBeta.miniMaxDecision(kalahBd);
                action = MinMaxNormal.miniMaxDecision(kalahBd);
                System.out.println("Action of AI: " + action);
                System.out.println("MinMaxNormal: " + MinMaxNormal.getCounts());
                System.out.println("MinMaxAlphaBeta: " + MinMaxAlphaBeta.getCounts());
                System.out.println("maxAlphaWithHeuristic: " + maxAlphaWithHeuristic.getCounts());
            } else {
                action = kalahBd.readAction();
                System.out.println("Action of Human: " + action);
            }
            kalahBd.move(action);
            kalahBd.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }
}

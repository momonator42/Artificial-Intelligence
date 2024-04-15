package kalah.impl;

import kalah.Kalah;
import kalah.KalahBoard;
import kalah.MinMax;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MiniMaxAlphaWithHeuristic implements MinMax {

    private static final int MAX_DEPTH = 6;
    private static int counts = 0;


    @Override
    public int miniMaxDecision(KalahBoard board) {
        int bestAction = 0;
        int bestValue = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (KalahBoard action : board.possibleActions()) {
            int value;
            if (action.isBonus()) {
                counts++;
                value = maxValue(action, alpha, beta, MAX_DEPTH);
            } else {
                counts++;
                value = minValue(action, alpha, beta, MAX_DEPTH - 1);
            }
            if (value > bestValue) {
                bestValue = value;
                bestAction = action.getLastPlay();
            }
            alpha = Math.max(alpha, value);
        }
        return bestAction;
    }

    private int maxValue(KalahBoard board, int alpha, int beta, int depth) {
        if (depth == 0 || board.isFinished()) {
            return evaluate(board);
        }
        int value = Integer.MIN_VALUE;


        List<KalahBoard> possibleActions = board.possibleActions();
        Collections.shuffle(possibleActions);
        possibleActions.sort(Comparator.comparingInt(a -> -evaluate(a)));

        for (KalahBoard action : possibleActions) {
            if (action.isBonus()) {
                counts++;
                value = Math.max(value, maxValue(action, alpha, beta, depth));
            } else {
                counts++;
                value = Math.max(value, minValue(action, alpha, beta, depth - 1));
            }
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    private int minValue(KalahBoard board, int alpha, int beta, int depth) {
        if (depth == 0 || board.isFinished()) {
            return evaluate(board);
        }
        int value = Integer.MAX_VALUE;

        List<KalahBoard> possibleActions = board.possibleActions();
        Collections.shuffle(possibleActions);
        possibleActions.sort(Comparator.comparingInt(MiniMaxAlphaWithHeuristic::evaluate));

        for (KalahBoard action : board.possibleActions()) {
            if (action.isBonus()) {
                counts++;
                value = Math.min(value, minValue(action, alpha, beta, depth));
            } else {
                counts++;
                value = Math.min(value, maxValue(action, alpha, beta, depth - 1));
            }
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

    @Override
    public int getCounts() {
        return counts;
    }
}

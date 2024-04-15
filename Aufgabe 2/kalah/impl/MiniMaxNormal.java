package kalah.impl;

import kalah.KalahBoard;
import kalah.MinMax;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MiniMaxNormal implements MinMax {

    private static final int MAX_DEPTH = 6;
    private static int counts = 0;


    @Override
    public int miniMaxDecision(KalahBoard board) {
        int bestAction = 0;
        int bestValue = Integer.MIN_VALUE;

        for (KalahBoard action : board.possibleActions()) {
            int value;
            if (action.isBonus()) {
                counts++;
                value = maxValue(action, MAX_DEPTH);
            } else {
                counts++;
                value = minValue(action, MAX_DEPTH - 1);
            }
            if (value > bestValue) {
                bestValue = value;
                bestAction = action.getLastPlay();
            }
        }
        return bestAction;
    }

    @Override
    public int getCounts() {
        return counts;
    }

    private int maxValue(KalahBoard board, int depth) {
        if (depth == 0 || board.isFinished()) {
            return evaluate(board);
        }
        int value = Integer.MIN_VALUE;


        for (KalahBoard action : board.possibleActions()) {
            if (action.isBonus()) {
                counts++;
                value = Math.max(value, maxValue(action, depth));
            } else {
                counts++;
                value = Math.max(value, minValue(action, depth - 1));
            }
        }
        return value;
    }

    private int minValue(KalahBoard board, int depth) {
        if (depth == 0 || board.isFinished()) {
            return evaluate(board);
        }
        int value = Integer.MAX_VALUE;

        for (KalahBoard action : board.possibleActions()) {
            if (action.isBonus()) {
                counts++;
                value = Math.min(value, minValue(action, depth));
            } else {
                counts++;
                value = Math.min(value, maxValue(action, depth - 1));
            }
        }
        return value;
    }

    public static int evaluate(KalahBoard board) {
        int scoreA = board.getAKalah();
        int scoreB = board.getBKalah();
        return scoreA - scoreB;
    }
}

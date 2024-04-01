package puzzle8;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Klasse IDFS für iterative deepening depth-first search
 * @author Ihr Name
 */
public class IDFS {

	private static final int MAX_DEPTH = 30;


	private static Deque<Board> dfs(Board curBoard, Deque<Board> path, int limit) {
		HashSet<Board> visited = new HashSet<>();
		visited.add(curBoard);

		if (limit == 0) {
			return null; // Maximaltiefe erreicht, Abbruch
		}

		if (curBoard.isSolved()) {
			return path; // Lösung gefunden
		}

		for (Board nextBoard : curBoard.possibleActions()) {
			if (!visited.contains(nextBoard)) {
				path.addLast(nextBoard);
				Deque<Board> result = dfs(nextBoard, path, limit - 1);
				if (result != null) {
					return result; // Lösung gefunden
				}
				path.removeLast(); // Backtracking
			}
		}

		return null; // Keine Lösung gefunden
	}
	
	private static Deque<Board> idfs(Board curBoard, Deque<Board> path) {
		for (int limit = 0; limit < MAX_DEPTH; limit++) {
			Deque<Board> result = dfs(curBoard, path, limit);
			if (result != null) {
				return result; // Lösung gefunden
			}
		}
		return null;
	}
	
	public static Deque<Board> idfs(Board curBoard) {
		Deque<Board> path = new LinkedList<>();
		path.addLast(curBoard);
		return idfs(curBoard, path);
	}
}

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
		if (curBoard.isSolved()) {
			return path;
		} else if (limit == 0) {
			// System.out.println("cutOff");
			return null;
		} else {
			boolean cutOffOccurred = false;
			for (Board child : curBoard.possibleActions()) {
				if (path.contains(child)) {
					continue;
				}
				path.add(child);
				Deque<Board> result = dfs(child, path, limit - 1);
				if (result == null) {
					cutOffOccurred = true;
				} else {
					return result;
				}
				path.removeLast();
			}
			if (cutOffOccurred) {
				// System.out.println("cutOff");
				return null;
			} else {
				// System.out.println("failure");
				return null; // Keine Lösung gefunden
			}
		}
	}
	
	private static Deque<Board> idfs(Board curBoard, Deque<Board> path) {
		for (int limit = 5; limit < MAX_DEPTH; limit++) {
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

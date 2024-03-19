package puzzle8;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Ihr Name
 */
public class A_Star {
	// cost ordnet jedem Board die Aktuellen Pfadkosten (g-Wert) zu.
	// pred ordnet jedem Board den Elternknoten zu. (siehe Skript S. 2-25). 
	// In cost und pred sind genau alle Knoten der closedList und openList enthalten!
	// Nachdem der Zielknoten erreicht wurde, lässt sich aus cost und pred der Ergebnispfad ermitteln.
	private static HashMap<Board,Integer> cost = new HashMap<>();
	private static HashMap<Board,Board> pred = new HashMap<>();
	
	// openList als Prioritätsliste.
	// Die Prioritätswerte sind die geschätzen Kosten f = g + h (s. Skript S. 2-66)
	private static IndexMinPQ<Board, Integer> openList = new IndexMinPQ<>();
	
	public static Deque<Board> aStar(Board startBoard) {
		cost.clear();
		pred.clear();
		openList.clear();

		cost.put(startBoard, 0);
		openList.add(startBoard, startBoard.h2()); // f = g + h, g = 0 for start node
		pred.put(startBoard, null);

		while (!openList.isEmpty()) {
			Board current = openList.removeMin(); // Get node with lowest f value

			if (current.isSolved()) {
				return reconstructPath(current);
			}

			for (Board next : current.possibleActions()) {
				int newCost = cost.get(current) + 1; // Assuming each move has a cost of 1
				if (!cost.containsKey(next) || newCost < cost.get(next)) {
					cost.put(next, newCost);
					int fValue = newCost + next.h2(); // f = g + h
					if (openList.get(next) == null) {
						openList.add(next, fValue);
					} else {
						openList.change(next, fValue);
					}
					pred.put(next, current);
				}
			}
		}

		return null; // No solution found
	}

	private static Deque<Board> reconstructPath(Board goal) {
		Deque<Board> path = new LinkedList<>();
		while (goal != null) {
			path.addFirst(goal);
			goal = pred.get(goal);
		}
		return path;
	}
}

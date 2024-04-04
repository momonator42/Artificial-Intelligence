package puzzle8;

import java.util.Deque;

/**
 * Hauptprogramm für 8-Puzzle-Problem.
 * @author Muhammed Ergül
 */
public class Puzzle8 {
	
	public static void main(String[] args) {
		Board b = new Board(new int[]{7,2,4,5,0,6,8,3,1}); // Loesbares Puzzle b zufällig generieren.

		System.out.println(b);

		Deque<Board> res = A_Star.aStar(b);
		int n = res == null ? -1 : res.size();
		System.out.println("Anz.Zuege: " + n + ": " + res);

		res = IDFS.idfs(b);
		n = res == null ? -1 : res.size();
		System.out.println("Anz.Zuege: " + n + ": " + res);
	}
}

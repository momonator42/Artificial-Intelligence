package puzzle8;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

/**
 * Klasse Board für 8-Puzzle-Problem
 * @author Ihr Name
 */
public class Board {

	private static final int[] GOAL_STATE = {0, 1, 2, 3, 4, 5, 6, 7, 8};
	/**
	 * Problmegröße
	 */
	public static final int N = 8;

	/**
	 * Board als Feld. 
	 * Gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 * 0 bedeutet leeres Feld.
	 */
	protected int[] board = new int[N+1];

	/**
	 * Generiert ein zufälliges Board.
	 */
	public Board() {
		shuffleBoard();
	}

	private void shuffleBoard() {
		List<Integer> numbers = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			numbers.add(i + 1);
		}

		Random rand = new Random();
		int index = 0;
		while (!numbers.isEmpty()) {
			int randomIndex = rand.nextInt(numbers.size());
			board[index++] = numbers.remove(randomIndex);
		}
	}
	
	/**
	 * Generiert ein Board und initialisiert es mit board.
	 * @param board Feld gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 */
	public Board(int[] board) {
		this.board = board;
	}

	@Override
	public String toString() {
		return "Puzzle{" + "board=" + Arrays.toString(board) + '}';
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Board other = (Board) obj;
		return Arrays.equals(this.board, other.board);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Arrays.hashCode(this.board);
		return hash;
	}
	
	/**
	 * Paritätsprüfung.
	 * @return Parität.
	 */
	public boolean parity() {

		int inversions = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = i + 1; j < board.length; j++) {
				if (board[i] != 0 && board[j] != 0 && board[i] > board[j]) {
					inversions++;
				}
			}
		}


		return inversions % 2 == 0;
	}
	
	/**
	 * Heurstik h1. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h1() {
		int misplacedCount = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] != GOAL_STATE[i] && board[i] != 0 && GOAL_STATE[i] != 0) {
				misplacedCount++;
			}
		}
		return misplacedCount;
	}
	
	/**
	 * Heurstik h2. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h2() {
		int manhattanDistance = 0;
		int dimension = (int) Math.sqrt(board.length);

		for (int i = 0; i < board.length; i++) {
			if (board[i] != 0) { // Ignoriere den leeren Platz
				int currentRow = i / dimension;
				int currentCol = i % dimension;

				// Finde die Position des aktuellen Steins im Zielzustand
				int goalIndex = findIndex(board[i]);
				int goalRow = goalIndex / dimension;
				int goalCol = goalIndex % dimension;

				// Berechne die Manhattan-Distanz für den aktuellen Stein
				int distance = Math.abs(currentRow - goalRow) + Math.abs(currentCol - goalCol);
				manhattanDistance += distance;
			}
		}

		return manhattanDistance;
	}

	private static int findIndex(int element) {
		for (int i = 0; i < Board.GOAL_STATE.length; i++) {
			if (Board.GOAL_STATE[i] == element) {
				return i;
			}
		}
		return -1; // Element nicht gefunden
	}
	
	/**
	 * Liefert eine Liste der möglichen Aktion als Liste von Folge-Boards zurück.
	 * @return Folge-Boards.
	 */
	public List<Board> possibleActions() {
		List<Board> boardList = new LinkedList<>();
		int emptyIndex = -1;

		// Finde den Index des leeren Feldes
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 0) {
				emptyIndex = i;
				break;
			}
		}

		// Mögliche Aktionen: Bewegen des leeren Feldes nach oben, unten, links oder rechts, wenn möglich
		int dimension = (int) Math.sqrt(board.length);
		int emptyRow = emptyIndex / dimension;
		int emptyCol = emptyIndex % dimension;

		// Bewegung nach oben
		if (emptyRow > 0) {
			int[] newBoard = Arrays.copyOf(board, board.length);
			swap(newBoard, emptyIndex, emptyIndex - dimension);
			boardList.add(new Board(newBoard));
		}

		// Bewegung nach unten
		if (emptyRow < dimension - 1) {
			int[] newBoard = Arrays.copyOf(board, board.length);
			swap(newBoard, emptyIndex, emptyIndex + dimension);
			boardList.add(new Board(newBoard));
		}

		// Bewegung nach links
		if (emptyCol > 0) {
			int[] newBoard = Arrays.copyOf(board, board.length);
			swap(newBoard, emptyIndex, emptyIndex - 1);
			boardList.add(new Board(newBoard));
		}

		// Bewegung nach rechts
		if (emptyCol < dimension - 1) {
			int[] newBoard = Arrays.copyOf(board, board.length);
			swap(newBoard, emptyIndex, emptyIndex + 1);
			boardList.add(new Board(newBoard));
		}

		return boardList;
	}

	private void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	
	/**
	 * Prüft, ob das Board ein Zielzustand ist.
	 * @return true, falls Board Ziestzustand (d.h. 0,1,2,3,4,5,6,7,8)
	 */
	public boolean isSolved() {
		for (int i = 0; i < board.length; i++) {
			if (board[i] != i) {
				return false;
			}
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		Board b = new Board(new int[]{7,2,4,5,0,6,8,3,1});		// abc aus Aufgabenblatt
		Board goal = new Board(new int[]{0,1,2,3,4,5,6,7,8});
				
		System.out.println(b);
		System.out.println(b.parity());
		System.out.println(b.h1());
		System.out.println(b.h2());
		
		for (Board child : b.possibleActions())
			System.out.println(child);
		
		System.out.println(goal.isSolved());
	}
}
	

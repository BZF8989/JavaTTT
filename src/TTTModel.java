import java.util.Arrays;
import java.util.Objects;

public class TTTModel implements Cloneable {

	private final static int BOARD_LEN = 9;

	public final int[] score;
	public final Player[] board;
	public final Player movesFirst;
	public Player whosMove;
	public Win win;
	public Player winner;
	public final Boolean[] readyForNextRound;

	public TTTModel() {
		this(Player.X, new int[Player.values().length]);
	}

	public TTTModel(Player firstMove, int[] aScore) {
		score = aScore;
		board = new Player[BOARD_LEN];
		movesFirst = firstMove;
		whosMove = firstMove;
		win = null;
		winner = null;
		readyForNextRound = new Boolean[Player.values().length];
	}

	private TTTModel(TTTModel other) {
		this.score = Arrays.copyOf(other.score, other.score.length);
		this.board = Arrays.copyOf(other.board, other.board.length);
		this.movesFirst = other.movesFirst;
		this.whosMove = other.whosMove;
		this.win = other.win;
		this.winner = other.winner;

		this.readyForNextRound = Arrays.copyOf(
			other.readyForNextRound, other.readyForNextRound.length);
	}

	public String toString() {
		return new StringBuilder()
			.append(this.getClass().getName())
			.append(":{")
			.append(" score:").append(Arrays.toString(score))
			.append(" board:").append(Arrays.toString(board))
			.append(" movesfirst:").append(movesFirst)
			.append(" whosMove:").append(whosMove)
			.append(" win:").append(win)
			.append(" winner:").append(winner)
			.append(" readyForNextRound:").append(Arrays.toString(readyForNextRound))
			.append(" }")
			.toString();
	}

	public TTTModel clone() {
		return new TTTModel(this);
	}

	public boolean equals(Object o) {
		return o instanceof TTTModel && equals((TTTModel) o);
	}

	private boolean equals(TTTModel o) {
		return (this == o)
			|| (Arrays.equals(this.score, o.score)
			&& Arrays.equals(this.board, o.board)
			&& this.movesFirst == o.movesFirst
			&& this.whosMove == o.whosMove
			&& this.win == o.win
			&& this.winner == o.winner
			&& Arrays.equals(this.readyForNextRound, o.readyForNextRound));
	}

	public int hashCode() {
		return Objects.hash(
			score,
			board,
			movesFirst,
			whosMove,
			win,
			winner,
			readyForNextRound);
	}
}

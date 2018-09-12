import java.beans.PropertyChangeListener;

public interface TTTPresenter {

	/** A player takes a square. Ignores illegal moves. Detemines if the round
		is over, sets fields appropriately, updates score. Always fires an event.*/
	public void move(Player p, int pos);

	/** Gets the score for all players over all rounds. */
	public int[] getScore();
	/** Gets the current play board. */
	public Player[] getBoard();
	/** Returns the player that should move this turn; null if round is over.*/
	public Player getWhosMove();

	/** Gets the winning scenario of the current round; null if no winner. */
	public Win getWin();
	/** Gets the winner of the current round; null if there is no winner. */
	public Player getWinner();
	/** Gets if the round is over. */
	public boolean isRoundOver();
	/** Gets if the round is a draw. */
	public boolean isDraw();

	/** A player indicates that they are ready for the next round.
		When all players are ready, the next round begins.
		Fires an event when next round begins.*/
	public void nextRound(Player p);

	/** Adds a listener to receive game state changes.
		Fires an event on add.
		The "source" on fired events should be this TTTPresenter
		The "property name" of fired events should be "TTTPresenter"
	*/
	public void addListener(PropertyChangeListener l);

	/** A String representation of this object. */
	public String toString();
}

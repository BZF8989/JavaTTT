import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class TTTPresenterImpl implements TTTPresenter {

	private final static Logger LOG = Logger.getLogger(TTTPresenterImpl.class.getName());

	private final static String PROPERTY_NAME = TTTPresenter.class.getName();
	private final PropertyChangeSupport listeners;
	private TTTModel model;

	public TTTPresenterImpl() {
		listeners = new PropertyChangeSupport(this);
		model = new TTTModel(Player.X, new int[2]);
	}

	/** A player takes a square. Ignores illegal moves. Detemines if the round
		is over, sets fields appropriately, updates score. Fires an event.*/
	public void move(Player p, int pos) {
	    if(getWhosMove() == p) {
            if (model.board[pos] == null) {
                model.board[pos] = p;
                if(isRoundOver()){
                    if(!isDraw()) {
                        if (p == Player.X) {
                            getScore()[0]++;
                        } else {
                            getScore()[1]++;
                        }
                        model.winner = p;
                        model.win = getWin();
                    }
				}else {
                    model.whosMove = p.getNext();
                }

                //fire event if move is valid
                fireEvent();
            }
            //LOG.info("board at position "+pos+" -- "+model.board[pos]);//DEBUGGING
        }
	}

	/** Gets the score for all players over all rounds. */
	public int[] getScore() {

		return model.score;
	}

	/** Gets the current play board. */
	public Player[] getBoard() {
	    return model.board;
	}

	/** Returns the player that should move this turn; null if round is over.*/
	public Player getWhosMove() {
		return model.whosMove;
	}

	/** Gets the winning scenario of the current round; null if no winner. */
	public Win getWin() {
	    return Arrays.stream(Win.values()).filter(w -> w.isWin(model.board)).findFirst().orElse(null);
	}

	/** Gets the winner of the current round; null if there is no winner. */
	public Player getWinner() {
		return getWin() != null ? getWin().getWinner(model.board): null;
	}

	/** Gets if the round is over. */
	public boolean isRoundOver() {
		return getWinner() != null || isDraw();
	}
	
	/** Gets if the round is a draw. */
	public boolean isDraw() {
            return Arrays.stream(model.board).allMatch(player -> (player != null)) && getWin()==null;
	}

	/** A player indicates that they are ready for the next round.
		When all players are ready, the next round begins.
		Fires an event when next round begins.*/
	public void nextRound(Player p) {
        if(p.equals(Player.X)){
            model.readyForNextRound[0] = true;
            //LOG.info(p.toString() + " is ready");
        }
        if(p.equals(Player.O)){
            model.readyForNextRound[1] = true;
			//LOG.info(p.toString() + " is ready");
        }
		if(Arrays.stream(model.readyForNextRound).allMatch(i -> i)) {
			TTTModel modeltemp = model;
			if (isDraw()) {
				model = new TTTModel(modeltemp.movesFirst.getNext(), getScore());
			} else {
				model = new TTTModel(getWinner().getNext(), getScore());
			}

			fireEvent();
		}

	}

	/** Adds a listener to receive game state changes.
		Fires an event on add.
		The "source" on fired events should be this TTTPresenter
		The "property name" of fired events should be "TTTPresenter"
	*/
	public void addListener(PropertyChangeListener l) {
        listeners.addPropertyChangeListener(l);
        fireEvent();

	}

	private void fireEvent() {
		listeners.firePropertyChange(PROPERTY_NAME, null, this);
	}


	/** A String representation of this object. */
	public String toString() {
		return this.getClass().getName() + ':' + Objects.toString(model);
	}
}

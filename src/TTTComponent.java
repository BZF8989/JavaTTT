import java.awt.*;
import java.util.Objects;
import java.util.logging.Logger;
import javax.swing.*;

@SuppressWarnings("serial")
public class TTTComponent extends JComponent {

    private final static Logger LOG = Logger.getLogger(TTTPresenterImpl.class.getName());


	private final TTTPresenter game;
	private final Player me;

	private final static int BOARD_SIZE= 9;





	public TTTComponent(TTTPresenter game, Player player) {
		this.game = Objects.requireNonNull(game, "game presenter must be non-null");
		this.me = Objects.requireNonNull(player, "player must be non-null");

        drawFrame();
    }

    private void drawFrame(){
        setLayout(new BorderLayout());
        JLabel topText = new JLabel();
        topText.setPreferredSize(new Dimension(400/3, 20));
        topText.setLayout(new GridLayout(0, 3));
        topText.add(new Label("You are " + me.toString()));
        JLabel move = new JLabel ();
        game.addListener(pce->{
            move.setText(game.getWhosMove().toString() + "'s move");
        });
        topText.add(move);

        JLabel score = new JLabel();
        game.addListener(pce->{
            score.setText("Score X: "+game.getScore()[0] + " O: " + game.getScore()[1]);
        });
        topText.add(score);
        add(topText, BorderLayout.PAGE_START);

        JPanel p = new JPanel();

        p.setLayout(new GridLayout(3,3));
        p.setPreferredSize(new Dimension(400, 400-20));
        JButton [] buttons = new JButton[BOARD_SIZE];

        for(int i =0; i< BOARD_SIZE; i++){
            final int f = i;
            final JButton b = new JButton();
            game.addListener(pce->{
                String s;
                if(game.getBoard() == null || game.getBoard()[f] == null){
                    s = null;
                    b.setEnabled(true);
                }else {
                    s = game.getBoard()[f].toString();
                    b.setEnabled(false);
//                    if(s.equals(Player.X.toString())){
//                        b.setBackground(Color.RED);
//                    }else{
//                        b.setBackground(Color.BLUE);
//                    }
                }
                b.setText(s);
            });
            buttons[i] = b;
            buttons[i].addActionListener(ae->{
                game.move(me, f);
                //LOG.info(f+" clicked by player: "+me.toString());//DEBUGGING
            });

            p.add(buttons[i]);
        }


        add(p, BorderLayout.CENTER);

        game.addListener(pce->{
            if(game.isRoundOver()) {
                if (game.isDraw()) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Draw");
                        game.nextRound(me);
                    });

                } else {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, game.getWinner().toString() + " won!");
                        game.nextRound(me);
                    });
                }
            }
        });



    }


}

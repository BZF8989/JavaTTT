import java.awt.Dimension;
import javax.swing.JFrame;


public class TTT {
	public static void main(String[] args) {
		TTTPresenter p = new TTTPresenterImpl();
		for(Player player : Player.values()) {
			JFrame f = new JFrame();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setContentPane(new TTTComponent(p, player));
			f.setMinimumSize(new Dimension(400,400));
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setVisible(true);
		}
	}
}

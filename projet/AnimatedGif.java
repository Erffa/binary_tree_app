package projet;

// importations pour actions et éléments graphiques
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimatedGif extends JPanel {
	/* panel sur lequel on place un gif */

	private ImageIcon imageIcon;

	public AnimatedGif(String gifName) {
		/* constructeur */
		imageIcon = new ImageIcon(getClass().getResource( gifName ));
		// redessine l'image toutes les 40 ms
		Timer timer = new Timer(40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		timer.start();
	}

	public void setGIF(String gifName) {
		/* setter de l'image sensiblement similaire au constructeur */
		imageIcon = new ImageIcon(getClass().getResource( gifName ));
		Timer timer = new Timer(40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		timer.start();
	}

	public String getGIF() {
		/* getter du nom de l'image */
		return imageIcon.getDescription();
	}

	@Override
	protected void paintComponent(Graphics g) {
		/* pour dessiner l'image sur le panel */
		super.paintComponent(g);
		int x = (getWidth() - imageIcon.getIconWidth()) / 2;
		int y = (getHeight() - imageIcon.getIconHeight()) / 2;
		imageIcon.paintIcon(this, g, x, y);
	}
}//line:52
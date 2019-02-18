package projet;

// importation bibli graphique
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class TitlePanel extends JPanel {
	/* construit un titre de section pour MyApp */

	private JLabel label; // où on affiche le titre
	private String title; // le titre
	private Color color; // couleur de fond pour le titre

	private static final Color DEFAULT_COLOR = Color.WHITE; // couleur par défaut

	/* les constructeurs */
	public TitlePanel(String title, Color color) {
		super();
		label = new JLabel(
			"<html>"+
			    "<body>"+
			    "<u><b>" + title + "</b></u>"+
			    "</body>"+
			"</html>", SwingConstants.CENTER);

		this.add(label);
		this.setBackground(color);
	}
	public TitlePanel(String title) {
		this(title, DEFAULT_COLOR);
	}
	public TitlePanel() {
		this("", DEFAULT_COLOR);
	}
}//line:35
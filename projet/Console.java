package projet;

// éléments graphiques utlisés
import javax.swing.JLabel;
import javax.swing.JPanel;
// definir les dimensions
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Color;
// format de donné pour le contenu de la console
import java.util.Vector;

public class Console extends JPanel {
	/* la console pour afficher les explications dans MyApp
	 * cette console n'est pas très adaptées à une utilisation en dehors de MyApp
	 * certains choix (dimension, nbr max de ligne) sont spécifiques à MyApp */
	
	int width, height; // largeur, hauteur de la console
	int text_width, text_height; // largeur, hauteur texte
	JLabel text; // la zone d'écriture
	Vector<String> consoleContent; // le contenu de la console : vecteur de lignes

	// les constantes : marges de la zone d'écriture
	public static final int MARGIN_TOP 		= 10;
	public static final int MARGIN_BOTTOM 	= 10;
	public static final int MARGIN_LEFT 	= 10;
	public static final int MARGIN_RIGHT 	= 10;

	public Console(int _width, int _height) {
		/* constructeur */

		// on définit les largeurs hauteur du panel et du label
		this.width = _width;
		this.height = _height;
		this.text_width = this.width - MARGIN_LEFT - MARGIN_RIGHT - 0;
		this.text_height = this.height - MARGIN_TOP - MARGIN_BOTTOM;

		// créationde la zone de texte (placé en haut à gauche)
		this.text = new JLabel("", SwingConstants.LEFT);
		this.text.setVerticalAlignment(SwingConstants.TOP);

		// on donne la dimension ete le style de agencement
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.setLayout(null);
		
		this.text.setPreferredSize(new Dimension(this.text_width, this.text_height));
		this.text.setSize(this.text.getPreferredSize());
		this.text.setBounds(
			MARGIN_LEFT,
			MARGIN_TOP, 
			this.text_width,
			this.text_height
		);
		this.add(this.text);

		// choix de la couleur de fond du panel
		Color colorConsole = new Color(230, 230, 250); //E6E6FA
		//Color colorConsole = new Color(220, 220, 250);
		this.setBackground(colorConsole);

		// pour pouvoir lister les lignes
		this.consoleContent = new Vector<String>();
		this.writeConsoleContent();
	}

	public void writeConsoleContent() {
		/* écrit le contenu de la console dans un format html */

		// on écrit le string à afficher
		String inner = ""+
		"<html>"+
		"<body bgcolor='#E6E6FA' " +  // couleur du fond
		"text-align:justify " + // vaine tentative d'avoir le textee en justifié
		">" +
		"__________\u00b0...\u0f3a Console \u0f3b...\u00b0_________"+ // permet de garder la console à la même dimension
		"<br/>" +
		"";

		// écriture du contenu ligne par ligne
		for(int i=0 ; i<consoleContent.size() ; i++)
			inner+=consoleContent.get(i)+"<br>"; 
		
		inner+="</body>"+
		"</html>"+
		"";

		// ajout du texte
		this.text.setText(inner);
	}
	public void addLineConsole(String line) {
		/* ajouter une ligne de texte au contenu de la console et l'affiche
		 * pour rester dans la console, on ne garde que 18 lignes */
		this.consoleContent.add(line);
			if (this.consoleContent.size()>18)
				this.consoleContent.remove(consoleContent.get(0));
		this.writeConsoleContent();
	}
	public void resetConsole() {
		/* détruit le contenu de la console la redessine, vide */
		this.consoleContent.clear();
		this.writeConsoleContent();
	}
}//line:101
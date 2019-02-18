package projet;

// importation des actions
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ActionPressExemple <T extends Comparable<T>> implements ActionListener {
	/* action liée au bouton exemple */

	private MyApp theApp; // l'application
	private String exmepleNum; // nom du bouton choisis
	private String type; // le type du panelJCB selectionné
	private String exemple; // l'exemple correspondant au choix
	
	/**
	* Constructeur
	*/
	ActionPressExemple(MyApp app) {
		this.theApp = app;
	}

	/**
	* action réalisé : dessine l'exemple et change la valeur des champs 
	*/
	@Override public void actionPerformed(ActionEvent actionEvent) {
		
		// on dit quel exemple est choisi
		this.exmepleNum = actionEvent.getActionCommand(); // le numéro (nom du bouton cliqué)
		this.type = theApp.getPJCBType(); // le type  actuel de l'application
		this.theApp.resetConsole(); // on efface le contenu de la console et on affiche l'action
		this.theApp.addLineConsole("On dessine l'exemple " + exmepleNum + " du type " + type);

		// en fonction du bouton, on affecte un exemple 
		switch(exmepleNum) {
			case "n\u0b001": {
				this.exemple = MyApp.listExemples.get(type)[0];
				break;
			}
			case "n\u0b002": {
				this.exemple = MyApp.listExemples.get(type)[1];
				break;
			}
			case "n\u0b003": {
				this.exemple = MyApp.listExemples.get(type)[2];
				break;
			}
		}
		// on change l'arbre, affiche l'exemple dans le JTextField et on redessine l'arbre
		try {
			theApp.setTheTree(OrderedBinaryTree.createTree(type, this.exemple));
			theApp.setEntry1(this.exemple);
			theApp.redrawTree();
		} catch(TreeException te) {
			// im useless but that's ok
		}
	}
}//line:53 //resetConsole
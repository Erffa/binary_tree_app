package projet;

// elements graphiques
import javax.swing.JCheckBox;
import javax.swing.JPanel;
// disposition graphique
import java.awt.GridLayout;
// gerer les evenements
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelJCB extends JPanel {
	/* Panel propre à MyApp pour choisir l type avec lequel travailler */
	 
	private static final long serialVersionUID = 1L; // total Eclipse of the heart

	private MyApp theApp;
	private JCheckBox[] list_jcbs; // liste des JCheckBox, un par type

	/* le constructeur */
	public PanelJCB(MyApp App) {
		/**  le constructeur du panel pour choisir les types*/

		// initialisation des variables
		// on créer les types en lisant de MyApp la liste des type à proposer
		this.theApp = App;
		this.list_jcbs = new JCheckBox[MyApp.listTypes.length];
		for(int i=0 ; i<list_jcbs.length ; i++)
			this.list_jcbs[i] = new JCheckBox(MyApp.listTypes[i]);

		// type par defaut : IncompatibleClassChangeError
		list_jcbs[0].setSelected(true);

		// ajout de la fonction
		for(JCheckBox jcb : list_jcbs)
			jcb.addActionListener(
				new ActionListenerJCB(list_jcbs, theApp));

		// placer les elements sur le panel
		this.setLayout(new GridLayout(2,3));
		for(JCheckBox jcb : list_jcbs)
			this.add(jcb);
	}

	// les fonctions pour communiquer
	public String getType() {
		/* retourne le type selectionner sur le panel*/
		for(int i=0 ; i<4 ; i++) {
			if(this.list_jcbs[i].isSelected())
				return MyApp.listTypes[i];
		}
		return "";
	}
	public void enableCBs() {
		/* activer les checkbox */
		for(JCheckBox jcb : list_jcbs)
			jcb.setEnabled(true);
	}
	public void disableCBs() {
		/* desactiver les checkbox */
		for(JCheckBox jcb : list_jcbs)
			jcb.setEnabled(false);
	}

// inner class pour definir l'ActionListener des boutons
class ActionListenerJCB implements ActionListener {
	/* action realise a l'appui d'un checkbox */

	private MyApp theAPP; // l'application, pour modifier les champs
	private String type; // le type actuel
	private JCheckBox[] list_jcbs; // liste des checkbox
	
	public ActionListenerJCB(JCheckBox[] _list_jcbs, MyApp App) {
		/* le constructeur */
		this.theAPP = App;
		this.list_jcbs = _list_jcbs;
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent actionEvent) {
		/* action : deselectionner les checkbox et construit un arbre par defaut */
		
		// on recupere le type comme nom du checkbox
		String clickedType = actionEvent.getActionCommand();
		// on definit les valeurs par defauts
		String theExemple = MyApp.listExemples.get(clickedType)[0];
		String theValue = MyApp.listExempleVals.get(clickedType);

		// write down what we've done
		theApp.resetConsole();
		theApp.addLineConsole("On dessine l'exemple n°1 du type " + clickedType);

		// affiche l'arbre du bon type et on ecrit dans les entry 1 et 2
		try {
			theAPP.setTheTree(OrderedBinaryTree.createTree(clickedType, theExemple));
			theAPP.setEntry1(theExemple);
			theAPP.setEntry2(theValue);
		} catch(TreeException te) {
			theApp.majorIssue(te);
		}
		theAPP.redrawTree();

		// ne garde que le bon checkbox selectionné
		for (JCheckBox jcb : list_jcbs) {
			if(jcb.getText().equals(clickedType)) {
				jcb.setSelected(true);
				this.type = clickedType;
			}
			else
				jcb.setSelected(false);
		}			
	}
}

}//line:113
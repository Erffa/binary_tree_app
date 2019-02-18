package projet;

import java.awt.Color;

public class ExplainDeleteValue<T extends Comparable<T>> extends ExplainFunction<T> {
	/* Class explique la suppression d'un element dans un arbre*/

	// variables particulieress a la fonction	
	private int theCASE; // cas de la recherche
	private Code xCode; // position de la valeur null
	private T xStock;

	// le constructeur
	public ExplainDeleteValue(MyApp App) {
		super(App);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		/* script de suppression */

		try {

		// on reinitialise les variables
		this.getStarted("supprime");
		if (this.doWeDo == false) {
			getFinished();
			return;
		}

		this.xCode = new Code();
		this.theCASE = 1;

		// case : the tree is only the root
		if(wanderingTree.isLeaf()) {
			theApp.addLineConsole("<i>La racine est une feuille</i>");
			theApp.addLineConsole("# On ne détruit pas l'arbre complétement");
			// on empêche de continuer
			this.doWeDo = false;
		}

		// lancement de la boucle
		while(this.doWeDo && this.theCASE!=0) {

			// on agit enfonction du cas 1:recherche 2:suppression 3:minimum
			switch(this.theCASE) {		

				// recherche du 1st xCode
				case 1: {

					// si arbre vide, valeur non trouvee : on previent et on s'arrete
					if(wanderingTree.isEmpty()) {
						theApp.addLineConsole("# L'arbre est vide, rien a supprimer");
						this.theCASE=0;
						break;
					}
					
					// sinon, on affiche la valeur et on compare
					this.printValue();
					int comparator = this.theVALUE.compareTo(wanderingTree.getValue());

					// si egal, trouve
					if(comparator == 0) {
						theApp.addLineConsole("<i>on a trouve la valeur a supprimer</i>");
						// on stock la valeur trouvee dans xCode
						this.xCode = this.cCode.clone();
						//
						theCURSOR.stamp(Color.ORANGE);
						// on se dirige vers la suppression
						this.theCASE = 2;
						break;
					}

					// si inferieur, on regarde a gauche
					if(comparator < 0) {
						this.printInf();
						// si le ss arbre gauche est vide, echec
						if(wanderingTree.getLbt().isEmpty()) {
							theApp.addLineConsole("> sous-arbre gauche vide, rien a supprimer");
							this.theCASE=0;
						}
						// sinon, on vire a gauche
						else {
							theApp.addLineConsole("<i>il y a un sous-arbre gauche</i>");
							this.leftMove("> depart vers sous-arbre gauche");
						}
					}
					else
					// si superieur, on regarde a droite
					if(comparator > 0) {
						this.printSup();
						// si le ss arbre gauche est vide, echec
						if(wanderingTree.getRbt().isEmpty()) {
							theApp.addLineConsole("<i>sous-arbre droit vide</i>");
							theApp.addLineConsole("> rien a supprimer");
							this.theCASE=0;
						}
						// sinon, on vire a droite
						else {
							theApp.addLineConsole("<i>il y a un sous-arbre droit</i>");
							this.rightMove("> depart vers sous-arbre droit");
						}
					}
					break; // FIN CAS 1
				}

				// premiere phase de suppression
				case 2: {
					theCURSOR.setColor(Color.RED);
					// si c'est une feuille on detruit x et on redessine l'arbre
					if(wanderingTree.isLeaf()) {
						theApp.addLineConsole("<i>c'est une feuille</i>");
						theApp.addLineConsole("> on la coupe");
						theApp.theTREE_destroy(xCode);
						theApp.redrawTree();
						this.theCASE = 0;
						break;
					}

					// si pas de sous arbre droit, on remplace x par sous arbre gauche
					if(wanderingTree.getRbt().isEmpty()) { //5 6 1 11 10 9 8 7
						theApp.addLineConsole("<i>pas de sous-arbre droit</i>");
						theApp.addLineConsole("> on remonte le sous-arbre gauche");
						theApp.theTREE_addTree(xCode, wanderingTree.getLbt() );
						theApp.redrawTree();
						this.theCASE = 0;
						break;
					}

					//si pas de sous arbre gauche, on remplace x par sous arbre droit
					if(wanderingTree.getLbt().isEmpty()) { //5 6 1 11 10 9 8 7
						theApp.addLineConsole("<i>pas de sous-arbre gauche</i>");
						theApp.addLineConsole("> on remonte le sous-arbre droit");
						theApp.theTREE_addTree(xCode, wanderingTree.getRbt() );
						theApp.redrawTree();
						this.theCASE = 0;
						break;
					}

					// sinon on cherche le minimum du ss arbre droit pour remplacer la valeur
					theCURSOR.stamp(Color.RED);
					theCURSOR.setColor(Color.GREEN);
					theApp.addLineConsole("<i>ni feuille, ni branche</i>");
					this.rightMove("> recherche du minimum à droite");
					this.theCASE = 3;
					break; // FIN CAS 2
				}

				// phase recherche de minimum
				case 3: {

					// si arbre gauche vide, minimum trouve
					if (wanderingTree.getLbt().isEmpty()) {
						theApp.addLineConsole("<i>minimum trouvé : " + wanderingTree.getValue() + "</i>");
						try {
							theApp.getTheTree().changeValue(xCode, wanderingTree.getValue());
							theApp.addLineConsole("> on remplace la valeur par le minimum");
						} 
						catch(Exception e) {
							this.theCASE = 0;
							break;
						}

						// 
						theCURSOR.stamp(Color.BLUE);
						// la croix est apresent au curseur
						xCode = this.cCode.clone();
						// on redessine l'arbre
						theApp.redrawTree();
						// on replace le curseur
						theCURSOR.moveAt_(cCode);

						this.await();

						// on se retrouve face à 2 cas :
						// 
						if(wanderingTree.isLeaf()) {
							theApp.addLineConsole("<i>Le minimum est une feuille</i>");
							theApp.addLineConsole("> on la coupe");
							theApp.theTREE_destroy(xCode);
							theApp.redrawTree();
							this.theCASE = 0;
							break;
						}
						//si pas de sous arbre gauche, on remplace x par sous arbre droit
						if(wanderingTree.getLbt().isEmpty()) { //5 6 1 11 10 9 8 7
							theApp.addLineConsole("<i>Le minimum n'a pas de sous-arbre gauche</i>");
							theApp.addLineConsole("> on remonte le sous-arbre droit");
							theApp.theTREE_addTree(xCode, wanderingTree.getRbt() );
							theApp.redrawTree();
							this.theCASE = 0;
							break;
						}

					}
					// sinon on remonte a gauche
					else 
						this.leftMove("<i>on remonte le sous-arbre gauche</i>");
					break; // FIN CAS 3
				}
			}// FIN SWITCH

			// pause entre etapes
			this.await();
		}

		}
		catch(Exception e) {
			theApp.clean();
			theApp.majorIssue(e);
			theApp.enableButtons();
		}
		
		this.getFinished();
	}
}//line:216
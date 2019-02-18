package projet;

public class ExplainAddLeaf<T extends Comparable<T>> extends ExplainFunction<T> {
	/* Class explique l'ajout d'un element dans unarbre */

	private OrderedBinaryTree<T> theLEAF; // la feuille à rajouter

	/* le constructeur */
	public ExplainAddLeaf(MyApp App) { 
		super(App);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {
		/* script de recherche pédagogique de l'ajout d'une feuille
		 * à un arbre binaire ordonné */

		// try pour catcher les erreurs au besoin sans perdre l'application
		try {		

		// réinitialisation des variables et créationde la feuille à rajouter
		this.getStarted("ajoute");
		this.theLEAF = new OrderedBinaryTree(this.theVALUE);

		// lancement de la boucle si permission accordée
		while(doWeDo) {

			// on affiche la valeur puis on la compare
			this.printValue();
			int comparator = this.theVALUE.compareTo(wanderingTree.getValue());

			//si égal
			if(comparator == 0) {
				// on affiche sur la console est on arrete la recherche
				theApp.addLineConsole("<i>la valeur est d\u00e9j\u00e0 dans l'arbre</i>");
				theApp.addLineConsole("# l'ajout a echou\u00e9");
				break;
			}
			else
			// si la valeur est plus grande --> arbre gauche
			if(comparator < 0) {
				this.printInf();
				// si il n y a pas de sag, on ajoute
				if( wanderingTree.getLbt().isEmpty() ) {
					theApp.addLineConsole("<i>il n'y a pas de sous-arbre gauche</i>");
					theApp.addLineConsole("> on ajoute");
					wanderingTree.setLbt(theLEAF);
					theApp.redrawTree();
					theCURSOR.moveAt_( cCode.newNextLeftCode() );
					break;
				} // sinon on a gauche
				else {
					theApp.addLineConsole("<i>il y a un sous-arbre gauche</i>");
					this.leftMove("> on part \u00e0 gauche");
				}
			}
			else
			// si la valeur est plus grande --> arbre droit
			if(comparator  > 0) {
				this.printSup();
				// si il n y a pas de sad, on ajoute
				if( wanderingTree.getRbt().isEmpty() ) {
					theApp.addLineConsole("<i>il n'y a pas de sous-arbre droit</i>");
					theApp.addLineConsole("> on ajoute");
					wanderingTree.setRbt(theLEAF);
					theApp.redrawTree();
					theCURSOR.moveAt_( cCode.newNextRightCode() );
					break;
				} // sinon, on part a droite
				else {
					theApp.addLineConsole("<i>il y a un sous-arbre droit</i>");		
					this.rightMove("> on part à droite");
				}
			}
			// pause entre étapes
			this.await();
		}

		}
		// en cas d'erreur
		catch(Exception e) {
			theApp.clean();
			theApp.majorIssue(e);
			theApp.enableButtons();
		}

		// on conclut la fonction
		this.getFinished();
	}
}//line:89
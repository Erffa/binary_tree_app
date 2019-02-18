package projet;

/**
* Class explique la recherche d'un element dans un arbre
*/
public class ExplainSearch<T extends Comparable<T>> extends ExplainFunction<T> {

	/**
	* Constructor
	*/
	ExplainSearch(MyApp App) { super(App); }

	/**
	* script de recherche
	*/
	@Override public void run() {

		try {

			// réinitialise les variables
			this.getStarted("cherche");
			
			// on lance la loop
			while(this.doWeDo) {

				// si vide : on a pas trouvé
				if(wanderingTree.isEmpty()) {
					theApp.addLineConsole("valeur non trouv\u00e9e");
					break;
				}

				// sinon, on affiche la valeur et on compare
				this.printValue();
				@SuppressWarnings("unchecked")
				int comparator = this.theVALUE.compareTo(wanderingTree.getValue());
				
				// si égal : trouvé
				if(comparator == 0) {
					theApp.addLineConsole("<i>la valeur est \u00e9gale</i>");
					theApp.addLineConsole("> trouve !");
					break;
				}

				// si la valeur est plus petite --> sous-arbre gauche
				if(comparator  < 0) {
					this.printInf();
					// y a t il un sag
					if(wanderingTree.getLbt().isEmpty()) {
						// si pas d'arbre, not found
						theApp.addLineConsole("<i>il n'y a pas de sous-arbre gauche</i>");
						theApp.addLineConsole("> la valeur n'est pas dans l'arbre :(");
						break;
					} else {
						// sinon, on part a gauche
						this.leftMove("> on part \u00e0 gauche");
					}
				}
				else
				// si la valeur est plus grande --> sous-arbre droit
				if(comparator  > 0) {
					this.printSup();
					// y a t il un sad
					if(wanderingTree.getRbt().isEmpty()) {
						// si pas d'arbre, not found
						theApp.addLineConsole("<i>il n'y a pas de sous-arbre droit</i>");
						this.pause();
						theApp.addLineConsole("> la valeur n'est pas dans l'arbre :(");
						break;
					} else {
						// sinon, on part a droite
						this.rightMove("> on part \u00e0 droite");
					}
				}
				// pause intermédiaire
				this.await();
			}// Fin while

		}
		catch(Exception e) {
			theApp.clean();
			theApp.majorIssue(e);
			theApp.enableButtons();
		}

		this.getFinished();
	}
}//line:82
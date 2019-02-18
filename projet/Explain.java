package projet;

/**
* Classe permettant l'existence des voids ds ExplainFunction 
* sans cette classe, on a des problèmes de types
*/
public abstract class Explain<T extends Comparable<T>> {
		
	// variables scommunes à toutes les fonctions
	protected MyApp theApp; // l'application
	protected T theVALUE; // la valeur 
	protected OrderedBinaryTree<T> wanderingTree; //le sous arbre qui se ballade
	protected Code cCode; // le code du curseur
	protected Cursor<T> theCURSOR; // le curseur
	protected boolean doWeDo; // va t'on executer l'action
	protected Thread theThread;

	/**
	* constructeurs pour les variables immuables
	*/
	public Explain(MyApp App) {
		this.theApp = App;
		this.theCURSOR = App.getTheCursor();
		this.theThread = App.thread;
		this.wanderingTree = App.wanderingTREE;
	}
} //line:24
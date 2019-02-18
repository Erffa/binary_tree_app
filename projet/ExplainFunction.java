package projet;

/**
* Classe abstraite étendue par toutes les fonctions pédagogiques
* ExplainFunction regroupe des fonctions utiles pour toutes ces fonction
* étend Runnable pour être l'argument d'un Thread et implémente Explain 
* et gère ainsi bien les type generiques
*/
public abstract class ExplainFunction<T extends Comparable<T>> extends Explain implements Runnable {
	
	// constructor
	public ExplainFunction(MyApp App) {
		super(App);
	}
	
	// Obligatoire pour tout bon Runnable
	@Override public abstract void run(); 

	/** 
	* actions réalisées en début d'action : réinitialise les variables 
	* et teste la validité de la valeur proposées
	*/
	public void getStarted(String action) {
		
		theApp.setResizable(false);

		this.wanderingTree = theApp.getTheTree();
		
		// y a t'il au moins un arbre ?
		if(wanderingTree.isEmpty()) {
			theApp.addLineConsole("# ERROR : il n'y a pas d'arbre");
			this.doWeDo = false;
			return;
		}
		// réinitialise les variables 
		try {
			this.theVALUE = theApp.getValue();
		} // if bad value, print and set to null
		catch(NumberFormatException e) {
			theApp.addLineConsole("# not a " + theApp.getPJCBType() + " : " + theApp.getEntry2());
			this.theVALUE = null;
		}
		
		this.cCode = new Code();
		this.theCURSOR = new Cursor(theApp);
		this.theCURSOR.moveAt_(this.cCode);

		//si bonne valeur on autorise lancement du script et on change le gif
		if(theVALUE!=null) {
			this.theApp.addLineConsole("~ On " + action + " : " + theVALUE);
			this.doWeDo = true;
			theApp.clickTime();
		} 
		//si valeur nulle on ne fera pas tout le script
		else {
			this.doWeDo = false;
		}
		// première pause
		this.await();
	}

	public void getFinished() {
		/* action réaliser en fin de fonction */
		// on dit au revoir, on change le gif et on rend les boutons cliquables

		theApp.setResizable(true);

		theApp.getThePAD().removeDrawable(this.theCURSOR);
		theApp.getThePAD().updateUI();
		theApp.addLineConsole("<br>(\u2217 \u2018\u203f\u2018)/ au revoir, a bient\u00f4t");
		theApp.coffeeTime();
		theApp.enableButtons();
	}

	@SuppressWarnings("unchecked")
	public void setValue() {
		/* on récupère la valeur passée en paramètre */
		// le type courant
		String type = this.theApp.getPJCBType();
		// la valeur brute (string)
		String rawValue = this.theApp.getEntry2();
		// la valeur en question typé est affecté à theVALUE
		try {
			theVALUE = theApp.getValue();
		}
		// si problème : mauvaise valeur 
		catch(NumberFormatException e) {
			// on affiche le probleme
			this.theApp.addLineConsole("# not a " + type + " : " + rawValue);
			theVALUE = null;
		}
	}

	/**
	*
	*/
	public void leftMove(String comment) {
		theApp.addLineConsole(comment);
		wanderingTree = wanderingTree.getLbt();
		cCode.moveLeft();
		try {
			theCURSOR.slowMoveAt_(cCode, this.theThread);
		}
		catch(InterruptedException ie ) {}
	}
	/**
	*
	*/
	protected void rightMove(String comment) {
		theApp.addLineConsole(comment);
		wanderingTree = wanderingTree.getRbt();
		cCode.moveRight();
		try {
			theCURSOR.slowMoveAt_(cCode, this.theThread);
		}
		catch(InterruptedException ie ) {}
	}

	/* fonctions pour afficher des messages particulier */
	protected void printValue() { theApp.addLineConsole("\u25bf valeur du noeud : " + wanderingTree.getValue() + " \u25bf"); }
	protected void printInf() { theApp.addLineConsole("<i>la valeur recherch\u00e9e est inf\u00e9rieur</i>"); }
	protected void printSup() { theApp.addLineConsole("<i>la valeur recherch\u00e9e est sup\u00e9rieur</i>"); }

	/**
	* Pause the program for t milliseconds
	*/
	protected void pause(int t) {
		try {
		   this.theThread.sleep(t);
		} 
		catch (InterruptedException ie) {
			theApp.majorIssue(ie);
		}
	}
	/**
	* Pause the program for 1 second
	*/
	protected void pause() { pause(1000); }

	/**
	* attend que l'utilisateur clique l'écran
	*/
	protected void await() {
		theApp.awaiting = true;
		while(theApp.awaiting) {
			this.pause(100);
		}
	}
} //line:139
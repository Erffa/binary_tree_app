package projet;

// imporations des actions
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class ActionPressFunction <T extends Comparable<T>> implements ActionListener {
	/* Action liée aux boutons de fonctions */

	private MyApp theApp; // l'applcation, pour effacer
	private Thread theTHREAD; // thread de l'appmication 
	private Runnable task;  // argument du thread

	public ActionPressFunction(MyApp App) {
		/* Constructeur */
		this.theApp = App;
		this.theTHREAD = App.thread;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		/* action performed : explain and realise the selected function */
		// disable the other buttons not to be bother by multiple actions
		theApp.disableButtons();
		// to know which action is selected (which button is pressed)
		switch(actionEvent.getActionCommand()) {
			case "Chercher": {
				this.task = new ExplainSearch<T>(this.theApp);
				break;
			}
			case "Ajouter": {
				this.task = new ExplainAddLeaf<T>(this.theApp);
				break;
			}
			case "Enlever": {
				this.task = new ExplainDeleteValue<T>(this.theApp);
				break;
			}
		}
		// création du thread avec le bon runnable, nettoyage du pad et lancement de l'action
		theTHREAD = new Thread( this.task );
		theApp.clean();
		try {
			theTHREAD.start();		
		}
		catch(Exception e) {
			theApp.clean();
			theApp.majorIssue(e);
		}
	}
} //line:49
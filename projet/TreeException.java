package projet;

public class TreeException extends Exception {
	/* Exceptions générée pendant la manipulation d'Arbre */
	
	private static final long serialVersionUID = 4268683909689514533L;// for the sake of Eclipse
	private int code; // code d'erreur

	/* le constructeur */
	public TreeException(int _code){
		this.code = _code;
	}

	@Override
	public String toString(){
		/* affiche le bon message d'erreur en fonction du code */
		switch(this.code){
			case 0:
				return "LeafNotInTheTreeException";
			case 1:
				return "FoolishTryToCutTheWholeTreeException";
			case 2:
				return "LeafAlreadyInTheTreeException";
			case 3:
				return "ViolentMonkeyInTheTreeException";
			case 4:
				return "BadPatternToCreateTreeException";
			case 5:
				return "AintNoTreeThereMyDudeException";
			case 6:
				return "CodeLeadNowhereInTheTreeException";
			case 7:
				return "BadEmplacementToAddInTheTreeException";
			case 8:
				return "ValueNotInTheTreeException";	
			
			default:
				return "AlienTreeException";
		}
	}
}//line:39
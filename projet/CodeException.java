package projet;

public class CodeException extends Exception {
	/* Exceptions générée lors de la manipulation de Code */

	private static final long serialVersionUID = -3188477492912977607L; // pour faire plaisir à eclipse

	private int code; // code de l'erreur

	public CodeException(int _code){
		/* constructeur */
		this.code = _code;
	}
	@Override
	public String toString(){
		/* message affché en fonction du code */
		switch(this.code){
			case 0:
				return "NotGoodCodePatternException";

			case 1:
				return "NoSubCodeException";

			default:
				return "AlienCodeException";
		}
	}
}//line:27
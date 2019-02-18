package projet;

//import java.util.Vector;
//import java.util.Arrays;

public class Code implements Cloneable,Comparable<Code> {
	/* Code est un outil utilie pour manipuler les arbres
	 * le code est une suite de 0 et de 1. Le code vide a "x" comme vaeur par défaut
	 * on définit des méthodes pour générer les codes suivant (droite gauche) et trouver le précédent */

	private String code; // la caine de caractère contenant le code
	private static final Character x = 'x'; // le caractère du code vide
	private static final String X = "x"; // la chaine de caractère du code vide
	
	public Code(String _code) throws CodeException { 
		/* constructeur à partir d'un autre code sous forme de sting
		 * le plus simple à utiliser si on veut un code en particulier 
		 * si mauvais string (ex:'123'), renvoie une erreur */

		// on vérifie la validité de la chaine puis on modifie la variable
		for(int i=0 ; i<_code.length() ; i++) {
			if(_code.charAt(i)!='0' && _code.charAt(i)!='1')
				throw new CodeException(0);
		}
		this.code = _code;
	}
	public Code(Code other) {
		/* construit un code à partir d'un autre
		 * redondant avec clone() de Clonable */
		this.code = other.code;
	}
	public Code() {
		/* constructeur par défaut : la racine*/
		this.code = X;
	}

	/* les getters */
	public String getCode() { return this.code; }
	/* le setter */
	public void setCode(String _code) { this.code = _code; }
	
	/* les void */
	public void moveLeft() {
		/* le code incrémente à gauche */
		if(this.isRoot())
			this.code = "0";
		else
			this.code += "0";
	}
	public void moveRight() {
		/* le code incrémente à droite */
		if(this.isRoot())
			this.code = "1";
		else
			this.code += "1";
	}
	public void addCode(Code add) {
		/* ajoute un code à la suite */
		if(this.isRoot())
			this.setCode(add.code);
		else
			this.setCode(this.code+add);
	}
	public void preCode() throws CodeException {
		/* remonte un étage. On ne remonte pas au delà de la racine */
		if(this.isRoot())
			throw new CodeException(1);
		if(this.code.length() == 1)
			this.code = X;
		this.code = this.code.substring(1);
	}

	/* les method */
	@Override
	public Code clone() {
		/* pour obtenir un code similaire */
		return new Code(this);
	}
	
	public boolean isRoot() {
		/* teste si racine */
		if(this.code==X)
			return true;
		return false;
	}
	
	public int height() {
		/* hauteur du code (identique à hauteur de l'arbre) */
		if(this.isRoot())
			return 0;
		return 1 + this.newSubCode().height();
	}
	
	public Character firstSymbol() {
		/* renvoi le premier symbole du code (utile pour comparer) */
		if(this.isRoot())
			return x;
		return this.code.charAt(0);
	}
	
	@Override
	public String toString() {
		/* pour afficher les codes */
		return "Code : "+this.code;
	}

	@Override
	public int compareTo(Code other) {
		/* compare deux codes en fonction du quel désigne la position 
		 * la plus à gauche/droite dans un arbre binaire */ 

		// si même code, on retourne 0
		if(this.code.equals(other.code))
			return 0;
		// on compare les premiers symboles
		int comparator = compare2symbol(this.symbolAt(0), other.symbolAt(0));
		// si pareil, on descend dans les codes
		if(comparator == 0)
			return this.newSubCode().compareTo(other.newSubCode());
		// sinon on renvoi la comparaison
		return comparator;
	}
		
	public Code newSubCode() {
		/* Renvoi un nouveau code dans la valeur est l'antécédent du code actuel 
		 * On considère que l'antécédent pour faire des économies de try catch */
		if(this.code.length() <= 1)
			return new Code();
		try {
			return new Code(this.code.substring(1));
		} catch(CodeException ce) {
			return new Code();
		}
	}
	public Code newNextLeftCode() {
		/* Renvoi un nouveau code à gauche du code actuel */
		try {
			if(this.isRoot())
				return new Code("0");
			return new Code(this.code + "0");
		} catch(CodeException ce) {
			return new Code();
		}
	}
	public Code newNextRightCode() {
		/* Renvoi un nouveau code à droite du code actuel */
		try {
			if(this.isRoot())
				return new Code("1");
			return new Code(this.code + "1");
		} catch(CodeException ce) {
			return new Code();
		}
	}
	
	public Character symbolAt(int i) {
		/* valeur du code à la position indiquée. Vaut la constante
		 * des code racine si valeur inférieur à la longueur */
		if(i>=this.code.length())
			return x;
		return this.code.charAt(i);
	}

	/* Méthodes statiques */
	public static int compare2symbol(Character c1, Character c2) {
		/* compare deux symbole de code 
		 * le symbole d'une racine est la constante x */
		
		// si égaux : 0
		if(c1==c2)
			return 0;

		// les cas où c2>c1  -->  >0
		if(c1=='0' && c2== x ) return -1;
		if(c1=='0' && c2=='1') return -1;
		if(c1== x  && c2=='1') return -1;

		// les cas où c1>c2  -->  >0
		if(c1=='1' && c2=='0') return 1;
		if(c1=='1' && c2== x ) return 1;
		if(c1== x  && c2=='0') return 1;
		
		return 666; // cas impossible
	}
}//line:183
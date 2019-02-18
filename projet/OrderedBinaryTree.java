package projet;

// pour gérer les donnés
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

/**
* La classe Arbre Binaire Ordonnée qui implemente dessinable pour dessiner sur un pad
* et Cloneable pour copier des arbres 
*/
public class OrderedBinaryTree<T extends Comparable<T>>  implements Drawable, Cloneable {

	protected T value; // la valeur de l'arbre à ce noeud
	protected OrderedBinaryTree<T> lbt, rbt; // les sous-arbres

	protected Code code; // code du noeud (pas forcément renseigner, utile au moment de dessiner)
	protected Vector<Code> listCodes; // list des codes des sous-arbres

	protected static final int MARGIN_TOP = 10;
	protected static final int MARGIN_BOTTOM = 10;
	protected static final int MARGIN_SIDE = 50;

	@SuppressWarnings("rawtypes") // caremptyTree est non typé
	public static final OrderedBinaryTree emptyTree = new OrderedBinaryTree();
	
//////////////////
// CONSTRUCTORS //
//////////////////
//{
	/**
	*/
	public OrderedBinaryTree(T _value, OrderedBinaryTree<T> sag, OrderedBinaryTree<T> sad)
	{
		this.value = _value;
		this.lbt = sag;
		this.rbt = sad;
	}
	/**
	*/
	public OrderedBinaryTree(T valeur) { 
		this(
			valeur,
			(OrderedBinaryTree<T>)emptyTree,
			(OrderedBinaryTree<T>)emptyTree
		);
	}
	/**
	*/
	private OrderedBinaryTree() { this(null, emptyTree, emptyTree); }

	@Override
	public OrderedBinaryTree<T> clone() {
		return new OrderedBinaryTree<T>(this.value, this.lbt, this.rbt);
	}
//} end constructors

	// setter
	public void setTree(OrderedBinaryTree<T> tree) {
		this.value = tree.value;
		this.lbt = tree.lbt;
		this.rbt = tree.rbt;
	}
	public void setTree(T _value, OrderedBinaryTree<T> _lbt, OrderedBinaryTree<T> _rbt ) {
		// setter 
		this.value = _value;
		this.lbt = _lbt;
		this.rbt = _rbt;
	}
	public void setTree(T valeur) {
		this.value = valeur;
		this.lbt = emptyTree;
		this.rbt = emptyTree;
	}

	// setterd
	public void setValue(T _value){ this.value = _value; }
	public void setLbt(OrderedBinaryTree<T> _lbt) { this.lbt = _lbt; }
	public void setRbt(OrderedBinaryTree<T> _rbt) { this.rbt = _rbt; }
	// getters
	public T getValue() { return this.value; }
	public OrderedBinaryTree<T> getLbt(){ return this.lbt; }
	public OrderedBinaryTree<T> getRbt(){ return this.rbt; }

	public void setCode(Code _code) { this.code = _code; }
	public Code getCode() { return this.code; }

	/**
	* String representation of value for treeDraw
	*/
	public String getStrValue(){
		if (this.value==null)
			return "( x )";
		return this.value.toString();
	} 	

	/**
	* Printed version of a tree 
	*/
	@Override public String toString() {
		
		if(this.isEmpty())
			return "";
		if(this.isLeaf())
			return this.getStrValue();
		String res = "";
		res += this.getStrValue();
		res += "(";
		res += this.lbt.toString();
		res += ",";
		res += this.rbt.toString();
		res += ")";
		return res;
	}


////////////////////////////
// USUAL METHODS ON TREES //
////////////////////////////
//{
	/**
	* @return 
	*/
	public boolean isEmpty() { return this == emptyTree; }

	/**
	* @return 
	*/
	public boolean isLeaf() { return getLbt().isEmpty() && getRbt().isEmpty(); }

	/** 
	* height of the tree : max distance between the root and a leaf
	* @return 
	*/
	public int height() {
		
		if(this.isEmpty())
			return -1;
		return 1 + Math.max(this.lbt.height(), this.rbt.height());
	}
//} end usual methods on trees

////////////////////////////
// USUAL ACTIONS ON TREES //
////////////////////////////
//{
	/**
	* Les fonctions principales
	* Non utilisées dans l'application 
	* @param val
	*/
	public boolean searchValue(T val) {
		/* Cherche si la valeur est dans l'arbre */

		// on compare la valeur
		int check = this.value.compareTo(val);
		// si égal : trouvé
		if(check==0)
			return true;
		else
		// si inférieur, on cherche à gauche
		if( check>0 && !this.rbt.isEmpty() )
			return this.lbt.searchValue(value);
		else
		// si supérieur, on cherche à droite
		if( check<0 && !this.rbt.isEmpty() )
			return this.rbt.searchValue(value);
		return false;
	}

	/**
	* @param val
	*/
	public void addLeaf(T val) throws TreeException {
		/* Ajoute la valeur (si non présente dans l'arbre) */

		// création de la feuille à ajouter
		OrderedBinaryTree<T> theLeaf = new OrderedBinaryTree<T>(val);
		// on compare les valeurs
		int check = val.compareTo(this.value);
		// si égal : déja présent : erreur
		if(check==0)
			throw new TreeException(2);
		// inférieur --> on va à gauche
		if(check<0) {
			// si vide, on ajoute
			if(this.lbt.isEmpty())
				this.setLbt(theLeaf);
			// sinon on s'arrête
			else
				this.lbt.addLeaf(val);
		}
		// superieur --> on va à droite
		else {
			if(this.rbt.isEmpty())
				this.setRbt(theLeaf);
			else
				this.rbt.addLeaf(val);
		}
	}

	/**
	* Supprime la valeur indiquée de l'arbre 
	* si la valeur n'est pas présente : renvoi une erreur
	* on ne peut pas non plus couper la racine si c'est une feuille 
	* @param val
	*/
	public void deleteValue(T val) throws TreeException {
		

		// comparaison des valeurs
		int comparator = val.compareTo(this.value);

		// si égal, on est sur la valeur à supprimer. 
		if(comparator==0) {

			// si c'est une feuille : erreur (arrive uniquement si la racine au dépar est une feuille à supprimer
			if(this.isLeaf() || this.isEmpty()) 
				throw new TreeException(1);

			// si pas de sous arbre gauche, le noeud actuel devient le sous arbre droit
			if(this.lbt.isEmpty() && !this.rbt.isEmpty()) {
				this.setTree(this.rbt);
				return;
			}

			// si sous-arbre droit vide, l'inverse
			if(this.rbt.isEmpty() && !this.lbt.isEmpty()) {
				this.setTree(this.lbt);
				return;
			}

			// si les deux sous-arbres non vide, on va chercher le minimum à droite 
			if(!this.rbt.isEmpty() && !this.lbt.isEmpty()) {

				// le chercheur
				OrderedBinaryTree<T> wanderer = this.clone().rbt;

				// on remonte les sous arbres gauche 
				while(!wanderer.isEmpty() && wanderer.value.compareTo(val)<0) {
					wanderer = wanderer.lbt;
				}

				// on échange la valeur actuel avec le minimum puis recursivement, on supprime le minimum
				this.value = wanderer.value;
				this.rbt.deleteValue(wanderer.value);
				return;
			}
		}
		else
		// si inférieur
		if(comparator<0) {

			// si l'arbre gauche est vide : la valeur n'est pas présente
			if(this.lbt.isEmpty())
				throw new TreeException(8);

			// si le ss-arbre gauche est une feuille de la valeur à supprimer, on l'enlève
			if(this.lbt.value.compareTo(val)==0 && this.lbt.isLeaf())
				this.lbt = emptyTree;
			// sinon on va à gauche
			else 
				this.lbt.deleteValue(val);
		}
		else { // cs comparato > 0

			// si l'arbre droit est vide : la valeur n'est pas présente
			if(this.rbt.isEmpty())
				throw new TreeException(8);

			// si le ss-arbre gauche est une feuille de la valeur à supprimer, on l'enlève
			if(this.rbt.value.compareTo(val)==0 && this.rbt.isLeaf())
				this.rbt = emptyTree;
			// sinon on va à droite
			else
				this.rbt.deleteValue(val);
		}
	}
//} end usual actions on trees

//////////////////////////
// FONCTIONS GRAPHIQUES //
//////////////////////////
//{
	@Override
	public void paint(DrawingBoard pad, java.awt.Graphics g) {

		// ???
		this.resetListCodes();

		double pad_width  = pad.getWidth();
		double pad_height = pad.getHeight();

		double x_step = (pad_width-2*MARGIN_SIDE)/(listCodes.size()+1);
		double y_step = (pad_height-MARGIN_TOP-MARGIN_BOTTOM)/(this.height()+2);

		// map est une variable qui associe à chaque code le noeud graphique correspondant 
		Map<Code, Knot> map = new HashMap<Code, Knot>();

		Code cod = new Code();

		for (int i=0 ; i<this.listCodes.size() ; i++) {

			// le code concerné
			cod = listCodes.get(i);

			// les variables pour créer le noeud graphique
			double x_pos = MARGIN_SIDE + (i+1)*x_step;	
			double y_pos = MARGIN_TOP + ((double) cod.height()+1)*y_step;
			T val = this.valueAt(cod);

			// on créer le noeud, on le dessine et on le place dans map
			Knot knot = new Knot(x_pos, y_pos, val.toString(), pad);
			knot.paint(pad, g);
			map.put(cod, knot);			
		}

		// on dessine ensuite les lignes en s'aidant de la map
		this.paintLines(map, pad, g);
	}

	/**
	@param map 
	@param pad
	*/
	private void paintLines(Map<Code, Knot> map, DrawingBoard pad, java.awt.Graphics g) {
		
		// ??? 
		if(!map.containsKey(this.code)) { return; }

		//
		Code nextLeftCode = this.lbt.getCode();
		Code nextRightCode = this.rbt.getCode();

		// if next left code is in the map
		if( map.containsKey(this.lbt.getCode()) ) {
			map.get(this.getCode()).paint_lineTo_asLeftOn_(map.get(nextLeftCode), pad, g);
			this.lbt.paintLines(map, pad, g);
		}

		// if next right code is in the map
		if( map.containsKey(this.rbt.getCode()) ) {
			map.get(this.getCode()).paint_lineTo_asRightOn_(map.get(nextRightCode), pad, g);
			this.rbt.paintLines(map, pad, g);
		}
	}
//}end fonctions graphiques

//////////////////////////////////////
// FONCTIONS POUR EXPLAINDELETELEAF //
//////////////////////////////////////
//{
	@SuppressWarnings("unchecked")
	public void destroy(Code code) throws TreeException {
		/* remplace l'arbre au niveau du code par un arbreVide */

		//  on ne détruit pas la racine please
		if(code.isRoot())
			throw new TreeException(1);
		
		// sinon si code = "0" ou "1" detruit a gauche ou a droite
		//       sinon on continue du bon cote
		int len = code.height();
		Code subCode = code.newSubCode();

		switch(code.firstSymbol()) {
			case '0': {
				if(len==1)
					this.setLbt(emptyTree);
				else
					this.lbt.destroy(subCode);
				break;
			}
			case '1': {
				if(len==1)
					this.setRbt(emptyTree);
				else
					this.rbt.destroy(subCode);
				break;
			}
		}
	}

	/**
	* on change la valeur d'un noeud a la position du code
	* on devrait vérifier la validité  du changement... 
	* @param code
	* @param new_val
	*/
	public void changeValue(Code code, T new_val) throws TreeException {
				
		// on est a l'endroit du code
		if(code.isRoot()) {
			this.value = new_val;
			return;
		}
		
		Code subCode = code.newSubCode();
		switch(code.firstSymbol() ) {
			case '0': {
				if(this.lbt.isEmpty())
					throw new TreeException(6);
				else 
					this.lbt.changeValue(subCode, new_val);
				break;
			}
			case '1': {
				if(this.rbt.isEmpty())
					throw new TreeException(6);
				else 
					this.rbt.changeValue(subCode, new_val);
				break;
			}
		}
	}

	/**
	* remplace le noeud a la position du code par un arbre 
	* @param code
	* @param new_tree
	*/
	public void addTree(Code code, OrderedBinaryTree<T> new_tree) throws TreeException {

		if(code.isRoot()) {
			this.value = new_tree.value;
			this.setLbt(new_tree.lbt);
			this.setRbt(new_tree.rbt);
		}
		else {
			Code subCode = code.newSubCode();
			switch(code.firstSymbol()) {
				case '0': {
					this.lbt.addTree(subCode, new_tree);
					break;
				}
				case '1': {
					this.rbt.addTree(subCode, new_tree);
					break;
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	* creer un arbre a partir d'une String et du type
	* ex : Integer "4 3 5 " --> 3(4,5) 
	* @param type
	* @param sTree
	*/
	public static OrderedBinaryTree createTree(String type, String sTree) throws TreeException, NumberFormatException {
		

		// si vide, on retourne l'arbre vid
		if (sTree.equals("")) 
			return emptyTree;

		// on construit la list des val. la premiere devient la racine
		String[] list = spaceSpliter(sTree);
			
		OrderedBinaryTree tree = new OrderedBinaryTree(MyApp.convStringToType(type, list[0])); 
		// ajout des valeur une par une
		for(int i=1 ; i<list.length ; i++)
			tree.addLeaf(MyApp.convStringToType(type, list[i]));
		return tree;
	}
//} end fonctions pour explain delete

///////////////////////////////
// FONCTION POUR CREATE_TREE //
///////////////////////////////
//{

	/**
	* split un String sur les espaces : _jnj__rf_r___ --> [jnj,rf,r] 
	*/
	public static String[] spaceSpliter(String str) {
		
		// si vide, on retourne un array vide
		if(str.equals(""))
			return new String[0];
		// si premier caractere est un espace, on s'en debarasse
		if(str.charAt(0)==' ')
			return spaceSpliter(str.substring(1));
		// enfin on retourne le split
		return str.split("[ ]+");
	}	

	/*
	public void drawOn(DrawingBoard pad, java.w) {
		// Methode des dessinables por dessiner sur le pad

		//double pad_width = pad.getLargeur();
		//double pad_height = pad.getHauteur();

		this.resetListCodes();

		//double x_step = (pad_width-2*MARGIN_SIDE)/(listCodes.size()+1);
		//double y_step = (pad_height-MARGIN_TOP-MARGIN_BOTTOM)/(this.height()+2);

		// map est une variable qui associe à chaque code le noeud graphique correspondant 
		Map<Code, Knot> map = new HashMap<Code, Knot>();
		
		Code cod = new Code();

		for (int i=0 ; i<this.listCodes.size() ; i++) {

			// le code concerné
			cod = listCodes.get(i);

			// les variables pour créer le noeud graphique
			//double x_pos = MARGIN_SIDE + (i+1)*x_step;	
			//double y_pos = MARGIN_TOP + ((double) cod.height()+1)*y_step;
			T val = this.valueAt(cod);

			// on créer le noeud, on le dessine et on le place dans map
			//Knot knot = new Knot(x_pos, y_pos, val.toString(), pad);
			//knot.drawOn(pad);
			//map.put(cod, knot);
		}
		// on dessine ensuite les ligne en s'aidant de la map
		this.paintLines(map, pad, g);
	}
	*/

	/* on réinitialise la liste des codes il est important de ne le faire
	* qu'une fois pdt le dessin de l'arbre sinon les codes changent 
	* c'est pour ça qu'on utilise pas getXposAt_On  dans drawOn
	* car il fait appel encore une fois à resetListCodes 
	*/
	public void resetListCodes() {
		this.listCodes = new Vector<Code>();
		this.completeListCodes(this.listCodes, new Code());
	}

	private void completeListCodes(Vector<Code> listCodes, Code code) {
		/* fonction utilisée pour compléter la liste des codes */

		// si pas d'arbre, on passe (on ne dessine pas les arbres vides)
		if(this.isEmpty())
			return;
		// on donne à l'arbre son code
		this.setCode(code);
		
		// en procédant ainsi, la liste des codes est déja triée
		this.lbt.completeListCodes(listCodes, code.newNextLeftCode());
		listCodes.addElement(code);
		this.rbt.completeListCodes(listCodes, code.newNextRightCode());
	}

	public double getXposAt_On(Code code, DrawingBoard pad) {
		// donne la position en x de la valeur au code indiqué sur le pad indiqué 
		double pad_width = pad.getWidth();

		double x_step = (pad_width-2*MARGIN_SIDE)/(this.howMany()+1);
		double x_pos = MARGIN_SIDE + ((double) this.howManyLeft(code)+1)*x_step;

		return x_pos;
	}
	public double getYposAt_On(Code code, DrawingBoard pad) {
		// donne la position en y de la valeur au code indiqué sur le pad indiqué *
		double pad_height = pad.getHeight();

		double y_step = (pad_height-MARGIN_TOP-MARGIN_BOTTOM)/(this.height()+2);
		double y_pos = MARGIN_TOP + ((double) code.height()+1)*y_step;

		return y_pos;
	}
	// */

	public int howMany() {
		/* combien de sous-arbre non vides plus la racine dans l'arbre */
		if(this.isEmpty()) 
			return 0;
		else
			return 1 + this.lbt.howMany() + this.rbt.howMany();
	}
	public int howManyLeft(Code code) {
		/* combien de sous-arbre nà gauche de la racine */
		this.resetListCodes();
		int c = 0;
		for(Code co : this.listCodes) {
			if(co.compareTo(code)<0)
				c++;
		}
		return c;
	}

	public T valueAt(Code code) {
  		/* valeur de l'arbre au code donné*/
		if(code.isRoot())
			return this.value;
		else
		if(code.firstSymbol() == '0')
			return this.lbt.valueAt(code.newSubCode());
		else
		if(code.firstSymbol() == '1')
			return this.rbt.valueAt(code.newSubCode());

		return null;// don t go there
	}

	public int heightOf(T val) {
		/* la hauteur de l'arbre */
		int comparator = val.compareTo(this.value);

		if(comparator==0)
			return 0;
		else
		if(comparator <0)
			return 1+this.lbt.heightOf(val);
		else
			return 1+this.rbt.heightOf(val);
	}
//} end fonctions pour create_tree

}//line:517








/*
// public void drawOn(DrawingBoard db) {
 	// 	this.drawTreeOn(db); //tree.drawTreeOn(pad);
 	// }


// drawable
	public boolean isIn(double d1, double d2) {
		return false;
	}


*/
package projet;

// pour couleur et stocké les tampons
import java.awt.Color;
import java.util.Vector;
import java.awt.Graphics;

/**
* Créer un curseur circulaire qui se déplace sur son pad
* certaines méthodes sont très spécifique à leur utilisation 
* pour les arbres binaires 
*/
public class Cursor <T extends Comparable<T>> implements Drawable {


	// variables classiques
	private double x, y; // position
	private Color color; // couleur du curseur
	// final variables
	private static final double radius = 27.0; // rayon du curseur
	private static final int thickness = 2; // épaisseur du contour
	private static final Color defaultColor = Color.RED; // couleur par défaut des curseurs : rouge
	// variables pour PaD
	private DrawingBoard pad; // le pad où se ballade notre curseur
	private Drawable dess; // élément dessinable représentant le curseur
	private Vector<Drawable> listStamps; // listes des tampons/empreintes laissées
	private OrderedBinaryTree<T> cTree; // l'arbre concerné 
	private boolean visible = true;

	/* les constructeurs */
	public Cursor(double _x, double _y, Color _color, DrawingBoard _pad) {
		this.x = _x;
		this.y = _y;
		this.color = _color;
		this.pad = _pad;
		this.listStamps = new Vector<Drawable>(); // initialise la liste des tampons

		this.pad.addDrawable(this);
	}
	public Cursor(double _x, double _y, DrawingBoard _pad) {
		this(_x , _y , defaultColor, _pad);
	}
	public Cursor(DrawingBoard pad_, OrderedBinaryTree cTree_) {
		this(0, 0, pad_); 
		this.cTree = cTree_;
	}
	public Cursor(DrawingBoard pad_) {
		this(0, 0, pad_); 
	}
	public Cursor(MyApp app) {
		this(app.getThePAD(), app.getTheTree()); 
	}
	
	/* les getters */
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	public Color getColor() { return this.color; }
	public Drawable getDess() { return this.dess; }

	/* setters */
	public void setColor(Color _color) { this.color = _color; }
	public void setVisible(boolean b) { this.visible = b; }

	/** 
	* pour dessiner les curseurs
	*/
	@Override public void paint(DrawingBoard pad, Graphics g) {

		if (!this.visible)
			return;

		// draw the stamps
		for (Drawable stamp : this.listStamps)
			stamp.paint(pad, g);

		// draw the cursor
		g.drawOval((int)(this.x-Cursor.radius),(int)(this.y-Cursor.radius), (int)Cursor.radius*2,(int)Cursor.radius*2);
	}

	

	public void stamp(Color c) {
		Cursor this_ = this;
		Drawable newStamp = new Circle((int)this.x, (int)this.y, (int)Cursor.radius, c);
		listStamps.addElement(newStamp);
	}
	/**
	* laisse une empreinte sur le pad 
	*/
	public void stamp() {
		this.stamp(Color.RED);
	}

	public void redrawStamps() {
		/* redessine tous les tampons */
		//for (Drawable draw : this.listStamps)
			//pad.ajouter(draw);
	}

	/**
	* efface tout les tampons 
	*/
	public void eraseStamps() {
		
		listStamps = new Vector<Drawable>();
	}

	/**
	* efface tout : forme et tampon 
	*/
	public void eraseAll() {
		
		this.visible = false;
		eraseStamps();
	}

	/** bouger le curseur sur le pad **/
	public void moveTo(double _x, double _y) {
		/* bouger à une position (x,y) */

		// on change les coordonnées
		this.x = _x; 
		this.y = _y;
		
		// on efface la forme, on la change puis redessine
		
		this.pad.updateUI();
		java.awt.Graphics g = this.pad.getGraphics();
		g.drawOval((int)(this.x-Cursor.radius),(int)(this.y-Cursor.radius), (int)Cursor.radius*2,(int)Cursor.radius*2);
	}
	public void slowMoveTo(double _x, double _y, Thread thread) throws InterruptedException{
		/* bouge progressivement en (x,y)
		 * on pourrait améliorer la gestion de la vitesse (f(x) = (1 + cos(x - π)) / 2 )*/

		int n = 50; // number of repetitions
		// variables changer les positions progressivement
		double start_x = this.x; 
		double start_y = this.y;
		double step_x = (_x - this.x)/n ;
		double step_y = (_y - this.y)/n ;

		// on bouge et on fait pause
		for( int i=0 ; i<n+1 ; i++) {
			this.moveTo( start_x + i*step_x , start_y + i*step_y);
			thread.sleep(30); // the pause
			this.pad.updateUI();
		}
	}
	/* bouge le curseur à la position indiquée par le code dans l'arbre  */
	public void moveAt_(Code code) {
		double x_pos = this.cTree.getXposAt_On(code, this.pad);
		double y_pos = this.cTree.getYposAt_On(code, this.pad);
		this.moveTo(x_pos, y_pos);
	}
	/* bouge progressivement vers une position de l'arbre */
	public void slowMoveAt_(Code code, Thread _thread) throws InterruptedException {
		double _x = this.cTree.getXposAt_On(code, this.pad);
		double _y = this.cTree.getYposAt_On(code, this.pad);
		slowMoveTo(_x , _y, _thread);
	}
}

class Circle implements Drawable {

	int pos_x, pos_y, radius;
	Color c;

	Circle(int pos_x_, int pos_y_, int radius_, Color c_) {
		this.pos_x = pos_x_;
		this.pos_y = pos_y_;
		this.radius = radius_;
		this.c = c_;
	}

	@Override public void paint(DrawingBoard pad, Graphics g) {
		g.setColor(this.c);
		g.drawOval(this.pos_x-this.radius,this.pos_y-this.radius, this.radius*2,this.radius*2);
	}
}//line:139
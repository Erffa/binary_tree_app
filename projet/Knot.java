package projet;

// importation pour avoir longueur d'un mot sur pad
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
// la couleur
import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;

public class Knot implements Drawable {
	
	/* les variables */
	private DrawingBoard pad;
	// depart de la boite
	private double x_box, y_box;
	// la valeur du noeud
	private String value;
	// les pointd
	private Point topPoint, rightPoint, leftPoint;

	// les constantes 
	private static final double WIDTH  = 55;
	private static final double HEIGHT = 55;
	private static final Color COLOR = Color.BLUE;
	private static final int THICKNESS = 2;	

	/**
	* @param x_ Center of the Knot
	* @param y_ Center of the Knot
	* @param value_
	* @param pad_
	*/
	public Knot( double x_, double y_, String value_, DrawingBoard pad_) {

		this.value = value_;
		this.pad = pad_;
		
		this.x_box = x_ - WIDTH/2;
		this.y_box = y_ - HEIGHT/2;

		this.topPoint = new Point( this.x_box + WIDTH/2 , this.y_box );
		this.rightPoint = new Point(this.x_box  + WIDTH , this.y_box + HEIGHT );
		this.leftPoint = new Point(this.x_box , this.y_box + HEIGHT);
	}

// PAINT METHODS	
//{
	/**
	* Dessine le noeud sur un pad 
	* @see Drawable
	*/
	@Override
	public void paint(DrawingBoard pad, Graphics g) { 
		int x_box_i = (int)this.x_box;
		int y_box_i = (int)this.y_box;

		// Draw the rectangle
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Knot.COLOR);
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(Knot.THICKNESS));		
		g2.drawRect(x_box_i,y_box_i, (int)WIDTH,(int)HEIGHT);
		g2.setStroke(oldStroke);

    	// Draw the value
    	Font f = g.getFont();
    	int ws = g.getFontMetrics(f).stringWidth(this.value);
    	int hsa = g.getFontMetrics(f).getAscent();
    	int xs = x_box_i + (int)((WIDTH-ws)/2);
    	int ys = y_box_i + (int)(HEIGHT/2) + (int)(hsa/2);
    	g.setColor(Color.BLACK);
		g.drawString(this.value, xs,ys);
	}
	// private methods used in paint(DrawingBoard, Graphics)
	void paint_lineTo_asLeftOn_(Knot knot , DrawingBoard pad, java.awt.Graphics g) {
		this.leftPoint.drawLineTo(g, knot.topPoint);
	}
	void paint_lineTo_asRightOn_(Knot knot, DrawingBoard pad, java.awt.Graphics g) {
		this.rightPoint.drawLineTo(g, knot.topPoint);
	}
//}
	
}//line:126

/* renvoi la ligne qui relie 2 noeuds, droite ou gauche */
	/*
	
	public int pixelLength(String word) {
		Font f = this.text.getF();
    	//Graphics2D g = this.pad.getOnscreen();
    	return g.getFontMetrics(f).stringWidth(word);
    }
	//*/
/* méthodes obligatoire de dessinable */
	//public boolean isIn(double d1, double d2) {
		/* s'avoir si (x,y) est dans le noeud */
		// on utilise le isIn des rectangles
		// inutilsé dans notre projet, mais on pourrait imaginer une version
		// ou l'utilisateur doit cliquer sur le bon noeud pour poursuivre l'action 
		//return this.box.isIn(d1, d2);
	//}

		/**
	* dessiner le noued sur le pad 
	*
	public void drawOn(DrawingBoard pad) {
		
		//this.text.drawOn(pad);
		//this.box.drawOn(pad);
	}// */


/*

public Knot( double _x , double _y , String _value , DrawingBoard _pad) {
		//super(COLOR);

		this.value = _value;
		this.pad = _pad;

		this.x = _x;
		this.y = _y;
		
		this.x_box = this.x - WIDTH/2;
		this.y_box = this.y - HEIGHT/2;
		4
		//this.box = new Rectangle( this.x_box,this.y_box , WIDTH,HEIGHT , COLOR,THICKNESS );
		//this.text = new Texte("");
		
		// le texte affiché est limité par la taille de la boite
		// ex : 12345  -->  123..   pour rentrer dans la boite
		String written = new String(this.value);
		String fin = "";
		
		while (this.pixelLength(written + fin)>= WIDTH) {
			fin = "..";
			written = written.substring(0, written.length() - 1);
		}
		written += fin;
		this.text.setText(written);

		// on attend d'avoir le text à la bonne taille pour positionner le Texte
		this.text.setX( this.x - this.pixelLength(written)/2 );
		this.text.setY( this.y - 12 );
	

	public String toString() {
		// pour afficher un noeud dans le terminal
		String res = "(" + this.getX() + "," + this.getY() + ") : " + this.getValue() ;
		return res;
	}

	public void setOrig(double d1, double d2) {
		this.x = d1;
		this.y = d2;
	}

	// les getters //
	private String getValue(){ return this.value; }
	private Color getColor() { return COLOR; }
	private int getThickness() { return THICKNESS; }
	//getters de points
	private Point getTopPoint() { return this.topPoint; }
	private Point getRightPoint(){ return this.rightPoint; }
	private Point getLeftPoint() { return this.leftPoint; }

*/

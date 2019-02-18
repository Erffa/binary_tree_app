package projet;

/**
* Un point est un couple de coordonnées (x,y) 
*/
public class Point {
	
	private double x,y;

	// CONSTRUCTORS
	public Point( double xval , double yval ) {
		this.x = xval;
		this.y = yval;
	}
	public Point() {
		this(.0,.0);
	}

	void drawLineTo(java.awt.Graphics g, Point p) {
		g.drawLine((int)this.x,(int)this.y, (int)p.x,(int)p.y);
	}
}//line:27

/*
// couleur pour les lignes
import java.awt.Color;


public Line lineTo(Point p) {
	// renvoi la ligne qui relie le point et un autre 
	Line li = new Line( this.getX(),this.getY() , p.getX(),p.getY() , Color.BLACK,1 );
	return li;
}
	public double getX() { return this.x; }
public double getY() { return this.y; }
*/
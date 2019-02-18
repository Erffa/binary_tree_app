package projet;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;

/**
*/
public class DrawingBoard extends JPanel implements MouseListener {

	private OrderedBinaryTree tree;
	private ArrayList<Drawable> list_drawables;

// CONSTRUCTOR

	/**
	* @param tree_
	*/ 
	public DrawingBoard(OrderedBinaryTree tree_) {
		super();
		this.tree = tree_;
		this.list_drawables = new ArrayList<Drawable>();
		this.addMouseListener(this);
		this.setBackground(Color.WHITE);
	}

	void setTree(OrderedBinaryTree tree_) {
		this.tree = tree_;
		this.updateUI();
	}

	void addDrawable(Drawable d) {
		this.list_drawables.add(d);
	}
	void removeDrawable(Drawable d) {
		this.list_drawables.remove(d);
	}

	@Override public void paint(Graphics g) {
		super.paint(g);
		this.tree.paint(this, g);

		for (Drawable d : this.list_drawables) {
			d.paint(this, g);
		}
	}

	// OVERRIDE MOUSELISTENER //
	@Override public void mouseClicked(MouseEvent me) {}
	@Override public void mouseEntered(MouseEvent me) {}
	@Override public void mouseExited(MouseEvent me) {}
	@Override public void mousePressed(MouseEvent me) {}
	@Override public void mouseReleased(MouseEvent me) {}
}
/*
// importations originales
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.Vector;
// importations ajoutée
import javax.swing.JPanel;
*/

/*
public class DrawingBoard extends JLabel implements MouseListener, MouseMotionListener {

	protected static final Color DEFAULT_CLEAR_COLOR = Color.WHITE;
	protected static final Color DEFAULT_PEN_COLOR = Color.BLACK;
	protected static final Font DEFAULT_FONT = new Font("SansSerif", 0, 16);
	protected static final int DEFAULT_PEN_RADIUS = 1;

	private Vector<Drawable> voad;

	@SuppressWarnings("unused")
	private Font font;
	private int width;
	private int height;
	private Color penColor;
	private double penRadius;
	private BufferedImage offscreenImage;
	private BufferedImage onscreenImage;
	protected Graphics2D offscreen;
	protected Graphics2D onscreen;
	private JFrame frame;
	private Drawable selection;
	private double offX;
	private double offY;

	public Graphics2D getOnscreen() {
		return this.onscreen;
	}


	protected void setPenColor(Color paramColor) {
		if (paramColor == null) 
			throw new IllegalArgumentException();
		penColor = paramColor;
		offscreen.setColor(penColor);
	}
	protected void setPenColor() {
		setPenColor(DEFAULT_PEN_COLOR);
	}

	protected double getPenRadius() { 
		return penRadius; 
	}

	public void setPenRadius() { 
		setPenRadius(1);
	}
	protected void setPenRadius(int paramInt) {
		BasicStroke localBasicStroke = new BasicStroke(paramInt, 1, 1);
		offscreen.setStroke(localBasicStroke);
	}

	public void clear() {
		clear(DEFAULT_CLEAR_COLOR);
		voad.removeAllElements();
	}
	private void clear(Color paramColor) {
		clearOff(paramColor);
		draw();
	}

	private void clearOff(Color paramColor) {
		offscreen.setColor(paramColor);
		offscreen.fillRect(0, 0, width, height);
		offscreen.setColor(penColor);
	}


	public void ajouter(Drawable paramDessinable) {
		voad.add(paramDessinable);
		draw();
	}
	public void ajouter(Drawable... paramVarArgs) {
		for (Drawable localDessinable : paramVarArgs) 
			voad.add(localDessinable);
		draw();
	}

	public void supprimer(Drawable paramDessinable) {
		voad.remove(paramDessinable);
		draw();
	}
	public void supprimer(Drawable... paramVarArgs) {
		for (Drawable localDessinable : paramVarArgs) 
			voad.remove(localDessinable);
		draw();
	}

	public void draw() {
		clearOff(DEFAULT_CLEAR_COLOR);

		for (Drawable localDessinable : voad) {
			localDessinable.drawOn(this);
		}
		onscreen.drawImage(offscreenImage, 0, 0, null);
		frame.repaint();
	}
  
	public DrawingBoard(int int1, int int2, JFrame _frame) {
		

		// simple création d'un JLabel
		super();

		if ((int1 <= 0) || (int2 <= 0))
			throw new IllegalArgumentException("largeur et hauteur doivent etre positives");

		// nécessite le frame pour repeindre
		this.frame = _frame;

		width = int1;
		height = int2;

		voad = new Vector<Drawable>(); // contain drawable objects of the pad

		onscreenImage = new BufferedImage(width, height, 1);
		onscreen = onscreenImage.createGraphics();
		offscreenImage = new BufferedImage(width, height, 1);
		offscreen = offscreenImage.createGraphics();

		offscreen.setColor(DEFAULT_CLEAR_COLOR);
		offscreen.fillRect(0, 0, width, height);

		setPenColor();
		setPenRadius();
		clear();

		// créer et ajoute l'image au JLabel
		ImageIcon localImageIcon = new ImageIcon(onscreenImage);
		this.setIcon(localImageIcon);

		// rendre le pad réactif
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public double getLargeur() { return width; }
	public double getHauteur() { return height;}

	// fonctions à Override pour lier le pad et la souris 
	// MouseListener
	public void mouseClicked(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {}
	// MouseMotionListener
	public void mouseDragged(MouseEvent me) {}
	public void mouseMoved(MouseEvent me) {}
}//line:169
*/
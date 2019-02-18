package projet;

// pour les elements graphiques
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.ImageIcon;
// pour l'apparence
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
// les événemtns souris
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
// pour les actions
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
// pour resolution de l'ecran
import java.awt.Toolkit;
// pour l'icon sur bouton help
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
// pour les exemples
import java.util.Map;
import java.util.HashMap;

///
import java.awt.Color;

/**
* L'apllication graphique
*/
public class MyApp extends JFrame { 
	

	// composant graphique principaux
	private DrawingBoard thePAD;

	// les widget dynamiques
	private PanelJCB pjcb0;
	private JTextField entryExample, entryAction;
	private Console console;
	private AnimatedGif clickGIF;

	// list of all component to enable/disable
	private JButton[] list_buttons;

	// arbre et pad
	private OrderedBinaryTree theTREE;

	public OrderedBinaryTree wanderingTREE;
	private Cursor cursor; // le curseur pour expliquer$
	public Comparable theValue;

	public Thread thread;
	public boolean awaiting = false;

	/* constantes : tableaux des exemples et de type */
	/* fonctions statiques pour créer les variables statiques */
	private static Map<String, String[]> createListExemples() {

		Map<String, String[]> theListExemples = new HashMap<String, String[]>();
		theListExemples.put("Integer", new String[] {
		  "5 3 7 1 9 2 8 4 6",
		  "15 10 6 4 0 20 24 28 32 12 14 18 16",
		  "1 3 2 8 4 9 7 5 6"
		});
		theListExemples.put("Float", new String[] {
		  "0.0 -6.4 6.4 -4.1 4.1 -2.5 2.5 -3.9 3.9 -5.7 5.7 ",
		  "2.0 1.1 10.6 11.1 2.3 2.1 7.5 8.8 3.9",
		  "0.4 0.6 0.7 0.5 0.2"
		});
		theListExemples.put("Character", new String[] {
		  "g c k a e i m",
		  "g c k i o m q",
		  "k c a g e i t o v n r"
		});
		theListExemples.put("String", new String[] {
		  "jir xnj klm aze vjr lkz xgu",
		  "hr aj gt he hir zz",
		  "Odin Thor Freya Baldr Loki Tyr"
		});
		return theListExemples;
	}
	private static Map<String, String> createListExempleVals() {
		Map<String, String> theListExempleVals = new HashMap<String, String>();
		theListExempleVals.put("Integer", "2");
		theListExempleVals.put("Float", "7.9");
		theListExempleVals.put("Character", "k");
		theListExempleVals.put("String", "aze");
		return theListExempleVals;
	}
	public static final String[] listTypes = new String[] {"Integer", "Character", "Float", "String"};
	public static final Map<String, String[]> listExemples = createListExemples();
	public static final Map<String, String> listExempleVals = createListExempleVals();

	// 
	private static final String MANUAL = ""+
			"Pour écrire un arbre, cliquez sur un bouton exemple pour avoir un arbre déjà fabriqué "+
			"ou bien créez le votre dans le champ de la forme 5 4 6 7. Le premier élément sera la "+
			"valeur de la racine et les valeurs suivantes sont ajoutées à l'arbre dans l'ordre."+
			"<br/><br/>"+
			"Pour chercher, ajouter ou supprimer un élément, entrez la valeur dans le champ puis "+
			"choisissez le bouton correspondant. Une fois l'action lancée, appuyez sur la partie à droite à chaque étape."+
			"<br/><br/>"+
			"Pour effacer le contenu de la console, appuyez sur le bouton 'Effacer'. Pour revoir ce mode d'emploi, "+
			"cliquez sur le point d'interrogation."+
			"";

	// le constructeur
	@SuppressWarnings("unchecked")
	public MyApp() throws TreeException {
		super();		

		// construction de l'arbre et du curseur
		this.theTREE = OrderedBinaryTree.createTree("Integer", "5 3 7 1 9 2 8 4 6");

		// on modifie le pad créer pour être sensible aux clics :
		// un clic modifie l'état de 'awaiting', permet passer une pause
		this.thePAD = new DrawingBoard(this.theTREE) {	
			@Override public void mousePressed(MouseEvent me) {
				awaiting = !awaiting;
			}
		};

		//???thePAD.ajouter(theTREE);
		this.cursor = new Cursor(this.thePAD, this.theTREE);
		
		// on construit le panel
		buildPanel(); 

		// on empêche le frame d'être redimensionné, et on lui donne un titre
		this.setResizable(true);
		this.setDefaultCloseOperation(3);
		this.setTitle("Arbre binaire ordonn\u00b0e");

		// on affiche la fenetre
		this.setSize(500,500);
		//this.requestFocusInWindow();
		this.setVisible(true);

	}

	class FixedWidthPanel extends JPanel {

		private int fixed_width;

		public FixedWidthPanel(int fixed_width_) {
			super();
			this.fixed_width = fixed_width_;
		}
		
		@Override public Dimension getMinimumSize() { return new Dimension(this.fixed_width,(int)super.getMinimumSize().getHeight()); }
		@Override public Dimension getMaximumSize() { return new Dimension(this.fixed_width,(int)super.getMaximumSize().getHeight()); }
		@Override public Dimension getPreferredSize() { return new Dimension(this.fixed_width,(int)super.getPreferredSize().getHeight()); }
	}

	public void buildPanel() {
	//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//
	//*//*//*//*//*//*//*//*//*//*//  ~~~ CREATION DES WIDGETS ~~~ //*//*//*//*//*//*//*//*//*//*//*//
	//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//

		/* LES TYPES */

		JPanel panelTypes = new JPanel(new BorderLayout());

		TitlePanel titleTypes = new TitlePanel(" Choisir le type");
		JLabel labelTypes = new JLabel(" Les types : ");

		pjcb0 = new PanelJCB(this);

		panelTypes.add(titleTypes,BorderLayout.NORTH);
		panelTypes.add(labelTypes,BorderLayout.CENTER);
		panelTypes.add(pjcb0,BorderLayout.EAST);

		/* CONSTRUIRE L'ARBRE */

		JPanel panelBuild = new JPanel(new BorderLayout());

		TitlePanel titleBuild = new TitlePanel("Construire un arbre");
		JLabel labelBuild = new JLabel(" Choisir un exemple :");

		JButton butEx1 = new JButton("n\u0b001");
		JButton butEx2 = new JButton("n\u0b002");
		JButton butEx3 = new JButton("n\u0b003");

		this.entryExample = new JTextField();
		JButton butOk = new JButton("ok");

		Box boxExemple = new Box(BoxLayout.X_AXIS);
	
		boxExemple.add(butEx1);
		boxExemple.add(butEx2);
		boxExemple.add(butEx3);
		
		JPanel panelEntry = new JPanel(new BorderLayout());
		
		panelEntry.add(entryExample, BorderLayout.CENTER);
		panelEntry.add(butOk, BorderLayout.EAST);

		JPanel topBuild = new JPanel(new BorderLayout());
		topBuild.add(titleBuild, BorderLayout.NORTH);
		topBuild.add(labelBuild, BorderLayout.SOUTH);

		panelBuild.add(topBuild, BorderLayout.NORTH);
		panelBuild.add(boxExemple, BorderLayout.CENTER);
		panelBuild.add(panelEntry,BorderLayout.SOUTH);

		/* ACTIONS */

		JPanel panelAction = new JPanel(new BorderLayout());
		//
		TitlePanel titleAction = new TitlePanel("Agir sur l'arbre");
		JLabel labelAction = new JLabel(" Les actions :",SwingConstants.LEFT);

		JPanel topAction = new JPanel(new BorderLayout());
		topAction.add(titleAction, BorderLayout.NORTH);
		//topAction.add(labelAction, BorderLayout.SOUTH);
		//
		Box boxAction = new Box(BoxLayout.X_AXIS);
		//
		JButton butAct1 = new JButton("Chercher");
		JButton butAct2 = new JButton("Ajouter");
		JButton butAct3 = new JButton("Enlever");
		//
		boxAction.add(butAct1);
		boxAction.add(butAct2);
		boxAction.add(butAct3);
		//
		entryAction = new JTextField();

		panelAction.add(topAction, BorderLayout.NORTH);
		panelAction.add(boxAction, BorderLayout.CENTER);
		panelAction.add(entryAction, BorderLayout.SOUTH);

		/////////////
		/* CONSOLE */
	
		this.console = new Console(300, 400);
		this.clickGIF = new AnimatedGif("images/coffee.gif");
		JButton butClear = new JButton("Effacer");
		JButton butHelp;

		// create the button, try to put an image on it
		try {
			BufferedImage img = ImageIO.read(getClass().getResource("images/questionmark.png"));
			butHelp = new JButton(new ImageIcon(img));
		} catch (Exception ex) {
			butHelp = new JButton("?");
		}


		JPanel panelConsole = new JPanel(new BorderLayout());
		javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(
			javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
			javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		javax.swing.JViewport jvp = new javax.swing.JViewport();
		jvp.add(console);
		scroll.setViewport(jvp);

		JPanel panelConsole_bot = new JPanel();

		panelConsole.setLayout(new BoxLayout(panelConsole, BoxLayout.Y_AXIS));
		panelConsole_bot.setLayout(new BoxLayout(panelConsole_bot, BoxLayout.X_AXIS));

		panelConsole_bot.add(butClear);
		panelConsole_bot.add(clickGIF);
		panelConsole_bot.add(butHelp);

		panelConsole.add(scroll);
		panelConsole.add(panelConsole_bot);



		// ajout des elements au panel
		JPanel thePANEL = new FixedWidthPanel(300);
		thePANEL.setLayout( new BoxLayout(thePANEL,BoxLayout.Y_AXIS) );

		thePANEL.add(panelTypes);
		thePANEL.add(panelBuild);
		thePANEL.add(panelAction);
		thePANEL.add(panelConsole);


		//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//
		//*//*//*//*//*//*//*//*//*//*//  ~~~ AJOUT DES FONCTIONS ~~~ //*//*//*//*//*//*//*//*//*//*//*//
		//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//
		// ajouter des fonctions aux boutons

		// les exemples
		butEx1.addActionListener( new ActionPressExemple(this) );
		butEx2.addActionListener( new ActionPressExemple(this) );
		butEx3.addActionListener( new ActionPressExemple(this) );

		// pour le bouton ok : créatin d'un arbre
		MyApp this_ = this;
		butOk.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent evt) {

				// clear the console and the DrawingBoard
				this_.clean();

				// change the tree
				try{
					this_.setTheTree( OrderedBinaryTree.createTree(pjcb0.getType(), entryExample.getText()) );
					console.addLineConsole("> On dessine votre arbre de type " + pjcb0.getType());
				}
				// repetition in OrderedBinaryTree is forbidden
				catch(TreeException te) {
					console.addLineConsole("> error : repetition de valeur");
				} 
				// if the entered value don't match the current type
				catch(NumberFormatException nfe) {
					console.addLineConsole("> error : mauvaise valeur");
				}
			} 
		});

		// Chercher, Ajouter, Enlever
		butAct1.addActionListener( new ActionPressFunction(this) );
		butAct2.addActionListener( new ActionPressFunction(this) );
		butAct3.addActionListener( new ActionPressFunction(this) );

		// clear pour effacer la console
		butClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				console.resetConsole();
				console.addLineConsole(">");
			} 
		});

		// help pour afficher le manuel
		butHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) { // ??????????
				console.resetConsole();
				console.addLineConsole(MANUAL);
			} 
		});

		// stock all buttons
		this.list_buttons = new JButton[] {butEx1, butEx2, butEx3, butOk, butAct1, butAct2, butAct3, butClear, butHelp}; 

		//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//
		//*//*//*//*//*//*//*//*//*//*//  ~~~ GERER DIMENSIONS ~~~ //*//*//*//*//*//*//*//*//*//*//*//
		//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//
		/* couleurs pour titre */
		Color colorType   = new Color(0.1f,0.8f,0.1f,0.3f);
		Color colorBuild  = new Color(0.7f,0.8f,0.1f,0.3f);
		Color colorAction = new Color(0.8f,0.2f,0.1f,0.3f);

		titleTypes.setBackground(colorType);
		titleBuild.setBackground(colorBuild);
		titleAction.setBackground(colorAction);

		/* on donne leur dimension aux éléments graphique */
		panelTypes.setMaximumSize(new Dimension(300, 100));
		titleTypes.setMinimumSize(new Dimension(300, 30));
		titleTypes.setMaximumSize(new Dimension(300, 30));

		panelBuild.setMaximumSize(new Dimension(300, 100));
		titleBuild.setMinimumSize(new Dimension(300, 30));
		titleBuild.setMaximumSize(new Dimension(300, 30));
		boxExemple.setMaximumSize(new Dimension(300, 30));
		butEx1.setMaximumSize(new Dimension(100, 30));
		butEx2.setMaximumSize(new Dimension(100, 30));
		butEx3.setMaximumSize(new Dimension(100, 30));
		entryExample.setPreferredSize(new Dimension(240, 30));
		butOk.setPreferredSize(new Dimension(60, 30));

		panelAction.setMaximumSize(new Dimension(300, 100));
		boxAction.setMaximumSize(new Dimension(300, 30));
		butAct1.setMaximumSize(new Dimension(100, 30));
		butAct2.setMaximumSize(new Dimension(100, 30));
		butAct3.setMaximumSize(new Dimension(100, 30));

		panelConsole.setMaximumSize(new Dimension(300, 500));
		console.setSize(280, 360);

		butClear.setPreferredSize(new Dimension(100, 100));
		clickGIF.setPreferredSize(new Dimension(100, 100));
		butHelp.setPreferredSize(new Dimension(100, 100));
		butClear.setMaximumSize(new Dimension(100, 100));
		clickGIF.setMaximumSize(new Dimension(100, 100));
		butHelp.setMaximumSize(new Dimension(100, 100));
		butClear.setMinimumSize(new Dimension(100, 100));
		clickGIF.setMinimumSize(new Dimension(100, 100));
		butHelp.setMinimumSize(new Dimension(100, 100));


		// set the content pane
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,  BoxLayout.X_AXIS));
		mainPanel.add(thePANEL); // ajout du panel de commande à gauche
		mainPanel.add(thePAD); // ajout du pad à droite
		this.setContentPane(mainPanel);

		// default values
		this.entryExample.setText("5 3 7 1 9 2 8 4 6");
		this.entryAction.setText("3");
		this.console.addLineConsole(MANUAL);
	}

	//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//
	//*//*//*//*//*//*//*//*//*//*//  ~~~ FONCTIONS POUR ACTIONS ~~~ //*//*//*//*//*//*//*//*//*//*//*//
	//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//*//

	/* choix du gif. La main qui clique signifie qu'il faut 
	 cliquer sur le pad pour poursuivre l'action */
	void coffeeTime() {
		clickGIF.setGIF("images/coffee.gif");
	}
	void clickTime() {
		clickGIF.setGIF("images/click.gif");
	}

	/**
	* renvoie la valeur en paramètre au bon type 
	*/
	Comparable getValue() throws NumberFormatException {
		String type = getPJCBType();
		String rawValue = getEntry2();
		return MyApp.convStringToType(type, rawValue);
	}

	/**
	* convertie un string en la valeur au type voulu
	*/
	public static Comparable convStringToType(String type, String rawval) {
		switch(type) {
			case "Integer":
				return Integer.parseInt(rawval);
			case "Float":
				return Float.parseFloat(rawval);
			case "Character":
				return MyApp.parseChar(rawval);
			case "String":
				return rawval;
		}
		return null;
	}

	/**
	* pour convertir des chaines de caracteres en caractere
	* error si string est vide ou si longueur superieur a uns
	*/
	public static Character parseChar(String str) throws NumberFormatException {
		if (str.length()!=1)
			throw new NumberFormatException();
		return str.charAt(0);
	}

	/// getters
	// les varibales
	public DrawingBoard getThePAD() { return this.thePAD; }
	public OrderedBinaryTree getTheTree() { return this.theTREE; }
	public Cursor getTheCursor() { return this.cursor; }
	//les widgets
	public String getPJCBType() { return this.pjcb0.getType(); }
	public String getEntry2() { return this.entryAction.getText(); }

	/// les setters
	// les variables
	public void setTheTree(OrderedBinaryTree newTree) { 
		this.theTREE = newTree;
		this.thePAD.setTree(newTree);
	}
	// les widgets
	public void setEntry1(String str) { this.entryExample.setText(str); }
	public void setEntry2(String str) { this.entryAction.setText(str); }

	// fonction de console pour la console de l'applcation
	public void addLineConsole(String line) { console.addLineConsole(line); }
	public void resetConsole() { console.resetConsole(); }
	public void eraseAllCursors() { cursor.eraseAll(); }

	// fonction de MyApp pour arbre et curseur
	public void redrawTree() {
		this.thePAD.updateUI();
		
	}
	public void clean() {
		this.resetConsole();
		this.eraseAllCursors();
		this.redrawTree();
	}

	// activer/desactiver les boutons
	public void enableButtons() {
		for (JButton b : list_buttons) 
			b.setEnabled(true);
		this.pjcb0.enableCBs();
	}
	public void disableButtons() {
		for (JButton b : list_buttons) 
			b.setEnabled(false);
		this.pjcb0.disableCBs();
	}

	/* FONCTIONS POUR EXPLAINDELETELEAF */
	// si tout va bien, pas d'erreur mais au cas ou on gere ici

	/* pour appeler destroy sur l'arbre de l'application */
	public void theTREE_destroy(Code xCode) {

		try {
			this.theTREE.destroy(xCode);
		}
		catch(TreeException te) {
			this.majorIssue(te);
		}
	}

	/**
	* pour appeler addTree sur l'arbre de l'application
	*/
	public void theTREE_addTree(Code code, OrderedBinaryTree new_tree) {
		
		try {
			this.theTREE.addTree(code, new_tree);
		}
		catch(TreeException te) {
			this.majorIssue(te);
		}
	}

	public void majorIssue(Exception e) {
		/* à faire lors de problème majeur */
		this.clean();
		this.addLineConsole(" # problème majeur rencontré : ");
		this.addLineConsole(e.toString());
		e.printStackTrace();
	}
}//line:531


/*

// avoir les dimension de l'écran (finalement inutilisé)
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//double screen_width = screenSize.getWidth();
		//double screen_height = screenSize.getHeight();


		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(thePANEL, BorderLayout.WEST); // ajout du panel de commande à gauche
		mainPanel.add(thePAD, BorderLayout.EAST); // ajout du pad à droite

*/
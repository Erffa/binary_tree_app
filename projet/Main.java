package projet;

// gérer les styles
import javax.swing.UIManager;

@SuppressWarnings("rawtypes")
public class Main {
	/* Main classe uniquement pour lancer l'application */

	public static void main(String[] args) throws TreeException {
		/* la methode principale pour lancer l'application */

		// choix et application d'un style. Ranger par ordre de préférence
		String[] LookAndFeels = new String[] {
			"com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
			"com.sun.java.swing.plaf.moti.MotifLookAndFeel",
			"javax.swing.plaf.nimbus.NimbusLookAndFeel",
			"javax.swing.plaf.metal.MetalLookAndFeel"};

		// on teste les différents styles jusqu'à ce qu'un marche
		int i=0;
		while(i<4) {
			try{
				UIManager.setLookAndFeel(LookAndFeels[i]);
				System.out.println("UIManager utilisé : " + LookAndFeels[i]);
				break;
			} catch(Exception e){
				System.out.println("UIManager ignoré  : " + LookAndFeels[i]);
			} finally {
				i++;
			}
		}
		// lance l'application
		new MyApp();
	}
}//line:34
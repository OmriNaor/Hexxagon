/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

/**
 *
 * @author omri
 */
public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                HexxagonWindow b = new HexxagonWindow("Hexxagon");
                b.pack();
                b.setVisible(true);
                b.setLocationRelativeTo(null);
            }
        });

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame.ui;

import spaceracegame.DialogMainMenu;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class MainField3_0 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainField3_0.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainField3_0.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainField3_0.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainField3_0.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
        DialogMainMenu d1 = new DialogMainMenu();
        d1.createMainMenu();
    }
}

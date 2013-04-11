/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class Icons {

    public static ImageIcon iconNoMoney;
    public static ImageIcon iconEmpty;
    public static ImageIcon iconNotResearched;
    public static ImageIcon iconUSSRflag;
    public static ImageIcon iconUSAflag;
    public static ImageIcon iconUSSRflagsmall;
    public static ImageIcon iconUSAflagsmall;

    public static void initialize() {
        iconNoMoney = createImageIcon("icons/dollar.png");
        iconNotResearched = createImageIcon("icons/lightbulb_no.png");
        iconUSSRflag = createImageIcon("icons/USSRflag.png");
        iconUSAflag = createImageIcon("icons/USAflag.png");
        iconUSSRflagsmall = createImageIcon("icons/USSRflagsmall.png");
        iconUSAflagsmall = createImageIcon("icons/USAflagsmall.png");
    }

    protected static ImageIcon createImageIcon(String path) {
        return new ImageIcon(path);
    }
}

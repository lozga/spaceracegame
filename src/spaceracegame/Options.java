/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.io.*;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class Options {

    public static int displayLaunchResult = 0;
    public static String language;
    public static String langShort;
    public static int delayLaunchText = 1000;
    public static final int RESULT_INSTANT = 0;
    public static final int RESULT_SHORT = 1;
    public static final int RESULT_FULL = 2;

    public static void loadOptionsFile() {
        File file = new File("options.ini");
        if (file.exists()) {
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(file));
                String tempString;
                try {
                    while ((tempString = br.readLine()) != null) {
                        if (!tempString.startsWith("#")) {
                            String delimiter = "[=]";
                            String[] tempparsed = tempString.split(delimiter);
                            if (tempparsed.length == 2) {
                                if (tempparsed[0].equals("langShort")) {
                                    Options.langShort = tempparsed[1];
                                    Utils.loadLocalisedText();
                                }
                                if (tempparsed[0].equals("language")) {
                                    Options.language = tempparsed[1];
                                    Utils.loadLocalisedText();
                                }
                                if (tempparsed[0].equals("delayLaunchText")) {
                                    Options.delayLaunchText = Integer.parseInt(tempparsed[1]);
                                }
                            }
                        }
                    }
                    br.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
                Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Localisation.determineLanguage();
        }
    }

    public static void writeOptionsFile() {
        File file = new File("options.ini");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String s = "langShort=" + Options.langShort;
            bw.write(s);
            bw.newLine();
            s = "language=" + Options.language;
            bw.write(s);
            bw.newLine();
            s = "delayLaunchText=" + Integer.toString(Options.delayLaunchText);
            bw.write(s);
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

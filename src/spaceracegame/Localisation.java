/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.util.ArrayList;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class Localisation {

    public static ArrayList<LocalizedText> textarray = new ArrayList<LocalizedText>();

    public static void determineLanguage() {
        Options.language = System.getProperty("user.language");
        if (Options.language.toLowerCase().contains("en")) {
            Options.langShort = "en";
            Options.language="en_US";
        } else if (Options.language.toLowerCase().contains("ru")) {
            Options.langShort = "ru";
            Options.language="ru_RU";
        }
        Utils.loadLocalisedText();
    }
    
    public static String getText(String givenname){
        String returnString="";
        for (LocalizedText tempLocalizedText:textarray){
            if (tempLocalizedText.name.equals(givenname)){
                if(Options.langShort.equals("en")){
                    returnString=tempLocalizedText.en;
                }
                else if(Options.langShort.equals("ru")){
                    returnString=tempLocalizedText.ru;
                }
            }
        }
        return returnString;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import com.thoughtworks.xstream.XStream;

/**
 *
 * @author Fil
 */
public class XStreamEntity {
    private static XStream xs = new XStream();
    
    public static XStream getXStream(){
        return xs;
    }
}

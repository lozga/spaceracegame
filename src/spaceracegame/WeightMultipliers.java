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
public class WeightMultipliers {

    public static ArrayList<String[]> listweightmultipliers=new ArrayList<String[]>();

    public static int getMultiplier(String name) {
        for (int i=0;i<listweightmultipliers.size();i++){
            if (name.equals(listweightmultipliers.get(i)[0])){
                return Integer.parseInt(listweightmultipliers.get(i)[1]);
            }
        }
        return 0;
    }
}

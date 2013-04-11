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
public class LaunchResult {

    String stage;
    int dice;
    int statusint;
    String status;
    ArrayList<String> statusLong;

    public String getStage() {
        return this.stage;
    }

    public int getDice() {
        return this.dice;
    }

    public String getStatus() {
        return this.status;
    }

    public void set(String setstage, int setdice, String setstatus, ArrayList<String> setstatusLong, int setstatusint) {
        this.stage = setstage;
        this.dice = setdice;
        this.status = setstatus;
        this.statusint = setstatusint;
        this.statusLong=setstatusLong;
    }
    
    public String debugString(){
        String returnString = stage+", "+Integer.toString(dice)+", "+status+", "+Integer.toString(statusint);
        return  returnString;
    }
    
    
    public int getStatusInt(){
        return statusint;
    }
}

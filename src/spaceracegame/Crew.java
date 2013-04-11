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
public class Crew {
    int vehiclecode;
    int crewnumber;
    ArrayList<String> crewname;
    
    public void setVehicleCode(int code){
        vehiclecode=code;
    }
    
    public void setCrewNumber(int number){
        crewnumber=number;
    }
    
    public void setVehicleCrew(ArrayList<String> names){
        crewname=names;
    }
    
    public int getVehicleCode(){
        return vehiclecode;
    }
    
    public int getCrewNumber(){
        return crewnumber;
    }
    
    public ArrayList<String> getCrewName(){
        return crewname;
    }
}

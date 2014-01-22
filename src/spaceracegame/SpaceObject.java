/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class SpaceObject implements Serializable{

    int code;
    int weight;
    int durability;
    String name;
    String model;
    int prerequisite;
    int researchtime;
    int ordertime;
    int maxresearchpermonth;
    int maxdurability;
    int crew;
    ArrayList<ArrayList<String>> listCrewCapabilities;
    int costToPurchase;
    int costToResearch;
    int costToImprove;
    boolean reusable=false;
    ArrayList<LongTextLaunchData> longtext;

    public int getDurability() {
        return durability;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
    
    public String getModel(){
        return model;
    }

    public int getCapacity() {
        return weight;
    }

    public int getCode() {
        return code;
    }

    public int getPrerequisite() {
        return prerequisite;
    }

    public int getResearchTime() {
        return researchtime;
    }

    public int getOrderTime() {
        return ordertime;
    }

    public int getCrew() {
        return crew;
    }

    public int getCostToPurchase() {
        return costToPurchase;
    }

    public int getCostToResearch() {
        return costToResearch;
    }

    public int getCostToImprove() {
        return costToImprove;
    }

    public void setDurability(int value) {
        durability = value;
        if (durability < 0) {
            durability = 0;
        }
        if (durability > maxdurability) {
            durability = maxdurability;
        }
    }

    public void adjustDurability(int value) {
        durability = durability + value;
        if (durability < 0) {
            durability = 0;
        }
        if (durability > maxdurability) {
            durability = maxdurability;
        }
    }

    public void adjustDurabilityMonthly() {
        durability = durability + Utils.random(maxresearchpermonth);
        if (durability < 0) {
            durability = 0;
        }
        if (durability > maxdurability) {
            durability = maxdurability;
        }
    }

    public ArrayList<ArrayList<String>> getCrewCapabilities() {
        return listCrewCapabilities;
    }
    
    public LongTextLaunchData getLongDataByActionName (String givenname){
        for (int i=0;i<longtext.size();i++){
            if (longtext.get(i).action.equals(givenname)){
                return longtext.get(i);
            }
        }
        return null;
    }
    
  public SpaceObjectSave createSave(){
        SpaceObjectSave returnObjectSave=new SpaceObjectSave();
        
        returnObjectSave.code=code;
        returnObjectSave.weight=weight;
        returnObjectSave.durability=durability;
        returnObjectSave.researchtime=researchtime;
        returnObjectSave.ordertime=ordertime;
        returnObjectSave.maxresearchpermonth=maxresearchpermonth;
        returnObjectSave.maxdurability=maxdurability;
        returnObjectSave.costToPurchase=costToPurchase;
        returnObjectSave.costToResearch=costToResearch;
        returnObjectSave.costToImprove=costToImprove;
        
        return returnObjectSave;
    }
    
    public void loadSave(SpaceObjectSave givenSave){
    code=givenSave.code;
    weight=givenSave.weight;
    durability=givenSave.durability;
    researchtime=givenSave.researchtime;
    ordertime=givenSave.ordertime;
    maxresearchpermonth=givenSave.maxresearchpermonth;
    maxdurability=givenSave.maxdurability;
    costToPurchase=givenSave.costToPurchase;
    costToResearch=givenSave.costToResearch;
    costToImprove=givenSave.costToImprove;
    }
}

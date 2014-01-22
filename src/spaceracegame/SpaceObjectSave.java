/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Fil
 */
public class SpaceObjectSave implements Serializable {

    int code;
    int weight;
    int durability;
    int researchtime;
    int ordertime;
    int maxresearchpermonth;
    int maxdurability;
    int costToPurchase;
    int costToResearch;
    int costToImprove;
    
}

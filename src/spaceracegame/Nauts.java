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
public class Nauts implements Serializable{
   public ArrayList<Naut> listCandidates=new ArrayList<Naut>();
   public ArrayList<Naut> listNauts=new ArrayList<Naut>();
   public ArrayList<Naut> listOut = new ArrayList<Naut>();
   public int recruitmentnumber = 0;
   public int[] arrayrecruitmentpools={8,10,20,20};
   
   Player player;

    Nauts(Player givenplayer) {
        player=givenplayer;
    }

   
   public ArrayList<String> getNames(ArrayList<Naut> givenNauts){
       ArrayList<String> returnarray=new  ArrayList<String>();
       for (Naut tempNaut:givenNauts){
           returnarray.add(tempNaut.name);
       }
       return returnarray;
   }
   public Naut findNautByName(String givenname){
       for (Naut tempnaut:listNauts){
           if (tempnaut.getName().equals(givenname)){
               return tempnaut;
           }
       }
       return null;
   }
      public Naut findCandidateByName(String givenname){
       for (Naut tempnaut:listCandidates){
           if (tempnaut.getName().equals(givenname)){
               return tempnaut;
           }
       }
       return null;
   }
      public void KIANaut(String name){
          Naut tempNaut=findNautByName(name);
          tempNaut.fate=Localisation.getText("killedonmission");
          listOut.add(tempNaut);
          listNauts.remove(tempNaut);
      }
}

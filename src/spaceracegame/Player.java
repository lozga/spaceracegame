/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class Player implements Serializable {

    public int prestige = 0;
    public int cash = 100;
    public int relations_politics = 50;
    public int relations_researchers = 50;
    public int relations_military = 50;
    public int relations_production = 50;
    public int moon_recon = 33;
    public String name = "no name yet";
    public String displayedname;
    public SpaceObjectArray spaceObjectArray = new SpaceObjectArray();
    public Player player = Player.this;
    public Nauts nauts = new Nauts();
    public Missions missions = new Missions(player);
    public Crews crews = new Crews(missions);
    public PanelRIBs panelRIBs = new PanelRIBs(player);
    public PanelNauts panelnauts = new PanelNauts(player);
    public Research research = new Research(player);
    public Warehouse warehouse = new Warehouse(player);

    public void addPrestige(double value) {
        prestige = prestige + (int) value;
    }

    public String getName() {
        return name;
    }

    public void setName(String givenname) {
        name = givenname;
    }

    public int getPrestige() {
        return prestige;
    }

//    public void upldatePlayerFields(Player givenPlayer) {
//        Field[] fields =this.getClass().getDeclaredFields();
//        for (Field fieldwhere:fields){
//            try {
//                Field fieldfrom=givenPlayer.getClass().getDeclaredField(fieldwhere.getName());
//                if((fieldfrom!=null)&(!fieldfrom.equals(fieldwhere))){
//                    fieldwhere=fieldfrom;
//                }
//            } catch (NoSuchFieldException ex) {
//                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SecurityException ex) {
//                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    public void getPlayerSerializeData(PlayerSerialize givenPlayer) {
        prestige = givenPlayer.prestige;
        cash = givenPlayer.cash;
        relations_politics = givenPlayer.relations_politics;
        relations_researchers = givenPlayer.relations_researchers;
        relations_military = givenPlayer.relations_military;
        relations_production = givenPlayer.relations_production;
        moon_recon = givenPlayer.moon_recon;
        name = givenPlayer.name;
        displayedname = givenPlayer.displayedname;
        for (int i=0;i<givenPlayer.listRockets.size();i++){
            if (spaceObjectArray.listRockets.get(i).code==givenPlayer.listRockets.get(i).code){
                spaceObjectArray.listRockets.get(i).loadSave(givenPlayer.listRockets.get(i));
            }
            else{
                spaceObjectArray.findObjectByCode(givenPlayer.listRockets.get(i).code).loadSave(givenPlayer.listRockets.get(i));
            }
        }
        for (int i=0;i<givenPlayer.listPayloads.size();i++){
            if (spaceObjectArray.listPayloads.get(i).code==givenPlayer.listPayloads.get(i).code){
                spaceObjectArray.listPayloads.get(i).loadSave(givenPlayer.listPayloads.get(i));
            }
            else{
                spaceObjectArray.findObjectByCode(givenPlayer.listPayloads.get(i).code).loadSave(givenPlayer.listPayloads.get(i));
            }
        }
                for (int i=0;i<givenPlayer.listManned.size();i++){
            if (spaceObjectArray.listManned.get(i).code==givenPlayer.listManned.get(i).code){
                spaceObjectArray.listManned.get(i).loadSave(givenPlayer.listManned.get(i));
            }
            else{
                spaceObjectArray.findObjectByCode(givenPlayer.listManned.get(i).code).loadSave(givenPlayer.listManned.get(i));
            }
        }
        nauts = givenPlayer.nauts;
        missions = givenPlayer.missions;
        crews = givenPlayer.crews;
        research = givenPlayer.research;
        warehouse = givenPlayer.warehouse;
        missions.setPlayer(player);
        crews.setMissions(missions);
        research.setPlayer(player);
        warehouse.setPlayer(player);
    }
}

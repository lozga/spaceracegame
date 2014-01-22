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
public class PlayerSerialize implements Serializable {

    public int prestige;
    public int cash;
    public int relations_politics;
    public int relations_researchers;
    public int relations_military;
    public int relations_production;
    public int moon_recon;
    public String name;
    public String displayedname;
    ArrayList<SpaceObjectSave> listRockets;
    ArrayList<SpaceObjectSave> listPayloads;
    ArrayList<SpaceObjectSave> listManned;
    public Nauts nauts;
    public Missions missions;
    public Crews crews;
    public Research research;
    public Warehouse warehouse;

    public void getPlayerData(Player givenPlayer) {
        prestige = givenPlayer.prestige;
        cash = givenPlayer.cash;
        relations_politics = givenPlayer.relations_politics;
        relations_researchers = givenPlayer.relations_researchers;
        relations_military = givenPlayer.relations_military;
        relations_production = givenPlayer.relations_production;
        moon_recon = givenPlayer.moon_recon;
        name = givenPlayer.name;
        displayedname = givenPlayer.displayedname;
        listRockets=new ArrayList<SpaceObjectSave>();
        listPayloads=new ArrayList<SpaceObjectSave>();
        listManned=new ArrayList<SpaceObjectSave>();
        for (SpaceObject tempObject : givenPlayer.spaceObjectArray.listRockets) {
            listRockets.add(tempObject.createSave());
        }
        for (SpaceObject tempObject : givenPlayer.spaceObjectArray.listPayloads) {
            listPayloads.add(tempObject.createSave());
        }
        for (SpaceObject tempObject : givenPlayer.spaceObjectArray.listManned) {
            listManned.add(tempObject.createSave());
        }
        nauts = givenPlayer.nauts;
        missions = givenPlayer.missions;
        missions.removePlayer();
        crews = givenPlayer.crews;
        crews.removeMissions();
        research = givenPlayer.research;
        research.removePlayer();
        warehouse = givenPlayer.warehouse;
        warehouse.removePlayer();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.io.Serializable;

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
    public Nauts nauts = new Nauts(player);
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
}

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
public class Players{

    public static ArrayList<Player> players = new ArrayList<Player>();
    public static Player currentPlayer = new Player();

    public static void initialize() {
        players.clear();
        Player player1 = new Player();
        player1.setName("USSR");
        player1.displayedname=Localisation.getText("ussr");
        player1.cash = 50;
        Players.players.add(player1);
        Player player2=new Player();
        player2.setName("USA");
        player2.displayedname=Localisation.getText("usa");
        player2.cash=50;
        Players.players.add(player2);
        for (Player player:players){
            player.missions.MissionStorage=new ArrayList<Mission>();
        }
        Players.currentPlayer = Players.players.get(0);
    }
}

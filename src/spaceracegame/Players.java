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
public class Players {

    public static ArrayList<Player> players = new ArrayList<Player>();
    public static Player currentPlayer = new Player();

    public static void initialize(boolean ismultiplayer, boolean isfirstplayerUSSR, boolean isserver) {
        players.clear();
        Player player1 = new Player();
        if (isfirstplayerUSSR) {
            player1.setName("USSR");
            player1.displayedname = Localisation.getText("ussr");
        } else {
            player1.setName("USA");
            player1.displayedname = Localisation.getText("usa");
        }
        player1.cash = 50;
        Players.players.add(player1);
        Player player2 = new Player();
        if (isfirstplayerUSSR) {
            player2.setName("USA");
            player2.displayedname = Localisation.getText("usa");
        } else {
            player2.setName("USSR");
            player2.displayedname = Localisation.getText("ussr");
        }
        player2.cash = 50;
        Players.players.add(player2);
        for (Player player : players) {
            player.missions.MissionStorage = new ArrayList<Mission>();
        }
        if (isserver) {
            Players.currentPlayer = Players.players.get(0);
        } else {
            Players.currentPlayer = Players.players.get(1);
        }
        if (!ismultiplayer){
            Players.currentPlayer=Players.players.get(0);
        }
    }
}

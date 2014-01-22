/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import com.thoughtworks.xstream.XStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Fil
 */
public class SocketServer implements Runnable {

    static ServerSocket server;
    static Socket client;
    static Thread t;
    static PrintWriter out;
    static BufferedReader in;
    static String strIn;

    public static void CreateServerSocket() {
        try {
            server = new ServerSocket(9999);
            t = new Thread(new SocketServer());
            t.start();
        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void closeServer() {
        try {
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendSide(String side) {
        out.println("side");
        out.println(side);
    }

    public static void sendStartGame() {
        out.println("startgame");
    }

    public static void sendServerIsAlive() {
        out.println("serverisalive");
    }

    public static void sendServerPrestige() {
        out.println("serverprestige");
        out.println(Players.players.get(0).prestige);
    }

    public static void sendRefreshField() {
        out.println("refreshgamefield");
    }

    public static void sendClientPlayertoClient() {
        XStream xs = new XStream();
        GameSave gs = new GameSave();
        ArrayList<PlayerSerialize> listps = new ArrayList<PlayerSerialize>();

        PlayerSerialize ps = new PlayerSerialize();
        ps.getPlayerData(Players.players.get(1));
        listps.add(ps);
        gs.playersSerialize = listps;
        gs.gamedate = Dateutils.gamedate;
        String s = xs.toXML(gs);
        Utils.debugWriteStringToFile(s, "clientplayertoclient");
        out.println("clientplayertoclient");
        out.println(s);
    }

    @Override
    public void run() {

        try {
            client = server.accept();
            DialogMultiplayerStart.updateAfterConnection();
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out.println("test from server");
            
            while ((strIn = in.readLine()) != null) {
                System.out.println(strIn);
//                JOptionPane.showMessageDialog(null, strIn);
                if (strIn.contains("clientplayertoserver")) {
//                    strIn = in.readLine();
//                    strIn = strIn.replace("**", "\n");
                    String s=new String();
                    while(!(strIn=in.readLine()).contains("clientready")){
                        s=s+strIn;
                    }
                    XStream xs = new XStream();
                    GameSave gs = (GameSave) xs.fromXML(s);
                    Players.players.get(1).getPlayerSerializeData(gs.playersSerialize.get(0));
                 PanelNextTurn.setClientReady();
//                } else if (strIn.contains("clientready")) {
//                    PanelNextTurn.setClientReady();
                } else if (strIn.contains("clientisalive")) {
                    DialogMainField.startNewGame(true, true, DialogMultiplayerStart.isFirstPlayerUSSR);
                    DialogMultiplayerStart.callDispose();

                }
            }
            out.println("CYCLE ENDED!!!!!!!!!!!!!!!!!!!!!!");
        } catch (IOException ex) {
            out.println("caught error");
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

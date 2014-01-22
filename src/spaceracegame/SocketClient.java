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
import java.net.InetSocketAddress;
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
public class SocketClient implements Runnable {

    static Socket client;
    static Thread t;
    static PrintWriter out;
    static BufferedReader in;
    static String strIn;

    public static boolean CreateClientSocket(InetAddress ip) {
        try {
            client = new Socket();
            client.connect(new InetSocketAddress(ip, 9999), 0);
            t = new Thread(new SocketClient());
            t.start();
            return true;
        } catch (UnknownHostException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static void closeClient() {
        try {
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendClientPlayertoServer() {
        try {
            out.println("procedure start");
            XStream xs =XStreamEntity.getXStream(); //new XStream();
            out.println("xs created");
            GameSave gs = new GameSave();
            out.println("gs created");
            ArrayList<PlayerSerialize> listps = new ArrayList<PlayerSerialize>();
            PlayerSerialize ps = new PlayerSerialize();
            out.println("ps created");
            ps.getPlayerData(Players.players.get(1));
            listps.add(ps);
            gs.playersSerialize = listps;
            gs.gamedate = Dateutils.gamedate;
            String s =xs.toXML(gs);
            out.println("gs converted");
            String[] ssplit = s.split("\n");
//            s = s.replace("\n", "**");
//            Utils.debugWriteStringToFile(t.toString(), "threaddata");
//            Utils.debugWriteStringToFile(s, "clientplayertoserver");
            out.println("clientplayertoserver");
//            out.println(s);
            for (String spart : ssplit) {
                out.println(spart);
            }
            out.println("clientready");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
            Utils.debugWriteStringToFile(e.toString(), "sendclientexception");
        }
    }

    public static void sendClientReady() {
        out.println("clientready");
        out.flush();
    }

    public static void sendClientIsAlive() {
        out.println("clientisalive");
        out.flush();
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while ((strIn = in.readLine()) != null) {
//                JOptionPane.showMessageDialog(null, strIn);
                System.out.println(strIn);
                if (strIn.contains("side")) {
                    strIn = in.readLine();
                    System.out.println(strIn);
                    DialogMultiplayerStart.setSide(strIn);
                } else if (strIn.contains("startgame")) {
                    DialogMainField.startNewGame(true, false, DialogMultiplayerStart.isFirstPlayerUSSR);
                    DialogMultiplayerStart.callDispose();
                } else if (strIn.contains("serverprestige")) {
                    strIn = in.readLine();
                    System.out.println(strIn);
                    Players.players.get(0).prestige = Integer.parseInt(strIn);
                } else if (strIn.contains("clientplayertoclient")) {
                    strIn = in.readLine();
                    System.out.println(strIn);
                    XStream xs = new XStream();
                    GameSave gs = (GameSave) xs.fromXML(strIn);
                    Players.players.get(1).getPlayerSerializeData(gs.playersSerialize.get(0));
                } else if (strIn.contains("refreshgamefield")) {
                    PanelNextTurn.refreshGameField();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

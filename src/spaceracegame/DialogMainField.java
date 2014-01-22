/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

//import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStream;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class DialogMainField {

    public static JFrame mainFrame;
    static PanelLabelTable plt;
    static JPanel cardsPanel;
    static JFileChooser fc = new JFileChooser();

    public static void createMainField() {
        mainFrame = new JFrame(Localisation.getText("gamename")+" "+Boolean.toString(Options.isserver));
        Utils.setDamnedSize(mainFrame, new Dimension(900, 700));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        createTopMenu();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 5;
        gbc.weighty = 5;
        gbc.gridheight = 3;
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JPanel panelLeft = new JPanel(new GridLayout(1, 0));
        panelLeft.setBackground(Color.DARK_GRAY);
        if (Options.ismultiplayergame) {
            panelLeft.add(createMultiplayerPanel());
        } else {
            panelLeft.add(createTabbedPanels());
        }
        mainPanel.add(panelLeft, gbc);

        gbc.weightx = 5;
        gbc.weighty = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 3;
        JPanel panelBottom = new JPanel(new GridLayout(1, 0));
        panelBottom.setBackground(Color.GRAY);
        panelBottom.add(PanelLabelTable.createTable());
        mainPanel.add(panelBottom, gbc);

        gbc.weightx = 1;
        gbc.weighty = 5;
        gbc.gridheight = 3;
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 0;
        JPanel panelRight = new JPanel(new GridLayout(1, 0));
        panelRight.setBackground(Color.LIGHT_GRAY);
        panelRight.add(PanelWarehouse.createWarehousePanel());
        mainPanel.add(panelRight, gbc);

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 3;
        JPanel panelRightBottom = new JPanel(new GridLayout(1, 0));
        panelRightBottom.setBackground(Color.decode("#A0A0A0"));
        panelRightBottom.add(PanelNextTurn.createNextTurnButtonPanel());
        mainPanel.add(panelRightBottom, gbc);

//        gbc.weightx = 1;
//        gbc.weighty = 1;
//        gbc.gridheight = 1;
//        gbc.gridwidth = 1;
//        gbc.gridx = 4;
//        gbc.gridy = 4;
//        JPanel panelRightBottom2 = new JPanel(new GridLayout(1, 0));
//        panelRightBottom.setBackground(Color.decode("#B0B0B0"));
//        mainPanel.add(panelRightBottom2, gbc);

        mainFrame.setContentPane(mainPanel);
        mainFrame.pack();
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);

        Utils.fixComponentSize(panelLeft);
        Utils.fixComponentSize(panelRight);
        Utils.fixComponentSize(panelBottom);
        Utils.fixComponentSize(panelRightBottom);
    }

    public static JPanel createTabbedPanels() {
        cardsPanel = new JPanel(new CardLayout());
        JPanel[] cards = new JPanel[Players.players.size()];
        for (int i = 0; i < Players.players.size(); i++) {
            Player player = Players.players.get(i);
            cards[i] = createOneCard(player);
            cardsPanel.add(cards[i], player.name);
        }
        return cardsPanel;
    }

    public static JPanel createMultiplayerPanel() {
        JPanel returnpanel = new JPanel(new GridLayout(0,1));

        if (Options.isserver) { //Server is always first player
            returnpanel.add(createOneCard(Players.players.get(0)));
        } else {
            returnpanel.add(createOneCard(Players.players.get(1)));
        }
        return returnpanel;
    }

    public static JPanel createOneCard(Player givenPlayer) {
        JPanel returnPanel = new JPanel(new GridLayout(1, 0));
        JTabbedPane tabbedMainPane = new JTabbedPane();
        tabbedMainPane.addTab(Localisation.getText("researchbuy"), givenPlayer.panelRIBs.createRIBPanel());
        tabbedMainPane.addTab(Localisation.getText("nauts"), givenPlayer.panelnauts.createPanelNauts());
        returnPanel.add(tabbedMainPane);
        return returnPanel;
    }

    public static void switchCard(Player givenPlayer) {
        CardLayout cl = (CardLayout) (cardsPanel.getLayout());
        cl.show(cardsPanel, givenPlayer.name);
    }

    public static void createTopMenu() {
        JMenuBar menubar = new JMenuBar();
        JMenu menugame = new JMenu(Localisation.getText("game"));
        JMenuItem menusave = new JMenuItem(Localisation.getText("savegame"));
        menusave.addActionListener(listenerSaveGame);
        menugame.add(menusave);
        JMenuItem menuload = new JMenuItem(Localisation.getText("loadgame"));
        menuload.addActionListener(listenerLoadGame);
        menugame.add(menuload);
        menugame.addSeparator();
        JMenuItem menuquit = new JMenuItem(Localisation.getText("quit"));
        menuquit.addActionListener(listenerQuit);
        menugame.add(menuquit);
        menubar.add(menugame);
        mainFrame.setJMenuBar(menubar);
    }
    private static ActionListener listenerQuit = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };
    private static ActionListener listenerLoadGame = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadGame(mainFrame);
        }
    };
    private static ActionListener listenerSaveGame = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveGame(mainFrame);
        }
    };

    public static void saveGame(Component givenComponent) {
        int retval = fc.showSaveDialog(givenComponent);
        if (retval == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
                XStream xs = new XStream();
                GameSave gs = new GameSave();
                ArrayList<PlayerSerialize> listps = new ArrayList<PlayerSerialize>();
                for (Player tempplayer : Players.players) {
                    PlayerSerialize ps = new PlayerSerialize();
                    ps.getPlayerData(tempplayer);
                    listps.add(ps);
                }
                gs.playersSerialize = listps;
                gs.gamedate = Dateutils.gamedate;
                String s = xs.toXML(gs);
                bw.write(s);
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(DialogMainField.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static int loadGame(Component givenComponent) {
        int result = 0;
        int retval = fc.showOpenDialog(givenComponent);
        if (retval == JFileChooser.APPROVE_OPTION) {
            try {
                mainFrame.dispose();
            } catch (Exception ex) {
                Logger.getLogger(DialogMainField.class.getName()).log(Level.SEVERE, null, ex);
            }
            Players.initialize(false, true, true);
            try {
                spaceracegame.Utils.loadgamedata();
            } catch (ParseException ex) {
                Logger.getLogger(DialogMainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
//            Icons.initialize();
            Dateutils.initialize();
            for (Player tempPlayer : Players.players) {
                tempPlayer.warehouse.initialize();
                tempPlayer.research.initialize();
            }
            File file = fc.getSelectedFile();
            XStream xs = new XStream();
            GameSave gs = (GameSave) xs.fromXML(file);
            for (int i = 0; i < Players.players.size(); i++) {
                Players.players.get(i).getPlayerSerializeData(gs.playersSerialize.get(i));
            }

//            Players.players=gs.players;

//              for (int i=0;i<Players.players.size();i++){
//                Players.players.get(i).upldatePlayerFields(gs.players.get(i));
//            }          
            Dateutils.gamedate = gs.gamedate;
            LaunchWindows.generateLaunchWindows();
            DialogMainField.createMainField();

            for (Player tempPlayer : Players.players) {
                tempPlayer.panelRIBs.verifybuttons();
                tempPlayer.panelRIBs.updatelabels();
            }
            Players.currentPlayer = Players.players.get(0);
            PanelLabelTable.setTableData();
            PanelWarehouse.setWarehouse();
            result = 1;
        }
        return result;
    }

    public static void startNewGame(boolean multiplayer, boolean isserver, boolean isplayer1USSR) {
        Options.ismultiplayergame = multiplayer;
        Options.isserver = isserver;
        Players.initialize(multiplayer, isplayer1USSR, isserver);
        try {
            spaceracegame.Utils.loadgamedata();
        } catch (ParseException ex) {
            Logger.getLogger(DialogMainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
//            Icons.initialize();
        Dateutils.initialize();
        for (Player tempPlayer : Players.players) {
            tempPlayer.warehouse.initialize();
            tempPlayer.research.initialize();
        }
        LaunchWindows.generateLaunchWindows();
        DialogMainField.createMainField();
        Players.currentPlayer.panelRIBs.verifybuttons();
        PanelWarehouse.setWarehouse();
        if (!Options.isserver){
            SocketClient.sendClientIsAlive();
        }
        else{
            SocketServer.sendServerIsAlive();
        }
    }
}

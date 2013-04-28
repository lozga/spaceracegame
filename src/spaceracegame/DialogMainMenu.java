/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class DialogMainMenu {

    JFrame mainFrame;

    public void createMainMenu() {
        Options.loadOptionsFile();
        mainFrame = new JFrame(Localisation.getText("gamename"));
        Utils.setDamnedSize(mainFrame, new Dimension(300, 400));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(0, 1));

        JButton buttonNewGame = new JButton(Localisation.getText("newgame"));
        buttonNewGame.addActionListener(listenerNewGame);
        mainPanel.add(buttonNewGame);

        JButton buttonLoadGame = new JButton(Localisation.getText("loadgame"));
        buttonLoadGame.addActionListener(listenerLoadGame);
        mainPanel.add(buttonLoadGame);

        JButton buttonMultiplayer = new JButton(Localisation.getText("multiplayer"));
        buttonMultiplayer.addActionListener(listenerMultiplayer);
        buttonMultiplayer.setEnabled(false);
        mainPanel.add(buttonMultiplayer);

        JButton buttonOptions = new JButton(Localisation.getText("options"));
        buttonOptions.addActionListener(listenerOptions);
        mainPanel.add(buttonOptions);

        JButton buttonQuit = new JButton(Localisation.getText("quit"));
        buttonQuit.addActionListener(listenerQuit);
        mainPanel.add(buttonQuit);

        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);

    }
    private ActionListener listenerNewGame = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.dispose();
            Players.initialize();
            try {
                spaceracegame.Utils.loadgamedata();
            } catch (ParseException ex) {
                Logger.getLogger(DialogMainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }

            Icons.initialize();
            Dateutils.initialize();
            for (Player tempPlayer : Players.players) {
                tempPlayer.warehouse.initialize();
                tempPlayer.research.initialize();

            }
            LaunchWindows.generateLaunchWindows();
            DialogMainField.createMainField();
            Players.currentPlayer.panelRIBs.verifybuttons();
            PanelWarehouse.setWarehouse();
        }
    };
    private ActionListener listenerLoadGame = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = DialogMainField.loadGame(mainFrame);
            if (result == 1) {
                mainFrame.dispose();
            }
        }
    };
    private ActionListener listenerMultiplayer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    };
    private ActionListener listenerOptions = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.dispose();
            DialogOptions.createOptionsDialog();
        }
    };
    private ActionListener listenerQuit = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };
}

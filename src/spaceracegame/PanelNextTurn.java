/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class PanelNextTurn {

    private static JLabel labelNextMonthErrors;
    private static JButton buttonNextTurn;
    private static boolean isserverready = false;
    private static boolean isclientready = false;

    public static JPanel createNextTurnButtonPanel() {
        JPanel returnPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("errors"), TitledBorder.CENTER, TitledBorder.CENTER);
        labelNextMonthErrors = new JLabel();
        labelNextMonthErrors.setBorder(title);
        labelNextMonthErrors.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        returnPanel.add(labelNextMonthErrors, gbc);
        buttonNextTurn = new JButton(Localisation.getText("nextturn"));
        buttonNextTurn.addActionListener(listenerNextTurn);
        gbc.gridy = 1;
        returnPanel.add(buttonNextTurn, gbc);

        return returnPanel;
    }
    private static ActionListener listenerNextTurn = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!Options.ismultiplayergame) {
                nextPlayerOrTurn();
            } else if (Options.isserver) {
                nextTurnServer();
            } else if (!Options.isserver) {
                nextTurnClient();
            }
        }
    };

    public static void setNextTurn(Boolean state, String errorText) {
        buttonNextTurn.setEnabled(state);
        labelNextMonthErrors.setText(errorText);
    }

    private static void nextPlayerOrTurn() {

        if (Players.currentPlayer.equals(Players.players.get(0))) {
            Players.currentPlayer = Players.players.get(1);
        } else {
            Dateutils.nextmonth();
            Players.currentPlayer = Players.players.get(0);
            ArrayList<Mission> listmissions = new ArrayList<Mission>();
            ArrayList<Player> listplayers = new ArrayList<Player>();
            for (Player tempPlayer : Players.players) {
                for (int i = 0; i < tempPlayer.missions.MissionStorage.size(); i++) {
                }
                Iterator<Mission> itr = tempPlayer.missions.MissionStorage.iterator();
                while (itr.hasNext()) {
                    Mission tempMission = itr.next();
                    if (tempMission.missiondate.equals(Dateutils.gamedate)) {
                        listmissions.add(tempMission);
                        listplayers.add(tempPlayer);
                    }
                }
            }
            while (listmissions.size() > 0) {
                int number = Utils.random(listmissions.size());
                Mission tempMission = listmissions.remove(number);
                Player tempPlayer = listplayers.remove(number);
                DialogLaunchMission tmp = new DialogLaunchMission(tempPlayer, tempMission);
                tmp.createLaunchAndResultDialog();
            }
            Calendar tempCalendar = GregorianCalendar.getInstance();
            tempCalendar.setTime(Dateutils.gamedate);
            if (tempCalendar.get(Calendar.MONTH) == Calendar.JANUARY) {
                addBudget();
            }
        }
        Players.currentPlayer.warehouse.parseDelivery();
        Players.currentPlayer.research.parseResearch();
        DialogMainField.switchCard(Players.currentPlayer);
        PanelLabelTable.setTableData();
        PanelWarehouse.setWarehouse();
        Players.currentPlayer.panelRIBs.conductresearch();
        Players.currentPlayer.panelRIBs.verifybuttons();
        Players.currentPlayer.panelRIBs.updatelabels();
    }

    private static void nextTurnServer() {
        buttonNextTurn.setText(Localisation.getText("waitforclient"));
        buttonNextTurn.setEnabled(false);
        isserverready = true;
        checkNewTurnMultiplayer();
    }

    private static void nextTurnClient() {
        buttonNextTurn.setText(Localisation.getText("waitforhost"));
        buttonNextTurn.setEnabled(false);
        SocketClient.sendClientPlayertoServer();
//        SocketClient.sendClientReady();
    }

    public static void addBudget() {
        int budget = 50;
        for (Player player : Players.players) {
            for (Prestige prestige : PrestigeArray.prestigelist) {
                if (prestige.achievedlist.contains(player.name)) {
                    budget = budget + prestige.bonus;
                }
            }
            player.cash = player.cash + budget;
        }
    }

    public static void setClientReady() {
        isclientready = true;
        checkNewTurnMultiplayer();
    }

    public static void checkNewTurnMultiplayer() {
        if (isserverready && isclientready) {
            isserverready = false;
            isclientready = false;
            Dateutils.nextmonth();
            ArrayList<Mission> listmissions = new ArrayList<Mission>();
            ArrayList<Player> listplayers = new ArrayList<Player>();
            for (Player tempPlayer : Players.players) {
                for (int i = 0; i < tempPlayer.missions.MissionStorage.size(); i++) {
                }
                Iterator<Mission> itr = tempPlayer.missions.MissionStorage.iterator();
                while (itr.hasNext()) {
                    Mission tempMission = itr.next();
                    if (tempMission.missiondate.equals(Dateutils.gamedate)) {
                        listmissions.add(tempMission);
                        listplayers.add(tempPlayer);
                    }
                }
            }

            if (Options.isserver) {
                SocketServer.sendServerPrestige();
                SocketServer.sendClientPlayertoClient();
                int number = Utils.random(listmissions.size());
                Mission tempMission = listmissions.remove(number);
                Player tempPlayer = listplayers.remove(number);
                DialogLaunchMission tmp = new DialogLaunchMission(tempPlayer, tempMission);
                tmp.createLaunchAndResultDialog();
            } else {
            }
            Calendar tempCalendar = GregorianCalendar.getInstance();
            tempCalendar.setTime(Dateutils.gamedate);
            if (tempCalendar.get(Calendar.MONTH) == Calendar.JANUARY) {
                addBudget();
            }
            refreshGameField();
            SocketServer.sendRefreshField();

        }
    }

    public static void refreshGameField() {
        Players.currentPlayer.warehouse.parseDelivery();
        Players.currentPlayer.research.parseResearch();
        PanelLabelTable.setTableData();
        PanelWarehouse.setWarehouse();
        Players.currentPlayer.panelRIBs.conductresearch();
        Players.currentPlayer.panelRIBs.verifybuttons();
        Players.currentPlayer.panelRIBs.updatelabels();
        buttonNextTurn.setText(Localisation.getText("nextturn"));
        buttonNextTurn.setEnabled(true);
    }
}

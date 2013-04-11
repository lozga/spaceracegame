/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.Color;
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

    public static JPanel createNextTurnButtonPanel() {
        JPanel returnPanel = new JPanel(new GridLayout(0, 1));

        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Errors", TitledBorder.CENTER, TitledBorder.CENTER);
        labelNextMonthErrors = new JLabel();
        labelNextMonthErrors.setBorder(title);
        labelNextMonthErrors.setHorizontalAlignment(JLabel.CENTER);
        returnPanel.add(labelNextMonthErrors);
        buttonNextTurn = new JButton(Localisation.getText("nextturn"));
        buttonNextTurn.addActionListener(listenerNextTurn);
        returnPanel.add(buttonNextTurn);

        return returnPanel;
    }
    private static ActionListener listenerNextTurn = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            changePlayerOrTurn();
        }
    };

    public static void setNextTurn(Boolean state, String errorText) {
        buttonNextTurn.setEnabled(state);
        labelNextMonthErrors.setText(errorText);
    }

    private static void changePlayerOrTurn() {

        if (Players.currentPlayer.name.equals("USSR")) {
            Players.currentPlayer = Players.players.get(1);
        } else {
            Dateutils.nextmonth();
            Players.currentPlayer = Players.players.get(0);
            ArrayList<Mission> listmissions = new ArrayList<Mission>();
                        ArrayList<Player> listplayers = new ArrayList<Player>();
            for (Player tempPlayer : Players.players) {
                for (int i=0;i<tempPlayer.missions.MissionStorage.size();i++){
                    
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
                Player tempPlayer=listplayers.remove(number);
                DialogLaunchMission tmp = new DialogLaunchMission(tempPlayer,tempMission);
                tmp.createLaunchAndResultDialog();
            }
            Calendar tempCalendar= GregorianCalendar.getInstance();
            tempCalendar.setTime(Dateutils.gamedate);
            if(tempCalendar.get(Calendar.MONTH)==Calendar.JANUARY){
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
    
    public static void addBudget(){
        int budget=50;
        for (Player player:Players.players){
            for (Prestige prestige:PrestigeArray.prestigelist){
                if (prestige.achievedlist.contains(player.name)){
                    budget=budget+prestige.bonus;
                }
            }
            player.cash=player.cash+budget;
        }
    }
}

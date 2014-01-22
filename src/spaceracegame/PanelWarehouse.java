/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class PanelWarehouse {

    private static JLabel labelWarehouse;
    private static JLabel labelInfo;
    private static JLabel labelFlag;
    private static JLabel labelPrestige;

    public static JPanel createWarehousePanel() {
        JPanel returnPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton buttonNewMission = new JButton("<html>" + Localisation.getText("newmission") + "</html>");
        buttonNewMission.addActionListener(listenerNewMission);
        returnPanel.add(buttonNewMission, gbc);

        JPanel panelStatsandFlag = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSF = new GridBagConstraints();
        TitledBorder titleInfo = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("info"), TitledBorder.CENTER, TitledBorder.CENTER);
        panelStatsandFlag.setBorder(titleInfo);
        labelInfo = new JLabel("");
        labelInfo.setHorizontalAlignment(JLabel.LEFT);
        labelInfo.setVerticalAlignment(JLabel.TOP);
        gbcSF.gridx = 0;
        gbcSF.gridy = 0;
        gbcSF.fill = GridBagConstraints.HORIZONTAL;
        gbcSF.weightx = 1;
        gbcSF.anchor = GridBagConstraints.LINE_START;
        panelStatsandFlag.add(labelInfo, gbcSF);

        labelFlag = new JLabel();
        gbcSF.gridx = 1;
        gbcSF.weightx = 0;
        gbcSF.anchor = GridBagConstraints.LINE_END;
        panelStatsandFlag.add(labelFlag, gbcSF);

        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;
        returnPanel.add(panelStatsandFlag, gbc);

        labelPrestige = new JLabel("");
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("prestige"), TitledBorder.CENTER, TitledBorder.CENTER);
        labelPrestige.setBorder(title);
        labelPrestige.setHorizontalAlignment(JLabel.LEFT);
        labelPrestige.setVerticalAlignment(JLabel.TOP);
        gbc.gridy = 2;
        returnPanel.add(labelPrestige, gbc);

        labelWarehouse = new JLabel("");
        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("warehouse"), TitledBorder.CENTER, TitledBorder.CENTER);
        labelWarehouse.setBorder(title);
        labelWarehouse.setHorizontalAlignment(JLabel.LEFT);
        labelWarehouse.setVerticalAlignment(JLabel.TOP);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 10;
        gbc.gridy = 3;
        returnPanel.add(labelWarehouse, gbc);

        return returnPanel;
    }

    public static void setWarehouse() {
        labelWarehouse.setText(Players.currentPlayer.warehouse.getCurrentWarehouse());
        setInfoData();
        if (Players.currentPlayer.name.equals("USSR")) {
            labelFlag.setIcon(Icons.iconUSSRflag);
        } else {
            labelFlag.setIcon(Icons.iconUSAflag);
        }
    }

    public static void setInfoData() {
        String returnString = "<html>";
        returnString = returnString + Localisation.getText("date") + ": <b>" + Dateutils.gamedateformat.format(Dateutils.gamedate) + "</b><br>";
        returnString = returnString + Localisation.getText("budget") + ": <b>" + Integer.toString(Players.currentPlayer.cash) + "</b><br>";
        returnString = returnString + Localisation.getText("prestige") + ": <b>" + Integer.toString(Players.currentPlayer.prestige) + "</b><br>";
        returnString = returnString + "</html>";
        labelInfo.setText(returnString);

        returnString = "<html>";
        for (Player player : Players.players) {
            returnString = returnString + player.displayedname;
            if (player.equals(Players.currentPlayer)) {
                returnString = returnString + " " + Localisation.getText("you");
            }
            returnString = returnString + ": <b>" + Integer.toString(player.prestige) + "</b><br>";
        }
        returnString = returnString + "</html>";
        labelPrestige.setText(returnString);
    }
    private static ActionListener listenerNewMission = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            DialogNewMission tmp = new DialogNewMission();
            tmp.createAndPopulateNewMissionDialog();
            PanelLabelTable.setTableData();
        }
    };
}

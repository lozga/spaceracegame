/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class PanelLabelTable {

    static JPanel panelLabelTable;
    static int rows = 5;
    static int cols = 8;
    public static JLabel[][] labelTable = new JLabel[rows][cols];

    public static JPanel createTable() {
        panelLabelTable = new JPanel();
        setTableData();
        return panelLabelTable;
    }

    public static void setTableData() {


        prepareTable();

        Calendar tempcalendar = GregorianCalendar.getInstance();
        tempcalendar.setTime(Dateutils.getDateMonthShift(1));
        Date tempdate = new Date();
        for (int i = 1; i < cols - 1; i++) {
            tempdate.setTime(tempcalendar.getTimeInMillis());
            labelTable[0][i].setText(Dateutils.gamedateformat.format(tempdate));
            tempcalendar.add(Calendar.MONTH, 1);
        }
        labelTable[0][0].setText(Localisation.getText("month"));
        labelTable[0][cols - 1].setText(Localisation.getText("later"));

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++) {
                if (j == 0) {
                    switch (i) {
                        case 1:
                            labelTable[i][j].setText(Localisation.getText("research"));
                            break;
                        case 2:
                            labelTable[i][j].setText(Localisation.getText("purchase"));
                            break;
                        case 3:
                            labelTable[i][j].setText(Localisation.getText("missions"));
                            break;
                        case 4:
                            labelTable[i][j].setText(Localisation.getText("launchwindows"));
                            break;
                    }
                } else {
                    tempdate = Dateutils.getDateMonthShift(j);
                    String tempstring = "<html>";
                    switch (i) {
                        case 1: {
                            boolean[] tempcount = new boolean[1000];
                            for (int k = 0; k < tempcount.length; k++) {
                                tempcount[k] = Players.currentPlayer.research.getResearchByDateYesNo(tempdate, k);
                            }
                            for (int k = 0; k < tempcount.length; k++) {
                                if (tempcount[k] == true) {
                                    tempstring = tempstring + Players.currentPlayer.spaceObjectArray.findObjectByCode(k).getName() + "<br>";
                                }
                            }
                        }
                        break;
                        case 2: {
                            int[] tempcount = new int[Players.currentPlayer.warehouse.storehouse.length];
                            for (int k = 0; k < tempcount.length; k++) {
                                tempcount[k] = Players.currentPlayer.warehouse.getDeliveryByDateCountObject(tempdate, k);
                            }
                            for (int k = 0; k < tempcount.length; k++) {
                                if (tempcount[k] > 0) {
                                    tempstring = tempstring + " "+Players.currentPlayer.spaceObjectArray.findObjectByCode(k).getName()+": "+ Integer.toString(tempcount[k]) + "<br>";
                                }
                            }
                        }
                        break;
                        case 3: {
                            for (MissionTypes tempMissionType : MissionTypesArray.listMissionTypes) {
                                int tempcount = 0;
                                for (Mission tempMission : Players.currentPlayer.missions.MissionStorage) {
                                    if (((tempMission.missiondate.equals(tempdate)))
                                            & (tempMissionType.getCode() == tempMission.missioncode)) {
                                        tempcount++;
                                    }
                                }
                                if (tempcount > 0) {
                                    tempstring = tempstring + Integer.toString(tempcount) + " " + tempMissionType.getName() + "<br>";
                                }
                            }
                        }
                        break;
                        case 4: {
                            tempstring = tempstring + LaunchWindows.searchWindowsforMonth(tempdate);
                        }
                        break;
                    }
                    tempstring = tempstring + "</html>";
                    labelTable[i][j].setText(tempstring);
                }
            }
        }
        for (int i = 1; i < rows; i++) {
            tempdate = Dateutils.getDateMonthShift(cols - 2);
            String tempstring = "<html>";
            switch (i) {
                case 1: {
                    boolean[] tempcount = new boolean[1000];
                    for (int k = 0; k < tempcount.length; k++) {
                        tempcount[k] = Players.currentPlayer.research.getResearchAfterDateYesNo(tempdate, k);
                    }
                    for (int k = 0; k < tempcount.length; k++) {
                        if (tempcount[k] == true) {
                            tempstring = tempstring + Players.currentPlayer.spaceObjectArray.findObjectByCode(k).getName() + "<br>";
                        }
                    }
                }
                break;
                case 2: {
                    int[] tempcount = new int[Players.currentPlayer.warehouse.storehouse.length];
                    for (int k = 0; k < tempcount.length; k++) {
                        tempcount[k] = Players.currentPlayer.warehouse.getDeliveryAfterDateCountObject(tempdate, k);
                    }
                    for (int k = 0; k < tempcount.length; k++) {
                        if (tempcount[k] > 0) {
                            tempstring = tempstring + " "+Players.currentPlayer.spaceObjectArray.findObjectByCode(k).getName()+": "+ Integer.toString(tempcount[k]) + "<br>";
                        }
                    }
                }
                break;
                case 3: {
                    for (MissionTypes tempMissionType : MissionTypesArray.listMissionTypes) {
                        int tempcount = 0;
                        for (Mission tempMission : Players.currentPlayer.missions.MissionStorage) {
                            if (((tempMission.missiondate.after(tempdate)))
                                    & (tempMissionType.getCode() == tempMission.missioncode)) {
                                tempcount++;
                            }
                        }
                        if (tempcount > 0) {
                            tempstring = tempstring + Integer.toString(tempcount) + " " + tempMissionType.getName() + "<br>";
                        }
                    }
                }
                break;
            }
            tempstring = tempstring + "</html>";
            labelTable[i][cols - 1].setText(tempstring);
        }
        panelLabelTable.removeAll();
        panelLabelTable.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                panelLabelTable.add(labelTable[i][j], gbc);

            }
        }
    }

    public static void prepareTable() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (labelTable[i][j] == null) {
                    labelTable[i][j] = new JLabel();
                    labelTable[i][j].setText("");
                    labelTable[i][j].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
                    labelTable[i][j].setHorizontalAlignment(JLabel.CENTER);
                }

            }
        }

    }
}

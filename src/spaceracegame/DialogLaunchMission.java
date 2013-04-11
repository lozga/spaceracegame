/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class DialogLaunchMission {

    javax.swing.Timer timerScrubCountdown;
    JLabel labelLaunchDialogCountdown;
    JToggleButton toggleLaunchDialogDisplayResult;
    JToggleButton toggleLaunchDialogDisplayShort;
    JToggleButton toggleLaunchDialogDisplayFull;
    JToggleButton toggleLaunchDialogScrub;
    JDialog dialogLaunchDialog;
    JButton buttonLaunchDialogClose;
    JLabel labelLaunchDialogResult;
    javax.swing.Timer timerDisplayResult = new Timer(Options.delayLaunchText, null);
    Mission currentmission;
    Player currentplayer;
    int stepcounter;
    int longstepcounter;
    boolean isStageName;
    ArrayList<String> launchLongResultText = new ArrayList<String>();
    String prestigeresult = "";

    DialogLaunchMission(Player givenPlayer, Mission givenMission) {
        currentmission = givenMission;
        currentplayer = givenPlayer;

    }

//    public void createLaunchAndResultDialog(Player givenPlayer) {
//        currentmission = givenPlayer.missions.getFirstAvailableMission();
    public void createLaunchAndResultDialog() {
        int width = 500;
        dialogLaunchDialog = new JDialog((Frame) null, Localisation.getText("launchmission"), true);
        dialogLaunchDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Utils.setDamnedSize(dialogLaunchDialog, new Dimension(width, 800));
        JPanel panelLaunchDialogMain = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panelLaunchDialogDisplaySelection = new JPanel(new GridLayout(1, 0));

        panelLaunchDialogDisplaySelection.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), Localisation.getText("resultdisplayoptions"), TitledBorder.CENTER, TitledBorder.CENTER));
        ButtonGroup groupToggleLaunchDialogDisplayMode = new ButtonGroup();
        toggleLaunchDialogDisplayResult = new JToggleButton(Localisation.getText("onlyresult"));
        groupToggleLaunchDialogDisplayMode.add(toggleLaunchDialogDisplayResult);
        panelLaunchDialogDisplaySelection.add(toggleLaunchDialogDisplayResult);

        toggleLaunchDialogDisplayShort = new JToggleButton(Localisation.getText("shortsequence"));
        groupToggleLaunchDialogDisplayMode.add(toggleLaunchDialogDisplayShort);
        panelLaunchDialogDisplaySelection.add(toggleLaunchDialogDisplayShort);

        toggleLaunchDialogDisplayFull = new JToggleButton(Localisation.getText("fullsequence"));
        groupToggleLaunchDialogDisplayMode.add(toggleLaunchDialogDisplayFull);
        panelLaunchDialogDisplaySelection.add(toggleLaunchDialogDisplayFull);

        switch (Options.displayLaunchResult) {
            case 0:
                toggleLaunchDialogDisplayResult.setSelected(true);
                break;
            case 1:
                toggleLaunchDialogDisplayShort.setSelected(true);
                break;
            case 2:
                toggleLaunchDialogDisplayFull.setSelected(true);
                break;
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelLaunchDialogMain.add(panelLaunchDialogDisplaySelection,gbc);

        JPanel panelLaunchDialogUpperPanel = new JPanel(new GridLayout(1, 0));
        panelLaunchDialogUpperPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), Localisation.getText("launchcontrol"), TitledBorder.CENTER, TitledBorder.CENTER));
        gbc.gridy = 1;
        panelLaunchDialogMain.add(panelLaunchDialogUpperPanel, gbc);

        JLabel labelLaunchDialogDate = new JLabel(Dateutils.gamedateformat.format(Dateutils.gamedate));
        panelLaunchDialogUpperPanel.add(labelLaunchDialogDate);

        JLabel labelFlag = new JLabel();
        if (currentplayer.name.equals("USSR")) {
            labelFlag.setIcon(Icons.iconUSSRflagsmall);
        } else {
            labelFlag.setIcon(Icons.iconUSAflagsmall);
        }
        panelLaunchDialogUpperPanel.add(labelFlag);

        JLabel labelLaunchDialogMissionName = new JLabel(currentmission.missiontype.getName());
        panelLaunchDialogUpperPanel.add(labelLaunchDialogMissionName);

        labelLaunchDialogCountdown = new JLabel("5");
        labelLaunchDialogCountdown.setHorizontalAlignment(JLabel.CENTER);
        panelLaunchDialogUpperPanel.add(labelLaunchDialogCountdown);

        toggleLaunchDialogScrub = new JToggleButton(Localisation.getText("scrub"));
        panelLaunchDialogUpperPanel.add(toggleLaunchDialogScrub);

        JPanel panelLaunchDialogVehiclePanel = new JPanel(new GridLayout(1, 0));
        panelLaunchDialogVehiclePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("info"), TitledBorder.CENTER, TitledBorder.CENTER));
        gbc.gridy = 2;
        panelLaunchDialogMain.add(panelLaunchDialogVehiclePanel, gbc);

        JLabel labelLaunchDialogRocketName = new JLabel(currentmission.carrier.getName());
        panelLaunchDialogVehiclePanel.add(labelLaunchDialogRocketName);

        JLabel labelLaunchDialogPayloadName = new JLabel(currentmission.usefulpayload.getName());
        panelLaunchDialogVehiclePanel.add(labelLaunchDialogPayloadName);

        if (currentmission.missiontype.isManned()) {
            JLabel labelLaunchDialogCrewName = new JLabel(currentmission.crews.get(0).getCrewName().toString());
            panelLaunchDialogVehiclePanel.add(labelLaunchDialogCrewName);
        }


        JPanel panelLaunchDialogMainText = new JPanel(new BorderLayout());
        panelLaunchDialogMainText.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("sequence"), TitledBorder.CENTER, TitledBorder.CENTER));

        labelLaunchDialogResult = new JLabel();
        labelLaunchDialogResult.setVerticalAlignment(JLabel.TOP);
        panelLaunchDialogMainText.add(labelLaunchDialogResult, BorderLayout.LINE_START);

        gbc.weighty = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 3;
        panelLaunchDialogMain.add(panelLaunchDialogMainText, gbc);
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel panelLaunchDialogBottom = new JPanel(new GridLayout(1, 0));
        buttonLaunchDialogClose = new JButton(Localisation.getText("close"));
        buttonLaunchDialogClose.addActionListener(listenerLaunchDialogClose);
        panelLaunchDialogBottom.add(buttonLaunchDialogClose);

        gbc.gridy = 4;
        panelLaunchDialogMain.add(panelLaunchDialogBottom, gbc);


        timerScrubCountdown = new javax.swing.Timer(1000, listenerLaunchTimer);
        timerScrubCountdown.setRepeats(true);
        timerScrubCountdown.start();

        dialogLaunchDialog.setContentPane(panelLaunchDialogMain);
        dialogLaunchDialog.pack();
        dialogLaunchDialog.setLocationRelativeTo(DialogMainField.mainFrame);
        dialogLaunchDialog.setVisible(true);


    }
    ActionListener listenerLaunchDialogClose = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (timerDisplayResult.isRunning()) {
                displayStep();
            } else {
//                PanelWarehouse.setWarehouse();
                dialogLaunchDialog.dispose();
            }

        }
    };
    ActionListener listenerLaunchTimer = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int countdown = Integer.parseInt(labelLaunchDialogCountdown.getText());
            if (countdown > 0) {
                labelLaunchDialogCountdown.setText(Integer.toString(countdown - 1));
            } else {
                timerScrubCountdown.stop();
                if (toggleLaunchDialogDisplayResult.isSelected()) {
                    Options.displayLaunchResult = 0;
                } else if (toggleLaunchDialogDisplayShort.isSelected()) {
                    Options.displayLaunchResult = 1;
                } else if (toggleLaunchDialogDisplayFull.isSelected()) {
                    Options.displayLaunchResult = 2;
                }
                if (!toggleLaunchDialogScrub.isSelected()) {
                    displayMissionResult();
                } else {
                    labelLaunchDialogResult.setText("<html><h3>"+Localisation.getText("missionisscrubbed")+"</html>");
                    currentplayer.missions.MissionStorage.remove(currentmission);
                }
            }
        }
    };

    private void displayMissionResult() {
        currentmission.launch();
        if (currentmission.isFinished) {
            prestigeresult = currentplayer.missions.calculateprestige(currentmission);
            prestigeresult = prestigeresult + currentplayer.missions.checkCrewLoss(currentmission);
            currentplayer.missions.MissionStorage.remove(currentmission);
        }
        if (Options.displayLaunchResult == Options.RESULT_INSTANT) {
            String result = "<html>";
            for (int i = 0; i < currentmission.result.size(); i++) {
                result = result + "<b>" + currentmission.result.get(i).stage + ":</b> " + currentmission.result.get(i).status + "<br>";
            }
            result = result + prestigeresult + "</html>";
            labelLaunchDialogResult.setText(result);
        } else if (Options.displayLaunchResult == Options.RESULT_SHORT) {
            stepcounter = 0;
            labelLaunchDialogResult.setText("<html></html>");
            isStageName = false;
            displayStep();
            timerDisplayResult = new javax.swing.Timer(Options.delayLaunchText, listenerDisplayResultTimer);
            timerDisplayResult.setRepeats(true);
            timerDisplayResult.start();

        } else if (Options.displayLaunchResult == Options.RESULT_FULL) {
            longstepcounter = 0;
            labelLaunchDialogResult.setText("<html></html>");
            prepareLongText();
            displayStep();
            timerDisplayResult = new javax.swing.Timer(Options.delayLaunchText, listenerDisplayResultTimer);
            timerDisplayResult.setRepeats(true);
            timerDisplayResult.start();
        }
    }
    ActionListener listenerDisplayResultTimer = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            displayStep();
        }
    };

    private void displayStep() {
        String tempString = "";
        if (Options.displayLaunchResult == Options.RESULT_SHORT) {
            if (stepcounter < currentmission.result.size()) {
                tempString = labelLaunchDialogResult.getText().substring(0, labelLaunchDialogResult.getText().length() - 7); //remove </html> tag
                if (!isStageName) {
                    tempString = tempString + "<b>" + currentmission.result.get(stepcounter).stage + ":</b> ";
                } else {
                    tempString = tempString + currentmission.result.get(stepcounter).status + "<br>";
                    stepcounter = stepcounter + 1;
                }
                tempString = tempString + "</html>";
                isStageName = !isStageName;
                labelLaunchDialogResult.setText(tempString);

            } else {
                timerDisplayResult.stop();
                tempString = labelLaunchDialogResult.getText().substring(0, labelLaunchDialogResult.getText().length() - 7); //remove </html> tag
                tempString = tempString + prestigeresult + "</html>";
                labelLaunchDialogResult.setText(tempString);
            }
        } else if (Options.displayLaunchResult == Options.RESULT_FULL) {
            if (longstepcounter < launchLongResultText.size()) {
                tempString = labelLaunchDialogResult.getText().substring(0, labelLaunchDialogResult.getText().length() - 7); //remove </html> tag
                tempString = tempString + launchLongResultText.get(longstepcounter) + "<br>";
                tempString = tempString + "</html>";
                labelLaunchDialogResult.setText(tempString);
                longstepcounter = longstepcounter + 1;
            } else {
                timerDisplayResult.stop();
                tempString = labelLaunchDialogResult.getText().substring(0, labelLaunchDialogResult.getText().length() - 7); //remove </html> tag
                tempString = tempString + prestigeresult + "</html>";
                labelLaunchDialogResult.setText(tempString);
            }
        }
    }

    private void prepareLongText() {
        launchLongResultText = new ArrayList<String>();
        for (int i = 0; i < currentmission.result.size(); i++) {
            LongTextLaunchData longdata;
            launchLongResultText.add("<b>" + currentmission.result.get(i).stage + ":</b> ");
            SpaceObject tempObject = currentmission.getSpaceObjectByStringCode(currentmission.missiontype.sequence.get(i).check); //Address to list of long text of SpaceObjectchecking on current stage
            int tempstatus = currentmission.result.get(i).statusint;
            if ((tempObject.longtext != null) & (tempObject.getLongDataByActionName(currentmission.result.get(i).stage) != null)) {
                int insert = -1;
                int randmessage;
                longdata = tempObject.getLongDataByActionName(currentmission.result.get(i).stage);
                ArrayList<String> errtext = new ArrayList<String>();
                if (tempstatus == 1) {
                    randmessage = Utils.random(longdata.list1.size());
                    insert = longdata.list1.get(randmessage).compatible.get(Utils.random(longdata.list1.get(randmessage).compatible.size()));
                    for (int k = 0; k < longdata.list1.get(randmessage).sequence.size(); k++) {
                        errtext.add(longdata.list1.get(randmessage).sequence.get(k));
                    }
                } else if (tempstatus == 2) {
                    randmessage = Utils.random(longdata.list2.size());
                    insert = longdata.list2.get(randmessage).compatible.get(Utils.random(longdata.list2.get(randmessage).compatible.size()));
                    for (int k = 0; k < longdata.list2.get(randmessage).sequence.size(); k++) {
                        errtext.add(longdata.list2.get(randmessage).sequence.get(k));
                    }
                } else if (tempstatus == 3) {
                    randmessage = Utils.random(longdata.list3.size());
                    insert = longdata.list3.get(randmessage).compatible.get(Utils.random(longdata.list3.get(randmessage).compatible.size()));
                    for (int k = 0; k < longdata.list3.get(randmessage).sequence.size(); k++) {
                        errtext.add(longdata.list3.get(randmessage).sequence.get(k));
                    }
                }
                System.out.println("Error text for launch" + errtext);
                for (int j = 0; j < longdata.list0.size(); j++) {
                    launchLongResultText.add(longdata.list0.get(j).get(Utils.random(longdata.list0.get(j).size())));
                    if (insert == j) {
                        for (int k = 0; k < errtext.size(); k++) {
                            launchLongResultText.add(errtext.get(k));
                        }
                        if (tempstatus == 2 | tempstatus == 3) {
//                            j = tempObject.longtext.size();
                            break;
                        }
                    }
                }
            } else {
                launchLongResultText.add(currentmission.result.get(i).status);
            }
        }
    }
}

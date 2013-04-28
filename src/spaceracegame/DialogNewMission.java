/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class DialogNewMission {

    public JDialog dialogNewMission;
    public JRadioButton radioUnmanned;
    public JRadioButton radioManned;
    public JPanel panelDuration;
    public JCheckBox checkDO;
    public JCheckBox checkEVA;
    public JCheckBox checkLM;

    public class MutableList extends JList {

        MutableList() {
            super(new DefaultListModel());
        }

        DefaultListModel getContents() {
            return (DefaultListModel) getModel();
        }
    }
    public MutableList listCompartibleVehicles;
    public MutableList[] listCrews;
    public MutableList listTarget;
    public MutableList listAvailableMissions;
    public JRadioButton[] radioDurations;
    public MutableList listRocket;
    public JButton buttonNewMissionOK;
    public JLabel labelNewMissionErrors;
    public JScrollPane scrollAvailableMissions;

    public void createAndPopulateNewMissionDialog() {
        //create and populate New Mission Form
        dialogNewMission = new JDialog((Frame) null, Localisation.getText("newmission"), true);
        Dimension dmsn = new Dimension(1000, 400);
        Utils.setDamnedSize(dialogNewMission, dmsn);
//        dialogNewMission.setResizable(false);

        JPanel panelNewMission = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;

        JPanel panelFilters = new JPanel(new GridBagLayout());
        dmsn = new Dimension(300, 200);
        Utils.setDamnedSize(panelFilters, dmsn);
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("filters"), TitledBorder.CENTER, TitledBorder.CENTER);
        panelFilters.setBorder(title);

        JPanel panelManned = new JPanel(new GridLayout(0, 1));
        radioUnmanned = new JRadioButton(Localisation.getText("unmanned"));
        radioUnmanned.setSelected(true);
        radioUnmanned.addActionListener(listenerNewMissionFilter);
        radioManned = new JRadioButton(Localisation.getText("manned"));
        radioManned.addActionListener(listenerNewMissionFilter);
        ButtonGroup groupManned = new ButtonGroup();
        groupManned.add(radioUnmanned);
        groupManned.add(radioManned);
        panelManned.add(radioUnmanned);
        panelManned.add(radioManned);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panelFilters.add(panelManned, gbc);

        JPanel panelTarget = new JPanel(new GridLayout(0, 1));
        listTarget = new MutableList();
        listTarget.getContents().addElement(Localisation.getText("any"));
        listTarget.getContents().addElement(Localisation.getText("earth"));
        listTarget.getContents().addElement(Localisation.getText("moon"));
        listTarget.getContents().addElement(Localisation.getText("mars"));
        listTarget.getContents().addElement(Localisation.getText("venus"));
        listTarget.getContents().addElement(Localisation.getText("jupiter"));
        listTarget.getContents().addElement(Localisation.getText("saturn"));
        listTarget.getContents().addElement(Localisation.getText("mercury"));
        listTarget.addListSelectionListener(listenerNewMissionListFilter);
        panelTarget.add(listTarget);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        panelFilters.add(panelTarget, gbc);

        panelDuration = new JPanel(new GridLayout(0, 1));
        radioDurations = new JRadioButton[Durations.durations.size() + 1];
        ButtonGroup groupDurations = new ButtonGroup();

        radioDurations[0] = new JRadioButton(Localisation.getText("any"));
        groupDurations.add(radioDurations[0]);
        radioDurations[0].setSelected(Boolean.TRUE);
        radioDurations[0].addActionListener(listenerNewMissionFilter);
        panelDuration.add(radioDurations[0]);


        for (int i = 0; i < Durations.durations.size(); i++) {
            radioDurations[i + 1] = new JRadioButton(Durations.durations.get(i).getName());
            radioDurations[i + 1].addActionListener(listenerNewMissionFilter);
            groupDurations.add(radioDurations[i + 1]);
            panelDuration.add(radioDurations[i + 1]);
        }

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        panelFilters.add(panelDuration, gbc);

        JPanel panelDOLMEVA = new JPanel(new GridLayout(0, 1));
        checkDO = new JCheckBox(Localisation.getText("docking"));
        checkDO.addActionListener(listenerNewMissionFilter);
        panelDOLMEVA.add(checkDO);

        checkEVA = new JCheckBox(Localisation.getText("eva"));
        checkEVA.addActionListener(listenerNewMissionFilter);
        panelDOLMEVA.add(checkEVA);

        checkLM = new JCheckBox(Localisation.getText("lm"));
        checkLM.addActionListener(listenerNewMissionFilter);
        panelDOLMEVA.add(checkLM);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        panelFilters.add(panelDOLMEVA, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panelNewMission.add(panelFilters, gbc);

        JPanel panelListAvailableMissions = new JPanel(new GridLayout(0, 1));
        dmsn = new Dimension(200, 200);
        Utils.setDamnedSize(panelListAvailableMissions, dmsn);
        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("availablemissions"), TitledBorder.CENTER, TitledBorder.CENTER);
        panelListAvailableMissions.setBorder(title);

        listAvailableMissions = new MutableList();
        scrollAvailableMissions = new JScrollPane(listAvailableMissions);
        listAvailableMissions.addListSelectionListener(listenerNewMissionListMission);
        panelListAvailableMissions.add(scrollAvailableMissions);
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        panelNewMission.add(panelListAvailableMissions, gbc);

        JPanel panelListVehicles = new JPanel(new GridLayout(0, 1));
        dmsn = new Dimension(150, 200);
        Utils.setDamnedSize(panelListVehicles, dmsn);
        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("possiblevehicles"), TitledBorder.CENTER, TitledBorder.CENTER);
        panelListVehicles.setBorder(title);

        listCompartibleVehicles = new MutableList();
        listCompartibleVehicles.addListSelectionListener(listenerNewMissionListVehicle);
        panelListVehicles.add(listCompartibleVehicles);

        gbc.gridx = 4;
        panelNewMission.add(panelListVehicles, gbc);

        JPanel panelListCrews = new JPanel(new GridBagLayout());
        GridBagConstraints gbccrews = new GridBagConstraints();
        dmsn = new Dimension(300, 200);
        Utils.setDamnedSize(panelListCrews, dmsn);
        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("availablecrews"), TitledBorder.CENTER, TitledBorder.CENTER);
        panelListCrews.setBorder(title);

        int size = 2;
        listCrews = new MutableList[size];
        JLabel labelMainCrew = new JLabel(Localisation.getText("main"));
        labelMainCrew.setHorizontalAlignment(JLabel.CENTER);
        gbccrews.gridx = 0;
        gbccrews.gridy = 0;
        gbccrews.weightx = 1;
        gbccrews.fill = GridBagConstraints.HORIZONTAL;

        JPanel panelListRockets = new JPanel(new GridLayout(0, 1));
        dmsn = new Dimension(150, 200);
        Utils.setDamnedSize(panelListRockets, dmsn);
        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("compatiblerockets"), TitledBorder.CENTER, TitledBorder.CENTER);
        panelListRockets.setBorder(title);

        listRocket = new MutableList();
        listRocket.addListSelectionListener(listenerNewMissionSelectionRocket);
        panelListRockets.add(listRocket);
        gbc.gridx = 5;
        panelNewMission.add(panelListRockets, gbc);

        JPanel panelErrors = new JPanel(new GridLayout(0, 1));
        dmsn = new Dimension(300, 100);
        Utils.setDamnedSize(panelErrors, dmsn);
        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("errors"), TitledBorder.CENTER, TitledBorder.CENTER);
        panelErrors.setBorder(title);
        labelNewMissionErrors = new JLabel();
        panelErrors.add(labelNewMissionErrors);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panelNewMission.add(panelErrors, gbc);
        
                panelListCrews.add(labelMainCrew, gbccrews);
        listCrews[0] = new MutableList();
        listCrews[0].addListSelectionListener(listenerNewMissionCrewSelect);
        gbccrews.gridy = 1;
        gbccrews.weighty = 1;
        gbccrews.fill = GridBagConstraints.BOTH;
        JScrollPane scrollMainCrew = new JScrollPane(listCrews[0]);
        scrollMainCrew.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelListCrews.add(scrollMainCrew, gbccrews);
        JLabel labelBackupCrew = new JLabel(Localisation.getText("backup"));
        labelBackupCrew.setHorizontalAlignment(JLabel.CENTER);
        gbccrews.gridx = 1;
        gbccrews.gridy = 0;
        gbccrews.weighty = 0;
        gbccrews.fill = GridBagConstraints.HORIZONTAL;
        panelListCrews.add(labelBackupCrew, gbccrews);
        listCrews[1] = new MutableList();
        listCrews[1].addListSelectionListener(listenerNewMissionCrewSelect);
        gbccrews.gridy = 1;
        gbccrews.weighty = 1;
        gbccrews.fill = GridBagConstraints.BOTH;
        JScrollPane scrollBackupCrew = new JScrollPane(listCrews[1]);
        scrollBackupCrew.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelListCrews.add(scrollBackupCrew, gbccrews);

        gbc.gridx = 4;
        panelNewMission.add(panelListCrews, gbc);

        JPanel panelNewMissionOKCancel = new JPanel(new GridBagLayout());
        dmsn = new Dimension(1000, 60);
        Utils.setDamnedSize(panelNewMissionOKCancel, dmsn);

        buttonNewMissionOK = new JButton(Localisation.getText("ok"));
        buttonNewMissionOK.addActionListener(listenerNewMissionButtonOK);
        buttonNewMissionOK.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelNewMissionOKCancel.add(buttonNewMissionOK, gbc);

        JButton buttonNewMissionCancel = new JButton(Localisation.getText("cancel"));
        buttonNewMissionCancel.addActionListener(listenerNewMissionButtonCancel);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelNewMissionOKCancel.add(buttonNewMissionCancel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 7;
        panelNewMission.add(panelNewMissionOKCancel, gbc);

        updateNewMissionFilters();
        dialogNewMission.setContentPane(panelNewMission);
        dialogNewMission.pack();
        dialogNewMission.setLocationRelativeTo(DialogMainField.mainFrame);
        dialogNewMission.setVisible(true);
    }
    private ActionListener listenerNewMissionFilter = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateNewMissionFilters();
        }
    };
    private ListSelectionListener listenerNewMissionListFilter = new ListSelectionListener() {

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            updateNewMissionFilters();
        }
    };

    private void updateNewMissionFilters() { //update filters state from click on them
        boolean isManned = radioManned.isSelected();
        boolean isDocking = checkDO.isSelected();
        boolean isEVA = checkEVA.isSelected();
        boolean isLM = checkLM.isSelected();
        String Target;

        if (listTarget.isSelectionEmpty()) {
            Target = "";
        } else if (listTarget.getSelectedValue().toString().equals(Localisation.getText("any"))) {
            Target = "";
        } else {
            Target = listTarget.getSelectedValue().toString();
        }
        if (!isManned) {
            for (int i = 0; i < radioDurations.length; i++) {
                radioDurations[i].setEnabled(false);
            }
//            for (int i=0;i<listCrews.length;i++){
//                listCrews[i].setEnabled(false);
//            }
            listCompartibleVehicles.setEnabled(false);
//            checkEVA.setEnabled(false);
        } else {
            for (int i = 0; i < radioDurations.length; i++) {
                radioDurations[i].setEnabled(true);
            }
//            for (int i=0;i<listCrews.length;i++){
//                listCrews[i].setEnabled(true);
//            }
            listCompartibleVehicles.setEnabled(true);
//            checkEVA.setEnabled(true);

        }
        listAvailableMissions.getContents().removeAllElements();
        ArrayList<MissionTypes> availableMissions = new ArrayList<MissionTypes>();
        for (int i = 0; i < MissionTypesArray.listMissionTypes.size(); i++) {
            MissionTypes tempMission = MissionTypesArray.listMissionTypes.get(i);
            if ((tempMission.isManned() == isManned)
                    & (tempMission.isDocking() == isDocking)
                    & (tempMission.isEVA() == isEVA)
                    & (tempMission.isLM() == isLM)
                    & ((Target.equals("")) | (Target.equals(tempMission.getTarget())))
                    & ((!isManned) | (getDurationSelected() == tempMission.getDuration()) | (getDurationSelected() == 0))) {
                availableMissions.add(tempMission);
                listAvailableMissions.getContents().addElement(tempMission.getName());
            }
        }
        listAvailableMissions.clearSelection();
        scrollAvailableMissions.getViewport().setView(listAvailableMissions);


    }
    private ListSelectionListener listenerNewMissionListMission = new ListSelectionListener() { //Selection from mission selector

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            if (!listAvailableMissions.isSelectionEmpty()) {
                MissionTypes tempMission = MissionTypesArray.findMissionTypeByName(listAvailableMissions.getSelectedValue().toString());
                ArrayList<Integer> payloads = tempMission.getCompartiblePayloads();
                listCompartibleVehicles.getContents().removeAllElements();
                for (int i = 0; i < payloads.size(); i++) {
                    if (Players.currentPlayer.spaceObjectArray.findObjectByCode(payloads.get(i)) != null) {
                        listCompartibleVehicles.getContents().addElement(Players.currentPlayer.spaceObjectArray.findObjectByCode(payloads.get(i)).getName());
                    }
                }
                if (listCompartibleVehicles.getContents().size() == 1) {
                    listCompartibleVehicles.setEnabled(false);
                    updateNewMissionVehicles();
                } else {
                    listCompartibleVehicles.setEnabled(true);
                }
                validateNewMissionOKButton();
            }
        }
    };
    private ListSelectionListener listenerNewMissionListVehicle = new ListSelectionListener() { //selection on available vehicles list

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            updateNewMissionVehicles();
        }
    };

    private void updateNewMissionVehicles() { //update crews for selected vehicles
        if (radioManned.isSelected()) {
            for (int i = 0; i < listCrews.length; i++) {
                listCrews[i].setEnabled(true);
            }
            initializeNewMissionCrews();
        } else {
            for (int i = 0; i < listCrews.length; i++) {
                listCrews[i].setEnabled(false);
            }

        }
        updateNewMissionCompartibleRockets();
    }

    private void initializeNewMissionCrews() { // new crews
        SpaceObject vehicle = new SpaceObject();
        if (listCompartibleVehicles.getContents().getSize() == 1) {
            vehicle = Players.currentPlayer.spaceObjectArray.findObjectByName(listCompartibleVehicles.getContents().get(0).toString());
        } else {
            if (!listCompartibleVehicles.isSelectionEmpty()) {
                vehicle = Players.currentPlayer.spaceObjectArray.findObjectByName(listCompartibleVehicles.getSelectedValue().toString());
            }
        }

        ArrayList<Crew> tempCrews = Players.currentPlayer.crews.findFreeCrewsByVehicle(vehicle.getCode());
        for (int i = 0; i < listCrews.length; i++) {
            listCrews[i].getContents().removeAllElements();
            for (int j = 0; j < tempCrews.size(); j++) {
                String names = "";
                ArrayList<String> listnames = tempCrews.get(j).getCrewName();
                for (int k = 0; k < listnames.size(); k++) {
                    if (k > 0) {
                        names = names + ", ";
                    }
                    names = names + listnames.get(k);
                }
                listCrews[i].getContents().addElement(names);
            }
        }
    }
    private ListSelectionListener listenerNewMissionCrewSelect = new ListSelectionListener() { //crew selection

        @Override
        public void valueChanged(ListSelectionEvent e) {
            validateNewMissionOKButton();
        }
    };

    private void updateNewMissionCompartibleRockets() {
        if ((!listAvailableMissions.isSelectionEmpty()) & ((!listCompartibleVehicles.isSelectionEmpty()) | (listCompartibleVehicles.getContents().getSize() == 1))) {
            MissionTypes tempMission = MissionTypesArray.findMissionTypeByName(listAvailableMissions.getSelectedValue().toString());
            SpaceObject tempVehicle;
            if (listCompartibleVehicles.getContents().getSize() == 1) {
                tempVehicle = Players.currentPlayer.spaceObjectArray.findObjectByName(listCompartibleVehicles.getContents().get(0).toString());
            } else {
                tempVehicle = Players.currentPlayer.spaceObjectArray.findObjectByName(listCompartibleVehicles.getSelectedValue().toString());
            }
            int totalweight = (int) (tempVehicle.getWeight() * WeightMultipliers.getMultiplier(tempMission.getTarget()));
            ArrayList<String> listCompartibleRockets = new ArrayList<String>();
            for (SpaceObject rocket : Players.currentPlayer.spaceObjectArray.listRockets) {
                if (rocket.getCapacity() >= totalweight) {
                    listCompartibleRockets.add(rocket.getName());
                }
            }
            listRocket.getContents().removeAllElements();
            for (int i = 0; i < listCompartibleRockets.size(); i++) {
                listRocket.getContents().addElement(listCompartibleRockets.get(i));
            }
        }
        validateNewMissionOKButton();
    }

    public int getDurationSelected() {
        for (int i = 0; i < radioDurations.length; i++) {
            if (radioDurations[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }
    private ListSelectionListener listenerNewMissionSelectionRocket = new ListSelectionListener() {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            validateNewMissionOKButton();
        }
    };

    public void validateNewMissionOKButton() {
        buttonNewMissionOK.setEnabled(false);
        boolean isMissionSelected = !listAvailableMissions.isSelectionEmpty();
        boolean isVehicleSelected = !listCompartibleVehicles.isSelectionEmpty() | listCompartibleVehicles.getContents().getSize() == 1;
        int amountCrewSelected = 0;
        for (int i = 0; i < listCrews.length; i++) {
            if (!listCrews[i].isSelectionEmpty()) {
                amountCrewSelected = amountCrewSelected + 1;
            }
        }
        boolean isCrewSame = false;
        if (!listCrews[0].isSelectionEmpty()) {
            if (!listCrews[1].isSelectionEmpty()) {
                if (listCrews[0].getSelectedValue().toString().equals(listCrews[1].getSelectedValue().toString())) {
                    isCrewSame = true;
                }
            }
        }
        boolean isCrewSelected = radioUnmanned.isSelected() | amountCrewSelected > 0;
        boolean isCrewSingle = amountCrewSelected == 1;
        boolean isRocketSelected = !listRocket.isSelectionEmpty();
        String errorString = "<html>";
        if (isCrewSame) {
            errorString = errorString + "<li>"+Localisation.getText("primarysame");
        }
        if (isCrewSingle) {
            errorString = errorString + "<li>"+Localisation.getText("onecrew");
        }
        if (!isMissionSelected) {
            errorString = errorString + "<li>"+Localisation.getText("nomission");
        }
        if (!isVehicleSelected) {
            errorString = errorString + "<li>"+Localisation.getText("novehicle");
        }
        if (!isCrewSelected) {
            errorString = errorString + "<li>"+Localisation.getText("nocrew");
        }
        if (!isRocketSelected) {
            errorString = errorString + "<li>"+Localisation.getText("norocket");
        }
        if (isRocketSelected & isMissionSelected & isVehicleSelected & isCrewSelected) {
            MissionTypes selectedMissiontype = MissionTypesArray.findMissionTypeByName(listAvailableMissions.getSelectedValue().toString());
            boolean isLaunchwindow = LaunchWindows.isLaunchWindowFor(Dateutils.getDateMonthShift(1), selectedMissiontype.target)|((selectedMissiontype.target.equals(Localisation.getText("earth")))|(selectedMissiontype.target.equals(Localisation.getText("moon"))));
            SpaceObject rocket = Players.currentPlayer.spaceObjectArray.findObjectByName(listRocket.getSelectedValue().toString());
            boolean isRocketResearched = Players.currentPlayer.research.verifyResearchByCode(rocket.getCode());
            if (!isRocketResearched) {
                errorString = errorString + "<li>"+Localisation.getText("norocketresearch");
            }
            SpaceObject vehicle;
            if (listCompartibleVehicles.getContents().size() == 1) {
                vehicle = Players.currentPlayer.spaceObjectArray.findObjectByName(listCompartibleVehicles.getContents().get(0).toString());
            } else {
                vehicle = Players.currentPlayer.spaceObjectArray.findObjectByName(listCompartibleVehicles.getSelectedValue().toString());
            }
            boolean isVehicleResearched = Players.currentPlayer.research.verifyResearchByCode(vehicle.getCode());
            if (!isVehicleResearched) {
                errorString = errorString + "<li>"+Localisation.getText("novehicleresearch");
            }

            boolean isRocketInStock = Players.currentPlayer.warehouse.isInStock(rocket.getCode());
            if (!isRocketInStock) {
                errorString = errorString + "<li>"+Localisation.getText("norocketstock");
            }
            boolean isVehicleInStock = Players.currentPlayer.warehouse.isInStock(vehicle.getCode());
            if (!isVehicleInStock) {
                errorString = errorString + "<li>"+Localisation.getText("novehiclestock");
            }
            if (!isLaunchwindow) {
                errorString = errorString + "<li>"+Localisation.getText("nolaunchwindow");
            }
            if (isRocketResearched & isVehicleResearched & isRocketInStock & isVehicleInStock & isLaunchwindow) {
                buttonNewMissionOK.setEnabled(true);
            }
        }
        errorString = errorString + "</html>";
        labelNewMissionErrors.setText(errorString);
    }
    private ActionListener listenerNewMissionButtonOK = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            SpaceObject rocket = Players.currentPlayer.spaceObjectArray.findObjectByName(listRocket.getSelectedValue().toString());
            SpaceObject vehicle;
            if (listCompartibleVehicles.getContents().size() == 1) {
                vehicle = Players.currentPlayer.spaceObjectArray.findObjectByName(listCompartibleVehicles.getContents().get(0).toString());
            } else {
                vehicle = Players.currentPlayer.spaceObjectArray.findObjectByName(listCompartibleVehicles.getSelectedValue().toString());
            }
            Mission newMission = new Mission(Players.currentPlayer);
            newMission.setCarrier(rocket);
            newMission.setPayload(vehicle);
            newMission.setDate(Dateutils.getDateMonthShift(1));
            ArrayList<Crew> crews = new ArrayList<Crew>();
            for (int i = 0; i < listCrews.length; i++) {
                if (!listCrews[i].isSelectionEmpty()) {
                    crews.add(Players.currentPlayer.crews.findCrewByNames(listCrews[i].getSelectedValue().toString()));
                }
            }
            newMission.SetCrew(crews);
            newMission.setType(MissionTypesArray.findMissionTypeByName(listAvailableMissions.getSelectedValue().toString()).getCode());
            newMission.prepareUsedComponents();
            Players.currentPlayer.missions.MissionStorage.add(newMission);
            dialogNewMission.dispose();
            //MainField2_0.setTableData();
        }
    };
    private ActionListener listenerNewMissionButtonCancel = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            dialogNewMission.dispose();
        }
    };
}

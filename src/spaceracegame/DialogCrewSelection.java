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

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class DialogCrewSelection {

    private static JDialog dialogCrewSelection;
    public static JComboBox comboVehicle;
    private static final int constcrews = 8;
    private static JComboBox[][] comboCrewselect;

    public static void createCrewSelectionForm(int defaultvehicle) {
        dialogCrewSelection = new JDialog((Frame) null, Localisation.getText("crewselection"), true);

        if (defaultvehicle < 1) {
            defaultvehicle = Players.currentPlayer.spaceObjectArray.listManned.get(0).getCode();
        }
        SpaceObject vehicle = Players.currentPlayer.spaceObjectArray.findObjectByCode(defaultvehicle);
        Dimension dimensionCrewSelect = new Dimension(900, vehicle.getCrew() * 4 * 50 + 3 * 50);
        dialogCrewSelection.setMinimumSize(dimensionCrewSelect);
        dialogCrewSelection.setMaximumSize(dimensionCrewSelect);
        dialogCrewSelection.setSize(dimensionCrewSelect);
        dialogCrewSelection.setPreferredSize(dimensionCrewSelect);
        dialogCrewSelection.setContentPane(formCrewSelectionForm(vehicle));
        formComboCrews();
        dialogCrewSelection.pack();
        dialogCrewSelection.setLocationRelativeTo(DialogMainField.mainFrame);
        dialogCrewSelection.setVisible(true);
    }

    public static JPanel formCrewSelectionForm(SpaceObject vehicle) {
        JPanel fcsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        comboVehicle = new JComboBox(Players.currentPlayer.spaceObjectArray.getMannedNames().toArray());

        comboVehicle.setSelectedIndex(Players.currentPlayer.spaceObjectArray.findObjectNumberByCode(vehicle.getCode()));
        comboVehicle.addActionListener(listenerComboVehicle);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 10, 5);
        fcsPanel.add(comboVehicle, gbc);
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridwidth = 1;
        JPanel[] panelCrew = new JPanel[constcrews];
        int i;
        comboCrewselect = new JComboBox[constcrews][vehicle.getCrew()];
        for (i = 0; i < constcrews; i++) {
            panelCrew[i] = new JPanel(new GridBagLayout());
            panelCrew[i].setLayout(new GridBagLayout());
            JLabel crewnumber = new JLabel(Localisation.getText("crew")+" " + Integer.toString(i + 1));
//            Dimension dmsncrewnumber = new Dimension(200, 30);
//            crewnumber.setMinimumSize(dmsncrewnumber);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.weightx=1;
            gbc.insets = new Insets(2, 2, 2, 0);
            gbc.gridheight = vehicle.getCrew();

            panelCrew[i].add(crewnumber, gbc);
//            Dimension dmsn = new Dimension(200, 50);
            ArrayList<String> tempNauts = new ArrayList<String>();

            if (Players.currentPlayer.crews.isCrewByVehicleAndNumber(vehicle.getCode(), i)) {
                for (int k = 0; k < vehicle.getCrew(); k++) {
                    tempNauts.add(Players.currentPlayer.crews.findCrewByVehicleAndNumber(vehicle.getCode(), i).getCrewName().get(k));
                }
            } else {

                for (int k = 0; k < vehicle.getCrew(); k++) {
                    tempNauts.add(Localisation.getText("empty"));
                }
            }



            for (int j = 0; j < vehicle.getCrew(); j++) {
                if (j == 0) {
                    gbc.insets = new Insets(10, 0, 0, 0);
                }
                if (j == vehicle.getCrew()) {
                    gbc.insets = new Insets(0, 0, 10, 0);
                }

                JLabel labelcrewduties = new JLabel(vehicle.getCrewCapabilities().get(j).toString());
                gbc.gridx = 1;
                gbc.gridy = j;

                gbc.gridheight = 1;
                gbc.weightx=3;
                panelCrew[i].add(labelcrewduties, gbc);

                comboCrewselect[i][j] = new JComboBox();
                comboCrewselect[i][j].addItem(tempNauts.get(j));
//                comboCrewselect[i][j].setMaximumSize(dmsncrewnumber);
//                comboCrewselect[i][j].setMinimumSize(dmsncrewnumber);
//                comboCrewselect[i][j].setPreferredSize(dmsncrewnumber);
                comboCrewselect[i][j].setActionCommand(Integer.toString(i) + "," + Integer.toString(j));
                comboCrewselect[i][j].addActionListener(listenerCrewSelection);
//                comboCrewselect.setMinimumSize(dmsn);
                gbc.gridx = 2;
                gbc.gridy = j;
                gbc.weightx=3;
                panelCrew[i].add(comboCrewselect[i][j], gbc);

//                JLabel labelCrewCapabilities = new JLabel("CAP DO LM EVA END");
//                labelCrewCapabilities.setMinimumSize(dmsn);
//                gbc.gridx = 2;
//                gbc.gridy = j;
//                gbc.weightx = 1;
//                gbc.weighty = 1;
//
//                panelCrew[i].add(labelCrewCapabilities, gbc);

            }
            if (i < 4) {
                gbc.gridx = 0;
                gbc.gridy = i + 1 + 1;
            } else {
                gbc.gridx = 1;
                gbc.gridy = i + 1 - 4 + 1;
            }
            gbc.insets = new Insets(2, 10, 2, 10);
            fcsPanel.add(panelCrew[i], gbc);
        }

        JPanel panelAutoClear = new JPanel(new GridLayout());

        JButton buttonCrewAutoForm = new JButton(Localisation.getText("autoformcrew"));
        buttonCrewAutoForm.setEnabled(false); //TODO: create autoform

        panelAutoClear.add(buttonCrewAutoForm);

        JButton buttonCrewRemoveAll = new JButton(Localisation.getText("removeall"));
        panelAutoClear.add(buttonCrewRemoveAll, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        fcsPanel.add(panelAutoClear, gbc);

        JPanel panelOKCancel = new JPanel(new GridLayout());

        JButton buttonCrewOK = new JButton(Localisation.getText("ok"));
        buttonCrewOK.addActionListener(listenerButtonCrewOK);
        panelOKCancel.add(buttonCrewOK, gbc);

        JButton buttonCrewCancel = new JButton(Localisation.getText("cancel"));
        buttonCrewCancel.addActionListener(listenerButtonCrewCancel);
        panelOKCancel.add(buttonCrewCancel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2 + constcrews / 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        fcsPanel.add(panelOKCancel, gbc);

        return fcsPanel;
    }
    private static ActionListener listenerCrewSelection = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            formComboCrews();
        }
    };
    static ActionListener listenerButtonCrewOK = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            SpaceObject vehicle = Players.currentPlayer.spaceObjectArray.findMannedByName(comboVehicle.getSelectedItem().toString());

            for (int i = 0; i < constcrews; i++) {
                Crew tempcrew = new Crew();
                tempcrew.setVehicleCode(vehicle.getCode());
                tempcrew.setCrewNumber(i);
                int crewcounter = 0;
                for (int j = 0; j < vehicle.getCrew(); j++) {
                    if (!comboCrewselect[i][j].getSelectedItem().equals(Localisation.getText("empty"))) {
                        crewcounter = crewcounter + 1;
                    }
                }
                ArrayList<String> tempnames = new ArrayList<String>();
                if (crewcounter == vehicle.getCrew()) { //If the crew is full for the craft
                    for (int j = 0; j < vehicle.getCrew(); j++) {
                        tempnames.add(comboCrewselect[i][j].getSelectedItem().toString());
                    }
                    tempcrew.setVehicleCrew(tempnames);
                    if (Players.currentPlayer.crews.isCrewByVehicleAndNumber(vehicle.getCode(), i)) { //If there is a crew in this position
                        Players.currentPlayer.crews.listCrews.remove(Players.currentPlayer.crews.findCrewByVehicleAndNumber(vehicle.getCode(), i)); //Rewrite it
                        Players.currentPlayer.crews.listCrews.add(tempcrew);
                    } else { //If there is no crew add it
                        Players.currentPlayer.crews.listCrews.add(tempcrew);
                    }

                } else { //If crew is incomplete, delete it
                    Players.currentPlayer.crews.listCrews.remove(Players.currentPlayer.crews.findCrewByVehicleAndNumber(vehicle.getCode(), i));
                }
            }
            dialogCrewSelection.dispose();
        }
    };
    static ActionListener listenerButtonCrewCancel = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            dialogCrewSelection.dispose();
        }
    };
    static ActionListener listenerComboVehicle = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            comboVehicle.removeActionListener(listenerComboVehicle);
            dialogCrewSelection.getContentPane().removeAll();
            dialogCrewSelection.setContentPane(formCrewSelectionForm(Players.currentPlayer.spaceObjectArray.findMannedByName(comboVehicle.getSelectedItem().toString())));
            Dimension dimensionCrewSelect = new Dimension(800, Players.currentPlayer.spaceObjectArray.findObjectByName(comboVehicle.getSelectedItem().toString()).getCrew() * 4 * 50 + 3 * 50);
            dialogCrewSelection.setMinimumSize(dimensionCrewSelect);
            dialogCrewSelection.setMaximumSize(dimensionCrewSelect);
            dialogCrewSelection.setSize(dimensionCrewSelect);
            dialogCrewSelection.setPreferredSize(dimensionCrewSelect);
            formComboCrews();
//            frameCrewSelection.pack();
//            frameCrewSelection.validate();

//            String vehicle = comboVehicle.getSelectedItem().toString();
//            frameCrewSelection.dispose();
//            createCrewSelectionForm(SpaceObjectArray.findObjectByName(vehicle).getCode());
        }
    };

    public static void formComboCrews() {
        SpaceObject vehicle = Players.currentPlayer.spaceObjectArray.findMannedByName(comboVehicle.getSelectedItem().toString());
        int crewamount = vehicle.getCrew();
        Object[][] storage = new String[constcrews][crewamount];
        ArrayList<Object> listunique = new ArrayList<Object>();
        for (int i = 0; i < constcrews; i++) {
            for (int j = 0; j < crewamount; j++) {
                storage[i][j] = comboCrewselect[i][j].getSelectedItem();
                if (!listunique.contains(storage[i][j])) {
                    listunique.add(storage[i][j]);
                }
            }
        }
        System.out.println("formcombocrews, listunique" + listunique.toString());
        for (int i = 0; i < constcrews; i++) {
            for (int j = 0; j < crewamount; j++) {
                ArrayList<String> names = new ArrayList<String>();
                names.add(Localisation.getText("empty"));
                Object selected = comboCrewselect[i][j].getSelectedItem();
                for (int k = 0; k < Players.currentPlayer.nauts.listNauts.size(); k++) {
                    String NautName = Players.currentPlayer.nauts.listNauts.get(k).getName();
                    if (!listunique.contains(NautName) & !Players.currentPlayer.crews.isCrewAnotherVehicle(NautName, vehicle.getCode())) {
                        names.add(NautName);
                    } else {
                        if (comboCrewselect[i][j].getSelectedItem().equals(NautName)) {
                            names.add(NautName);
                        }
                    }
                }
                System.out.println("formcombocrews, crewnames" + Integer.toString(i) + ", " + Integer.toString(j) + ", " + names);
                comboCrewselect[i][j].removeActionListener(listenerCrewSelection);
                comboCrewselect[i][j].removeAllItems();
                for (int l = 0; l < names.size(); l++) {
                    comboCrewselect[i][j].addItem(names.get(l));
                }
                comboCrewselect[i][j].setSelectedItem(selected);
                comboCrewselect[i][j].addActionListener(listenerCrewSelection);

            }
        }
        dialogCrewSelection.pack();
        dialogCrewSelection.setLocationRelativeTo(DialogMainField.mainFrame);
    }
}

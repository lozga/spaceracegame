/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import spaceracegame.ui.MainField3_0;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class PanelRIBs implements Serializable {

    JToggleButton[] toggleRocketResearch;
    JToggleButton[] toggleRocketImprove;
    JButton[] buttonRocketBuy;
    JLabel[] labelRocketResearchtofinish;
    JLabel[] labelRocketName;
    JLabel[] labelRocketWeight;
    JToggleButton[] togglePayloadResearch;
    JToggleButton[] togglePayloadImprove;
    JButton[] buttonPayloadBuy;
    JLabel[] labelPayloadResearchtofinish;
    JLabel[] labelPayloadName;
    JLabel[] labelPayloadWeight;
    JToggleButton[] toggleMannedResearch;
    JToggleButton[] toggleMannedImprove;
    JButton[] buttonMannedBuy;
    JLabel[] labelMannedResearchtofinish;
    JLabel[] labelMannedName;
    JLabel[] labelMannedWeight;
    int plannedBudget;
    boolean[][] storageResearch;
    boolean[][] storageImprove;
    JButton buttonMannedCrew[];
    private Player player;

    PanelRIBs(Player givenplayer) {
        player = givenplayer;
    }

    public JPanel createRIBPanel() {
        JPanel returnPanel = new JPanel(new GridLayout(1, 0));
        JTabbedPane tabbedRIB = new JTabbedPane();

        JPanel panelRIB1 = new JPanel();
        GUIStorage tempGUIStorage = loadButtonsForResearch(panelRIB1, listenerToggleRocketResearch, listenerToggleRocketImprove, listenerButtonBuyRocket, player.spaceObjectArray.listRockets);
        toggleRocketResearch = tempGUIStorage.toggleresearch;
        toggleRocketImprove = tempGUIStorage.toggleImprove;
        labelRocketResearchtofinish = tempGUIStorage.labeltofinishresearch;
        labelRocketName = tempGUIStorage.name;
        labelRocketWeight = tempGUIStorage.weight;
        buttonRocketBuy = tempGUIStorage.buttonBuy;
        panelRIB1 = tempGUIStorage.panel;
        JScrollPane scrollPaneR = new JScrollPane(panelRIB1);
        scrollPaneR.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneR.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tabbedRIB.addTab(Localisation.getText("rockets"), scrollPaneR);

        JPanel panelRIB2 = new JPanel();
        tempGUIStorage = loadButtonsForResearch(panelRIB2, listenerTogglePayloadResearch, listenerTogglePayloadImprove, listenerButtonBuyPayload, player.spaceObjectArray.listPayloads);
        togglePayloadResearch = tempGUIStorage.toggleresearch;
        togglePayloadImprove = tempGUIStorage.toggleImprove;
        labelPayloadResearchtofinish = tempGUIStorage.labeltofinishresearch;
        labelPayloadName = tempGUIStorage.name;
        labelPayloadWeight = tempGUIStorage.weight;
        buttonPayloadBuy = tempGUIStorage.buttonBuy;
        panelRIB2 = tempGUIStorage.panel;
        JScrollPane scrollPaneU = new JScrollPane(panelRIB2);
        scrollPaneU.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneU.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tabbedRIB.addTab(Localisation.getText("ribunmanned"), scrollPaneU);

        JPanel panelRIB3 = new JPanel();
        tempGUIStorage = loadButtonsForResearch(panelRIB3, listenerToggleMannedResearch, listenerToggleMannedImprove, listenerButtonBuyManned, player.spaceObjectArray.listManned);
        toggleMannedResearch = tempGUIStorage.toggleresearch;
        toggleMannedImprove = tempGUIStorage.toggleImprove;
        labelMannedResearchtofinish = tempGUIStorage.labeltofinishresearch;
        labelMannedName = tempGUIStorage.name;
        labelMannedWeight = tempGUIStorage.weight;
        buttonMannedBuy = tempGUIStorage.buttonBuy;
        buttonMannedCrew = tempGUIStorage.buttonCrew;
        panelRIB3 = tempGUIStorage.panel;
        JScrollPane scrollPaneM = new JScrollPane(panelRIB3);
        scrollPaneM.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneM.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tabbedRIB.addTab(Localisation.getText("ribmanned"), scrollPaneM);

        returnPanel.add(tabbedRIB);
        return returnPanel;
    }
    private transient ActionListener listenerToggleRocketResearch = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int number = Integer.parseInt(e.getActionCommand());
            modify_research(player.spaceObjectArray.listRockets.get(number).getCode(), toggleRocketResearch[number], labelRocketResearchtofinish[number]);
            verifyButtonsForPurchase();
        }
    };
    private transient ActionListener listenerTogglePayloadResearch = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int number = Integer.parseInt(e.getActionCommand());
            modify_research(player.spaceObjectArray.listPayloads.get(number).getCode(), togglePayloadResearch[number], labelPayloadResearchtofinish[number]);
            verifyButtonsForPurchase();
        }
    };
    private transient ActionListener listenerToggleMannedResearch = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int number = Integer.parseInt(e.getActionCommand());
            modify_research(player.spaceObjectArray.listManned.get(number).getCode(), toggleMannedResearch[number], labelMannedResearchtofinish[number]);
            verifyButtonsForPurchase();
        }
    };
    private transient ActionListener listenerButtonBuyRocket = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int number = Integer.parseInt(e.getActionCommand());
            player.warehouse.addDeliveryInt(player.spaceObjectArray.listRockets.get(number).getOrderTime(), player.spaceObjectArray.listRockets.get(number).getCode());
            player.cash = player.cash - player.spaceObjectArray.listRockets.get(number).getCostToPurchase();
            verifyButtonsForPurchase();
            PanelLabelTable.setTableData();
            PanelWarehouse.setInfoData();
        }
    };
    private transient ActionListener listenerButtonBuyPayload = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int number = Integer.parseInt(e.getActionCommand());
            player.warehouse.addDeliveryInt(player.spaceObjectArray.listPayloads.get(number).getOrderTime(), player.spaceObjectArray.listPayloads.get(number).getCode());
            player.cash = player.cash - player.spaceObjectArray.listPayloads.get(number).getCostToPurchase();
            verifyButtonsForPurchase();
            PanelLabelTable.setTableData();
            PanelWarehouse.setInfoData();
        }
    };
    private transient ActionListener listenerButtonBuyManned = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int number = Integer.parseInt(e.getActionCommand());
            player.warehouse.addDeliveryInt(player.spaceObjectArray.listManned.get(number).getOrderTime(), player.spaceObjectArray.listManned.get(number).getCode());
            player.cash = player.cash - player.spaceObjectArray.listManned.get(number).getCostToPurchase();
            verifyButtonsForPurchase();
            PanelLabelTable.setTableData();
            PanelWarehouse.setInfoData();
        }
    };
    private transient ActionListener listenerToggleRocketImprove = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int number = Integer.parseInt(e.getActionCommand());
            modify_improve(player.spaceObjectArray.listRockets.get(number).getCode(), toggleRocketImprove[number]);
            verifyButtonsForPurchase();
        }
    };
    private transient ActionListener listenerTogglePayloadImprove = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int number = Integer.parseInt(e.getActionCommand());
            modify_improve(player.spaceObjectArray.listPayloads.get(number).getCode(), togglePayloadImprove[number]);
            verifyButtonsForPurchase();
        }
    };
    private transient ActionListener listenerToggleMannedImprove = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int number = Integer.parseInt(e.getActionCommand());
            modify_improve(player.spaceObjectArray.listManned.get(number).getCode(), toggleMannedImprove[number]);
            verifyButtonsForPurchase();
        }
    };
    private transient ActionListener listenerCrewSelection = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            DialogCrewSelection.createCrewSelectionForm(Integer.parseInt(e.getActionCommand()));
        }
    };

    public GUIStorage loadButtonsForResearch(JPanel panel, ActionListener listenerToggleResearch, ActionListener listenerToggleImprove, ActionListener listenerBuy, ArrayList<SpaceObject> listObjectData) {

        GUIStorage Storage = new GUIStorage();

        Storage.panel = panel;
        Storage.panel.setLayout(new GridLayout(0, 1));
        JPanel[] panelrockets = new JPanel[listObjectData.size()];
        JLabel[] labelName = new JLabel[listObjectData.size()];
        JLabel[] labelDesc = new JLabel[listObjectData.size()];
        JToggleButton[] toggleResearch = new JToggleButton[listObjectData.size()];
        JLabel[] labelToFinishResearch = new JLabel[listObjectData.size()];
        JLabel[] labelRocketCapacity = new JLabel[listObjectData.size()];
        JToggleButton[] toggleImprove = new JToggleButton[listObjectData.size()];
        JButton[] buttonBuy = new JButton[listObjectData.size()];
        JButton[] buttonCrew = new JButton[listObjectData.size()];

        for (int i = 0;
                i < listObjectData.size();
                i++) {

            panelrockets[i] = new JPanel(new GridLayout(1, 0));

//            JPanel panelNameModel = new JPanel(new GridLayout(0, 1));
            labelName[i] = new JLabel("<html>" + listObjectData.get(i).getName() + "<br>" + listObjectData.get(i).getModel() + "</html>");
//            panelNameModel.add(labelName[i]);
//            labelDesc[i] = new JLabel("<html>"+listObjectData.get(i).getModel()+"</html>");
//            panelNameModel.add(labelDesc[i]);
//            panelrockets[i].add(panelNameModel);
            panelrockets[i].add(labelName[i]);

            labelRocketCapacity[i] = new JLabel(Integer.toString(listObjectData.get(i).getCapacity()));
            panelrockets[i].add(labelRocketCapacity[i]);


//            panelrockets[i].add(labelToFinishResearch[i]);

            toggleResearch[i] = new JToggleButton();
            toggleResearch[i].setLayout(new GridLayout(0, 1));
            JLabel labelToggleResearchName = new JLabel(Localisation.getText("ribresearch"));
            labelToggleResearchName.setHorizontalAlignment(JLabel.CENTER);
            toggleResearch[i].add(labelToggleResearchName);
            JLabel labelToggleResearchCost = new JLabel(Integer.toString(listObjectData.get(i).getCostToResearch()) + " " + Localisation.getText("$turn"));
            labelToggleResearchCost.setHorizontalAlignment(JLabel.CENTER);
            toggleResearch[i].add(labelToggleResearchCost);
            labelToFinishResearch[i] = new JLabel(Integer.toString(listObjectData.get(i).getResearchTime()) + " " + Localisation.getText("month(s)"));
            labelToFinishResearch[i].setHorizontalAlignment(JLabel.CENTER);
            toggleResearch[i].add(labelToFinishResearch[i]);
            toggleResearch[i].setFocusable(false);
            toggleResearch[i].setActionCommand(Integer.toString(i));
            toggleResearch[i].addActionListener(listenerToggleResearch);
            panelrockets[i].add(toggleResearch[i]);

            toggleImprove[i] = new JToggleButton();
            toggleImprove[i].setLayout(new GridLayout(0, 1));
            JLabel labelToggleImproveName = new JLabel(Localisation.getText("improve"));
            labelToggleImproveName.setHorizontalAlignment(JLabel.CENTER);
            toggleImprove[i].add(labelToggleImproveName);
            JLabel labelToggleImproveCost = new JLabel(Integer.toString(listObjectData.get(i).getCostToImprove()) + " " + Localisation.getText("$turn"));
            labelToggleImproveCost.setHorizontalAlignment(JLabel.CENTER);
            toggleImprove[i].add(labelToggleImproveCost);
            toggleImprove[i].setFocusable(false);
            toggleImprove[i].setActionCommand(Integer.toString(i));
            toggleImprove[i].addActionListener(listenerToggleImprove);
            panelrockets[i].add(toggleImprove[i]);

            buttonBuy[i] = new JButton();
            buttonBuy[i].setLayout(new GridLayout(0, 1));
            JLabel labelButtonBuy = new JLabel(Localisation.getText("buy"));
            labelButtonBuy.setHorizontalAlignment(JLabel.CENTER);
            buttonBuy[i].add(labelButtonBuy);
            JLabel labelBuyCost = new JLabel(Integer.toString(listObjectData.get(i).getCostToPurchase()) + " $");
            labelBuyCost.setHorizontalAlignment(JLabel.CENTER);
            buttonBuy[i].add(labelBuyCost);
            JLabel labelButtonBuyDelivery = new JLabel(Integer.toString(listObjectData.get(i).getOrderTime()) + " " + Localisation.getText("month(s)"));
            labelButtonBuyDelivery.setHorizontalAlignment(JLabel.CENTER);
            buttonBuy[i].add(labelButtonBuyDelivery);
            buttonBuy[i].setFocusable(false);
            buttonBuy[i].addActionListener(listenerBuy);
            buttonBuy[i].setActionCommand(Integer.toString(i));
            panelrockets[i].add(buttonBuy[i]);

            if (player.spaceObjectArray.isMannedbycode(listObjectData.get(i).getCode())) {
                buttonCrew[i] = new JButton(Localisation.getText("crew"));
                buttonCrew[i].setFocusable(false);
                buttonCrew[i].addActionListener(listenerCrewSelection);
                buttonCrew[i].setActionCommand(Integer.toString(listObjectData.get(i).getCode()));
                panelrockets[i].add(buttonCrew[i]);
            }

            Storage.panel.add(panelrockets[i]);
        }

        Storage.panel.validate();



        Storage.toggleresearch = toggleResearch;
        Storage.toggleImprove = toggleImprove;
        Storage.name = labelName;
        Storage.weight = labelName;
        Storage.labeltofinishresearch = labelToFinishResearch;
        Storage.buttonBuy = buttonBuy;
        Storage.buttonCrew = buttonCrew;

        return Storage;
    }

    private void modify_research(int code, JToggleButton button, JLabel label) {
        if (button.isSelected()) {
            int months = Integer.parseInt(label.getText().replaceAll("[^0-9]", ""));
            player.research.addResearch(Dateutils.getDateMonthShift(months), code);

        } else {

            label.setText(Integer.toString(player.research.getMonthsToFinish(code)) + " " + Localisation.getText("month(s)"));
            player.research.removeResearchByCode(code);

        }
        PanelLabelTable.setTableData();
    }

    private void modify_improve(int code, JToggleButton button) {
        if (button.isSelected()) {
            player.research.improve[code] = true;
        } else {
            player.research.improve[code] = false;
        }
    }

    void verifyCrewButtons() {
        boolean value = player.nauts.listNauts.size() > 0;
        for (int i = 0; i < buttonMannedCrew.length; i++) {
            buttonMannedCrew[i].setEnabled(value);
        }
    }

    public void verifyButtonsForPurchase() {
        verifyPlannedBudget();

        verifyNextMonthButton();

        verifyOKButtons();

//        verifyButtonsForPurchaseByType(toggleRocketResearch, toggleRocketImprove, buttonRocketBuy, SpaceObjectArray.listRockets, plannedBudget, 0);
//        verifyButtonsForPurchaseByType(togglePayloadResearch, togglePayloadImprove, buttonPayloadBuy, SpaceObjectArray.listPayloads, plannedBudget, 1);
//        verifyButtonsForPurchaseByType(toggleMannedResearch, toggleMannedImprove, buttonMannedBuy, SpaceObjectArray.listManned, plannedBudget, 2);

    }

    private void verifyPlannedBudget() {
        plannedBudget = 0;

        plannedBudget = plannedBudget + verifyButtonsForPurchaseCalculate(toggleRocketResearch, toggleRocketImprove, player.spaceObjectArray.listRockets);
        plannedBudget = plannedBudget + verifyButtonsForPurchaseCalculate(togglePayloadResearch, togglePayloadImprove, player.spaceObjectArray.listPayloads);
        plannedBudget = plannedBudget + verifyButtonsForPurchaseCalculate(toggleMannedResearch, toggleMannedImprove, player.spaceObjectArray.listManned);
    }

    private void verifyNextMonthButton() {
        boolean state;
        String text = "<html>";
        if (plannedBudget > player.cash) {
            state = false;
            text = text + "<li> " + Localisation.getText("nobudget");
        } else {
            state = true;
        }
        if (text.length() < 7) {
            text = text + "<h4> " + Localisation.getText("noerrors");
        }
        text = text + "</html>";
        PanelNextTurn.setNextTurn(state, text);
    }

    private void verifyOKButtons() {
        verifyOKButtonsByType(player.spaceObjectArray.listRockets, buttonRocketBuy, 0);
        verifyOKButtonsByType(player.spaceObjectArray.listPayloads, buttonPayloadBuy, 1);
        verifyOKButtonsByType(player.spaceObjectArray.listManned, buttonMannedBuy, 2);
    }

    private void verifyOKButtonsByType(ArrayList<SpaceObject> listObjects, JButton[] buttonPurchase, int storageNumber) {

        for (int i = 1; i < buttonPurchase.length; i++) {
            SpaceObject object = listObjects.get(i);
            if (storageImprove[storageNumber][i]) {
                boolean possible = player.cash - object.getCostToPurchase() >= 0;
                buttonPurchase[i].setEnabled(possible);
//                if (!possible) {
//                    buttonPurchase[i].setIcon(Icons.iconNoMoney);
//                } else {
//                    buttonPurchase[i].setIcon(null);
//                }
                setInnerLabelIcon(buttonPurchase[i], possible, Icons.iconNoMoney);
            }
        }

    }

    private int verifyButtonsForPurchaseCalculate(JToggleButton[] toggleResearch, JToggleButton[] toggleImprove, ArrayList<SpaceObject> listObjects) {
        int tempBudget = 0;
        for (int i = 1; i < toggleResearch.length; i++) {
            SpaceObject object = listObjects.get(i);
            if (toggleResearch[i].isEnabled() & toggleResearch[i].isSelected()) {
                tempBudget = tempBudget + object.getCostToResearch();
            }
            if (toggleImprove[i].isEnabled() & toggleImprove[i].isSelected()) {
                tempBudget = tempBudget + object.getCostToImprove();
            }
        }
        return tempBudget;
    }

    private void setInnerLabelIcon(JComponent component, boolean enable, Icon icon) {
        Component c = component.getComponent(0);
        if (c instanceof JLabel) {
            JLabel l = (JLabel) c;
            if (!enable) {
                l.setIcon(icon);
            } else {
                l.setIcon(null);
            }
        }
    }

    public void verifybuttons() { //verify buttons available for research and improve. Also creates a matrix of available buttons for further purchase check
        int maxsize = Math.max(player.spaceObjectArray.listRockets.size(), Math.max(player.spaceObjectArray.listPayloads.size(), player.spaceObjectArray.listManned.size()));
        storageResearch = new boolean[3][maxsize];
        storageImprove = new boolean[3][maxsize];

        verifyButtonsByType(toggleRocketResearch, toggleRocketImprove, buttonRocketBuy, player.spaceObjectArray.listRockets, 0);
        verifyButtonsByType(togglePayloadResearch, togglePayloadImprove, buttonPayloadBuy, player.spaceObjectArray.listPayloads, 1);
        verifyButtonsByType(toggleMannedResearch, toggleMannedImprove, buttonMannedBuy, player.spaceObjectArray.listManned, 2);

        verifyButtonsForPurchase();
        verifyCrewButtons();
    }

    private void verifyButtonsByType(JToggleButton[] toggleResearch, JToggleButton[] toggleImprove, JButton[] buttonBuy, ArrayList<SpaceObject> listObjects, int storageNumber) {
        for (int i = 0; i < listObjects.size(); i++) {
            SpaceObject vehicle = listObjects.get(i);
            boolean isResearched = player.research.finished_research[vehicle.getCode()];
            if (!isResearched) { //If a rocket is not researched verify research availability
                boolean enable = player.research.finished_research[vehicle.getPrerequisite()];
                toggleResearch[i].setEnabled(enable);
                storageResearch[storageNumber][i] = enable;
                setInnerLabelIcon(toggleResearch[i], enable, Icons.iconNotResearched);
            } else {
                boolean enable = false;
                toggleResearch[i].setEnabled(enable); //Already researched rocket can not be researched
                storageResearch[storageNumber][i] = enable;
            }

            // Verify improvements and order. They are possible only for researched rocket


            toggleImprove[i].setEnabled(isResearched);
            storageImprove[storageNumber][i] = isResearched;

//            if (!isResearched) {
//                toggleImprove[i].setIcon(Icons.iconNotResearched);
//            } else {
//                toggleImprove[i].setIcon(null);
//            }
            setInnerLabelIcon(toggleImprove[i], isResearched, Icons.iconNotResearched);
            buttonBuy[i].setEnabled(isResearched);
//            if (!isResearched) {
//                buttonBuy[i].setIcon(Icons.iconNotResearched);
//            } else {
//                buttonBuy[i].setIcon(null);
//            }
            setInnerLabelIcon(buttonBuy[i], isResearched, Icons.iconNotResearched);
        }
//        verifyButtonsForPurchase();
    }

    public void conductresearch() {
        for (Iterator<StoreObject> it = player.research.research.iterator(); it.hasNext();) {
            StoreObject tempObject = it.next();
            SpaceObject object = player.spaceObjectArray.findObjectByCode(tempObject.objectcode);
            if (player.cash - object.getCostToResearch() >= 0) {
                object.adjustDurabilityMonthly();
                player.cash = player.cash - object.getCostToResearch();

                System.out.println("conduct research, object " + object.getName() + ", new budget - " + Integer.toString(player.cash));
            } else {
                System.out.println("conduct research, no funds for " + object.getName() + ", " + Integer.toString(player.cash) + ", Remove" + player.research.getResearchNames());
//                Research.removeResearch(tempObject);
                it.remove();
                System.out.println("conduct research, after removal of" + object.getName() + ", " + Integer.toString(player.cash) + ", Remove, After" + player.research.getResearchNames());
                if (player.spaceObjectArray.isRocketbycode(tempObject.objectcode)) {
//                    toggleRocketResearch[SpaceObjectArray.findObjectNumberByCode(tempObject.objectcode)].setSelected(false);
                    setSafeSelected(toggleRocketResearch[player.spaceObjectArray.findObjectNumberByCode(tempObject.objectcode)], false);
                } else if (player.spaceObjectArray.isPayloadbycode(tempObject.objectcode)) {
//                    togglePayloadResearch[SpaceObjectArray.findObjectNumberByCode(tempObject.objectcode)].setSelected(false);
                    setSafeSelected(togglePayloadResearch[player.spaceObjectArray.findObjectNumberByCode(tempObject.objectcode)], false);
                } else if (player.spaceObjectArray.isMannedbycode(tempObject.objectcode)) {
//                    toggleMannedResearch[SpaceObjectArray.findObjectNumberByCode(tempObject.objectcode)].setSelected(false);
                    setSafeSelected(toggleMannedResearch[player.spaceObjectArray.findObjectNumberByCode(tempObject.objectcode)], false);
                }
            }
            System.out.println("conduct research " + player.spaceObjectArray.findObjectByCode(tempObject.objectcode).getName()
                    + " ,new durability - " + Integer.toString(player.spaceObjectArray.findObjectByCode(tempObject.objectcode).getDurability()));
        }

        for (int i = 0; i < player.research.improve.length; i++) {
            if (player.research.improve[i] == true) {
                SpaceObject object = player.spaceObjectArray.findObjectByCode(i);
                if (player.cash - object.getCostToResearch() >= 0) {
                    object.adjustDurabilityMonthly();
                    player.cash = player.cash - object.getCostToResearch();
                } else {
                    player.research.improve[i] = false;
                    if (player.spaceObjectArray.isRocketbycode(i)) {
//                        toggleRocketImprove[SpaceObjectArray.findObjectNumberByCode(i)].setSelected(false);
                        setSafeSelected(toggleRocketImprove[player.spaceObjectArray.findObjectNumberByCode(i)], false);
                    } else if (player.spaceObjectArray.isPayloadbycode(i)) {
//                        togglePayloadImprove[SpaceObjectArray.findObjectNumberByCode(i)].setSelected(false);
                        setSafeSelected(togglePayloadImprove[player.spaceObjectArray.findObjectNumberByCode(i)], false);
                    } else if (player.spaceObjectArray.isMannedbycode(i)) {
                        toggleMannedImprove[player.spaceObjectArray.findObjectNumberByCode(i)].setSelected(false);
                        setSafeSelected(toggleMannedImprove[player.spaceObjectArray.findObjectNumberByCode(i)], false);
                    }
                }
                //FOR DEBUG
                System.out.println("conduct research, object " + player.spaceObjectArray.findObjectByCode(i).getName()
                        + " new durability" + Integer.toString(player.spaceObjectArray.findObjectByCode(i).getDurability()));
                //END FOR DEBUG

            }
        }
    }

    public void setSafeSelected(AbstractButton button, boolean value) {
        ActionListener[] listListeners = button.getActionListeners();
        for (int i = 0; i < listListeners.length; i++) {
            button.removeActionListener(listListeners[i]);
        }
        button.setSelected(value);
        for (int i = 0; i < listListeners.length; i++) {
            button.addActionListener(listListeners[i]);
        }
    }

    public void updatelabels() {
        for (int i = 0; i < player.research.research.size(); i++) {

            if (player.spaceObjectArray.isRocketbycode(player.research.research.get(i).objectcode)) {
                //we know code of researching object. So, label with the number of number of arraylist of researches with given code is receiving count of months to finish research
                labelRocketResearchtofinish[player.spaceObjectArray.findObjectNumberByCode(player.research.research.get(i).objectcode)].setText(Integer.toString(player.research.getMonthsToFinish(player.research.research.get(i).objectcode)) + Localisation.getText("month(s)"));
            }
            if (player.spaceObjectArray.isPayloadbycode(player.research.research.get(i).objectcode)) {
                labelPayloadResearchtofinish[player.spaceObjectArray.findObjectNumberByCode(player.research.research.get(i).objectcode)].setText(Integer.toString(player.research.getMonthsToFinish(player.research.research.get(i).objectcode)) + Localisation.getText("month(s)"));
            }
            if (player.spaceObjectArray.isMannedbycode(player.research.research.get(i).objectcode)) {
                labelMannedResearchtofinish[player.spaceObjectArray.findObjectNumberByCode(player.research.research.get(i).objectcode)].setText(Integer.toString(player.research.getMonthsToFinish(player.research.research.get(i).objectcode)) + Localisation.getText("month(s)"));
            }
        }
    }
}
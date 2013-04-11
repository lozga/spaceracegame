/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class DialogHireNauts {

    Player player;

    DialogHireNauts(Player givenplayer) {
        player = givenplayer;
    }

    private class MutableList extends JList {

        MutableList() {
            super(new DefaultListModel());
        }

        DefaultListModel getContents() {
            return (DefaultListModel) getModel();
        }
    }
    private JDialog dialogNauts;
    private MutableList listCandidates;
    private MutableList listNauts;
    private ArrayList<Naut> listselected;
    private JLabel labelLeftSkills;
    private JLabel labelRightSkills;
    private JLabel labelRemaining;
    private int remaining;
    private JButton buttonOK;

    public void createdialog() {

        dialogNauts = new JDialog((Frame) null, Localisation.getText("hirenauts"), true);
        Utils.setDamnedSize(dialogNauts, new Dimension(300, 500));

        JPanel panelNewNauts = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel labelTop = new JLabel(formrecruitmentnumber(player.nauts.recruitmentnumber + 1));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panelNewNauts.add(labelTop, gbc);

        labelRemaining = new JLabel();
        gbc.gridx = 1;
        panelNewNauts.add(labelRemaining, gbc);

        labelLeftSkills = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelNewNauts.add(labelLeftSkills, gbc);

        labelRightSkills = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelNewNauts.add(labelRightSkills, gbc);

        JButton buttonacquire = new JButton(">>");
        buttonacquire.addActionListener(listenerMoveRight);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelNewNauts.add(buttonacquire, gbc);

        JButton buttondismiss = new JButton("<<");
        buttondismiss.addActionListener(listenerMoveLeft);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelNewNauts.add(buttondismiss, gbc);

        listCandidates = new MutableList();
        listCandidates.addListSelectionListener(listenerLeftListClick);
        JScrollPane scrollcandidates = new JScrollPane(listCandidates);
        scrollcandidates.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollcandidates.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1;
        panelNewNauts.add(scrollcandidates, gbc);

        listNauts = new MutableList();
        listNauts.addListSelectionListener(listenerRightListClick);
        JScrollPane scrollnauts = new JScrollPane(listNauts);
        scrollnauts.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollnauts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weighty = 1;
        panelNewNauts.add(scrollnauts, gbc);

        buttonOK = new JButton(Localisation.getText("ok"));
        buttonOK.addActionListener(listenerButtonOK);
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelNewNauts.add(buttonOK,gbc);

        JButton buttonCancel = new JButton(Localisation.getText("cancel"));
        buttonCancel.addActionListener(listenerButtonCancel);
        gbc.weighty = 0;
        gbc.gridx = 1;
        gbc.gridy = 4;
        panelNewNauts.add(buttonCancel,gbc);

        initializedata();
        dialogNauts.setContentPane(panelNewNauts);
        dialogNauts.pack();
        dialogNauts.setLocationRelativeTo(DialogMainField.mainFrame);
        dialogNauts.setVisible(true);

    }
    private ListSelectionListener listenerLeftListClick = new ListSelectionListener() {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            updateleftskills();
        }
    };
    private ListSelectionListener listenerRightListClick = new ListSelectionListener() {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            updaterightskills();
        }
    };
    private ActionListener listenerMoveRight = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            moveRight();
        }
    };
    private ActionListener listenerMoveLeft = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            moveLeft();
        }
    };
    private ActionListener listenerButtonOK = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            clickOK();
        }
    };
    private ActionListener listenerButtonCancel = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            dialogNauts.dispose();
        }
    };

    private int obscureskill(int skill) {
        int returnint = 0;
        returnint = skill + (Utils.random(10) - 5);
        if (returnint < 0) {
            returnint = 0;
        } else if (returnint > 10) {
            returnint = 10;
        }

        return returnint;
    }

    private String formrecruitmentnumber(int number) {
        return "<html>"+Localisation.getText("recruitmentnumber")+": <b>" + Integer.toString(number) + "</b></html>";
    }

    private String formremaining(int number) {
        return "<html>"+Localisation.getText("positionsremaining")+": <b>" + Integer.toString(number) + "</b></html>";
    }

    private void initializedata() {
        listselected = new ArrayList<Naut>();
        for (Naut tempNaut : player.nauts.listCandidates) {
            if (tempNaut.recrutingnumber == player.nauts.recruitmentnumber + 1) {
                Naut writeNaut = new Naut();
                writeNaut.name = tempNaut.name;
                writeNaut.skillCapsule = obscureskill(tempNaut.skillCapsule);
                writeNaut.skillDock = obscureskill(tempNaut.skillDock);
                writeNaut.skillEVA = obscureskill(tempNaut.skillEVA);
                writeNaut.skillEndurance = obscureskill(tempNaut.skillEndurance);
                writeNaut.skillLM = obscureskill(tempNaut.skillLM);
                listselected.add(writeNaut);
            }
        }

        for (Naut tempNaut : listselected) {
            listCandidates.getContents().addElement(tempNaut.name);
        }
        listCandidates.setSelectedIndex(0);
        updateleftskills();

        remaining = player.nauts.arrayrecruitmentpools[player.nauts.recruitmentnumber];
        labelRemaining.setText(formremaining(remaining));

    }

    private void updateleftskills() {
        if (!listCandidates.isSelectionEmpty()) {
            Naut selectedNaut = findSelectedByName(listCandidates.getSelectedValue().toString());
            String leftlabel = "<html>";
            leftlabel = leftlabel + ""+Localisation.getText("cap")+": <b>" + Integer.toString(selectedNaut.skillCapsule) + "</b><br>";
            leftlabel = leftlabel + ""+Localisation.getText("do")+": <b>" + Integer.toString(selectedNaut.skillDock) + "</b><br>";
            leftlabel = leftlabel + ""+Localisation.getText("eva")+": <b>" + Integer.toString(selectedNaut.skillEVA) + "</b><br>";
            leftlabel = leftlabel + ""+Localisation.getText("lm")+": <b>" + Integer.toString(selectedNaut.skillLM) + "</b><br>";
            leftlabel = leftlabel + ""+Localisation.getText("end")+": <b>" + Integer.toString(selectedNaut.skillEndurance) + "</b><br>";
            labelLeftSkills.setText(leftlabel);
        } else {
            String leftlabel = "<html>";
            leftlabel = leftlabel + ""+Localisation.getText("cap")+": <br>";
            leftlabel = leftlabel + ""+Localisation.getText("do")+": <br>";
            leftlabel = leftlabel + ""+Localisation.getText("eva")+": <br>";
            leftlabel = leftlabel + ""+Localisation.getText("lm")+": <br>";
            leftlabel = leftlabel + ""+Localisation.getText("end")+": <br>";
            labelLeftSkills.setText(leftlabel);
        }

    }

    private void updaterightskills() {
        if (!listNauts.isSelectionEmpty()) {
            Naut selectedNaut = findSelectedByName(listNauts.getSelectedValue().toString());
            String rightlabel = "<html>";
            rightlabel = rightlabel + ""+Localisation.getText("cap")+": <b>" + Integer.toString(selectedNaut.skillCapsule) + "</b><br>";
            rightlabel = rightlabel + ""+Localisation.getText("do")+": <b>" + Integer.toString(selectedNaut.skillDock) + "</b><br>";
            rightlabel = rightlabel + ""+Localisation.getText("eva")+": <b>" + Integer.toString(selectedNaut.skillEVA) + "</b><br>";
            rightlabel = rightlabel + ""+Localisation.getText("lm")+": <b>" + Integer.toString(selectedNaut.skillLM) + "</b><br>";
            rightlabel = rightlabel + ""+Localisation.getText("end")+": <b>" + Integer.toString(selectedNaut.skillEndurance) + "</b><br>";
            labelRightSkills.setText(rightlabel);
        } else {
            String rightlabel = "<html>";
            rightlabel = rightlabel + ""+Localisation.getText("cap")+": <br>";
            rightlabel = rightlabel + ""+Localisation.getText("do")+": <br>";
            rightlabel = rightlabel + ""+Localisation.getText("eva")+": <br>";
            rightlabel = rightlabel + ""+Localisation.getText("lm")+": <br>";
            rightlabel = rightlabel + ""+Localisation.getText("end")+": <br>";
            labelRightSkills.setText(rightlabel);
        }

    }

    private Naut findSelectedByName(String name) {
        Naut returnNaut = new Naut();

        for (Naut tempNaut : listselected) {
            if (tempNaut.name.equals(name)) {
                returnNaut = tempNaut;
            }
        }

        return returnNaut;
    }

    private void moveRight() {
        if (!listCandidates.isSelectionEmpty() & remaining > 0) {
            String name = listCandidates.getSelectedValue().toString();
            listCandidates.getContents().removeElement(name);
            listNauts.getContents().addElement(name);
            remaining = remaining - 1;
            updateleftskills();
            updaterightskills();
            labelRemaining.setText(formremaining(remaining));
            buttonOK.setEnabled(remaining == 0);
            listCandidates.setSelectedIndex(0);
        }

    }

    private void moveLeft() {
        if (!listNauts.isSelectionEmpty()) {
            String name = listNauts.getSelectedValue().toString();
            listNauts.getContents().removeElement(name);
            listCandidates.getContents().addElement(name);
            remaining = remaining + 1;
            updateleftskills();
            updaterightskills();
            labelRemaining.setText(formremaining(remaining));
            buttonOK.setEnabled(remaining == 0);
            listNauts.setSelectedIndex(0);
        }

    }

    private void clickOK() {
        for (int i = 0; i < listNauts.getContents().getSize(); i++) {
            player.nauts.listNauts.add(player.nauts.findCandidateByName(listNauts.getContents().getElementAt(i).toString()));
        }
        dialogNauts.dispose();
        player.panelnauts.updateListNauts();
    }
}

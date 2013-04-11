/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class PanelNauts implements Serializable{

    JPanel panelListNauts;
    JPanel mainPanel;
    Player player;
    
        PanelNauts(Player givenplayer) {
        player=givenplayer;
    }

    public JPanel createPanelNauts() {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel upperPanel = new JPanel(new FlowLayout());
        JButton buttonNewCrew = new JButton(Localisation.getText("hirenauts"));
        buttonNewCrew.addActionListener(listenerNewCrew);
        upperPanel.add(buttonNewCrew);
        upperPanel.setMaximumSize(new Dimension(3000, 50));
        upperPanel.setBorder(new LineBorder(Color.black));
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor=GridBagConstraints.PAGE_START;
        mainPanel.add(upperPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 40;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.PAGE_START;
        mainPanel.add(createLowerPanel(),gbc);
        return mainPanel;
    }
    private transient ActionListener listenerNewCrew = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            DialogHireNauts dhn= new DialogHireNauts(player);
            dhn.createdialog();
            
            updateListNauts();
        }
    };

    public void updateListNauts() {
        panelListNauts.removeAll();
        panelListNauts.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor=GridBagConstraints.NORTH;
        for (int i = 0; i < player.nauts.listNauts.size() + 1; i++) {
            if (i == 0) {
                gbc.gridx = 0;
                gbc.gridy = 0;
                panelListNauts.add(new JLabel(Localisation.getText("name")),gbc);
                gbc.gridx = 1;
                panelListNauts.add(new JLabel(Localisation.getText("cap")),gbc);
                gbc.gridx = 2;
                panelListNauts.add(new JLabel(Localisation.getText("lm")),gbc);
                gbc.gridx = 3;
                panelListNauts.add(new JLabel(Localisation.getText("eva")),gbc);
                gbc.gridx = 4;
                panelListNauts.add(new JLabel(Localisation.getText("do")),gbc);
                gbc.gridx = 5;
                panelListNauts.add(new JLabel(Localisation.getText("end")),gbc);
            } else {
                gbc.gridy = i;
                gbc.gridx = 0;
                Naut tempNaut = player.nauts.listNauts.get(i - 1);
                panelListNauts.add(new JLabel(tempNaut.getName()),gbc);
                gbc.gridx = 1;
                panelListNauts.add(new JLabel(Integer.toString(tempNaut.skillCapsule)),gbc);
                gbc.gridx = 2;
                panelListNauts.add(new JLabel(Integer.toString(tempNaut.skillLM)),gbc);
                gbc.gridx = 3;
                panelListNauts.add(new JLabel(Integer.toString(tempNaut.skillEVA)),gbc);
                gbc.gridx = 4;
                panelListNauts.add(new JLabel(Integer.toString(tempNaut.skillDock)),gbc);
                gbc.gridx = 5;
                panelListNauts.add(new JLabel(Integer.toString(tempNaut.skillEndurance)),gbc);
            }
        }
        mainPanel.validate();
        player.panelRIBs.verifyCrewButtons();
    }

    private JPanel createLowerPanel() {
        panelListNauts = new JPanel(new GridBagLayout());
        panelListNauts.setBorder(new LineBorder(Color.black));
        updateListNauts();
        return panelListNauts;
    }

}

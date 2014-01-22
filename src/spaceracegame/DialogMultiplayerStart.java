/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author filippterekhov
 */
public class DialogMultiplayerStart {

    static JDialog dialogMultiplayerStart;
    static JLabel labelStatus;
    public static boolean isFirstPlayerUSSR;
    static JLabel labelHostFlag;
    static JLabel labelClientFlag;
    static boolean isserver;
    static JButton buttonSwitch;
    static JButton buttonStart;
    static JFrame frameParentFrame;

    public static void createDialog(boolean givenIsServer, boolean isconnected, JFrame parentFrame) {
        frameParentFrame = parentFrame;
        isserver = givenIsServer;
        dialogMultiplayerStart = new JDialog(parentFrame, Localisation.getText("multiplayer"), false);
        Utils.setDamnedSize(dialogMultiplayerStart, new Dimension(300, 300));

        JPanel panelmain = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        labelStatus = new JLabel();
        if (isconnected) {
            labelStatus.setText(Localisation.getText("connectionestablished"));
        } else {
            labelStatus.setText(Localisation.getText("waitforopponent"));
        }
        labelStatus.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panelmain.add(labelStatus, gbc);

        String s = Localisation.getText("host");
        if (isserver) {
            s = s + " " + Localisation.getText("you");
        }
        JLabel labelHost = new JLabel(s);
        labelHost.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelmain.add(labelHost, gbc);

        String s2 = Localisation.getText("client");
        if (!isserver) {
            s2 = s2 + " " + Localisation.getText("you");
        }
        JLabel labelClient = new JLabel(s2);
        labelClient.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelmain.add(labelClient, gbc);

        labelHostFlag = new JLabel(Icons.iconUSSRflagsmall);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelmain.add(labelHostFlag, gbc);

        labelClientFlag = new JLabel(Icons.iconUSAflagsmall);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelmain.add(labelClientFlag, gbc);

        isFirstPlayerUSSR = true;

        buttonSwitch = new JButton(Localisation.getText("switch"));
        buttonSwitch.addActionListener(listenerButtonSwitch);
        buttonSwitch.setEnabled(false);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        panelmain.add(buttonSwitch, gbc);

        JPanel panelOKCancel = new JPanel(new GridLayout(1, 0));

        buttonStart = new JButton(Localisation.getText("start"));
        buttonStart.addActionListener(listenerButtonStart);
        buttonStart.setEnabled(false);
        panelOKCancel.add(buttonStart);



        JButton buttonCancel = new JButton(Localisation.getText("cancel"));
        buttonCancel.addActionListener(listenerButtonCancel);
        panelOKCancel.add(buttonCancel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        panelmain.add(panelOKCancel, gbc);

        if (isserver) {
            SocketServer.CreateServerSocket();
        }

        dialogMultiplayerStart.setContentPane(panelmain);
        dialogMultiplayerStart.setLocationRelativeTo(null);
        dialogMultiplayerStart.pack();
        dialogMultiplayerStart.setVisible(true);

    }
    private static ActionListener listenerButtonSwitch = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {

            if (isFirstPlayerUSSR) {
                labelHostFlag.setIcon(Icons.iconUSAflagsmall);
                labelClientFlag.setIcon(Icons.iconUSSRflagsmall);
                isFirstPlayerUSSR = !isFirstPlayerUSSR;
            } else {
                labelHostFlag.setIcon(Icons.iconUSSRflagsmall);
                labelClientFlag.setIcon(Icons.iconUSAflagsmall);
                isFirstPlayerUSSR = !isFirstPlayerUSSR;
            }
            SocketServer.sendSide(Boolean.toString(isFirstPlayerUSSR));
        }
    };

    public static void setSide(String givenside) {
        boolean boolside = Boolean.valueOf(givenside);
        isFirstPlayerUSSR = boolside;
        if (boolside) {

            labelHostFlag.setIcon(Icons.iconUSSRflagsmall);
            labelClientFlag.setIcon(Icons.iconUSAflagsmall);
        } else {
            labelHostFlag.setIcon(Icons.iconUSAflagsmall);
            labelClientFlag.setIcon(Icons.iconUSSRflagsmall);
        }
    }
    private static ActionListener listenerButtonCancel = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                SocketServer.closeServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialogMultiplayerStart.dispose();
        }
    };
    private static ActionListener listenerButtonStart = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SocketServer.sendStartGame();
//            DialogMainField.startNewGame(true, true, isFirstPlayerUSSR);
//            dialogMultiplayerStart.dispose();
//            frameParentFrame.dispose();
        }
    };

    public static void updateAfterConnection() {
        labelStatus.setText(Localisation.getText("connectionestablished"));
        if (isserver) {
            buttonStart.setEnabled(true);
            buttonSwitch.setEnabled(true);
        }
    }

    public static void callDispose() {
        dialogMultiplayerStart.dispose();
        frameParentFrame.dispose();
    }
}

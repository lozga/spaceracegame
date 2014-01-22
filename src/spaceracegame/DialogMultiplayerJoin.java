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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author filippterekhov
 */
public class DialogMultiplayerJoin {

    JDialog dialogMultiplayerJoin;
    JLabel labelStatus;
    JTextField textIP;
    JFrame frameParentFrame;

    public void createDialog(JFrame parentFrame) {
        frameParentFrame=parentFrame;
        dialogMultiplayerJoin = new JDialog(parentFrame, Localisation.getText("multiplayer"), true);
        Utils.setDamnedSize(dialogMultiplayerJoin, new Dimension(400, 200));
        JPanel panelmain = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        labelStatus = new JLabel(Localisation.getText("inputip"));
        labelStatus.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panelmain.add(labelStatus, gbc);

        textIP = new JTextField();
        textIP.setText("127.0.0.1");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        panelmain.add(textIP, gbc);

        JPanel panelOKCancel = new JPanel(new GridLayout(1, 0));

        JButton buttonStart = new JButton(Localisation.getText("connect"));
        buttonStart.addActionListener(listenerConnect);
        panelOKCancel.add(buttonStart);

        JButton buttonCancel = new JButton(Localisation.getText("cancel"));
        buttonCancel.addActionListener(listenerButtonCancel);
        panelOKCancel.add(buttonCancel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelmain.add(panelOKCancel, gbc);

        dialogMultiplayerJoin.setContentPane(panelmain);
        dialogMultiplayerJoin.setLocationRelativeTo(null);
        dialogMultiplayerJoin.pack();
        dialogMultiplayerJoin.setVisible(true);

    }
    private ActionListener listenerButtonCancel = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                SocketClient.closeClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialogMultiplayerJoin.dispose();
        }
    };
    private ActionListener listenerConnect = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                if (SocketClient.CreateClientSocket(InetAddress.getByName(textIP.getText()))) {
                    dialogMultiplayerJoin.dispose();
                    DialogMultiplayerStart.createDialog(false,true,frameParentFrame);

                } else {
                    labelStatus.setText(Localisation.getText("connectionfailed"));
                }
            } catch (UnknownHostException ex) {
                labelStatus.setText(ex.getMessage());
                Logger.getLogger(DialogMultiplayerJoin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
}

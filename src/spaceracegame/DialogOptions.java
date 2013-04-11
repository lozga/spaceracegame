/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class DialogOptions {

    private static JDialog dialogOptions;
    static JComboBox comboLanguage;
    static JTextField fieldLaunchDelay;

    public static void createOptionsDialog() {
        dialogOptions = new JDialog((Frame) null, Localisation.getText("options"), true);
        Utils.setDamnedSize(dialogOptions, new Dimension(200, 400));
        JPanel mainpanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panelLanguage = new JPanel(new GridLayout(1, 0));
        TitledBorder titleLanguage = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("language"), TitledBorder.CENTER, TitledBorder.CENTER);
        panelLanguage.setBorder(titleLanguage);
        comboLanguage = new JComboBox();
        comboLanguage.addItem("English");
        comboLanguage.addItem("Русский");
        panelLanguage.add(comboLanguage);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        mainpanel.add(panelLanguage, gbc);

        JPanel panelDelay = new JPanel(new GridLayout(1, 0));
        TitledBorder titleDelay = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Localisation.getText("delaylaunchtext"), TitledBorder.CENTER, TitledBorder.CENTER);
        panelDelay.setBorder(titleDelay);
        JLabel labelDelay = new JLabel(Localisation.getText("delaylaunchtext(ms)"));
        panelDelay.add(labelDelay);
        fieldLaunchDelay=new JTextField(Integer.toString(Options.delayLaunchText));
        panelDelay.add(fieldLaunchDelay);
        gbc.gridy=1;
        mainpanel.add(panelDelay,gbc);

        JPanel panelstub = new JPanel();
        gbc.gridy = 4;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(panelstub, gbc);

        JPanel panelOKCancel = new JPanel(new GridLayout(1, 0));
        JButton buttonOK = new JButton(Localisation.getText("ok"));
        buttonOK.addActionListener(listenerOK);
        panelOKCancel.add(buttonOK);
        JButton buttonCancel = new JButton(Localisation.getText("cancel"));
        buttonCancel.addActionListener(listenerCancel);
        panelOKCancel.add(buttonCancel);
        gbc.gridy = 5;
        gbc.weighty = 0;
        mainpanel.add(panelOKCancel, gbc);

        dialogOptions.setContentPane(mainpanel);
        dialogOptions.setLocationRelativeTo(null);
        dialogOptions.pack();
        dialogOptions.setVisible(true);
    }
    private static ActionListener listenerOK = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (comboLanguage.getSelectedItem() != null) {
                String s = comboLanguage.getSelectedItem().toString();
                if (s.equals("English")) {
                    Options.langShort = "en";
                } else if (s.equals("Русский")) {
                    Options.langShort = "ru";
                }
            }
            if(!fieldLaunchDelay.getText().equals("")){
                int number=Integer.parseInt(fieldLaunchDelay.getText());
                if((number>0)&&(number<60000)){
                    Options.delayLaunchText=number;
                }
            }
            Options.writeOptionsFile();
            dialogOptions.dispose();
            DialogMainMenu d1 = new DialogMainMenu();
            d1.createMainMenu();
        }
    };
    private static ActionListener listenerCancel = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            dialogOptions.dispose();
            DialogMainMenu d1 = new DialogMainMenu();
            d1.createMainMenu();
        }
    };
}

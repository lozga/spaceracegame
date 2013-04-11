/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.io.Serializable;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class Naut implements Serializable{

    String name;
    int skillCapsule;
    int skillDock;
    int skillEVA;
    int skillLM;
    int skillEndurance;
    int recrutingnumber;
    String fate;

    public String getName() {
        return name;
    }
    
}

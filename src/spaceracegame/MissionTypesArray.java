/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.util.ArrayList;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class MissionTypesArray {

    public static ArrayList<MissionTypes> listMissionTypes = new ArrayList<MissionTypes>();

    public static MissionTypes findMissionTypeByName(String givenname) {

        for (MissionTypes tempmissiontype : listMissionTypes) {
            if (tempmissiontype.name.equals(givenname)) {
                return tempmissiontype;
            }
        }
        return null;
    }

    public static MissionTypes findMissionTypeByCode(int givencode) {

        for (MissionTypes tempmissiontype : listMissionTypes) {
            if (tempmissiontype.code == givencode) {
                return tempmissiontype;
            }
        }
        return null;
    }
    
}

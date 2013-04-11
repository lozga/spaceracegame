/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class SpaceObjectArray implements Serializable{

    public ArrayList<SpaceObject> listRockets = new ArrayList<SpaceObject>();
    public ArrayList<SpaceObject> listPayloads = new ArrayList<SpaceObject>();
    public ArrayList<SpaceObject> listManned = new ArrayList<SpaceObject>();

    public SpaceObject findObjectByName(String givenname) {

        for (SpaceObject tempObject : listRockets) {
            if (tempObject.name.equals(givenname)) {
                return tempObject;
            }
        }
        for (SpaceObject tempObject : listPayloads) {
            if (tempObject.name.equals(givenname)) {
                return tempObject;
            }
        }
        for (SpaceObject tempObject : listManned) {
            if (tempObject.name.equals(givenname)) {
                return tempObject;
            }
        }
        return null;
    }

    public SpaceObject findMannedByName(String givenname) {
        for (SpaceObject tempObject : listManned) {
            if (tempObject.name.equals(givenname)) {
                return tempObject;
            }
        }
        return null;
    }

    public SpaceObject findObjectByCode(int givencode) {

        for (SpaceObject tempObject : listRockets) {
            if (tempObject.code == givencode) {
                return tempObject;
            }
        }
        for (SpaceObject tempObject : listPayloads) {
            if (tempObject.code == givencode) {
                return tempObject;
            }
        }
        for (SpaceObject tempObject : listManned) {
            if (tempObject.code == givencode) {
                return tempObject;
            }
        }

        return null;
    }

    public int findObjectNumberByCode(int givencode) {
        for (int i = 0; i < listRockets.size(); i++) {
            if (listRockets.get(i).getCode() == givencode) {
                return i;
            }
        }
        for (int i = 0; i < listPayloads.size(); i++) {
            if (listPayloads.get(i).getCode() == givencode) {
                return i;
            }
        }
        for (int i = 0; i < listManned.size(); i++) {
            if (listManned.get(i).getCode() == givencode) {
                return i;
            }
        }
        return -1;
    }

    public boolean isRocketbycode(int givencode) {
        boolean result = false;
        for (SpaceObject tempobject : listRockets) {
            if (tempobject.code == givencode) {
                result = true;
            }
        }
        return result;
    }

    public boolean isPayloadbycode(int givencode) {
        boolean result = false;
        for (SpaceObject tempobject : listPayloads) {
            if (tempobject.code == givencode) {
                result = true;
            }
        }
        return result;
    }

    public boolean isMannedbycode(int givencode) {
        boolean result = false;
        for (SpaceObject tempobject : listManned) {
            if (tempobject.code == givencode) {
                result = true;
            }
        }
        return result;
    }

    public ArrayList<String> getMannedNames() {
        ArrayList<String> returnarray = new ArrayList<String>();
        for (int i = 0; i < listManned.size(); i++) {
            returnarray.add(listManned.get(i).name);
        }
        return returnarray;
    }
}

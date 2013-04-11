/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class Research implements Serializable{

    public ArrayList<StoreObject> research = new ArrayList<StoreObject>(); //Array for future deliveries of objects. Format: Date, Object
    public boolean[] finished_research;
    public boolean[] improve;
    private Player player;

    Research(Player givenPlayer) {
        player = givenPlayer;
    }

    public void initialize() {
        finished_research = new boolean[player.warehouse.storehouse.length];
        finished_research[0] = true;
        improve = new boolean[player.warehouse.storehouse.length];
    }

    public ArrayList<StoreObject> getResearchByDate(Date date) {

        ArrayList<StoreObject> tempstoreobjectlist = new ArrayList<StoreObject>();
        Calendar leftCalendar = GregorianCalendar.getInstance();
        leftCalendar.setTime(date);
        leftCalendar.add(Calendar.DAY_OF_MONTH, -1);
        Date leftDate = leftCalendar.getTime();
        Calendar rightCalendar = GregorianCalendar.getInstance();
        rightCalendar.setTime(date);
        rightCalendar.add(Calendar.MONTH, 1);
        Date rightDate = rightCalendar.getTime();
        for (int i = 0; i < research.size(); i++) {
            if (((research.get(i).objectdate.before(rightDate))) & ((research.get(i).objectdate.after(leftDate)))) {
                tempstoreobjectlist.add(research.get(i));
            }
        }
        return tempstoreobjectlist;
    }

    public ArrayList<StoreObject> getResearchAfterDate(Date date) {

        ArrayList<StoreObject> tempstoreobjectlist = new ArrayList<StoreObject>();

        for (int i = 0; i < research.size(); i++) {
            if (research.get(i).objectdate.after(date)) {
                tempstoreobjectlist.add(research.get(i));
            }
        }
        return tempstoreobjectlist;
    }

    public ArrayList<StoreObject> removeResearchByDate(Date date) {

        ArrayList<StoreObject> tempstoreobjectlist = new ArrayList<StoreObject>();

        for (int i = 0; i < research.size(); i++) {
            if (research.get(i).objectdate.equals(date)) {
                tempstoreobjectlist.add(research.remove(i));
            }
        }
        return tempstoreobjectlist;
    }

    public ArrayList<StoreObject> removeResearchByCode(int code) {

        ArrayList<StoreObject> tempstoreobjectlist = new ArrayList<StoreObject>();

        for (int i = 0; i < research.size(); i++) {
            if (research.get(i).objectcode == code) {
                tempstoreobjectlist.add(research.remove(i));
            }
        }
        return tempstoreobjectlist;
    }

    public void removeResearch(StoreObject givenresearch) {
        research.remove(givenresearch);
    }

    public void parseResearch() {
        for (int i = 0; i < research.size(); i++) {
            if (research.get(i).objectdate.before(Dateutils.getDateMonthShift(1))) {
                finished_research[research.get(i).objectcode] = true;
                research.remove(i);
                i--;
            }
        }
    }

    public void addResearch(Date objectdate, int gameobject) {
        StoreObject tempstoreobject = new StoreObject();
        tempstoreobject.objectcode = gameobject;
        tempstoreobject.objectdate = objectdate;
        research.add(tempstoreobject);
    }

    public boolean getResearchByDateYesNo(Date date, int objectcode) {
        boolean result = false;
        ArrayList<StoreObject> templist = getResearchByDate(date);
        for (int i = 0; i < templist.size(); i++) {
            if (templist.get(i).objectcode == objectcode) {
                result = true;
            }
        }
        return result;
    }

    public boolean getResearchAfterDateYesNo(Date date, int objectcode) {
        boolean result = false;
        ArrayList<StoreObject> templist = getResearchAfterDate(date);
        for (int i = 0; i < templist.size(); i++) {
            if (templist.get(i).objectcode == objectcode) {
                result = true;
            }
        }
        return result;
    }

    public boolean verifyResearchByCode(int code) {

        try {
            return finished_research[code];
        } catch (Exception e) {
            return false;
        }

    }

    public int getMonthsToFinish(int code) {

        for (int i = 0; i < research.size(); i++) {
            if (research.get(i).objectcode == code) {
                return Dateutils.getIntMonthShift(research.get(i).objectdate);

            }
        }
        return -1;
    }

    public ArrayList<String> getResearchNames() { //For debug
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < research.size(); i++) {
            names.add(player.spaceObjectArray.findObjectByCode(research.get(i).objectcode).getName());
        }
        return names;
    }
}

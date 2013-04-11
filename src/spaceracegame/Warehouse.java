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
public class Warehouse implements Serializable {

    public ArrayList<StoreObject> delivery = new ArrayList<StoreObject>(); //Array for future deliveries of objects. Format: Date, Object
    public ArrayList<StoreObject> usage = new ArrayList<StoreObject>(); //Array for future usage of objects. Format: Date, Object
    public int[] storehouse;
    private Player player;
//    public static int[] warehouseUsed;

    Warehouse(Player givenPlayer) {
        player = givenPlayer;
    }

    public void initialize() {
        storehouse = new int[1000];
    }

    public ArrayList<StoreObject> getDeliveryByDate(Date date) {

        ArrayList<StoreObject> tempstoreobjectlist = new ArrayList<StoreObject>();

        for (int i = 0; i < delivery.size(); i++) {
            if (delivery.get(i).objectdate.equals(date)) {
                tempstoreobjectlist.add(delivery.get(i));
            }
        }
        return tempstoreobjectlist;
    }

    public ArrayList<StoreObject> getDeliveryAfterDate(Date date) {

        ArrayList<StoreObject> tempstoreobjectlist = new ArrayList<StoreObject>();

        for (int i = 0; i < delivery.size(); i++) {
            if (delivery.get(i).objectdate.after(date)) {
                tempstoreobjectlist.add(delivery.get(i));
            }
        }
        return tempstoreobjectlist;
    }

    public ArrayList<StoreObject> removeDeliveryByDate(Date date) {

        ArrayList<StoreObject> tempstoreobjectlist = new ArrayList<StoreObject>();

        for (int i = 0; i < delivery.size(); i++) {
            if (delivery.get(i).objectdate.equals(date)) {
                tempstoreobjectlist.add(delivery.remove(i));
            }
        }
        return tempstoreobjectlist;
    }

    public void parseDelivery() {



        for (int i = 0; i < delivery.size(); i++) {
            if (delivery.get(i).objectdate.before(Dateutils.getDateMonthShift(1))) {
                storehouse[delivery.get(i).objectcode]++;
                delivery.remove(i);
                i--;

            }
        }

    }

    public void addDelivery(Date objectdate, int gameobject) {
        StoreObject tempstoreobject = new StoreObject();
        tempstoreobject.objectcode = gameobject;
        tempstoreobject.objectdate = objectdate;
        delivery.add(tempstoreobject);

    }

    public void addDeliveryInt(int monthshift, int gameobject) {
        StoreObject tempstoreobject = new StoreObject();
        tempstoreobject.objectcode = gameobject;
        tempstoreobject.objectdate = Dateutils.getDateMonthShift(monthshift);
        delivery.add(tempstoreobject);
    }

    public int getDeliveryByDateCountObject(Date date, int objectcode) {
        int counter = 0;
        ArrayList<StoreObject> templist = getDeliveryByDate(date);
        for (int i = 0; i < templist.size(); i++) {
            if (templist.get(i).objectcode == objectcode) {
                counter++;
            }
        }
        return counter;
    }

    public int getDeliveryAfterDateCountObject(Date date, int objectcode) {
        int counter = 0;
        ArrayList<StoreObject> templist = getDeliveryAfterDate(date);
        for (int i = 0; i < templist.size(); i++) {
            if (templist.get(i).objectcode == objectcode) {
                counter++;
            }
        }
        return counter;
    }

    public String getCurrentWarehouse() {
        String tempstring = "<html>";
        for (int i = 0; i < storehouse.length; i++) {
            if (storehouse[i] > 0) {
                tempstring = tempstring + " " + player.spaceObjectArray.findObjectByCode(i).name + ": " + Integer.toString(storehouse[i]) + " <br>";
            }
        }
        return tempstring;
    }

    public boolean isInStock(int code) {
        boolean result = storehouse[code] > 0;
        return result;
    }
}

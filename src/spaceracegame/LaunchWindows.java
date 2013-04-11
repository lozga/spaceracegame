/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class LaunchWindows {

    public static ArrayList<StoreObject> listLaunchWindows = new ArrayList<StoreObject>();
    public static ArrayList<PlanetSinodicPeriod> listPlanetPeriods = new ArrayList<PlanetSinodicPeriod>();

    public static void generateLaunchWindows() {
        listLaunchWindows.clear();
        for (PlanetSinodicPeriod planet : listPlanetPeriods) {
            Calendar tempcalendar = GregorianCalendar.getInstance();
            tempcalendar.setTime(planet.anchordate);
            //rewind to 1956
            while (tempcalendar.get(Calendar.YEAR) > 1956) {
                tempcalendar.add(Calendar.DATE, -planet.synodicperiod);
            }
            //then start from 1957 to 1985
            while (tempcalendar.get(Calendar.YEAR) < 1986) {
                Calendar writecalendar = GregorianCalendar.getInstance();
                writecalendar.setTime(tempcalendar.getTime());
                writecalendar.set(Calendar.DAY_OF_MONTH, 1);
                writecalendar.set(Calendar.HOUR, 1);
                writecalendar.set(Calendar.MINUTE, 1);
                writecalendar.set(Calendar.SECOND, 1);
                //then write 3 windows for current, previous and following months
                StoreObject window = new StoreObject();
                window.objectcode = planet.code;
                window.objectdate = writecalendar.getTime();
                listLaunchWindows.add(window);

                if (planet.code != 1) {
                    writecalendar.add(Calendar.MONTH, -1);
                    window = new StoreObject();
                    window.objectcode = planet.code;
                    window.objectdate = writecalendar.getTime();
                    listLaunchWindows.add(window);

                    writecalendar.add(Calendar.MONTH, +2);
                    window = new StoreObject();
                    window.objectcode = planet.code;
                    window.objectdate = writecalendar.getTime();
                    listLaunchWindows.add(window);
                }

                tempcalendar.add(Calendar.DATE, planet.synodicperiod);
            }

        }
    }

    public static String searchWindowsforMonth(Date givendate) {
        String returnString = "";

        for (StoreObject tempObject : listLaunchWindows) {
            if (isEqualByMonthandYear(tempObject.objectdate, givendate)) {
                if (returnString.length() > 0) {
                    returnString = returnString + ", ";
                }
                returnString = returnString + getPlanetNameByCode(tempObject.objectcode);
            }
        }
        return returnString;
    }

    private static String getPlanetNameByCode(int code) {
        String returnString = "";
        for (PlanetSinodicPeriod period : listPlanetPeriods) {
            if (period.code == code) {
                returnString = period.name;
            }
        }
        return returnString;
    }

    private static boolean isEqualByMonthandYear(Date date1, Date date2) {
        boolean result = false;
        Calendar calendar1 = GregorianCalendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = GregorianCalendar.getInstance();
        calendar2.setTime(date2);

        if ((calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) & (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))) {
            result = true;
        }
        return result;
    }

    public static boolean isLaunchWindowFor(Date date, String planet) {
        boolean result = false;
        if (!((planet.equals("Earth")) | (planet.equals("Moon")))) {
            for (StoreObject window : listLaunchWindows) {
                if (isEqualByMonthandYear(window.objectdate, date)) {
                    if (findPlanetbyCode(window.objectcode).equals(planet)) {
                        result = true;
                    }
                }
            }
        } else {
            result = true;
        }
        return result;
    }

    private static String findPlanetbyCode(int code) {
        String returnString = "";
        for (PlanetSinodicPeriod planet : listPlanetPeriods) {
            if (planet.code == code) {
                returnString = planet.name;
            }
        }
        return returnString;
    }
}

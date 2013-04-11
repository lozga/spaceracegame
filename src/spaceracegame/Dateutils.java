/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class Dateutils implements Serializable{

    public static DateFormat gamedateformat = new SimpleDateFormat("MMM yyyy", new Locale (Options.language));
    public static Date gamedate= new Date();
    private static Calendar calendar = GregorianCalendar.getInstance();

    public static void initialize() {



        calendar.set(Calendar.YEAR, 1957);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 1);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 1);

        gamedate.setTime(calendar.getTimeInMillis());

        System.out.println("Date initialized - "+gamedateformat.format(gamedate));

    }


    public static void nextmonth() {
        Calendar tempcalendar = GregorianCalendar.getInstance();
        tempcalendar.setTime(gamedate);
        tempcalendar.add(Calendar.MONTH, 1);
        gamedate.setTime(tempcalendar.getTimeInMillis());
    }

    public static Date getDateMonthShift(int Shift) {
        Calendar tempcalendar = GregorianCalendar.getInstance();
        tempcalendar.setTime(gamedate);
        tempcalendar.add(Calendar.MONTH, Shift);
        return tempcalendar.getTime();

    }

    public static int getIntMonthShift(Date Shift) {
        Calendar tempcalendar1 = GregorianCalendar.getInstance();
        Calendar tempcalendar2 = GregorianCalendar.getInstance();
        tempcalendar1.setTime(Shift);
        tempcalendar2.setTime(gamedate);
        int month1 = tempcalendar1.get(Calendar.MONTH);
        int month2 = tempcalendar2.get(Calendar.MONTH);
        month1 = month1 + 12 * (tempcalendar1.get(Calendar.YEAR) - tempcalendar2.get(Calendar.YEAR));
        return month1 - month2;
    }
}

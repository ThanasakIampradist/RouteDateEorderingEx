/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication61;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thanasakiampradist
 */
public class JavaApplication61 {

    /**
     * @param args the command line arguments
     */
    public static SimpleDateFormat DATE_SHOW = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    public static SimpleDateFormat TIME_SHOW = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    public static SimpleDateFormat EE_FORMAT = new SimpleDateFormat("EE", Locale.ENGLISH);
    public static Calendar CALENDAR_NOW = Calendar.getInstance(Locale.ENGLISH);
    public static Calendar CALENDAR_REC = Calendar.getInstance(Locale.ENGLISH);
    public static Calendar CALENDAR_TMP = Calendar.getInstance(Locale.ENGLISH);
    public static Calendar CALENDAR_TMP2 = Calendar.getInstance(Locale.ENGLISH);
    public static Calendar CALENDAR_CHK = Calendar.getInstance(Locale.ENGLISH);
    public static Calendar CALENDAR_ORD = Calendar.getInstance(Locale.ENGLISH);
    public static SimpleDateFormat DateSQlFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public static SimpleDateFormat DateCmbFmt = new SimpleDateFormat("dd/MM/yyyy (EEE)", Locale.ENGLISH);

    static Date Temp = new Date();

    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Temp = DateSQlFmt.parse("2021-05-16");
        } catch (ParseException ex) {
            Logger.getLogger(JavaApplication61.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] Dayset = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
//        String[] Dayset = {"Mon", "Wed", "Fri"};
        ArrayList<String> list = getDataRound(3, "09:00", "XXXX", "D", "", "09:00", Dayset, "1");
        for (String obj : list) {
            System.out.println(obj);
        }
    }

    public static ArrayList<String> getDataRound(int leadtime, String timesend, String grouporder_id, String type, String Daylastmodify, String Timelastmodify, String[] Dayset, String Round) {
        ArrayList<String> data = new ArrayList<String>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//            String[] daynot = {"N"};
        // Virable For Check DayNot Bakery
        boolean _IsDateGenData = false;
        boolean _DayNotProcess = false;
        String _dateNow = "";
        String _dateRec = "";
        //
    String[] daynot = {"N"};
//        String[] daynot = {"Sun"};
        _DayNotProcess = CheckDayNotProcess(daynot);
//    String timecurr = TIME_SHOW.format(new Date());
//    String datecurr = DATE_SHOW.format(new Date());
//    _dateNow = DateSQlFmt.format(new Date());
        String timecurr = "02:00";
        String datecurr = DATE_SHOW.format(Temp);
        _dateNow = DateSQlFmt.format(Temp);
        //
        Calendar car = Calendar.getInstance(Locale.ENGLISH);
        car.setTime(Temp);
        car.add(Calendar.DAY_OF_MONTH, leadtime);
        String[] dayset = Dayset;
        String daylastmodify = Daylastmodify;
        String timelastmodify = Timelastmodify;
        if (timecurr.compareTo(timesend) > 0) {
            car.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (type.equals("D") || type.equals("S")) {
            for (int i = 0; i < 5; i++) {
                for (int b = 0; b < dayset.length; b++) {
                    String s1 = EE_FORMAT.format(car.getTime()).toLowerCase();
                    if (s1.equals(dayset[b].toLowerCase())) {
                        _IsDateGenData = false;
                        String daterec = DATE_SHOW.format(car.getTime());
                        _dateRec = DateSQlFmt.format(car.getTime());
                        // ????????????????????????????????????????????????????????????????????????????????????????????? ?????????????????? 45 ?????????
                        if (editNowDate(datecurr, daterec, leadtime, timesend, timecurr, daynot, daylastmodify)) {
                            // ???????????????????????????????????????????????? Gen Data ?????????????????????????????????????????????
                            // Check -> DateRec For GenData To Sap || DayNot Size > 0 || DayNot != N
                            if ((_DayNotProcess) && (timecurr.compareTo(timesend) > 0)) {
                                if (editNow(_dateNow, _dateRec, leadtime, timesend, timecurr, daynot)) {
                                    _IsDateGenData = true;
                                }
                            }
                            // Add Date For Select Receive
                            if (!_IsDateGenData) {
                                if (Round.equals("1")) {
//                  data.add(DateCmbFmt.format(car.getTime()) + " - ????????????");
                                    data.add(DateSQlFmt.format(car.getTime()));
                                } else {
//                  data.add(DateCmbFmt.format(car.getTime()) + " - ????????????");
                                    data.add(DateSQlFmt.format(car.getTime()));
                                }
                            }
                        }
                    }
                }
                car.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        return data;
    }

    public static boolean CheckDayNotProcess(String[] _DayNot) {
        if (_DayNot.length > 0) {
            for (int i = 0; i < _DayNot.length; i++) {
                if (_DayNot[i].equals("N")) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public static boolean editNowDate(String date_now, String date_rec, int leadtime,
            String timesend, String timenow, String[] daynot, String daylastmodify) {
        boolean isEdit = false;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String eeRec = "";
        String eeTmp = "";
        String eeNow = "";
        String[] rec = date_rec.split("/");
        String[] now = date_now.split("/");
        int adddate = 0;

        //set date to calendar
        CALENDAR_NOW.set(Integer.parseInt(now[2]), Integer.parseInt(now[1]) - 1, Integer.parseInt(now[0]));
        CALENDAR_REC.set(Integer.parseInt(rec[2]), Integer.parseInt(rec[1]) - 1, Integer.parseInt(rec[0]));
        CALENDAR_TMP.set(Integer.parseInt(now[2]), Integer.parseInt(now[1]) - 1, Integer.parseInt(now[0]));
        CALENDAR_TMP2.set(Integer.parseInt(now[2]), Integer.parseInt(now[1]) - 1, Integer.parseInt(now[0]));

        eeNow = EE_FORMAT.format(CALENDAR_NOW.getTime());
        boolean isDaynot = false;
        for (int j = 0; j < daynot.length; j++) {
            if (eeNow.equals(daynot[j])) {
                isDaynot = true;
            }
        }

        if (!isDaynot) {
            if (timenow.compareTo(timesend) >= 0) {
                CALENDAR_TMP.add(Calendar.DAY_OF_MONTH, +leadtime);
            } else {

                CALENDAR_TMP.add(Calendar.DAY_OF_MONTH, +leadtime - 1);
                CALENDAR_TMP2.add(Calendar.DAY_OF_MONTH, +leadtime - 2);
            }
        } else {
            CALENDAR_TMP.add(Calendar.DAY_OF_MONTH, +leadtime - 1);
        }

        String Day_NotProduce_1 = EE_FORMAT.format(CALENDAR_TMP2.getTime());
        boolean DayAfter_NotProduce = false;
        for (int j = 0; j < daynot.length; j++) {
            if (Day_NotProduce_1.equals(daynot[j])) {
                DayAfter_NotProduce = true;
                break;
            }
        }
        if (DayAfter_NotProduce) {
            CALENDAR_TMP.add(Calendar.DAY_OF_MONTH, 1);
        }

        eeTmp = EE_FORMAT.format(CALENDAR_TMP.getTime());

        //check for day not send or not make
//            CALENDAR_NOW.add(Calendar.DAY_OF_MONTH, -1);
        for (int i = 0; i < leadtime; i++) {
            CALENDAR_NOW.add(Calendar.DAY_OF_MONTH, +1);
            eeRec = EE_FORMAT.format(CALENDAR_NOW.getTime());
            for (int j = 0; j < daynot.length; j++) {
                if (eeRec.equals(daynot[j])) {
//                    if (!eeTmp.equals(daynot[j])) {
                    if (eeTmp.equals(daynot[j])) {
                        CALENDAR_TMP.add(Calendar.DAY_OF_MONTH, +1);
//                        System.out.println("CALENDAR_TMP : " + DateSQlFmt.format(CALENDAR_TMP.getTime()));
                    }
                }
            }
        }
        //Check Day Last Modify
        if (!daylastmodify.equals("")) {
            //???????????????????????????
            CALENDAR_CHK.set(Integer.parseInt(now[2]), Integer.parseInt(now[1]) - 1, Integer.parseInt(now[0]));
            eeRec = EE_FORMAT.format(CALENDAR_CHK.getTime());
            if (eeRec.equals("Mon")) {
                if (chkdayint(eeRec) > chkdayint(daylastmodify)) {
                    adddate = 14;
                } else {
                    adddate = 7;
                }
            }
            if (eeRec.equals("Tue")) {
                if (chkdayint(eeRec) > chkdayint(daylastmodify)) {
                    adddate = 13;
                } else {
                    adddate = 6;
                }
            }
            if (eeRec.equals("Wed")) {
                if (chkdayint(eeRec) > chkdayint(daylastmodify)) {
                    adddate = 12;
                } else {
                    adddate = 5;
                }
            }
            if (eeRec.equals("Thu")) {
                if (chkdayint(eeRec) > chkdayint(daylastmodify)) {
                    adddate = 11;
                } else {
                    adddate = 4;
                }
            }
            if (eeRec.equals("Fri")) {
                if (chkdayint(eeRec) > chkdayint(daylastmodify)) {
                    adddate = 10;
                } else {
                    adddate = 3;
                }
            }
            if (eeRec.equals("Sat")) {
                if (chkdayint(eeRec) > chkdayint(daylastmodify)) {
                    adddate = 9;
                } else {
                    adddate = 2;
                }
            }
            if (eeRec.equals("Sun")) {
                if (chkdayint(eeRec) > chkdayint(daylastmodify)) {
                    adddate = 8;
                } else {
                    adddate = 1;
                }
            }
            if (timenow.compareTo(timesend) >= 0) {
                if (eeRec.equals(daylastmodify)) {
                    adddate = adddate + 7;
                }
            }
            CALENDAR_CHK.add(Calendar.DAY_OF_MONTH, +adddate);
            //check datetime to edit order
            if (CALENDAR_REC.getTime().compareTo(CALENDAR_CHK.getTime()) >= 0) {  //befor Chang >=
                isEdit = true;
            }

        } else //check datetime to edit order
        {
//            System.out.println("CALENDAR_REC : " + DateSQlFmt.format(CALENDAR_REC.getTime()));
//            System.out.println("CALENDAR_TMP : " + DateSQlFmt.format(CALENDAR_TMP.getTime()));
            if (CALENDAR_REC.getTime().compareTo(CALENDAR_TMP.getTime()) > 0) {  //befor Chang >=
                isEdit = true;
            }
        }
        return isEdit;
    }

    public static boolean editNow(String date_now, String date_rec, int leadtime,
            String timesend, String timenow, String[] daynot) {
        SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        boolean isEdit = false;
        // date_now = ?????????????????????????????????Gen ???????????????????????????????????????????????????
        // date_rec = ?????????????????? Target ?????????????????????????????????????????????
        // leadtime = ????????????????????????????????????????????????????????????
        // timesend = ?????????????????????????????????????????????????????????
        // timenow = ???????????? Target ?????????????????? ???????????? ????????? 09:00;
        // Array DayNot[] = ???????????????????????????????????????

        String eeRec = "";
        String eeTmp = "";
        String eeNow = "";
        String eeORD = "";
        String[] rec = date_rec.split("-");
        String[] now = date_now.split("-");

        //set date to calendar
        CALENDAR_NOW.set(Integer.parseInt(now[0]), Integer.parseInt(now[1]) - 1, Integer.parseInt(now[2]));
        CALENDAR_REC.set(Integer.parseInt(rec[0]), Integer.parseInt(rec[1]) - 1, Integer.parseInt(rec[2]));
        CALENDAR_TMP.set(Integer.parseInt(rec[0]), Integer.parseInt(rec[1]) - 1, Integer.parseInt(rec[2]));
        CALENDAR_ORD.set(Integer.parseInt(now[0]), Integer.parseInt(now[1]) - 1, Integer.parseInt(now[2]));
        eeNow = EE_FORMAT.format(CALENDAR_NOW.getTime()); // ????????? Week ????????????????????????????????? Gen

        boolean isDaynot = false;
        for (int j = 0; j < daynot.length; j++) {
            if (eeNow.equals(daynot[j])) {
                isDaynot = true;
            }
        }
        if (!isDaynot) {
            CALENDAR_TMP.add(Calendar.DAY_OF_MONTH, -leadtime);
            while (!CALENDAR_ORD.after(CALENDAR_REC)) {
                //System.out.println(CALENDAR_ORD.getTime());
                eeORD = EE_FORMAT.format(CALENDAR_ORD.getTime());
                for (int j = 0; j < daynot.length; j++) {
                    if (eeORD.equals(daynot[j])) {
                        CALENDAR_TMP.add(Calendar.DAY_OF_MONTH, -1);
                    }
                }
                CALENDAR_ORD.add(Calendar.DATE, 1);
            }
        }

        // ???????????????????????????????????????????????????????????????
        // {
        String Day_NotProduce_1 = EE_FORMAT.format(CALENDAR_REC.getTime());
        boolean DayAfter_NotProduce = false;
        for (int j = 0; j < daynot.length; j++) {
            if (Day_NotProduce_1.equals(daynot[j])) {
                DayAfter_NotProduce = true;
                break;
            }
        }
        if (DayAfter_NotProduce) {
            CALENDAR_TMP.add(Calendar.DAY_OF_MONTH, 1);
        }
        // }

        String TDateNow = simp.format(CALENDAR_NOW.getTime());
        String TDateTMP = simp.format(CALENDAR_TMP.getTime());

        //check datetime to edit order // ?????????????????????????????????????????? ?????????????????? ?????????????????? Target
        if (TDateNow.compareTo(TDateTMP) == 0) {  //befor Chang >=
            isEdit = true;
        }
        String LTime = String.valueOf(leadtime);
        return isEdit;
    }

    public static int chkdayint(String txtday) {
        int intday = 0;
        if (txtday.equals("Mon")) {
            intday = 1;
        }
        if (txtday.equals("Tue")) {
            intday = 2;
        }
        if (txtday.equals("Wed")) {
            intday = 3;
        }
        if (txtday.equals("Thu")) {
            intday = 4;
        }
        if (txtday.equals("Fri")) {
            intday = 5;
        }
        if (txtday.equals("Sat")) {
            intday = 6;
        }
        if (txtday.equals("Sun")) {
            intday = 7;
        }

        return intday;
    }

}

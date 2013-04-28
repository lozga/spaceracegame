/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import spaceracegame.ui.MainField3_0;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class Missions implements Serializable{

    public ArrayList<Mission> MissionStorage;
    public ArrayList<Mission> MissionArchive;
    private Player player;

    Missions(Player givenPlayer) {
        player = givenPlayer;
    }

//    public boolean readyForLaunch() {
//        cleanMissionStorage();
//        for (int i = 0; i < MissionStorage.size(); i++) {
//            if (MissionStorage.get(i).missiondate.equals(Dateutils.gamedate)) {
//                return true;
//            }
//        }
//        return false;
//    }
    public void cleanMissionStorage() {
        for (int i = 0; i < MissionStorage.size(); i++) {
            if ((MissionStorage.get(i).missiondate == null) | (MissionStorage.get(i).missiondate.before(Dateutils.gamedate))) {
                MissionStorage.remove(i);
            }
        }
    }

    public Mission removeFirstAvailableMission() {
        cleanMissionStorage();
        for (int i = 0; i < MissionStorage.size(); i++) {
            if (MissionStorage.get(i).missiondate.equals(Dateutils.gamedate)) {
                return MissionStorage.remove(i);
            }
        }
        return null;
    }

    public Mission getFirstAvailableMission() {
        cleanMissionStorage();
        for (int i = 0; i < MissionStorage.size(); i++) {
            if (MissionStorage.get(i).missiondate.equals(Dateutils.gamedate)) {
                return MissionStorage.get(i);
            }
        }
        return null;
    }

    public boolean isMissionavailable() {
        cleanMissionStorage();
        boolean result = false;
        for (int i = 0; i < MissionStorage.size(); i++) {
            if (MissionStorage.get(i).missiondate.equals(Dateutils.gamedate)) {
                result = true;
            }
        }
        return result;
    }

    public String calculateprestige(Mission mission) {
        String returnString = "";
        mission.prepareForPrestige();
        for (Prestige prestige : PrestigeArray.prestigelist) {
            System.out.println("calculate prestige for " + prestige.getName());
            boolean isall = true; // all conditions achievement flag
            ArrayList<ArrayList<String[]>> conditions = prestige.getConditions();
            for (int i = 0; i < conditions.size(); i++) {
                boolean isone = false; //one condition achievment flag
                boolean accessed = false;
                ArrayList<String[]> onecondition = conditions.get(i);
                for (int j = 0; j < onecondition.size(); j++) {
                    String[] pair = onecondition.get(j);
                    if (pair.length == 2) { //check pair correct size
                        String paramname = pair[0];
                        try {
                            Field f = mission.getClass().getDeclaredField(paramname);
                            try {
                                System.out.println("accessed field " + f.getName() + ", " + f.get(mission).toString() + ", " + pair[1] + ", is equal = " + f.get(mission).toString().equals(pair[1]));
                                accessed = true;
                                if (f.get(mission).toString().equals(pair[1])) { //if there is at least one parameter with correct value
                                    isone = true; //OR conditions line enabled
                                }
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (NoSuchFieldException ex) {
                            Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SecurityException ex) {
                            Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                System.out.print("isall - " + Boolean.toString(isall) + ", isone - " + Boolean.toString(isone) + ", accessed - " + Boolean.toString(accessed));
                if (accessed) {
                    isall = isall & isone;
                } //conditions line check
                System.out.print(", after OR condition - " + Boolean.toString(isall));
                System.out.println();
            }
            int reward;
            if (isall) {
                if (prestige.isGeneral()) { //for general prestige types
                    int intresult = mission.result.get(mission.result.size() - 1).getStatusInt();
                    boolean success = ((intresult == 0) | (intresult == 1));
                    reward = prestige.calculatePrestige(player.getName(), success);
                    System.out.println("general, success is " + String.valueOf(success));
                    returnString = returnString +Localisation.getText("forprestigetype") +" " + prestige.name + " "+Localisation.getText("and")+" ";
                    if (success) {
                        returnString = returnString + " "+Localisation.getText("success")+" ";
                    } else {
                        returnString = returnString + " "+Localisation.getText("failure")+" ";
                    }
                    returnString = returnString + " "+Localisation.getText("prestigemodified")+" " + Integer.toString(reward) + "<br>";

                } else {// for not-general prestige types
                    boolean success = false;
                    String name = conditions.get(0).get(0)[0].toLowerCase(); //not-general prestige should have only one condition
                    for (int i = 0; i < mission.listMissionSequenceSuccess.size(); i++) {
                        if (mission.listMissionSequenceSuccess.get(i)[0].toLowerCase().equals(name)) {
                            if ((mission.listMissionSequenceSuccess.get(i)[1].equals("1")) | (mission.listMissionSequenceSuccess.get(i)[1].equals("0"))) {
                                success = true;
                            }
                        }
                    }
                    System.out.println("not general, " + String.valueOf(success));
                    reward = prestige.calculatePrestige(player.getName(), success);
                    returnString = returnString +Localisation.getText("forprestigetype") +" " + prestige.name + " "+Localisation.getText("and")+" ";
                    if (success) {
                        returnString = returnString + " "+Localisation.getText("success")+" ";
                    } else {
                        returnString = returnString + " "+Localisation.getText("failure")+" ";
                    }
                    returnString = returnString + " "+Localisation.getText("prestigemodified")+" " + Integer.toString(reward) + "<br>";
                }
            }
            System.out.println("calculate prestige, name " + prestige.getName() + ", isall - " + Boolean.toString(isall));
            System.out.println(returnString);
        }
        return returnString;
    }

    public String checkCrewLoss(Mission givenMission) {
        String result = "";

        if (givenMission.missiontype.manned) {
            if (givenMission.isFinished) {
                if (givenMission.result.get(givenMission.result.size()-1).statusint == 2) { //for emergency landing 33%injury,33%death
                    int random = Utils.random(100);
                    if (random > 33) { //to be hit
                        if (random < 66) {
                            //TODO: add injury
                        } else {
                            Crew tempCrew = givenMission.crews.get(0);
                            for (String name : tempCrew.crewname) {
                                player.nauts.KIANaut(name);
                                result=result+name+" "+Localisation.getText("islost")+"<br>";
                            }
                        }
                    }
                } else if (givenMission.result.get(givenMission.result.size()-1).statusint == 3) { //for crash 100% death
                    Crew tempCrew = givenMission.crews.get(0);
                    for (String name : tempCrew.crewname) {
                        player.nauts.KIANaut(name);
                        result=result+name+" "+Localisation.getText("islost")+"<br>";
                    }
                }
            }
        }

        return result;
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class Mission {

//    public static Mission currentMission = new Mission();
    public Date missiondate;
    public int missioncode;
    public SpaceObject carrier;
    public SpaceObject usefulpayload;
    public int[] requiredcomponents;
    public ArrayList<Crew> crews;
    public MissionTypes missiontype;
    public ArrayList<LaunchResult> result = new ArrayList<LaunchResult>();
    int currentstep = 0;
    public ArrayList<Date> listDates = new ArrayList<Date>();
    public boolean isFinished = false;
    public ArrayList<String[]> listMissionSequenceSuccess;
    public int p_crew;
    public boolean p_eva;
    public boolean p_do;
    public String p_typeofactivity;
    public String p_target;
    public boolean p_manned;
    
    private Player player;

    Mission(Player givenPlayer) {
        player=givenPlayer;
    }

    public void setType(int code) {
        missioncode = code;
        missiontype = MissionTypesArray.findMissionTypeByCode(code);
    }

    public void setCarrier(SpaceObject givencarrier) {
        carrier = givencarrier;
    }

    public void setPayload(SpaceObject givenPayload) {
        usefulpayload = givenPayload;
    }

    public void setDate(Date givenDate) {
        missiondate = givenDate;
    }

    public void SetCrew(ArrayList<Crew> givenCrew) {
        crews = givenCrew;
    }

    public ArrayList<LaunchResult> launchresult() {

        removeusedcomponents();

        System.out.println("Lanch result of carrier " + carrier.durability + "  payload" + usefulpayload.durability);
        System.out.println("mission type is " + Integer.toString(missioncode));

        ArrayList<LaunchResult> returndata = new ArrayList<LaunchResult>();
        listMissionSequenceSuccess = new ArrayList<String[]>();

        for (int i = currentstep; i < missiontype.sequence.size(); i++) {
            LaunchResult tempresult = new LaunchResult();
            int stagedice = Utils.random(100);
            int stepstatus = 3;
            Sequence tempSequence = missiontype.sequence.get(i);
            if (tempSequence.check != null) {
                stepstatus = stageStatus(stagedice, getSpaceObjectByStringCode(tempSequence.check));
            }
            tempresult.set(tempSequence.name, stagedice, Mission.textresult(stepstatus), Mission.textLong(stepstatus), stepstatus);
            returndata.add(tempresult);
            if (!missiontype.sequence.get(i).successOf.equals("none")) {
                String[] tempStrings = new String[2];
                tempStrings[0] = missiontype.sequence.get(i).successOf;
                tempStrings[1] = String.valueOf(stepstatus);
                listMissionSequenceSuccess.add(tempStrings);
            }
            System.out.println(tempresult.debugString());
            if (stepstatus == 3) { //Critical failure
                isFinished = true;
                return returndata;
            } else if (stepstatus == 2) { //Step-critical failure. Initiates jump to return from mission steps for manned missions
                if (tempSequence.stepgo != -1) {
                    i = tempSequence.stepgo - 1;
                } else {
                    isFinished = true;
                    return returndata;
                }
            } else if ((stepstatus == 1) | (stepstatus == 0)) { // Minor failure and OK
                if (missiontype.sequence.get(i).shift != 0) {
                    currentstep = i + 1;
                    listDates.add(missiondate);
                    missiondate = Dateutils.getDateMonthShift(missiontype.sequence.get(i).shift);
                    return returndata;
                }
                if (i == missiontype.sequence.size() - 1) {
                    isFinished = true;
                }
            }
        }
        return returndata;
    }

    public void launch() {
        result.addAll(launchresult());
    }

    public static boolean isFailure(int dice, SpaceObject spaceobject) {
        if (dice < spaceobject.durability) {
            return false;
        } else {
            return true;
        }
    }

    public static int SelectFailure(int dice, SpaceObject spaceobject) {
        int dicefailure = Utils.random(100);
        if (dicefailure < 25) {
            return 3;
        } else {
            if (dicefailure < 50) {
                return 2;
            } else {
                return 1;
            }
        }
    }

    public static int stageStatus(int dice, SpaceObject spaceobject) {
        if (isFailure(dice, spaceobject)) {
            return SelectFailure(dice, spaceobject);
        } else {
            return 0;
        }
    }

    public static String textresult(int result) {
        if (result == 0) {
            return Localisation.getText("stagenominal");
        } else {
            if (result == 1) {
                return Localisation.getText("minorfailure");
            } else {
                if (result == 2) {
                    return Localisation.getText("majorfailure");
                } else {
                    if (result == 3) {
                        return Localisation.getText("criticalfailure");
                    } else {
                        return "Incorrect value!";
                    }
                }
            }
        }

    }

    public static ArrayList<String> textLong(int result) {
        ArrayList<String> tempResult = new ArrayList<String>();
        return tempResult;
    }

    public void removeusedcomponents() {
        for (int i = 0; i < player.warehouse.storehouse.length; i++) {
            player.warehouse.storehouse[i] = player.warehouse.storehouse[i] + requiredcomponents[i];
        }
    }

    public void prepareUsedComponents() {
        requiredcomponents = new int[player.warehouse.storehouse.length];
        requiredcomponents[carrier.getCode()] = -1;
        requiredcomponents[usefulpayload.getCode()] = -1;
    }

    public void prepareForPrestige() {
        if (!missiontype.isManned()) {
            p_crew = 0;
            p_manned = false;
        } else {
            p_crew = usefulpayload.crew;
            p_manned = true;

        }
        p_eva = missiontype.EVA;
        p_do = missiontype.docking;
        p_typeofactivity = missiontype.typeofactivity;
        p_target = missiontype.target;
    }

    public ArrayList<LaunchResult> getResult() {
        return result;
    }

    public SpaceObject getSpaceObjectByStringCode(String code) {
        SpaceObject returnObject = new SpaceObject();
        if (code.equals("c")) {
            returnObject = carrier;
        } else if (code.equals("p")) {
            returnObject = usefulpayload;
        } else if (code.equals("mr")) {
            returnObject.durability = Players.currentPlayer.moon_recon;
        }
        return returnObject;
    }
}

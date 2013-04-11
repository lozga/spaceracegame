/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class Crews implements Serializable{

    public ArrayList<Crew> listCrews = new ArrayList<Crew>();
    private Missions missions;

    Crews(Missions givenMissions) {
        missions=givenMissions;
    }

    public boolean isCrewByVehicleAndNumber(int vehicle, int number) {
        for (Crew tempCrew : listCrews) {
            if ((tempCrew.vehiclecode == vehicle) & (tempCrew.crewnumber == number)) {
                return true;
            }
        }
        return false;

    }

    public Crew findCrewByVehicleAndNumber(int vehicle, int number) {
        for (Crew tempCrew : listCrews) {
            if ((tempCrew.vehiclecode == vehicle) & (tempCrew.crewnumber == number)) {
                return tempCrew;
            }
        }
        return null;
    }

    public ArrayList<Crew> findFreeCrewsByVehicle(int vehicle) {
        ArrayList<Crew> result = new ArrayList<Crew>();
        for (Crew tempCrew : listCrews) {
            if (tempCrew.vehiclecode == vehicle) {
                boolean isused = false;
                for (Mission tempMission : missions.MissionStorage) {
                    for (Crew missionCrew : tempMission.crews) {
                        if (missionCrew == tempCrew) {
                            isused = true;
                        }
                    }
                }
                if (!isused) {
                    result.add(tempCrew);
                }
            }
        }
        return result;
    }

    public boolean isCrewByVehicle(int vehicle) {
        for (Crew tempCrew : listCrews) {
            if (tempCrew.vehiclecode == vehicle) {
                return true;
            }
        }
        return false;

    }

    public Crew findCrewByNames(String names) {
        names = names.replace(" ", "");
        String[] namesParsed = names.split(",");
        ArrayList<String> listNamesParsed = new ArrayList<String>();
        listNamesParsed.addAll(Arrays.asList(namesParsed));
        for (Crew tempCrew : listCrews) {
            if (tempCrew.crewname.equals(listNamesParsed)) {
                return tempCrew;
            }
        }
        return null;
    }

    public boolean isCrewAnotherVehicle(String name, int vehiclecode) {
        for (Crew tempCrew : listCrews) {
            if (vehiclecode != tempCrew.vehiclecode) {
                for (int i = 0; i < tempCrew.crewname.size(); i++) {
                    if (tempCrew.crewname.get(i).equals(name)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

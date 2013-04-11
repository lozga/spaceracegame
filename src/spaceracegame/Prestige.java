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
public class Prestige {
    String name;
    String reward_first;
    String reward_second;
    String reward_fail;
    ArrayList<ArrayList<String[]>> conditions;
    ArrayList<String> achievedlist = new ArrayList<String>();
    boolean general;
    int bonus;
    
    public boolean isGeneral(){
        return general;
    }

    public String getName() {
        return name;
    }

    public int getRewardFirst() {
        return Integer.parseInt(reward_first);
    }

    public int getRewardSecond() {
        return Integer.parseInt(reward_second);
    }

    public int getRewardFail() {
        return Integer.parseInt(reward_fail);
    }

    public ArrayList<ArrayList<String[]>> getConditions() {
        return conditions;
    }

    public int getAchievedNumber() {
        return achievedlist.size();
    }

    public int calculatePrestige(String givenname, boolean isSuccess) {
        ArrayList<String> listunique = new ArrayList<String>();
        for (int i = 0; i < achievedlist.size(); i++) {
            boolean isthere = false;
            for (int j = 0; j < listunique.size(); j++) {
                if (achievedlist.get(i).equals(listunique.get(j))) {
                    isthere = true;
                }
            }
            if (!isthere) {
                listunique.add(achievedlist.get(i));
            }
        }

        int reward;
        if (isSuccess) {
            if (listunique.isEmpty()) {
                reward = Integer.parseInt(reward_first);
            } else if (!listunique.get(0).equals(givenname)) {
                reward = Integer.parseInt(reward_second);
            } else {
                reward = (int) (Integer.parseInt(reward_first)/(Math.pow(2,achievedlist.size())));
            }
            achievedlist.add(givenname); //Do not forget to add success mission to list of achievements
        }
        else{
            reward=Integer.parseInt(reward_fail);
        }

        for (Player tempPlayer:Players.players){
            if (tempPlayer.getName().equals(givenname)){
                System.out.println("Calculate Prestige, player current prestige "+tempPlayer.getName()+" "+tempPlayer.getPrestige());
                tempPlayer.addPrestige(reward);
                System.out.println("Calculate Prestige, add player "+tempPlayer.getName()+" prestige "+String.valueOf(reward));
                System.out.println("Calculate Prestige, player current prestige "+tempPlayer.getName()+" "+tempPlayer.getPrestige());
            }
        }
        return reward;
    }
    
}

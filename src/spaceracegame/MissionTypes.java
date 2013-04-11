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
public class MissionTypes {

    int code;
    String name;
    int payloadcode;
    boolean manned;
    boolean docking;
    boolean EVA;
    boolean LM;
    ArrayList<Sequence> sequence;
    ArrayList<Integer> compatiblepayloads;
    String target;
    String typeofactivity;
    int duration;
    boolean general;


    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getPayloadCode() {
        return payloadcode;
    }

    public ArrayList<Sequence> getSequence() {
        return sequence;
    }

    public boolean isManned() {
        return manned;
    }

    public boolean isDocking() {
        return docking;
    }

    public boolean isEVA() {
        return EVA;
    }

    public boolean isLM() {
        return LM;
    }

    public String getTarget() {
        return target;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<Integer> getCompartiblePayloads() {
        return compatiblepayloads;
    }
}

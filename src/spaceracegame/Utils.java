/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceracegame;

import java.awt.Component;
import java.awt.Dimension;
import java.io.*;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import spaceracegame.ui.MainField3_0;

/**
 *
 * @author Terekhov F.V. <fterekhov@gmail.com>
 */
public class Utils {

    private static java.util.Random RANDOM = new java.util.Random();

    public static int random(int max) {
        return (RANDOM.nextInt(max));
    }

    public static void loadgamedata() throws ParseException {
        File file;
        BufferedReader br;
        String filename;

        for (Player player : Players.players) {
            filename = "data/rockets-" + player.getName() +"-"+Options.langShort+ ".ini";
            file = new File(filename);
            ArrayList<SpaceObject> emptyobj = new ArrayList<SpaceObject>();
            player.spaceObjectArray.listRockets.retainAll(emptyobj);
            player.spaceObjectArray.listPayloads.retainAll(emptyobj);
            player.spaceObjectArray.listManned.retainAll(emptyobj);
            ArrayList<Naut> emptynaut = new ArrayList<Naut>();
            player.nauts.listCandidates.retainAll(emptynaut);
            ArrayList<MissionTypes> emptymis = new ArrayList<MissionTypes>();
            MissionTypesArray.listMissionTypes.retainAll(emptymis);
            ArrayList<String> emptymultiplier = new ArrayList<String>();
            WeightMultipliers.listweightmultipliers.retainAll(emptymultiplier);

            player.spaceObjectArray.listRockets.clear();
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
                try {
                    String tempstring;
                    SpaceObject tempObject = null;
                    while ((tempstring = br.readLine()) != null) {
                        if (!tempstring.startsWith("#")) {
                            if (tempstring.startsWith("{")) {
                                tempObject = new SpaceObject();
                            } else if (tempstring.startsWith("}")) {
                                tempObject.longtext = readLongText(tempObject.code);
                                player.spaceObjectArray.listRockets.add(tempObject);
                            } else {
                                String delimiter = "[=]";
                                String[] tempparsed = tempstring.split(delimiter);
                                if (tempparsed.length == 2) {
                                    String s = tempparsed[0];
                                    try {
                                        Field f = tempObject.getClass().getDeclaredField(s);
                                        try {
                                            if (f.getType() == int.class) {
                                                f.setInt(tempObject, Integer.parseInt(tempparsed[1]));
                                            } else if (f.getType() == String.class) {
                                                f.set(tempObject, tempparsed[1]);
                                            } else if (f.getType() == boolean.class) {
                                                f.setBoolean(tempObject, Boolean.parseBoolean(tempparsed[1]));
                                            } else if (f.getType() == float.class) {
                                                f.setFloat(tempObject, Float.parseFloat(tempparsed[1]));
                                            }
                                        } catch (IllegalArgumentException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (IllegalAccessException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (NoSuchFieldException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SecurityException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                            }

                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
            }

            filename = "data/payloads-" + player.getName() +"-"+Options.langShort+ ".ini";
            file = new File(filename);
            player.spaceObjectArray.listPayloads.clear();
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
                try {
                    String tempstring;
                    SpaceObject tempObject = null;
                    while ((tempstring = br.readLine()) != null) {
                        if (!tempstring.startsWith("#")) {
                            if (tempstring.startsWith("{")) {
                                tempObject = new SpaceObject();
                            } else if (tempstring.startsWith("}")) {
                                tempObject.longtext = readLongText(tempObject.code);
                                player.spaceObjectArray.listPayloads.add(tempObject);
                            } else {
                                String delimiter = "[=]";
                                String[] tempparsed = tempstring.split(delimiter);
                                if (tempparsed.length == 2) {
                                    String s = tempparsed[0];
                                    try {
                                        Field f = tempObject.getClass().getDeclaredField(s);
                                        try {
                                            if (f.getType() == int.class) {
                                                f.setInt(tempObject, Integer.parseInt(tempparsed[1]));
                                            } else if (f.getType() == String.class) {
                                                f.set(tempObject, tempparsed[1]);
                                            } else if (f.getType() == boolean.class) {
                                                f.setBoolean(tempObject, Boolean.parseBoolean(tempparsed[1]));
                                            } else if (f.getType() == float.class) {
                                                f.setFloat(tempObject, Float.parseFloat(tempparsed[1]));
                                            }
                                        } catch (IllegalArgumentException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (IllegalAccessException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (NoSuchFieldException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SecurityException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                            }

                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
            }

            filename = "data/manned-" + player.getName() +"-"+Options.langShort+ ".ini";
            file = new File(filename);
            player.spaceObjectArray.listManned.clear();
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
                try {
                    String tempstring;
                    boolean start = false;
                    boolean crewcapabilities = false;
                    SpaceObject tempObject = null;
                    while ((tempstring = br.readLine()) != null) {
                        if (!tempstring.startsWith("#")) {
                            if (tempstring.startsWith("{")) {
                                start = true;
                                tempObject = new SpaceObject();
                            } else if (tempstring.startsWith("crewcapabilities")) {
                                crewcapabilities = true;
                                tempObject.listCrewCapabilities = new ArrayList<ArrayList<String>>();
                            } else if (tempstring.startsWith("}")) {
                                start = false;
                                crewcapabilities = false;
                                tempObject.longtext = readLongText(tempObject.code);
                                player.spaceObjectArray.listManned.add(tempObject);
                            } else {
                                if (start & !crewcapabilities) {
                                    String delimiter = "[=]";
                                    String[] tempparsed = tempstring.split(delimiter);
                                    if (tempparsed.length == 2) {
                                        delimiter = "[=]";
                                        tempparsed = tempstring.split(delimiter);
                                        if (tempparsed.length == 2) {
                                            String s = tempparsed[0];
                                            try {
                                                Field f = tempObject.getClass().getDeclaredField(s);
                                                try {
                                                    if (f.getType() == int.class) {
                                                        f.setInt(tempObject, Integer.parseInt(tempparsed[1]));
                                                    } else if (f.getType() == String.class) {
                                                        f.set(tempObject, tempparsed[1]);
                                                    } else if (f.getType() == boolean.class) {
                                                        f.setBoolean(tempObject, Boolean.parseBoolean(tempparsed[1]));
                                                    } else if (f.getType() == float.class) {
                                                        f.setFloat(tempObject, Float.parseFloat(tempparsed[1]));
                                                    }
                                                } catch (IllegalArgumentException ex) {
                                                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (IllegalAccessException ex) {
                                                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            } catch (NoSuchFieldException ex) {
                                                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (SecurityException ex) {
                                                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                            }

                                        }
                                    }

                                } else if (start & crewcapabilities) {
                                    if (tempObject.crew > 0) {
//                                    for (int i = 0; i < tempObject.crew; i++) {
                                        String delimiter = "[,]";
                                        String[] tempparsed = tempstring.split(delimiter);
                                        ArrayList<String> templist = new ArrayList<String>(Arrays.asList(tempparsed));
                                        tempObject.listCrewCapabilities.add(templist);
//                                    }
                                    }
                                }
                            }

                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
            }

            filename = "data/nauts-" + player.getName() +"-"+Options.langShort+ ".ini";
            file = new File(filename);
            player.nauts.listCandidates.clear();
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));

                try {

                    String tempstring;
                    while ((tempstring = br.readLine()) != null) {



                        if (!tempstring.startsWith("#")) {

                            String delimiter = "[,]";
                            String[] tempparsed = tempstring.split(delimiter);
                            Naut naut = new Naut();
//                            naut.code = Integer.parseInt(tempparsed[0]);
//                            naut.name = tempparsed[1];
//                            naut.recrutingnumber = Integer.parseInt(tempparsed[2]);

                            naut.name = tempparsed[0];
                            naut.recrutingnumber = Integer.parseInt(tempparsed[1]);

                            //Stub - random abilities

                            naut.skillCapsule = Utils.random(10);
                            naut.skillDock = Utils.random(10);
                            naut.skillEVA = Utils.random(10);
                            naut.skillLM = Utils.random(10);
                            naut.skillEndurance = Utils.random(10);

                            player.nauts.listCandidates.add(naut);
                        }
                    }

                } catch (IOException ex) {
                    Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        file = new File("data/mission types"+"-"+Options.langShort+".ini");
        MissionTypesArray.listMissionTypes.clear();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            try {
                String tempstring;
                boolean start = false;
                boolean issequence = false;
                boolean payloads = false;
                MissionTypes tempObject = null;
                while ((tempstring = br.readLine()) != null) {
                    if (!tempstring.startsWith("#")) {
                        if (tempstring.startsWith("{")) {
                            start = true;
                            tempObject = new MissionTypes();
                            tempObject.EVA = false;
                            tempObject.LM = false;
                            tempObject.docking = false;
                            tempObject.manned = false;
                            tempObject.duration = 0;
                        } else if (tempstring.startsWith("sequence")) {
                            issequence = true;
                            payloads = false;
                            tempObject.sequence = new ArrayList<Sequence>();
                        } else if (tempstring.startsWith("}")) {
                            start = false;
                            issequence = false;
                            payloads = false;
                            MissionTypesArray.listMissionTypes.add(tempObject);
                        } else if (tempstring.startsWith("compatiblepayloads")) {
                            payloads = true;
                            tempObject.compatiblepayloads = new ArrayList<Integer>();
                        } else {
                            if (start & !issequence & !payloads) {
                                String delimiter = "[=]";
                                String[] tempparsed = tempstring.split(delimiter);
                                if (tempparsed.length == 2) {
                                    String s = tempparsed[0];
                                    try {
                                        Field f = tempObject.getClass().getDeclaredField(s);
                                        try {
                                            if (f.getType() == int.class) {
                                                f.setInt(tempObject, Integer.parseInt(tempparsed[1]));
                                            } else if (f.getType() == String.class) {
                                                f.set(tempObject, tempparsed[1]);
                                            } else if (f.getType() == boolean.class) {
                                                f.setBoolean(tempObject, Boolean.parseBoolean(tempparsed[1]));
                                            } else if (f.getType() == float.class) {
                                                f.setFloat(tempObject, Float.parseFloat(tempparsed[1]));
                                            }
                                        } catch (IllegalArgumentException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (IllegalAccessException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (NoSuchFieldException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SecurityException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }

                            } else if (start & payloads) {
                                String delimiter = "[,]";
                                String[] parsedsequence = tempstring.split(delimiter);
                                for (int i = 0; i < parsedsequence.length; i++) {
                                    tempObject.compatiblepayloads.add(Integer.parseInt(parsedsequence[i]));
                                }

                            } else if (start & issequence) {
                                String[] parsedsequence = tempstring.split("[;]");
                                for (int j = 0; j < parsedsequence.length; j++) {
                                    String[] parsedone = parsedsequence[j].split("[,]");
                                    Sequence tempSequence = new Sequence();
                                    tempSequence.stepgo = -1;
                                    tempSequence.shift = 0;
                                    tempSequence.successOf = "none";
                                    for (int i = 0; i < parsedone.length; i++) {
                                        String[] one = parsedone[i].split("[=]");
                                        String s = one[0];
                                        try {
                                            Field f = tempSequence.getClass().getDeclaredField(s);
                                            try {
                                                if (f.getType() == int.class) {
                                                    f.setInt(tempSequence, Integer.parseInt(one[1]));
                                                } else if (f.getType() == String.class) {
                                                    f.set(tempSequence, one[1]);
                                                } else if (f.getType() == boolean.class) {
                                                    f.setBoolean(tempSequence, Boolean.parseBoolean(one[1]));
                                                } else if (f.getType() == float.class) {
                                                    f.setFloat(tempSequence, Float.parseFloat(one[1]));
                                                }
                                            } catch (IllegalArgumentException ex) {
                                                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (IllegalAccessException ex) {
                                                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        } catch (NoSuchFieldException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (SecurityException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    }
                                    tempObject.sequence.add(tempSequence);
                                }
                            }
                        }

                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
        }


        file = new File("data/durations"+"-"+Options.langShort+".ini");
        Durations.durations.clear();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));

            try {

                String tempstring;
                while ((tempstring = br.readLine()) != null) {



                    if (!tempstring.startsWith("#")) {

                        String delimiter = "[,]";
                        String[] tempparsed = tempstring.split(delimiter);
                        Duration tempDur = new Duration();
                        tempDur.code = Integer.parseInt(tempparsed[0]);
                        tempDur.name = tempparsed[1];
                        Durations.durations.add(tempDur);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        WeightMultipliers.listweightmultipliers.clear();
        file = new File("data/multipliers"+"-"+Options.langShort+".ini");
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            try {
                String tempstring;
                while ((tempstring = br.readLine()) != null) {
                    if (!tempstring.startsWith("#")) {
                        String delimiter = "[,]";
                        String[] tempparsed = tempstring.split(delimiter);
                        WeightMultipliers.listweightmultipliers.add(tempparsed);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
        }


        file = new File("data/prestige"+"-"+Options.langShort+".ini");
        PrestigeArray.prestigelist.clear();

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            try {
                String tempstring;
                boolean start = false;
                boolean conditions = false;
                Prestige tempObject = null;
                while ((tempstring = br.readLine()) != null) {
                    if (!tempstring.startsWith("#")) {
                        if (tempstring.startsWith("{")) {
                            start = true;
                            tempObject = new Prestige();
                            tempObject.general = true;
                        } else if (tempstring.startsWith("conditions")) {
                            conditions = true;
                            tempObject.conditions = new ArrayList<ArrayList<String[]>>();
                        } else if (tempstring.startsWith("}")) {
                            start = false;
                            conditions = false;
                            PrestigeArray.prestigelist.add(tempObject);
                        } else {
                            if (start & !conditions) {
                                String delimiter = "[=]";
                                String[] tempparsed = tempstring.split(delimiter);
                                if (tempparsed.length == 2) {
                                    String s = tempparsed[0];
                                    try {
                                        Field f = tempObject.getClass().getDeclaredField(s);
                                        try {
                                            if (f.getType() == int.class) {
                                                f.setInt(tempObject, Integer.parseInt(tempparsed[1]));
                                            } else if (f.getType() == String.class) {
                                                f.set(tempObject, tempparsed[1]);
                                            } else if (f.getType() == boolean.class) {
                                                f.setBoolean(tempObject, Boolean.parseBoolean(tempparsed[1]));
                                            } else if (f.getType() == float.class) {
                                                f.setFloat(tempObject, Float.parseFloat(tempparsed[1]));
                                            }
                                        } catch (IllegalArgumentException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (IllegalAccessException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (NoSuchFieldException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SecurityException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }

                            } else if (start & conditions) {
                                String delimiter = "[,]";
                                String[] tempparsed = tempstring.split(delimiter);
                                ArrayList<String[]> returnonecondition = new ArrayList<String[]>();
                                for (int i = 0; i < tempparsed.length; i++) {
                                    delimiter = "[=]";
                                    String[] parsedcondition = tempparsed[i].split(delimiter);
                                    returnonecondition.add(parsedcondition);
                                }
                                tempObject.conditions.add(returnonecondition);
                            }
                        }

                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
        }

        file = new File("data/planetsynodicperiods"+"-"+Options.langShort+".ini");
        LaunchWindows.listPlanetPeriods.clear();

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            try {
                String tempstring;
                boolean start = false;
                PlanetSinodicPeriod tempObject = null;
                while ((tempstring = br.readLine()) != null) {
                    if (!tempstring.startsWith("#")) {
                        if (tempstring.startsWith("{")) {
                            start = true;
                            tempObject = new PlanetSinodicPeriod();
                        } else if (tempstring.startsWith("}")) {
                            start = false;
                            LaunchWindows.listPlanetPeriods.add(tempObject);
                        } else {
                            if (start) {
                                String delimiter = "[=]";
                                String[] tempparsed = tempstring.split(delimiter);
                                if (tempparsed.length == 2) {
                                    String s = tempparsed[0];
                                    try {
                                        Field f = tempObject.getClass().getDeclaredField(s);
                                        try {
                                            if (f.getType() == int.class) {
                                                f.setInt(tempObject, Integer.parseInt(tempparsed[1]));
                                            } else if (f.getType() == String.class) {
                                                f.set(tempObject, tempparsed[1]);
                                            } else if (f.getType() == boolean.class) {
                                                f.setBoolean(tempObject, Boolean.parseBoolean(tempparsed[1]));
                                            } else if (f.getType() == float.class) {
                                                f.setFloat(tempObject, Float.parseFloat(tempparsed[1]));
                                            } else if (f.getType() == Date.class) {
                                                Date tempDate;
                                                DateFormat tempDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                                                tempDate = tempDateFormat.parse(tempparsed[1]);
                                                f.set(tempObject, tempDate);
                                            }
                                        } catch (IllegalArgumentException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (IllegalAccessException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (NoSuchFieldException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SecurityException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }

                            }
                        }

                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static ArrayList<LongTextLaunchData> readLongText(int objectname) {
        LongTextLaunchData returndata = new LongTextLaunchData();
        ArrayList<LongTextLaunchData> returnlist = new ArrayList<LongTextLaunchData>();
        String filename = "longtexts/" + Integer.toString(objectname) +"-"+Options.langShort+ "-text.ini";

        File file = new File(filename);
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            try {
                String tempstring;
                boolean is0 = false;
                boolean is1 = false;
                boolean is2 = false;
                boolean is3 = false;
                LongTextLaunchDataSingle retsingle = new LongTextLaunchDataSingle();
                retsingle.compatible = new ArrayList<Integer>();
                retsingle.sequence = new ArrayList<String>();
                while ((tempstring = br.readLine()) != null) {
                    if (!tempstring.startsWith("#")) {
                        if (tempstring.startsWith("{0")) {
                            is0 = true;
                        } else if (tempstring.startsWith("action=")) {
                            returndata.action = tempstring.substring(7);
                        } else if (tempstring.startsWith("{1")) {
                            is1 = true;
                            retsingle = new LongTextLaunchDataSingle();
                            retsingle.compatible = new ArrayList<Integer>();
                            retsingle.sequence = new ArrayList<String>();
                        } else if (tempstring.startsWith("{2")) {
                            is2 = true;
                            retsingle = new LongTextLaunchDataSingle();
                            retsingle.compatible = new ArrayList<Integer>();
                            retsingle.sequence = new ArrayList<String>();
                        } else if (tempstring.startsWith("{3")) {
                            is3 = true;
                            retsingle = new LongTextLaunchDataSingle();
                            retsingle.compatible = new ArrayList<Integer>();
                            retsingle.sequence = new ArrayList<String>();
                        } else if (tempstring.startsWith("}")) {
                            if (is1) {
                                returndata.list1.add(retsingle);
                            } else if (is2) {
                                returndata.list2.add(retsingle);
                            } else if (is3) {
                                returndata.list3.add(retsingle);
                            }
                            is0 = false;
                            is1 = false;
                            is2 = false;
                            is3 = false;
                        } else if (tempstring.startsWith("[")) {
                            returndata = new LongTextLaunchData();
                            returndata.list0 = new ArrayList<ArrayList<String>>();
                            returndata.list1 = new ArrayList<LongTextLaunchDataSingle>();
                            returndata.list2 = new ArrayList<LongTextLaunchDataSingle>();
                            returndata.list3 = new ArrayList<LongTextLaunchDataSingle>();
                        } else if (tempstring.startsWith("]")) {
                            returnlist.add(returndata);
                        } else {
                            if (is0) {
                                String[] parsedstring = tempstring.split(";");
                                ArrayList<String> parsedlist = new ArrayList<String>();
                                parsedlist.addAll(Arrays.asList(parsedstring));
                                returndata.list0.add(parsedlist);
                            } else if (is1 | is2 | is3) {
                                if (tempstring.startsWith("c=")) {
                                    String[] parsedcompatibility = tempstring.substring(2).split(";");
                                    for (int i = 0; i < parsedcompatibility.length; i++) {
                                        retsingle.compatible.add(Integer.parseInt(parsedcompatibility[i]));
                                    }
                                } else if (tempstring.startsWith("d=")) {
                                    String[] parseddata = tempstring.substring(2).split(";");
                                    retsingle.sequence.addAll(Arrays.asList(parseddata));
                                }
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnlist;
    }

    public static void setDamnedSize(Component component, Dimension size) {
        component.setMinimumSize(size);
        component.setMaximumSize(size);
        component.setPreferredSize(size);
        component.setSize(size);
    }

    public static void loadLocalisedText() {
                    File file = new File("data/loacalizedtext.ini");
        BufferedReader br = null;

            try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
                try {
                    String tempstring;
                    LocalizedText tempObject = null;
                    while ((tempstring = br.readLine()) != null) {
                        if (!tempstring.startsWith("#")) {
                            if (tempstring.startsWith("{")) {
                                tempObject = new LocalizedText();
                            } else if (tempstring.startsWith("}")) {
                                Localisation.textarray.add(tempObject);
                            } else {
                                String delimiter = "[=]";
                                String[] tempparsed = tempstring.split(delimiter);
                                if (tempparsed.length == 2) {
                                    String s = tempparsed[0];
                                    try {
                                        Field f = tempObject.getClass().getDeclaredField(s);
                                        try {
                                            if (f.getType() == int.class) {
                                                f.setInt(tempObject, Integer.parseInt(tempparsed[1]));
                                            } else if (f.getType() == String.class) {
                                                f.set(tempObject, tempparsed[1]);
                                            } else if (f.getType() == boolean.class) {
                                                f.setBoolean(tempObject, Boolean.parseBoolean(tempparsed[1]));
                                            } else if (f.getType() == float.class) {
                                                f.setFloat(tempObject, Float.parseFloat(tempparsed[1]));
                                            }
                                        } catch (IllegalArgumentException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (IllegalAccessException ex) {
                                            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (NoSuchFieldException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SecurityException ex) {
                                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                            }

                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
                Logger.getLogger(MainField3_0.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}

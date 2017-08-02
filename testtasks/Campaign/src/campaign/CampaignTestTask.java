/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campaign;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Nick Mukhin
 */
public class CampaignTestTask {

    private static HashMap<String, HashSet<Integer>> map;

    private static class Item {

        final HashSet<Integer> set;
        final String key;

        Item(String key, HashSet<Integer> set) {
            this.key = key;
            this.set = set;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Usage: java campaign.CampaignTestTask <filename>");
                System.exit(1);
            }
            loadDataFile(args[0]);
            processUserInput();
        } catch (Exception ex) {
            Logger.getLogger(CampaignTestTask.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(2);
        }
    }

    private static Item parseLine(String line, boolean withKey) throws NumberFormatException {
        StringTokenizer tok = new StringTokenizer(line);
        String campaign = null;
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; tok.hasMoreTokens(); i++) {
            if (i == 0 && withKey) {
                campaign = tok.nextToken();
            } else {
                set.add(Integer.parseInt(tok.nextToken()));
            }
        }
        return new Item(campaign, set);
    }

    private static void loadDataFile(String fname) throws FileNotFoundException, IOException, NumberFormatException {
        BufferedReader br = null;
        FileReader fr = null;
        try {
            File file = new File(fname);
            map = new HashMap<String, HashSet<Integer>>(getNumberOfLines(file));
            br = new BufferedReader(fr = new FileReader(file));
            String curLine;
            while ((curLine = br.readLine()) != null) {
                Item itm = parseLine(curLine, true);
                if (itm.key != null) {
                    map.put(itm.key, itm.set);
                }
            }
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(CampaignTestTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static int getNumberOfLines(File file) throws IOException {
        return (int) Files.lines(file.toPath()).count();
    }

    private static int countMatch(HashSet<Integer> set1, HashSet<Integer> set2) {
        int qty = 0;
        for (Integer it : set1) {
            if (set2.contains(it)) {
                qty++;
            }
        }
        return qty;
    }

    private static void processUserInput() throws IOException {
        System.out.println("Enter list of segments as a space-separated line of integers (empty line to stop):");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String input = br.readLine();
            if (input.length() == 0) {
                break;
            }
            Item itm = parseLine(input, false);
            int prevelementIntersected = 0, elementsIntersected = 0;
            String selected = null;
            for (String campaign : map.keySet()) {
                if (map.get(campaign).containsAll(itm.set) || itm.set.containsAll(map.get(campaign))) {
                    selected = campaign;
                    break;
                } else { // find maximum matching set
                    elementsIntersected = countMatch(map.get(campaign), itm.set);
                    if (elementsIntersected > prevelementIntersected) {
                        selected = campaign;
                        prevelementIntersected = elementsIntersected;
                    }
                }
            }
            System.out.println(selected == null ? "no campaign" : selected);
        }
    }
}

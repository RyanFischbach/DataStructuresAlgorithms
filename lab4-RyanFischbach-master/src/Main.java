import java.io.*;
import java.util.*;

/*
Author: Ryan Fischbach
Professor: Dr. John
Assignment: KNode (Lab 4)
Course: CSC221
Date: 4/14/2020

This lab will pull a k-byte sequence from a file and determine its frequency of occurence
using a hash table. It will allow the user to query to find the number of occurences.
 */

public class Main {

    public static void main(String[] args) {

        //a hash table of shingles
        HashMap<String, KNode> shingles = new HashMap<>(17, (float) 0.75);

        //file path
        File file = new File(args[0]);

        //size
        int k = 0;

        //parse k from command line using try/catch
        try {
            KNode.setk(Integer.parseInt(args[1]));
            k = KNode.getK();
            if(Integer.parseInt(args[1]) < 1)
            {
                System.out.println("Please input a positive integer for k.");
                System.exit(99);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Error parsing command line integer.");
            System.exit(99);
        }

        //scanner used to grab from files
        Scanner inputScanner = null;

        //try to pull from file and print out its contents
        try {
            //create the scanner
            inputScanner = new Scanner(file);
            String previous = "";

            //while there's a next line
            while (inputScanner.hasNextLine()) {
                //grab the next line
                String currentLine = inputScanner.nextLine().toLowerCase();

                if (!previous.equals("")) {
                    //add the three characters from the previous line
                    currentLine = previous + " " + currentLine;
                }

                //do the standardization
                currentLine = currentLine.replaceAll("[^a-zA-z\\s)]", "");
                currentLine = currentLine.replaceAll("( )+", " ");

                //loop each combo of k characters in the line
                for (int i = 0; i < (currentLine.length() - (k-1)); i++) {
                    //create a string object for this new shingle
                    String newString = currentLine.substring(i, i + k);

                    //if this combination isn't in the shingles then add it
                    if (!shingles.containsKey(newString)) {
                        shingles.put(newString, new KNode(newString));
                    } else {
                        shingles.get(newString).incrementFrequency();
                    }
                }

                //grab the last characters of the current line to allow for spillover
                previous = currentLine.substring(currentLine.length() - 3);
            }

            //catch if file is not found
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Threw FileNotFoundException");

        } finally {
            //close the file if the scanner is open
            if (inputScanner != null) {
                inputScanner.close();
            }
        }

        //print the list
        printShingles(shingles);
        System.out.println("Number of distinct " + k + "-shingles: " + shingles.size());
        System.out.println();

        //grab user input to allow for testing
        String input = "";
        Scanner sc = new Scanner(System.in);
        int queryCounter = 1;

        do {
            System.out.println("Query #" + queryCounter + " -- Enter " + KNode.getK() + " character key: ");
            queryCounter++;
            input = sc.nextLine().toLowerCase();
            if (input.isEmpty()) {
                break;
            }
            assert input.length() == k : "Length is not " + k;

            //if it does exist, find and print its frequency
            if (shingles.containsKey(input)) {
                System.out.println("    Frequency for <" + input + "> is " + shingles.get(input).getFrequency());
            } else {
                System.out.println("    Frequency for <" + input + "> is 0. Not in list.");
            }
        }
        while (!input.isEmpty());
    }

    //method to print data in the hash table
    public static void printShingles(HashMap<String, KNode> shingles) {
        Iterator it = shingles.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            KNode current = (KNode) element.getValue();
            System.out.println(current.getNode() + " " + current.getFrequency());
        }
    }

}

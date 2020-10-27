import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/*
Author: Ryan Fischbach
Professor: Dr. John
Assignment: FourNode (Lab 1)
Course: CSC221
Date: 2/4/2020

This lab will pull a 4-byte sequence from a file and determine its frequency of occurence.

 */

public class Main {

    public static void main(String[] args) {

        //an ordered list of shingles
        ArrayList<FourNode> shingles = new ArrayList<FourNode>();

        //file path
        File file = new File(args[0]);

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
                String currentLine = inputScanner.nextLine();

                if (previous != "") {
                    //add the three characters from the previous line
                    currentLine = previous + " " + currentLine;
                }

                //do the standardization
                currentLine = currentLine.replaceAll("[^a-zA-z]", " ").toLowerCase();
                currentLine = currentLine.replaceAll("\\^([0-9]+)", " ");
                currentLine = currentLine.replaceAll("\\s+", " ");

                //loop each combo of four characters in the line
                for (int i = 0; i < (currentLine.length() - 3); i++) {
                    //create a string object for this new shingle
                    String newString = currentLine.substring(i, i + 4);

                    //perform binary search to see if this shingle already exists
                    int pos = binarySearch(new FourNode(newString), shingles);

                    //if this combination isn't in the shingles arrayList then add it
                    if (pos == -1) {

                        int j = 0;

                        for(FourNode node : shingles)
                        {
                            //find its sorted spot in the arrayList
                            if(newString.compareTo(node.getNode()) < 0)
                            {
                                break;
                            }
                            j++;
                        }
                        //insert it in its spot in the arrayList
                        shingles.add(j, new FourNode(newString));

                    }
                    //if this combination is in the shingles arrayList, increment its frequency
                    else {
                        shingles.get(pos).incrementFrequency();
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

        /*
        //print out nodes for testing purposes
        for (FourNode node : shingles) {
            System.out.println(node.getNode() + " " + node.getFrequency());
        }
         */

        //grab user input to allow for testing
        String input = "";
        Scanner sc = new Scanner(System.in);

        System.out.println("Please input a 4 node to check for.");
        input = sc.nextLine();

        //perform binary search on the target node
        int pos = binarySearch(new FourNode(input), shingles);

        //if it does exist, find and print its frequency
        if (pos != -1) {
            System.out.println(shingles.get(pos).getFrequency());

        //if it doesn't exist, print not in list
        } else {
            System.out.println("Not in list");
        }

    }

    // binary search method
    public static int binarySearch(FourNode node, ArrayList<FourNode> shingles) {

        //declare initial bounds
        int lowPos = 0;
        int highPos = shingles.size() - 1;

        //while loop to loop through the arrayList
        while (lowPos <= highPos) {
            //middle index is the average of high and low
            int midPos = (lowPos + highPos) / 2;

            //if you've found what you're searching for, return the index
            if (shingles.get(midPos).getNode().compareTo(node.getNode()) == 0) {
                return midPos;
            //if the value you're comparing to is higher, adjust highPos to narrow in
            } else if (shingles.get(midPos).getNode().compareTo(node.getNode()) > 0) {
                highPos = midPos - 1;
            //if the value you're comparing to is lower, adjust the lowPos to narrow in
            } else {
                lowPos = midPos + 1;
            }
        }
        //the target node is not in the arrayList so return -1
        return -1;

    }
}

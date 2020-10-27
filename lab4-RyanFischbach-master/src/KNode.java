/*
Author: Ryan Fischbach
Professor: Dr. John
Assignment: KNode (Lab 4)
Course: CSC221
Date: 4/14/2020

This lab will pull a k-byte sequence from a file and determine its frequency of occurence
using a hash table. It will allow the user to query to find the number of occurences.

 */

public class KNode {

    //the number of occurences of this K Node in the file
    private int Frequency = 1;

    //the K-node String
    private final String node;

    //the size of the node
    private static int k = 0;

    //default constructor
    public KNode(String x) {
        assert x.length() == k : "Length is not " + k + " as specified by the command line.";
        node = x;
    }

    //increment the frequency value
    public void incrementFrequency() {
        Frequency++;
    }

    //getter for the node
    public String getNode() {
        return node;
    }

    //getter for the Frequency
    public int getFrequency() {
        return Frequency;
    }

    //getter for k
    public static int getK() {
        return k;
    }

    //setter for k
    public static void setk(int kIn) {
        k = kIn;
    }


}

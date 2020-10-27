/*
Name: Ryan Fischbach
CSC221 - Lab 2
Instructor: Dr. John

This lab establishes a URL connection with a site and checks whether or not specified tags (inputted via a file)
are balanced on a certain HTML page. The program then outputs whether or not the tags are balanced.
 */

import java.util.Stack;
import java.util.regex.*;
import java.io.*;
import java.util.*;
import java.net.URL;
import java.net.URLConnection;

public class Main {

    public static void main(String[] args) {

        //prompt user to input a URL
        String url = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a Uniform Resource Locator's (URL's) full name: ");
        url = sc.next();

        // read in URL document
        StringBuilder inputData = new StringBuilder();
        try {
            //open a URL connection
            URL myDataURL = new URL(url);
            URLConnection myDataConnection = myDataURL.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(myDataConnection.getInputStream()));

            //construct tags from URL
            String inputLine = "";
            while ((inputLine = reader.readLine()) != null) {
                inputData.append(inputLine);
            }
            reader.close();

        }

        //catch exceptions
        catch (Exception e) {
            System.out.println("Aborting during reading from URL");
        }

        //use a regular expression to find tags in the web page
        Pattern p = Pattern.compile("<(/?)([a-zA-Z]+)");
        Matcher m = p.matcher(inputData.toString());

        //build set of tags
        Set<String> myTags = new TreeSet<>();
        try {
            //declare scanner with tag file
            Scanner myTagsFile = new Scanner(new FileReader("matchedtags"));

            //read in tag file
            while (myTagsFile.hasNext()) {
                String tag = myTagsFile.next();
                //System.out.println(tag);
                myTags.add(tag.toLowerCase());
            }

            //close the file
            myTagsFile.close();
        }

        //catch Exception
        catch (Exception e) {
            System.out.println("Aborting during reading in tags");
        }

        //use stack to check for "tag" and "/tag"
        Stack<String> myStack = new Stack<>();
        while (m.find()) {
            String token2 = m.group(2).toLowerCase();
            String token0 = m.group(0).toLowerCase();

            //if tag is in the set
            if (myTags.contains(token2)) {
                //if open tag
                if (m.group(1).equals(" ")) {
                    myStack.push(token0);
                }
                //if closed tag and top of stack matches
                else if (!myStack.isEmpty() && token2.equals(myStack.peek().substring(1))) {
                    myStack.pop();
                }
                //if closed tag and doesn't match
                else {
                    myStack.push(token0);
                }
            }

        }

        //result output
        System.out.println("Web Page Analysis Of: " + url);

        //if stack is empty (all tags accounted for)
        if (myStack.isEmpty()) {
            System.out.println("HTML Tags Are Balanced");
        }
        //if stack contains elements (not all tags accounted for)
        else {
            System.out.println("HTML Tags Aren't Balanced");
        }
    }
}

// Java implementation of search and insert operations
// on Trie

// This code is contributed by Sumit Ghosh
// Modified by David John and Ryan Fischbach, March 2020

public class Trie {

    // class variables
    static final int ALPHABET_SIZE = 26;

    //instantiate the root. Changed by Ryan
    static TrieNode root = new TrieNode();

    //int to track the number of words. Changed by Ryan
    static int numberWords;

    // trie node internal class
    private static class TrieNode
    {
        // reference to next child
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        // isEndOfWord is true if the node represents
        // end of a word
        boolean isEndOfWord;

        // default constructor for node, not a terminal and set all
        // references to null
        TrieNode(){
            isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                children[i] = null;
        }
    };



    // If not present, inserts key into trie
    // If the key is prefix of trie node,
    // just marks leaf node
    public static void insert(String key)
    {
        int level;
        int length = key.length();
        int index;

        TrieNode pCrawl = root;

        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';
            if (pCrawl.children[index] == null)
                pCrawl.children[index] = new TrieNode();

            pCrawl = pCrawl.children[index];
        }

        // mark last node as leaf
        pCrawl.isEndOfWord = true;

        //increment the number of words
        numberWords++;
    }

    // Modified search that returns 1 if found, 0 if prefix, -1 if not found. Changed by Ryan
    public static int search(String key)
    {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;

        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';

            if (pCrawl.children[index] == null)
                //children at index is null so word isn't there
                return -1;

            pCrawl = pCrawl.children[index];
        }

        if(pCrawl != null)
        {
            if(pCrawl.isEndOfWord)
                //at end of word therefor return 1
                return 1;
            //not at end of word, therefor is a prefix so return 0
            return 0;
        }
        else
        {
            //else return -1 if not found so far
            return -1;
        }
    }


}

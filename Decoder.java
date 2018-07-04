//This was created by Uel Dalilis and Quynh Anh Pham for a university assignment
import java.io.*;
import java.util.*;

public class Decoder {
    //declare a list to store all trie nodes
    private static List<Node> trie;
    public static void main(String[] args) {
        try {
            BufferedReader fi = new BufferedReader(new InputStreamReader(System.in));
            //set up trie and declare variables
            reset();
            String s;

            int countPhrase = 256;
            int next = -1;

            //while still have things to read
            while ((s = fi.readLine()) != null) {
                //convert line we just read to a phrase number
                int x = Integer.parseInt(s);

                //if the phrase we just read is the reset code then reset the trie and variables
                if (x == 256) {
                    reset();
                    next = -1;
                    countPhrase = 256;
                }
                //for all other phrase no.
                else {
                    //if the current trie element have a parent
                    if (trie.get(x).getParent() != -1) {
                        //declare variable
                        int temp = x;
                        List<Integer> path = new ArrayList<Integer>();

                        //get a list of phrases that part of the path to the phrase that we just read in
                        while (trie.get(temp).getParent() != -1) {
                            path.add(temp);
                            temp = trie.get(temp).getParent();
                        }
                        //add the dictionary node that the beginning to the path
                        path.add(temp);
                        //set the value of that dictionary node to be the value of the last node that was added to the trie
                        trie.get(countPhrase).setValue(trie.get(path.get(path.size() - 1)).getValue());

                        //traverse the path to print out the encoded string
                        for (int i = path.size() - 1; i >= 0; i--) {
                            System.out.write(trie.get(path.get(i)).getValue());
                        }

                        //add a new node to the trie for the mismatch
                        trie.add(new Node(x));
                        //increase count of number of phrase
                        countPhrase++;
                    }
                    else {
                        //if this is not first time we run read in a phrase number after the trie reset
                        if (next != -1) {
                            //set the value of current dictionary node to be the value of the last node that was added to the trie
                            trie.get(countPhrase).setValue(trie.get(x).getValue());
                        }
                        //write value of current dictionary node to file
                        System.out.write(trie.get(x).getValue());
                        //add a new node to the trie for the mismatch
                        trie.add(new Node(x));
                        //increase count of number of phrase
                        countPhrase++;
                        //set flag
                        next = 1;
                    }

                }
            }

        } catch (Exception e) {
            System.err.println("Exception " + e);
        }
    }

    //set up the trie as new
    public static void reset() {
    try{
        //initialise list
        trie = new ArrayList<Node>();

        //add in the initial dictionary
        for(int i = 0; i < 256; i++)
        {
            Node temp = new Node(-1);
            temp.setValue(i);
            trie.add(temp);
        }

        //add the reset code
        Node temp = new Node(-1);
        trie.add(temp);
	}
        catch(Exception e) {
            System.err.println("Exception " + e);
        }
    }

    //class node to store parent and value of a trie element
    private static class Node {
        //declare variables
        private int parent;
        private int value;

        //constructor
        public Node(int _parent) {
            parent = _parent;
            value = 0;
        }

        //return parent
        public int getParent() {
            return parent;
        }

        //return value
        public int getValue() {
            return value;
        }

        //set value of current node
        public void setValue(int value) {
            this.value = value;
        }
    }
}



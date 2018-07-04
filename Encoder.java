//This was created by Uel Dalilis and Quynh Anh Pham for a university assignment
import java.io.*;
import java.util.*;

public class Encoder {

    private static Trie[] tries;
    public static void main(String[] args) {
        try {
        	if(args.length != 1 || Integer.parseInt(args[0]) <= 8 )
            {
                System.err.println("Usage: java Encoder <int (atleast 9)>");
                return;
            } 
            //This is the number of bits the dictionary can get through before it resets
            int maxBits = Integer.parseInt(args[0]);
            reset();
            //Couting the number of phrases we have
            int countPhrase = 256;
            //Shifting our max bits to the left
            int maxPhrase = (1 << maxBits) -1;
            int c = 0;

            //Initialising out current and previous positions
            Trie current = null;
            Trie previous = null;

            //Reading through until the end of the file
            while((c = System.in.read()) != -1){
                //If current is empty then put the value being read into current
                if(current == null) {
                    current = tries[c];
                }
                else {
                    //Make previous the current too to store
                    previous = current;

                    //if the currents child is not empty
                    if (current.getChild() != null) {
                        //We look at the child
                        current = current.getChild();
                        //While the current is not the value we want to find then it checks the siblings
                        while (current.getCh() != c) {
                            //If we find a sibling we look at the sibling
                            if (current.getSiblings() != null) {
                                current = current.getSiblings();
                            }
                            //If there's no sibling left
                            else {
                                //write out the parents phrase
                                System.out.println(previous.getPhraseNo());
                                //increase phrase count by 1
                                countPhrase++;
                                //adding a new child
                                current.addSibling(c, countPhrase);
                                current = tries[c];
                                break;
                            }
                        }
                    }
                    //If the currents child is empty
                    else {
                        //write parents phrase
                        System.out.println(previous.getPhraseNo());
                        //putting in the value in the position were looking at in current
                        current = tries[c];
                        //Increase phrase count by 1
                        countPhrase++;
                        //Adding new child
                        previous.addChild(current.getCh(), countPhrase);
                    }
                }
                //If max bits has been reached then we reset
                if(countPhrase == maxPhrase){

                    //reset code
                    System.out.println(tries.length -1);
                    //reset the trie
                    reset();
                    //putting in the value in the position were looking at in current
                    current = tries[c];
                    //Put the phrase back as 256
                    countPhrase = 256;
                    previous = null;
                }
            }
            //Write out the current phrase number we're on
            System.out.println(current.getPhraseNo());
        }
        catch(Exception e) {
            System.err.println("Exception " + e);
        }



    }
    //This makes initialises a new try (overriting our old one) with 256 entries + reset
    private static void reset(){
    try{
        tries = new Trie[257];
        for(int i = 0; i <= 254; i++)
        {
            tries[i] = new Trie(i, i);
        }
        tries[255] = new Trie(255, 255);
        //This will be the reset code
        tries[256] = new Trie(0, 256);
        }
        catch(Exception e) {
            System.err.println("Exception " + e);
        }
    }

    static class Trie{
		    //declaring variables
		    private Trie child;
		    private Trie sibling;
		    private int ch;
		    private int phraseNo;
		    //constuctor
		    public Trie(int _ch, int _phraseNo)
		    {
		        child = null;
		        sibling = null;
		        ch = _ch;
		        phraseNo = _phraseNo;
		    }
		    //Returns character
		    public int getCh() {
		        return ch;
		    }
		    //returns the phrase number
		    public int getPhraseNo() {
		        return phraseNo;
		    }
		    //Return child
		    public Trie getChild() {
		        return child;
		    }
		    //return sibling
		    public Trie getSiblings() {
		        return sibling;
		    }
		    //Adds a new child
		    public void addChild(int _ch, int _phraseNo)
		    {
		        child = new Trie(_ch, _phraseNo);
		    }
		    //Adds a new sibling
		    public void addSibling(int _ch, int _phraseNo)
		    {
		        sibling = new Trie(_ch, _phraseNo);
		    }

    }
}


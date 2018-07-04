//This was created by Uel Dalilis and Quynh Anh Pham for a university assignment
import java.io.*;
import java.nio.*;

public class BitUnpacker {

    public static void main(String[] args) {
        try {
            //declare variables
            int noPhrase = 256;
            int data;
            boolean reset = false;
            int leftover = 0;
            int numbBitsLeftOver = 0;
	    byte[] byteArray = new byte[4];
	    int x= 0;
	    
            //read while there still data left to read
            while ((x = System.in.read(byteArray)) != -1){
            	ByteBuffer wrapped = ByteBuffer.wrap(byteArray);
                //read in a int of data
                data = wrapped.getInt();

                //create a temp variable to change value without changing the data we read in
                int temp = data;

                //declare the number of bits that still empty, starting at 32 bits
                int bitValid = 32;

                //while we still have data to unpack in the int of input
                while(temp != 0 || bitValid == 32){

                    //check if there was leftover bits from the previous int of data
                    if(numbBitsLeftOver != 0)
                    {
                        //increase number of bit of data still have to read
                        bitValid = bitValid + numbBitsLeftOver;
                    }

                    //if there still a whole phrase number we still need to output
                    if(bitValid-numberBitsUsed(noPhrase) >=0) {
                        //shift temp left until we get to the whole number phrase we want to output
                        temp >>>= (bitValid - numberBitsUsed(noPhrase));

                        //if there was leftover bits from the previous int of data
                        if(leftover != 0)
                        {
                            //add it to temp by bit OR it
                            temp = temp | (leftover << (numberBitsUsed(noPhrase) - numbBitsLeftOver));
                            //empty the leftover
                            leftover = 0;
                            numbBitsLeftOver = 0;
                        }

                        //write out temp as the next phrase number
                        System.out.println(temp);
                        int wrote = temp;

                        //set temp to the int of data we read in, with the bits we already output masked out
                        temp = data & ((1 << (bitValid - numberBitsUsed(noPhrase))) - 1);

                        //decrease the number of bit that we still need to output
                        bitValid = bitValid - numberBitsUsed(noPhrase);

			//if we got the reset code then set number of phrases back to 256
                        if(wrote == 256){
                            noPhrase = 256;
                            reset = true;
                        }
                        
                        //check if leftover data is equal to 0 but there still valid bits that not enough for the whole phrase
                        if(temp == 0 && (bitValid-numberBitsUsed(noPhrase)) < 0){
                            //carryover valid 0s bits
                            numbBitsLeftOver = bitValid;
                        }
                        //check if leftover data is equal to 0 but there still valid bits that enough for atleast a whole phrase
                        else if(temp == 0 && (bitValid-numberBitsUsed(noPhrase)) >= 0) {
                            //print 0s while there still whole phrases that are 0s
                            while ((bitValid-numberBitsUsed(noPhrase)) >= 0){
                            	//print value
                                System.out.println(temp);
                                //decrease the number of bit that we still need to output
                                bitValid = bitValid - numberBitsUsed(noPhrase);
                            }
                            //carryover leftover valid 0s bits
                            numbBitsLeftOver = bitValid;
                        }
                        else{
                            //otherwise empty the leftover bits
                            numbBitsLeftOver = 0;
                        }

                        //only increase the number of phrase if the phrase number we just outputed is not the reset code
                        if(reset == true){
                            reset = false;
                        }else {
                            noPhrase++;
                        }
                    }
                    else
                    {
                        //carry over the leftover bits
                        leftover = temp;
                        temp = 0;
                        numbBitsLeftOver = bitValid;
                    }
                }
            }
        }
        //catch exceptions
        catch(Exception e){
            System.err.println("Exception " + e);
        }
    }

    //workout the number of bits needed to encode a phrase number
    private static int numberBitsUsed(int noPhrase)
    {
        //declare variables
        int temp = noPhrase;
        int shiftCount = 0;

        //shift number of phrase left until it equal to zero
        while(temp != 0)
        {
            temp = temp >>> 1;
            //increment count by 1
            shiftCount ++;
        }
        //return number of bits needed
        return shiftCount;
        }
}


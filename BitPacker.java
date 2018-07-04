//This was created by Uel Dalilis and Quynh Anh Pham for a university assignment
import java.io.*;
import java.nio.*;
import java.util.*;

public class BitPacker {

    public static void main(String[] args) {
        try {
       		byte[] dataArray = new byte[4];
            //Created reader
            BufferedReader fi = new BufferedReader(new InputStreamReader(System.in));
            //Initialized variables
            String s;
            int noPhrase = 256;
            int bitsUsed = 9;
            //Read in the first line and convert it to int
            int data = Integer.parseInt(fi.readLine());
            //increase phrase number
            //noPhrase ++;
            boolean reset = false;

            //reads in the whole file
            while ((s = fi.readLine()) != null) {
            	//increase the phrase number by 1
                noPhrase ++;
                //if reset is true
                if(reset){
                    //put the phrase number back to 256
                    noPhrase = 256;
                    //make reset false
                    reset = false;
                }
                //reads in a line and convert it to int
                int dataNew = Integer.parseInt(s);
                //When the data being read is 256 then make reset true and increase phrase number
                if(dataNew == 256) reset = true;

                //if we havent reached 32 bits yet
                if((bitsUsed+numberBitsUsed(noPhrase)) <= 32)
                {
                    //shift the data to the left by the number of valid bits of the current value being read
                    data = data << numberBitsUsed(noPhrase);
                    //increased bits used by adding the number of bits the number of bits of the value currently being read
                    bitsUsed = bitsUsed + numberBitsUsed(noPhrase);
                    //or the data with the current value being read
                    data = data | dataNew;
                    //if 32 bits have been used
                    if(bitsUsed == 32){
                    	dataArray = ByteBuffer.allocate(4).putInt(data).array();
                   	 	for(int i = 0; i < dataArray.length; i ++)
                   		 {
                   		 System.out.write(dataArray[i]);
                    	}
                        //put data and bits used back to 0
                        data = 0;
                        bitsUsed = 0;
                    }
                }
                else
                {
                    //gets the amount of bits left we can put into the data that we print out
                    int bitsLeft = 32 - bitsUsed;
                    //shift the new data to the right by the number or bits in the current data - bits left and store it in a new variable
                    int tempLeft = dataNew >> (numberBitsUsed(noPhrase) - bitsLeft);
                    //shift the data to the left as much as possible without losing any data
                    data <<= (32-bitsUsed);
                    //or the data so we can fill up the 32 bits
                    data = data | tempLeft;
                    //write out the data
                    
                    dataArray = ByteBuffer.allocate(4).putInt(data).array();
                    for(int i = 0; i < dataArray.length; i ++)
                    {
                    	System.out.write(dataArray[i]);
                    }
                    
                    //put the data back to 0
                    data = 0;
                    //mask the new data so we can get the lost data we shifted to the right earlier
                    dataNew = dataNew & ((1 << (numberBitsUsed(noPhrase) - bitsLeft)) - 1);
                    //store the data
                    data = data | dataNew;
                    //gets the bits used
                    bitsUsed = numberBitsUsed(noPhrase) - bitsLeft;
                }
                
            }
            //if the bits used is above 0
           if (bitsUsed > 0){
                //shift data to the left by 30 minus the bits being used
                data <<= (32-bitsUsed);
                int round = (32-bitsUsed)/8;
                if((32-bitsUsed)%8 != 0)
                {
                round++;
                }
                //write out the data
                dataArray = ByteBuffer.allocate(4).putInt(data).array();              
                    for(int i = 0; i < round; i ++)
                    {                    	
                    System.out.write(dataArray[i]);                     
					}
                    System.out.println(); 
           }
        }
        catch(Exception e){
                System.err.println("Exception " + e);
        }
    }
    

    //this method gets the number of valid bits
    private static int numberBitsUsed(int noPhrase)
    {
        //intialise variables
        int temp = noPhrase;
        int shiftCount = 0;
        //loop to see how many valid bits there are
        while(temp != 0)
        {
            //shift the value to the left by 1
            temp = temp >>> 1;
            //increase shift count by 1
            shiftCount ++;
        }
        return shiftCount;
        }
}


// QuadraticProbing Hash table class

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

public class HashTableCuckoo implements HashTableInterface{

    /**
     * Construct the Cuckoo hash table.
     */
    public HashTableCuckoo() {
        this(DEFAULT_TABLE_SIZE, 50);
        load = 50;
        doClear();
        debug=false;
    }

    /**
     * Construct the hash table.
     *
     * @param size the approximate initial size.
     */
    public HashTableCuckoo(int size, int load) {
        allocateArrays(size*30);
        this.load = load;
        doClear();
        debug=false;
    }

    /**
     * @param limit Number of active entries to print
     * @return
     */
    public java.lang.String toString(int limit) {
        StringBuilder sb = new StringBuilder();
        int ct = 0;
        //print array 1
        for (int i = 0; i < array.length && ct < limit; i++) {
            if (array[i] != null && array[i].isActive) {
                sb.append(i + ": " + array[i].element + "\n");
                ct++;
            }
        }

        //print array 2
        for (int i = 0; i < array2.length && ct < limit; i++) {
            if (array2[i] != null && array2[i].isActive) {
                sb.append(i + ": " + array2[i].element + "\n");
                ct++;
            }
        }
        return sb.toString();
    }

    /**
     * Expand the hash table.
     */
    protected void rehash() {
        HashEntry[] oldArray = array;

        // Create a new double-sized, empty table
        allocateArrays(2 * oldArray.length);
        occupiedCt = 0;
        currentActiveEntries = 0;

        // Copy table over
        for (HashEntry entry : oldArray)
            if (entry != null && entry.isActive)
                insert(entry.element);
    }

    /**
     * @param x the Item to be hashed
     * @return the hashCode for the element
     */
    protected int myHash(String x) {
        int hashVal = x.hashCode();

        hashVal %= array.length;
        if (hashVal < 0)
            hashVal += array.length;

        return hashVal;
    }

    /**
     * Method that performs another funtion for hashing to be used as the second hash.
     *
     * @param x the item to hash
     * @return the hashed value
     */
    public int mySecondHash(String x) {
        int hashVal = x.hashCode();
        hashVal = hashVal % array.length;
        if (hashVal < 0) {
            hashVal += array.length;
        }
        return hashVal;
    }

    /**
     * Insert into the hash table. If the item is already present, do nothing.
     *
     * @param x the item to insert.
     */
    public boolean insert(String x) {

        int Pos1 = myHash(x);
        int Pos2 = mySecondHash(x);
        int thisProbe=1;
        int total_iterations = 0;
        int max_iterations = 1000;
        HashEntry Temp1;
        HashEntry Temp2;
        probeCt++;
        thisProbe++;
        // insert into the first array if empty
        if (array[Pos1] == null) {
                array[Pos1] = new HashEntry(x);
                currentActiveEntries++;
                if (thisProbe > maxProbeCt){
                    maxProbeCt=thisProbe;
                }
                return true;
            }
        probeCt++;
        thisProbe++;
        // insert into the second array if empty
        if (array2[Pos2] == null) {
                array2[Pos2] = new HashEntry(x);
                currentActiveEntries++;
                if (thisProbe > maxProbeCt){
                    maxProbeCt=thisProbe;
                }
                return true;
            }
        Temp1 = new HashEntry(x);
        currentActiveEntries++;
        //Have it try the first pos then the second then kickout the element recursivly from the next pos
        //for loop to swap positions until max iterations or empty spot is found
        while (total_iterations < max_iterations) {
            //from first array and put in second
            if(array[Pos2] == null ){
                array[Pos2] = Temp1;
                break;
            }else{
                Temp2 = array[Pos2];
                array[Pos2] = Temp1;
                Temp1 = Temp2;
                Pos1 = myHash(Temp1.element);
            }
            probeCt++;
            thisProbe++;
            //from second array and put in first
            if(array2[Pos1] == null ){
                array2[Pos1] = Temp1;
                break;
            }else{
                Temp2 = array2[Pos1];
                array2[Pos1] = Temp1;
                Temp1 = Temp2;
                Pos2 = mySecondHash(Temp1.element);
            }
            total_iterations += 2;
            thisProbe++;
            probeCt++;
        }

        //max iterations reached
        if (total_iterations >= max_iterations) {
            rehash();
            rehashCt++;
            if (thisProbe > maxProbeCt){
            maxProbeCt=thisProbe;
            }
            return insert(x);
        }

        if (thisProbe > maxProbeCt){
            maxProbeCt=thisProbe;
        }
        return true;
    }

    /**
     * Find an item in the hash table either its in first array or second
     * @param x the item to search for.
     * @return the matching item.
     */
    public String find(String x) {
        int currentPos1 = myHash(x);
        int currentPos2 = mySecondHash(x);
        probeCt++;
        maxProbeCt = 2;
        if (array[currentPos1] != null && array[currentPos1].element.equals(x)) {
            return array[currentPos1].element;
        } 
        probeCt++;
        if (array2[currentPos2] != null && array2[currentPos2].element.equals(x)) {
            return array2[currentPos2].element;
        } else {
            return null;
        }
    }

    /**
     * Return true if currentPos exists and is active.
     * @param currentPos the result of a call to findPos.
     * @return true if currentPos is active.
     */
    protected boolean isActive(int currentPos) {
        return array[currentPos] != null && array[currentPos].isActive;
    }

    /**
     * remove all entries in Hash Table
     */
    protected void doClear() {
        occupiedCt = 0;
        Arrays.fill(array, null);
        Arrays.fill(array2, null);
    }

    protected static class HashEntry {
        public String element;   // the element
        public boolean isActive;  // false if marked deleted
        public HashEntry(String e) {
            this(e, true);
        }
         public HashEntry(String e, boolean active) {
            element = e;
            isActive = active;
        }
    }

    protected static final int DEFAULT_TABLE_SIZE = 101;

    protected HashEntry[] array; // The array of elements
    protected HashEntry[] array2; // The array of elements

    protected int occupiedCt;         // The number of occupied cells: active or deleted
    protected int currentActiveEntries;                  // Current size
    protected int probeCt;
    protected int maxProbeCt;
    protected int insertCt;
    protected int rehashCt;
    protected int load;
    protected boolean debug;

    /**
     * Internal method to allocate array.
     * @param arraySize the size of the array.
     */
    protected void allocateArrays(int arraySize) {
        array = new HashEntry[nextPrime(arraySize)];
        array2 = new HashEntry[nextPrime(arraySize)];
        doClear();
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
   protected static int nextPrime(int n) {
        if (n % 2 == 0)
            n++;

        for (; !isPrime(n); n += 2) {
            ;
        }

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     *
     * @param n the number to test.
     * @return the result of the test.
     */
    protected static boolean isPrime(int n) {
        if (n == 2 || n == 3)
            return true;

        if (n == 1 || n % 2 == 0)
            return false;

        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;

        return true;
    }

    /**
     * Insert all words in file into hashtable
     * @param sc File containing words to add
     * @param max Maximum number of words to add (used for debug)
     */
    public void insertAll(BufferedReader sc,int max) {
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            probeCt = 0;
            maxProbeCt=0;
            insertCt = 0;
            rehashCt = 0;
            String w;
            while (insertCt <=max &&(w = (String) sc.readLine()) != null) {
                insert(w);
                insertCt++;
             }
            System.out.println("*** TableSize " + array.length + " Inserted " + insertCt +
                    "  load factor (" + load + "%) probes " + probeCt + " Average Cost " + df.format(probeCt / (float) insertCt) + " maxCost = " + maxProbeCt);
            if (rehashCt > 0) System.out.println(" Rehashed " + rehashCt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Try to find all words in a file
     * @param sc File containing words to find
     * @param msg Message to describe the kind of test this is (Not there, present, mixture)
     */
    public void findAll(BufferedReader sc, String msg) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            probeCt = 0;
            maxProbeCt=0;
            int ct = 0;
            int foundCt = 0;
            rehashCt = 0;
            String w;
            while ((w = (String) sc.readLine()) != null) {
                ct++;
                if (find(w) != null) foundCt++;
                else {
                    if (debug) {
                        System.out.println(" Not Found  '" + w + "' at line " + ct);
                    }
                }
            }
            System.out.println(msg + " Looked for " + ct + " Found " + foundCt +  " probes " + probeCt +
                    " Average Cost " + df.format(probeCt / (float) ct)+ " maxCost = " + maxProbeCt);
            if (rehashCt > 0) System.out.println(" Rehashed " + rehashCt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


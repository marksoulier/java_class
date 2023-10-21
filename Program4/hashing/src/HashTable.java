
// QuadraticProbing Hash table class

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

public class HashTable implements HashTableInterface{
    /**
     * Construct the hash table.
     */
    public HashTable() {
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
    public HashTable(int size, int load) {
        allocateArray(size);
        this.load = load;
        doClear();
        debug=false;
    }

    /**
     * Insert into the hash table. If the item is
     * already present, do nothing.
     * Implementation issue: This routine doesn't allow you to use a lazily deleted location.  Do you see why?
     *
     * @param x the item to insert.
     */
    public boolean insert(String x) {
        // Insert x as active
        int currentPos = findPos(x);
        if (isActive(currentPos))
            return false;

        array[currentPos] = new HashEntry(x, true);
        currentActiveEntries++;
        // Rehash; see Section 5.5
        if (++occupiedCt > array.length * load/100) {
            rehash();
            rehashCt++;
        }

        return true;
    }

    /**
     * @param limit Number of active entries to print
     * @return
     */
    public java.lang.String toString(int limit) {
        StringBuilder sb = new StringBuilder();
        int ct = 0;
        for (int i = 0; i < array.length && ct < limit; i++) {
            if (array[i] != null && array[i].isActive) {
                sb.append(i + ": " + array[i].element + "\n");
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
        allocateArray(2 * oldArray.length);
        occupiedCt = 0;
        currentActiveEntries = 0;

        // Copy table over
        for (HashEntry entry : oldArray)
            if (entry != null && entry.isActive)
                insert(entry.element);
    }



    protected int findPos(String x) {
        int offset = 1;
        int currentPos = myhash(x);
        int thisProbe=1;
        probeCt++;
        while (array[currentPos] != null &&
                !array[currentPos].element.equals(x)) {
            currentPos += offset;  // Compute ith probe
            probeCt++;
            thisProbe++;
            offset += 2;
            if (currentPos >= array.length)
                currentPos -= array.length;
        }
        if (thisProbe > maxProbeCt){
            maxProbeCt=thisProbe;
        }
        return currentPos;
    }

    /**
     * Remove from the hash table.
     * @param x the item to remove.
     * @return true if item removed
     */
    protected boolean remove(String x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            array[currentPos].isActive = false;
            currentActiveEntries--;
            return true;
        } else
            return false;
    }


    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return true if item is found
     */
    public boolean contains(String x) {
        int currentPos = findPos(x);
        return isActive(currentPos);
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return the matching item.
     */
    public String find(String x) {
        int currentPos = findPos(x);
        if (!isActive(currentPos)) {
            return null;
        } else {
            return array[currentPos].element;
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
    }

    /**
     * @param x the Item to be hashed
     * @return the hashCode for the element
     */
    protected int myhash(String x) {
        int hashVal = x.hashCode();

        hashVal %= array.length;
        if (hashVal < 0)
            hashVal += array.length;

        return hashVal;
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
    protected void allocateArray(int arraySize) {
        array = new HashEntry[nextPrime(arraySize)];
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


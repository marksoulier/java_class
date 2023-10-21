public class HashTableDouble extends HashTable {
    

    /**
     * Construct the hash table.
     *
     * @param size the approximate initial size.
     */
    public HashTableDouble() {
        super();
    }

    /**
     * Construct the hash table.
     *
     * @param size the approximate initial size.
     */
    public HashTableDouble(int size, int load) {
        super(size, load);
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
     * Method that performs double hashing.
     *
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    protected int findPos(String x) {
        int currentPos = myhash(x);
        int step_size = mySecondHash(x);
        int thisProbe=1;
        probeCt++;
        while (array[currentPos] != null &&
                !array[currentPos].element.equals(x)) {
            currentPos += step_size;  // Compute the next position
            if (currentPos >= array.length) currentPos -= array.length;
            probeCt++;
            thisProbe++;
        }
        if (thisProbe > maxProbeCt){
            maxProbeCt=thisProbe;
        }

        return currentPos;
}

}
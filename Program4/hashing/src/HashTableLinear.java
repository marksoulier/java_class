public class HashTableLinear extends HashTable {
    

    /**
     * Construct the hash table.
     *
     * @param size the approximate initial size.
     */
    public HashTableLinear() {
        super();
    }

    /**
     * Construct the hash table.
     *
     * @param size the approximate initial size.
     */
    public HashTableLinear(int size, int load) {
        super(size, load);
    }

    /**
     * Method that performs linear probing.
     *
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    protected int findPos(String x) {
        int offset = 1;
        int currentPos = myhash(x);
        int thisProbe=1;
        probeCt++;
        while (array[currentPos] != null &&
                !array[currentPos].element.equals(x)) {
            currentPos += offset;  // Compute the next position
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
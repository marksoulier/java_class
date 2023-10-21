import java.util.Arrays;
import java.util.Random;

public class BinPacking {
    static int BINSIZE=100;
    Integer [] requests;
    Integer [] sortedRequests;
    BinPacking(int size){
        Random rand  = new Random(size); //Seed will cause the same sequence of numbers to be generated each test
        requests = new  Integer[size];
        sortedRequests = new Integer[size];
        for (int i=0; i < size; i++){
            requests[i] =rand.nextInt(BINSIZE)+1;
            sortedRequests[i] = requests[i];
        }
        if (size <=500 ) System.out.println("Size " + size + " " +Arrays.toString(requests));
    }

    //Worst Fit with sorting before hand
    public void scheduleWorstFit(){
        //use heap sort to insert the requests and ptioritize them
        HeapSort<Integer> heapSort = new HeapSort<Integer>();
        heapSort.sort(sortedRequests);
        System.out.println("Sorted: " + Arrays.toString(sortedRequests));
        //place requests into disks
        fit(sortedRequests);
    }

    //Worst Fit without sorting before hand or off the line
    public void scheduleOfflineWorstFit(){
        System.out.println("Unsorted: " + Arrays.toString(requests));
        //place requests into disks
        fit(requests);
    }

    //do a fit method for inserting the requests into the disks
    public void fit(Integer []requestsTemp){
        //create a new heap sort to sort the disks with a leftist heap
        LeftistHeap<Disk> heapSort = new LeftistHeap<Disk>();
        //go through the requests and insert them into the disks
        for (int i=0; i < requestsTemp.length; i++){
            //if the min disk is null or the remaining space is less than the request
            if (heapSort.findMin() == null || heapSort.findMin().remainingSpace < requestsTemp[i]){
                //create a new disk
                Disk newDisk = new Disk(heapSort.getSize(), BINSIZE);
                //insert the request into the disk
                newDisk.add(requestsTemp[i]);
                //insert the disk into the heap
                heapSort.insert(newDisk);
            }//else insert the request into the disk
            else heapSort.findMin().add(requestsTemp[i]);
        }

        // if size of leftist heap is less than 20 print the heap otherwise dont
        if (heapSort.getSize() <=20) heapSort.print();

        //add up all space used in the disks by going through heap and adding up all the space used
        int totalSpaceUsed = 0;
        for (int i=0; i < heapSort.getSize(); i++){
            totalSpaceUsed += (100.0 - heapSort.findMin().remainingSpace);
            heapSort.deleteMin();
        }

        //print out the disks used and the average space used
        System.out.println("Disks used: " + heapSort.getSize() + " Average space used: " + totalSpaceUsed/100.0);
    }

    public static void main (String[] args)
    {
       //int [] fileSizes = {10, 20, 100, 500,10000,100000};
        int [] fileSizes = {10};

        for (int size :fileSizes){
            BinPacking b = new BinPacking(size);
            b.scheduleWorstFit();
            b.scheduleOfflineWorstFit();
        }

    }}

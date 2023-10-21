import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HashTableTester {
    public static void main(java.lang.String[] args) {
        int FILESIZE = 12972;
        int TESTMAX = 4;
        HashTableInterface H;
        try {
            for (int test = 1; test <= TESTMAX; test++)
                for (int load = 30; load < 100; load += 5) {
                    int tablesize = FILESIZE * 100 / load;
                    switch (test) {
                        case 1:
                            System.out.println("LINEAR PROBING");
                            H = new HashTableLinear(tablesize, load);
                            break;
                        case 2:
                            System.out.println("QUADRATIC PROBING");
                            H = new HashTable(tablesize, load);
                            break;
                        case 3:
                            System.out.println("DOUBLE HASHING");
                            H = new HashTableDouble(tablesize, load);
                            break;
                        default:
                            System.out.println("CUCKOO HASHING");
                            H = new HashTableCuckoo(tablesize, load);
                    }


                    BufferedReader words = new BufferedReader(new FileReader("words5.txt"));
                    ///home/yotta/class/java/Program4/hashing/bin/words5.txt
                    H.insertAll(words, FILESIZE);
                    words.close();

                    //print the table
                    // System.out.println(H.toString(FILESIZE));

                    //pause after each test
                    System.out.println("Press enter to continue");
                    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                    stdin.readLine();

                    BufferedReader randomWords = new BufferedReader(new FileReader("5words.txt"));
                    H.findAll(randomWords, " Present ");
                    randomWords.close();

                    //pause after each test
                    System.out.println("Press enter to continue");
                    stdin = new BufferedReader(new InputStreamReader(System.in));
                    stdin.readLine();


                    BufferedReader capWords = new BufferedReader(new FileReader("wordCap.txt"));
                    H.findAll(capWords, " NotThere");
                    capWords.close();

                    //pause after each test
                    System.out.println("Press enter to continue");
                    stdin = new BufferedReader(new InputStreamReader(System.in));
                    stdin.readLine();
                }
        } catch (Exception e) {
            System.out.println("Exception" + e);
        }
    }


}
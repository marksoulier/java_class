import java.util.Scanner;
import java.util.*;

public class Game {
    private static final String SOLVED_ID = "123456780";
    Board theBoard;
    String originalBoardID;
    String boardName;
    State Board;
    LinkedList movesList;
    int printCount = 0;
    int addCount = 0;
    int removeCount = 0;

    /**
     *  Solve the provided board
     * @param label Name of board (for printing)
     * @param b Board to be solved
     */
    public void playGiven(String label, Board b) {
        theBoard = b;
        originalBoardID = b.getId();
        boardName = label;
        System.out.println("Board initial: " + boardName + " \n" + theBoard.toString());
        // Create a linked list and add newly created moves to the end of the list
        movesList = new LinkedList();
        // add board to the end of the list
        Board = new State(theBoard.getId(), "");
        movesList.add(Board);
        solve();
    }

    public void solve() {
        //print all the items in the linked list for first 3 iterations
        if (printCount < 3) {
            movesList.printList();
            System.out.println();
            printCount++;
        }

        // Remove the first node from the list and return the
        State nextBoard = movesList.remove();
        removeCount++;
        //System.out.println("Next board: " + nextBoard);
        theBoard = new Board(nextBoard.getId());
        String steps = nextBoard.getSteps();
        char laststep = nextBoard.getLast();
        //System.out.println("Last step: " + laststep);
        // System.out.println("Steps: " + steps);

        // wait for enter to be pressed
        // Scanner in = new Scanner(System.in);
        // String resp;
        // System.out.println("Click any key to continue\n");
        // resp = in.nextLine();

        // Take the first move off the list and check for completed board
        if (theBoard.isSolved(theBoard.getId())) {
                Board exampleBoard = new Board(originalBoardID);
                for (int i = 0; i < steps.length(); i++) {
                    if(steps.charAt(i) == 'D'){
                        exampleBoard.slideDown();
                    }
                    if(steps.charAt(i) == 'U'){
                        exampleBoard.slideUp();
                    }
                    if(steps.charAt(i) == 'L'){
                        exampleBoard.slideLeft();
                    }
                    if(steps.charAt(i) == 'R'){
                        exampleBoard.slideRight();
                    }
                    System.out.println(steps.charAt(i) + "==>\n" + exampleBoard.toString());
                }
                System.out.println("Solved " + boardName + " \n" + theBoard.toString());
                System.out.println("Steps: " + steps + "(" + steps.length() + ")");
                System.out.println("Number of items added: " + addCount + " Removed: " + removeCount);
                return;
            }
        
        // If not completed, add all possible moves to the end of the list
        // if the last move is not opposite and move is possible
        if ((laststep != 'R') && (theBoard.slideLeft())) {
            State newBoard = new State(theBoard.getId(), steps + "L");
            //add onto the end of the list
            movesList.add(newBoard);
            addCount++;
            // revert the board back to the original state
            theBoard.slideRight();
        }
        if ((laststep != 'D') && (theBoard.slideUp())) {
            State newBoard = new State(theBoard.getId(), steps + "U");
            //add onto the end of the list
            movesList.add(newBoard);
            addCount++;
            // revert the board back to the original state
            theBoard.slideDown();
        }
        if ((laststep != 'U') && (theBoard.slideDown())) {
            State newBoard = new State(theBoard.getId(), steps + "D");
            //add onto the end of the list
            movesList.add(newBoard);
            addCount++;
            // revert the board back to the original state
            theBoard.slideUp();
        }
        if ((laststep != 'L') && (theBoard.slideRight())) {
            State newBoard = new State(theBoard.getId(), steps + "R");
            //add onto the end of the list
            movesList.add(newBoard);
            addCount++;
            // revert the board back to the original state
            theBoard.slideLeft();
        }

        // Recursively call solve until the board is solved
        solve();
    }

    /**
     * Create a random board (which is solvable) by jumbling jumnbleCount times.
     * Solve
     * @param label Name of board (for printing)
     * @param jumbleCount number of random moves to make in creating a board
     */
    public void playRandom(String label, int jumbleCount) {
        theBoard = new Board();
        theBoard.makeBoard(jumbleCount);
        System.out.println(label + "\n" + theBoard);
        playGiven(label, theBoard);
    }



    public static void main(String[] args) {
        String[] games = {"102453786", "123740658", "023156478", "413728065", "145236078", "123456870"};
        String[] gameNames = {"Easy Board", "Game1", "Game2", "Game3", "Game4", "Game5 No Solution"};
        Game g = new Game();
        Scanner in = new Scanner(System.in);
        Board b;
        String resp;
        for (int i = 0; i < games.length - 1; i++) {
            b = new Board(games[i]);
            g.playGiven(gameNames[i], b);
            System.out.println("Click any key to continue\n");
            resp = in.nextLine();
        }


        boolean playAgain = true;
        //playAgain = false;

        int JUMBLECT = 10;  // how much jumbling to do in random board
        while (playAgain) {
            g.playRandom("Random Board", JUMBLECT);

            System.out.println("Play Again?  Answer Y for yes\n");
            resp = in.nextLine().toUpperCase();
            playAgain = (resp != "") && (resp.charAt(0) == 'Y');
        }


    }


}

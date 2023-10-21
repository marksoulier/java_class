import java.util.Scanner;

public class SliderGame {
        private static final String SOLVED_ID = "123456780";
        Board theBoard;
        String originalBoardID;
        String boardName;
        LinkedList movesList;
        AVLTree<StateA> movesTree;
        int printCount = 0;
        int addCount = 0;
        int removeCount = 0;
    
        /**
         *  Solve the provided board
         * @param label Name of board (for printing)
         * @param b Board to be solved
         */
        public void playGiven(String label, Board b, String method) {
            theBoard = b;
            originalBoardID = b.getId();
            boardName = label;
            System.out.println("Board initial: " + boardName + " \n" + theBoard.toString());
            //decide between doing brute force or A*
            if(method == "bruteForce"){
                // Create a linked list and add newly created moves to the end of the list
                movesList = new LinkedList();
                // add board to the end of the list
                State Board = new State(theBoard.getId(), "");
                movesList.add(Board);
                bruteForceSolve();
                printCount = 0;
            }
            else if(method == "aStar"){
                //create AVL tree to store all the moves in order
                movesTree = new AVLTree<>();
                // add the first state to the list
                String function = "Manhattan"; //Hamming or Manhattan
                StateA Board = new StateA(theBoard.getId(), "", function);
                movesTree.insert(Board);
                aStarSolve(function);
                printCount = 0;
            }
            else {
                System.out.println("Wrong method type. aStar or bruteForce");
            }
            
        }

        /**
         * Solve the board using A*
         */
        public void aStarSolve(String method) {
            //print all the items in the linked list for first 3 iterations
            if (printCount < 3) {
                movesTree.printTree("aStar Tree");
                System.out.println();
                printCount++;
            }
    
            // Remove the first min priority from the tree and return
            StateA nextBoard = movesTree.findMin();
            //delete the min priority from the tree
            movesTree.deleteMin();
            removeCount++;

            //System.out.println("Next board: " + nextBoard);
            //get the Board ready for all moves possible.
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
                    System.out.println("Solved using A*: " + boardName + " \n" + theBoard.toString());
                    System.out.println("Steps: " + steps + "(" + steps.length() + ")");
                    System.out.println("Number of items added: " + addCount + " Removed: " + removeCount);
                    addCount = 0;
                    removeCount = 0;
                    return;
                }
            
            // If not completed, add all possible moves to the end of the list
            // if the last move is not opposite and move is possible
            if ((laststep != 'R') && (theBoard.slideLeft())) {
                StateA newBoard = new StateA(theBoard.getId(), steps + "L", method);
                //add onto the end of the list
                movesTree.insert(newBoard);
                addCount++;
                // revert the board back to the original state
                theBoard.slideRight();
            }
            if ((laststep != 'D') && (theBoard.slideUp())) {
                StateA newBoard = new StateA(theBoard.getId(), steps + "U", method);
                //add onto the end of the list
                movesTree.insert(newBoard);
                addCount++;
                // revert the board back to the original state
                theBoard.slideDown();
            }
            if ((laststep != 'U') && (theBoard.slideDown())) {
                StateA newBoard = new StateA(theBoard.getId(), steps + "D", method);
                //add onto the end of the list
                movesTree.insert(newBoard);
                addCount++;
                // revert the board back to the original state
                theBoard.slideUp();
            }
            if ((laststep != 'L') && (theBoard.slideRight())) {
                StateA newBoard = new StateA(theBoard.getId(), steps + "R", method);
                //add onto the end of the list
                movesTree.insert(newBoard);
                addCount++;
                // revert the board back to the original state
                theBoard.slideLeft();
            }
            // Recursively call solve until the board is solved
            aStarSolve(method);
        }
    
        /**
         * Solve the board using brute force
         */
        public void bruteForceSolve() {
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
                    System.out.println("Solved using Brute Force: " + boardName + " \n" + theBoard.toString());
                    System.out.println("Steps: " + steps + "(" + steps.length() + ")");
                    System.out.println("Number of items added: " + addCount + " Removed: " + removeCount);
                    addCount = 0;
                    removeCount = 0;
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
            bruteForceSolve();
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
            Board copyBoard = new Board(theBoard.getId());
            System.out.println(label + "\n" + theBoard);
            playGiven(label, theBoard, "bruteForce");
            playGiven(label, copyBoard, "aStar");
        }

   public static void main(String[] args) {
        String[] games = {"102453786", "123740658", "023156478", "413728065"};
        String[] gameNames = {"Easy Board", "Game1", "Game2", "Game3"};
        SliderGame g = new SliderGame();
        Scanner in = new Scanner(System.in);
        Board b;
        String resp;
        for (int i = 0; i < games.length; i++) {
            b = new Board(games[i]);
            g.playGiven(gameNames[i], b, "bruteForce");
            g.playGiven(gameNames[i], b, "aStar");
            System.out.println("Click any key to continue\n");
            resp = in.nextLine();
        }

        System.out.println("Puzzle that has significant diffrence of output in added and removed items.\n");
        String spgame = "813425076";
        b = new Board(spgame);
        g.playGiven("Special Board", b, "bruteForce");
        g.playGiven("Special Board", b, "aStar");

        System.out.println("Click any key to continue\n");
        resp = in.nextLine();
        
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

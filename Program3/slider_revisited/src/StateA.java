/*
A simple object for Astar algorithm that stores the board state, costs, steps and priority
 */

public class StateA implements Comparable<StateA>{
    private String steps;
    private String id;
    private int priority;
    private int cost_of_steps;
    private int cost_to_solution;


    StateA(String id, String steps, String method) {
        this.steps = steps;
        this.id = id;
        this.cost_of_steps = steps.length();


        if(method == "Hamming") {
            this.cost_to_solution = HammingCost();
        }else if(method == "Manhattan"){
            this.cost_to_solution = manhattanCost();
        }else if(method == "Manhattan-plus"){
            this.cost_to_solution = manhattanCostPlus();
        }else {
            System.out.println("Wrong type of cost function");
            this.cost_to_solution = 0;
        }
        this.priority = cost_of_steps + cost_to_solution;
    }

    @Override
    public int compareTo(StateA b2){
        if (this.priority > b2.priority){
            return 1;
        }else if(this.priority < b2.priority) {
            return -1;
        }
        return 0;
    }

    /**
     * manhattanCost() calculate the cost to solve the game using manhattan distance from final board
     * @return
     */
    public int manhattanCost(){
        int total_cost = 0;

        for(int i = 1; i < 9; i++){
            int x_pos = (i-1)%3;
            int y_pos = (i-1)/3;
            char c = (char) (i%9 + '0');
            int pos = id.indexOf(c);
            int x_correct_pos = pos%3;
            int y_correct_pos = pos/3;
            //System.out.println("i: " + i + " x_pos: " + x_pos + " y_pos: " + y_pos + " pos: " + pos + " x_correct_pos: " + x_correct_pos + " y_correct_pos: " + y_correct_pos);
            total_cost += Math.abs(x_correct_pos - x_pos);
            total_cost += Math.abs(y_correct_pos - y_pos);
        }
        return total_cost;
    }

    /**
     * manhattanCostPlus() calculate the cost to solve the game using manhattan distance from final board
     * @return
     */
    public int manhattanCostPlus(){
        int total_cost = 0;

        for(int i = 1; i < 9; i++){
            int x_pos = (i-1)%3;
            int y_pos = (i-1)/3;
            char c = (char) (i%9 + '0');
            int pos = id.indexOf(c);
            int x_correct_pos = pos%3;
            int y_correct_pos = pos/3;
            total_cost += Math.abs(x_correct_pos - x_pos);
            total_cost += Math.abs(y_correct_pos - y_pos);
        }
        return total_cost;
    }

    /**
     * HammingCost() calculate the cost to solve the game using incorrect squares
     * @return
     */
    public int HammingCost(){
        int total_cost = 0;

        for(int i = 1; i < 10; i++){
            char c = (char) (i%9 + '0');
            if(id.indexOf(c) != (i-1)){
                total_cost += 1;
            }
        }
        return total_cost;
    }


    /**
     * @return last move made
     */
    public char getLast(){
        if (steps.equals("")) return '*';
        int last = steps.length();
        return steps.charAt(last-1);
    }
    public String getId() {
        return id;
    }

    public String getSteps() {
        return steps;
    }

    public int getNumSteps() {
        return steps.length();
    }
    /**
     * toString() print the state
     * @return priority
     */
    public String toString(){
        return "State " + id + " steps: " + steps + " priority: " + priority + " cost of steps: " + cost_of_steps + " cost to solution: " + cost_to_solution;

    }
}

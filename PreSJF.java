import java.util.*;

public class PreSJF {

    private int numberOfProcess;
    private int[][] inputArray;
    private int[][] outputArray;
    private int[] finishTimeArray;
    private int[][] inputArrayCopy;
    private ArrayList<Integer> time = new ArrayList<>(); //store execution time
    private ArrayList<String> ganttChart = new ArrayList<>(); //store process

    /*
    inputArray[i][0] is process
    inputArray[i][1] is burst time
    inputArray[i][2] is arrival time
    inputArray[i][3] is priority
    inputArray[i][4] is waiting time
    inputArray[i][5] is turnaround time
    */

    PreSJF(int number, int[][] inputMatrix){
        numberOfProcess = number;
        inputArray = inputMatrix;
        inputArrayCopy = inputMatrix;
        outputArray = new int[numberOfProcess][6];
        finishTimeArray = new int[numberOfProcess];
    }

    public void process(){
        boolean check = false;
        int complete = 0;
        int t = 0;
        int min = Integer.MAX_VALUE;
        int shortest = 0;
        int finishTime;

        for(int i=0; i < numberOfProcess; i++){
            for(int j = 0; j < 4; j++){
                outputArray[i][j]=inputArray[i][j]; //copy all data from input array to output array
            }
        }

        while (complete != numberOfProcess) {
            // Find process with minimum burst time among the processes that arrives till the current time
            for (int i = 0; i < numberOfProcess; i++){
                if((inputArray[i][2] <= t) && inputArrayCopy[i][1]==min){ //if same burst time, check priority
                    if(inputArray[shortest][3] > inputArray[i][3]){ //check priority
                        min = inputArrayCopy[i][1];
                        shortest = i;
                        check = true;
                        time.add(t);
                    }
                }
                else if ((inputArray[i][2] <= t) && (inputArrayCopy[i][1] < min) && inputArrayCopy[i][1] > 0) { //if burst time less than current minimum burst time
                    min = inputArrayCopy[i][1];
                    shortest = i;
                    check = true;
                    time.add(t);
                }
            }

            if (check == false) {
                t++;
                continue;
            }

            //add process into gantt chart
            ganttChart.add("P"+inputArrayCopy[shortest][0]);

            // Reduce remaining time by one
            inputArrayCopy[shortest][1]--;

            // Update minimum burst time
            min = inputArrayCopy[shortest][1];
            if (min == 0)
                min = Integer.MAX_VALUE;

            // If process executed completely
            if (inputArrayCopy[shortest][1] == 0) {

                complete++;
                check = false;

                finishTime = t + 1; //find finish time

                time.add(finishTime);
                finishTimeArray[shortest] = finishTime; //add finish time to array
            }
            t++;
        }
    }

    public void updateOutputTable(){
        for (int i = 0; i < numberOfProcess; i++) {
            outputArray[i][4] = finishTimeArray[i] - outputArray[i][2]; //turnaround time = finish time - arrival time
            outputArray[i][5] = finishTimeArray[i] - outputArray[i][2] - outputArray[i][1]; //waiting time = finish time - arrival time - burst time

            if (outputArray[i][5] < 0)
                outputArray[i][5] = 0; //the lowest waiting time is 0
        }
    }

    //calculate waiting time and turnaround time
    public void calculateTime(){
        int totalTAT = 0;
        int totalWT = 0;

        for (int i = 0; i < numberOfProcess; i++) {
            totalTAT += outputArray[i][4];
            totalWT += outputArray[i][5];
        }

        double avgTAT = (double)totalTAT/numberOfProcess;
        double avgWT = (double)totalWT/numberOfProcess;

        System.out.println("\nAverage Turnaround Time: " + String.format("%.2f", avgTAT));
        System.out.println("Average Waiting Time: " + String.format("%.2f", avgWT));
    }

    public int[][] getOutputArray(){
        return outputArray;
    }

    public ArrayList<String> getGanttChart(){
        return ganttChart;
    }

    public ArrayList<Integer> getTime(){
        return time;
    }
}
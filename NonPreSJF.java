import java.util.ArrayList;
import java.util.Arrays;

public class NonPreSJF {

    private int totalCountTime;
    double avgTAT;
    double avgWT;
    private int numberOfProcess;
    private int[][] inputArray; //input matrix
    private int[][] outputArray; //output matrix
    private int[] finishTimeArray; //finish time's array
    private ArrayList<Integer> waitingQueue = new ArrayList<>(); //store waiting id
    private ArrayList<Integer> timeForGanttChart = new ArrayList<>(); //store first arrival time + finish time array
    private ArrayList<String> processForGanttChart = new ArrayList<>(); //store process

    /*inputArray[i][0] is process id
    inputArray[i][1] is burst time
    inputArray[i][2] is arrival time
    inputArray[i][3] is priority
    inputArray[i][4] is waiting time
    inputArray[i][5] is turnaround time*/

    NonPreSJF(int number, int[][] inputMatrix){
        numberOfProcess = number;
        inputArray = inputMatrix;
        outputArray = new int[numberOfProcess][6];
        finishTimeArray = new int[numberOfProcess];
    }

    //sort sequence by its arrival time
    public void sortSequenceByAT() {
        for (int i = 0; i < numberOfProcess; i++) { //for each process
            for (int j = 0; j < numberOfProcess - i - 1; j++) { //to prevent over sorting
                if (inputArray[j][2] > inputArray[j + 1][2]) { //if bigger arrival time
                    for (int k = 0; k < 5; k++) { //sort the process
                        int temp = inputArray[j][k];
                        inputArray[j][k] = inputArray[j + 1][k];
                        inputArray[j + 1][k] = temp; //sort to one process behind
                    }
                }
            }
        }
    }

    private void calculateTotalCount(){
        totalCountTime = inputArray[0][2]; //first arrival time
        for (int i = 0; i < numberOfProcess; i++) {
            totalCountTime += inputArray[i][1]; //add all burst time
        }
    }

    //find the smallest arrival time
    public int findATMinimum(){
        int minValue = inputArray[0][2];
        for(int i = 1;i < numberOfProcess;i++){
            if(inputArray[i][2] < minValue){
                minValue = inputArray[i][2];
            }
        }
        return minValue;
    }

    //sort sequence by its arrival time
    public void sortSequenceForSameAT(int lastIndex) {
        for (int i = 0; i < lastIndex + 1; i++) { //for each process
            for (int j = 0; j < lastIndex - i; j++) { //to prevent over sorting
                if (inputArray[j][1] > inputArray[j + 1][1]) { //if bigger burst time
                    for (int k = 0; k < 5; k++) { //sort the process
                        int temp = inputArray[j][k];
                        inputArray[j][k] = inputArray[j + 1][k];
                        inputArray[j + 1][k] = temp; //sort to one process behind
                    }
                }
            }
        }
    }

    public void sortSequenceByBT(){

        calculateTotalCount(); //find total count time
        int howManyDone = 0; //output array's row index
        int countDown;

        //to find who may become the first process
        int lastIndex = 0;
        for (int i = 0; i < numberOfProcess; i++) {
            if(inputArray[i][2] == findATMinimum()){
                lastIndex = i;
            }
        }

        if(lastIndex>0) //if more than 1 same first arrival time
            sortSequenceForSameAT(lastIndex);

        //add first process into output array
        for(int j = 0; j < inputArray[0].length; j++) //copy all columns into new row
            outputArray[howManyDone][j]=inputArray[0][j];

        countDown = inputArray[0][1]; //first burst time
        int nextIdToExecute = inputArray[0][0];

        //start counting / start loop
        for (int time = inputArray[0][2]; time < totalCountTime; time++) {

            //check is there any processes arrived at this time
            for (int i = howManyDone; i < numberOfProcess; i++) {
                if(inputArray[i][2] == time){ //if the process arrived then add it into waiting queue
                    waitingQueue.add(inputArray[i][0]); //add process id to waiting queue
                }
            }

            //if the previous process done executing
            if(countDown == 0){
                finishTimeArray[howManyDone] = time;
                //if there is process in waiting queue
                if(!waitingQueue.isEmpty()){
                    int lowestBT = 10000;

                    for (int i = 0; i < waitingQueue.size(); i++) { //remove done process from waiting queue
                        if(nextIdToExecute == waitingQueue.get(i))
                            waitingQueue.remove(i);
                    }

                    for (int i = 0; i < numberOfProcess; i++) { //loop whole input array
                        for (int j = 0; j < waitingQueue.size(); j++) { //see id matched or not
                            if(inputArray[i][0] == waitingQueue.get(j)){ //if id in waiting queue, find who has the lowest burst time
                                if(inputArray[i][1] < lowestBT){
                                    lowestBT = inputArray[i][1];
                                    nextIdToExecute = inputArray[i][0];
                                }
                            }
                        }
                    }

                    countDown = lowestBT; //update execution time
                    howManyDone++;

                    //add to output array
                    for(int i = 0; i < numberOfProcess; i++){
                        if(nextIdToExecute == inputArray[i][0]){
                            for(int j = 0; j < 6; j++) //copy all columns into new row
                                outputArray[howManyDone][j] = inputArray[i][j];
                        }
                    }
                }
            }
            //executing
            countDown--;
        }
        finishTimeArray[numberOfProcess-1] = totalCountTime;
    }

    //calculate waiting time and turnaround time
    public void calculateTime(){
        int totalTAT = 0;
        int totalWT = 0;

        for (int i = 0; i < numberOfProcess; i++) {
            outputArray[i][4] = finishTimeArray[i] - outputArray[i][2]; //turnaround time
            outputArray[i][5] = outputArray[i][4] - outputArray[i][1]; //waiting time

            totalTAT += outputArray[i][4];
            totalWT += outputArray[i][5];
        }

        avgTAT = (double)totalTAT/numberOfProcess;
        avgWT = (double)totalWT/numberOfProcess;
    }

    public void printAverageTime(){
        System.out.println("\nAverage Turnaround Time: " + String.format("%.2f", avgTAT));
        System.out.println("Average Waiting Time: " + String.format("%.2f", avgWT));
    }

    public ArrayList<String> createProcessForGanttChart(){
        for (int i = 0; i < numberOfProcess; i++) {
            String processName = "P" + outputArray[i][0];
            processForGanttChart.add(processName);
        }
        return processForGanttChart;
    }

    public ArrayList<Integer> createTimeForGanttChart(){
        timeForGanttChart.add(outputArray[0][2]); //first arrival time
        for (int i = 0; i < numberOfProcess; i++) {
            timeForGanttChart.add(finishTimeArray[i]);
        }
        return timeForGanttChart;
    }

    public void print2D(int[][] mat) //testing nia, delete ltr
    {
        // Loop through all rows
        for (int i = 0; i < mat.length; i++){
            // Loop through all elements of current row
            for (int j = 0; j < mat[i].length; j++)
                System.out.print(mat[i][j] + " ");
        }
    }

    private void printArrayList(ArrayList<Integer> array){ //testing nia, delete ltr
        for (int i = 0; i < array.size(); i++) {
            System.out.println("id in waiting queue " + i + ": " + array.get(i));
        }
    }

    public int[][] getOutputArray() {
        return outputArray;
    }
}

import java.util.ArrayList;

public class NonPrePrio {
    NonPrePrio(){}
    private ArrayList<Integer> arrivalQueue = new ArrayList<>();
    private int[][] inputArray;
    private int[][] outputArray;
    private int totalBurstTime;
    private int[] finishTimeArray;
    private int numberOfProcess;
    private int startingTime;
    
    private ArrayList<Integer> time = new ArrayList<>(); //store execution time
    private ArrayList<String> ganntChart = new ArrayList<>(); //store process
    
    /*inputArray[i][0] is process id
    inputArray[i][1] is burst time
    inputArray[i][2] is arrival time
    inputArray[i][3] is priority
    inputArray[i][4] is waiting time
    inputArray[i][5] is turnaround time*/

    NonPrePrio(int numberOfProcess,int[][] inputArray){

        this.inputArray = inputArray;
        this.numberOfProcess = numberOfProcess;
        outputArray = new int[numberOfProcess][6];
        finishTimeArray = new int[numberOfProcess];
        setStartingTime();
        totalBurstTime = calculateTotalBurst()+startingTime;
        
    }

    public void setStartingTime(){
        int min=inputArray[0][2];
        for(int i=1;i<3;i++){
            if(inputArray[i][2]<min){
                min = inputArray[i][2];
            }    
        }
        startingTime = min;
    }
    public int calculateTotalBurst(){
        
        for (int i=0;i<numberOfProcess;i++){
            
            totalBurstTime += inputArray[i][1];
        }
        return totalBurstTime;
    }

    public void nonPreemptivePriority(){
        
        int executeTime = 0; //time counter for executing process
        boolean executing = false; //if a process is executing 
        int processExecuting; //processID which is executing
        
        for(int s=startingTime; s<totalBurstTime+1; s++){
            
            //get new arrived processID
            for(int p=0; p< numberOfProcess; p++){    
                if(inputArray[p][2] == s)
                    arrivalQueue.add(inputArray[p][0]);
            }
            
            //if executing, minus time counter for execution
            if(executing){
                if(executeTime!=0){
                    executeTime-=1;
                    if(executeTime == 0)
                        executing = false;
                }    
            }
            //not executing, start new process
            else{
                //if end of all process, break loop
                if(s == totalBurstTime){
                    time.add(s);
                    break;
                }else{
                    
                    //get highest prio from arrivalQueueProcess
                    int highestPrio = getSmallest(arrivalQueue);
                    ArrayList<Integer> prioProcess = new ArrayList<Integer>();
                    
                    //get processes with highestPrio
                    for(int i=0;i<arrivalQueue.size();i++)
                        for(int j=0; j< numberOfProcess;j++)
                            if(arrivalQueue.get(i) == inputArray[j][0] && inputArray[j][3] == highestPrio)
                                prioProcess.add(inputArray[j][0]);        
                    
                    //if there is process to be executed
                    if(prioProcess.size()>=1){
                        executing = true;
                        processExecuting = prioProcess.get(0);
                        //get row of the process
                        int row = getRow(processExecuting);
                        //burstTime - 1
                        executeTime = inputArray[row][1]-1;
                        //finishTime of the process
                        finishTimeArray[row] = s + inputArray[row][1];
                        //ganttChart
                        ganntChart.add("P"+processExecuting);
                        time.add(s);
                        //remove from waitingQueue
                        arrivalQueue.remove(Integer.valueOf(processExecuting));
                       
                        if (executeTime == 0)
                            executing = false;
                    }
                    else{
                        time.add(s);
                        ganntChart.add("-");
                    }    
                }
                
            }   
        }
    }

    public int getRow(int value){
        for(int i=0; i<inputArray.length;i++){
            if(inputArray[i][0]== value)
                return i;    
        }
        return -1;
    }
    public int getSmallest(ArrayList<Integer> arrivalQueue){ //boolean process if true return process id, else return smallest number of the column
        int min = 100;
        //int processID = 0;
        
        for(int i=0;i<arrivalQueue.size();i++){
            for(int j=0; j< numberOfProcess;j++){
                if(arrivalQueue.get(i) == inputArray[j][0] && inputArray[j][3] < min)
                    min = inputArray[j][3];
            }
        }
        
        return min;
    }
     
    public void updateOutputTable(){
        for (int i = 0; i < numberOfProcess; i++) {
        
            for(int j = 0; j < 4; j++){
                outputArray[i][j]=inputArray[i][j]; //copy all data from input array to output array
            }
            
            outputArray[i][4] = finishTimeArray[i] - outputArray[i][2]; //turnaround time = finish time - arrival time
            outputArray[i][5] = finishTimeArray[i] - outputArray[i][2] - outputArray[i][1]; //waiting time = finish time - arrival time - burst time

            if (outputArray[i][5] < 0)
                outputArray[i][5] = 0; //the lowest waiting time is 0
        }
    }

    public int[][] getOutputArray(){
        return outputArray;
    }

    public ArrayList<String> getGanttChart(){
        return ganntChart;
    }

    public ArrayList<Integer> getTime(){
        return time;
    }

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
}

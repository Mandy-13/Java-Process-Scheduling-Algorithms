import java.util.Scanner;

public class Menu {

    private int numberOfProcess;
    private int[][] matrix = new int[10][6];
    private int[][] adjustedMatrix;
    private boolean flag = true;

    Menu(){}

    public void promptInput(){
        do{
            inputNumberOfProcess();
            inputProcessDetails();
        }while(!flag);

        autofillProcessID(); //fill process id
        adjustMatrix();
    }

    //get number of process
    private void inputNumberOfProcess() {
        flag = true;
        do{
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter number of process (3-10): ");
            int input = scan.nextInt();
            if(input>2 && input<11){
                numberOfProcess = input;
                break;
            }
            else{
                System.out.println("Number of process must be in between 3 and 10");
            }
        }while(true);
    }

    //get processes' input
    private void inputProcessDetails(){
        Scanner scan = new Scanner(System.in);
        String[] str = {"burst time","arrival time","priority"};

        for(int i=0; i<3; i++){
            System.out.print("Enter "+ str[i]+ ": ");
            String input = scan.nextLine();
            String[] inputStr = input.split("\\s+");

            // make sure length entered is same as getNumberOfProcess
            if(inputStr.length == numberOfProcess){ // if boundary is true
                try{ //check input is int
                    for(int j = 0; j < inputStr.length; j++) {
                        int num = Integer.parseInt(inputStr[j]);
                        switch (i) {
                            case 0:
                                matrix[j][1] = num;break;//burst time
                            case 1:
                                matrix[j][2] = num;break;//arrival time
                            case 2:
                                matrix[j][3] = num;break;//priority
                        }
                    }
                }catch(NumberFormatException ex){ //if not int
                    System.out.println("\nYou MUST only enter integer. Please Retry\n");
                    flag = false;
                    break;
                }
            }
            else{ // if boundary is false
                System.out.println("\nYour currently number of process is " + numberOfProcess +". So your input is invalid. Please Retry\n");
                flag = false;
                break;
            }
        }
    }

    private void autofillProcessID(){
        for (int i = 0; i < numberOfProcess; i++) {
            matrix[i][0] = i+1;
        }
    }

    private void adjustMatrix(){
        adjustedMatrix = new int[numberOfProcess][6];
        for(int i = 0; i < numberOfProcess; i++){
            for(int j = 0; j < 6; j++)
                adjustedMatrix[i][j]=matrix[i][j];
        }
    }

    //getter
    public int getNumberOfProcess() { return numberOfProcess; }
    public int[][] getAdjustedMatrix() { return adjustedMatrix; }
}
public class Table {

    private int numberOfProcess;
    private int[][] inputTableMatrix;
    private int[][] padSizeArray;
    private int[][] padStartArray;
    private int width = 16;

    Table(int number, int[][] inputMatrix){
        numberOfProcess = number;
        inputTableMatrix = inputMatrix;
        padSizeArray = new int[numberOfProcess][6];
        padStartArray = new int[numberOfProcess][6];
    }

    private int countDigit(int num){
        return String.valueOf(num).length();
    }

    private void findPadding(int numCol, int[][] inputTableMatrix){
        for (int i = 0; i < numberOfProcess; i++) {
            for (int j = 0; j < numCol; j++) {
                padSizeArray[i][j] = width - countDigit(inputTableMatrix[i][j]);
                padStartArray[i][j] = countDigit(inputTableMatrix[i][j]) + padSizeArray[i][j]/2;
            }
        }
    }

    public void inputTable() {
        findPadding(4, inputTableMatrix);
        String line = new String(new char[67]).replace('\0', '-'); //draw line
        System.out.println("+" + line +"+");
        System.out.println("|    Process     |   Burst Time   |  Arrival Time  |    Priority    |");
        System.out.println("+" + line +"+");
        for(int i = 0; i < numberOfProcess; i++){
            String[] tempArray = new String[4];
            for (int j = 0; j < 4; j++) {
                String temp = String.format("%" + padStartArray[i][j] + "s", inputTableMatrix[i][j]);
                tempArray[j] = String.format("%-" + width + "s", temp);
            }
            System.out.printf("|%s|%s|%s|%s|%n",tempArray[0],tempArray[1],tempArray[2],tempArray[3]);
        }

        System.out.println("+" + line +"+");
    }

    public void outputTable(int[][] outputTableMatrix) {
        findPadding(6, outputTableMatrix);
        String line = new String(new char[101]).replace('\0', '-'); //draw line
        System.out.println("+" + line +"+");
        System.out.println("|    Process     |   Burst Time   |  Arrival Time  |    Priority    |Turnaround  Time|  Waiting Time  |");
        System.out.println("+" + line +"+");
        for(int i = 0; i < numberOfProcess; i++){
            String[] tempArray = new String[6];
            for (int j = 0; j < 6; j++) {
                String temp = String.format("%" + padStartArray[i][j] + "s", outputTableMatrix[i][j]);
                tempArray[j] = String.format("%-" + width + "s", temp);
            }
            System.out.printf("|%s|%s|%s|%s|%s|%s|%n",tempArray[0],tempArray[1],tempArray[2],tempArray[3],tempArray[4],tempArray[5]);
        }

        System.out.println("+" + line +"+");
    }
}


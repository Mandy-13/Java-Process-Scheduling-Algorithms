import java.util.ArrayList;

public class GanttChart {

  GanttChart(){}
    
    public static String printGanttChart(ArrayList<String> ganttChart){
        ArrayList<String> newGanttChart = new ArrayList<String>();
        newGanttChart.addAll(ganttChart);

        for(int i = newGanttChart.size()-1; i>=1 ;i--){
            if(newGanttChart.get(i).equals(newGanttChart.get(i-1))){
                newGanttChart.remove(i);
            }
        }

        String formattedGanttChart = newGanttChart.toString().replace(",", "  | ").replace("[", "|  ").replace("]", "  |").trim();  

        return formattedGanttChart;
    }

    public static String printTime(ArrayList<Integer> time){
        ArrayList<Integer> newTime = new ArrayList<Integer>();
        // add first value
        newTime.add(time.get(0));

        for(int i = 1; i < time.size(); i++) {
            // Compare current to previous value
            if(time.get(i-1) != time.get(i)) {
                newTime.add(time.get(i));
            }
        }

        String formattedTime = newTime.toString().replace(",", "     ").replace("[", " ").replace("]", "    ").trim(); 

        return formattedTime;
    }

    public static void showGanttChart(ArrayList<String> ganttChart, ArrayList<Integer> time){
        System.out.println("\nGantt Chart: \n");
        System.out.println(printGanttChart(ganttChart));  
        System.out.println(printTime(time)); //print time without comma and bracket
    }
}

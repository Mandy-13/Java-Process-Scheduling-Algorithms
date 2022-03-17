import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //prompt input
        Menu menu = new Menu();
        menu.promptInput();

        //print input table
        System.out.println("\nInput Table\n");
        Table table = new Table(menu.getNumberOfProcess(), menu.getAdjustedMatrix());
        table.inputTable();

        //for Non-Preemptive Shortest Job First
        System.out.println("\nNon-Preemptive Shortest Job First \n");
        NonPreSJF nonPreSJF = new NonPreSJF(menu.getNumberOfProcess(), menu.getAdjustedMatrix());
        nonPreSJF.sortSequenceByAT();
        nonPreSJF.sortSequenceByBT();
        nonPreSJF.calculateTime();
        table.outputTable(nonPreSJF.getOutputArray());
        GanttChart.showGanttChart(nonPreSJF.createProcessForGanttChart(), nonPreSJF.createTimeForGanttChart());
        nonPreSJF.printAverageTime();

        //for Non-Preemptive Priority
        System.out.println("\nNon-Preemptive Priority\n");
        NonPrePrio nonPrePrio = new NonPrePrio(menu.getNumberOfProcess(), menu.getAdjustedMatrix());
        nonPrePrio.nonPreemptivePriority();
        nonPrePrio.updateOutputTable();
        table.outputTable(nonPrePrio.getOutputArray());
        GanttChart.showGanttChart(nonPrePrio.getGanttChart(), nonPrePrio.getTime());
        nonPrePrio.calculateTime();

        //for Preemptive Shortest Job First
        System.out.println("\nPreemptive Shortest Job First \n");
        PreSJF preSJF = new PreSJF(menu.getNumberOfProcess(), menu.getAdjustedMatrix());
        preSJF.process();
        preSJF.updateOutputTable();
        table.outputTable(preSJF.getOutputArray());
        GanttChart.showGanttChart(preSJF.getGanttChart(), preSJF.getTime());
        preSJF.calculateTime();

        
        
        //PreSJF preSJF = new PreSJF(menu.getNumberOfProcess(), menu.getAdjustedMatrix());
        
    }

}


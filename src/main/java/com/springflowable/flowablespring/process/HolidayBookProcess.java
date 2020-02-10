package com.springflowable.flowablespring.process;

import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.task.api.Task;

import java.util.*;

public class HolidayBookProcess {
    private UserInteraction userInteraction;
    private String employeeName;
    private String numberOfHolidays;
    private String description;

    public HolidayBookProcess(UserInteraction userInteraction) {
        this.userInteraction = userInteraction;
    }

    public Map<String, Object> scanUserVariables() {
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your name?");
        employeeName = sc.nextLine();
        System.out.println("What number of holidays you need?");
        numberOfHolidays = sc.nextLine();
        System.out.println("Could you describe request?");
        description = sc.nextLine();
        return new HashMap<String, Object>() {{
            put("employeeName", employeeName);
            put("numberOfHolidays", numberOfHolidays);
            put("description", description);
        }};
    }

    public void printTasks(List<Task> tasks) {
        userInteraction.printTasks(tasks);
    }

    public String chooseTaskAndReturnId(List<Task> tasks) {
        return userInteraction.chooseTaskAndReturnId(tasks);
    }

    public void presentRequest(Map<String, Object> taskVars) {
        List<String> description = Arrays.asList("Employee name:","requested holidays:","description:");
        List<String> values = Arrays.asList(taskVars.get("employeeName").toString(),
                                            taskVars.get("numberOfHolidays").toString(),
                                            taskVars.get("description").toString());
        userInteraction.presentTask(description, values);
    }

    public Map<String, Object> presentApprovalQuestion(){
        return userInteraction.presentApprovalQuestion();
    }

    public void presentFinishedHistoricTasks(List<HistoricActivityInstance> finishedTasks) {
        userInteraction.presentFinishedHistoricTasks(finishedTasks);
    }
}
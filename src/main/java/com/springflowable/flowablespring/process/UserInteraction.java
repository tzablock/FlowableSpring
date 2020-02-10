package com.springflowable.flowablespring.process;

import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.task.api.Task;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInteraction {
    public void printTasks(List<Task> tasks){
        System.out.println(String.format("You have %d tasks:", tasks.size()));
        for (int taskNum=0; taskNum<tasks.size(); taskNum ++){
            final int START_FROM_ONE = 1;
            System.out.println(String.format("%d ) %s", taskNum + START_FROM_ONE, tasks.get(taskNum).getName()));
        }
    }

    public String chooseTaskAndReturnId(List<Task> tasks){
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose task number:");
        int taskNum = Integer.parseInt(sc.nextLine());
        int INDEX_ZERO = 1;
        Task task = tasks.get(taskNum - INDEX_ZERO);
        return task.getId();
    }

    public void presentTask(List<String> description, List<String> values) {
        for (int i=0; i<values.size(); i++){
            System.out.print(String.format(" %s %s ", description.get(i), values.get(i)));
        }
        System.out.println();
    }

    public Map<String, Object> presentApprovalQuestion() {
        System.out.println("Do you like to approve this request? (y-approve, n-reject)");
        boolean approve = new Scanner(System.in).nextLine().toLowerCase().equals("y");
        return Collections.singletonMap("approve", approve);
    }

    public void presentFinishedHistoricTasks(List<HistoricActivityInstance> finishedTasks) {
        finishedTasks.forEach(ft -> System.out.println(String.format("Task with id: %s and name: %s finished after %d ms.", ft.getActivityId(), ft.getActivityName(), ft.getDurationInMillis())));
    }
}

import java.time.LocalDate;

public class ConstructionTask {
private int taskId;
private int stageId;
private String taskName;
private String description;

private int progressPercentage;
private String status;
private LocalDate startDate;
private LocalDate endDate;

public ConstructionTask(int taskId, int stageId, String taskName, String description, int progressPercentage, String status, LocalDate startDate, LocalDate endDate) {
this.taskId = taskId;
this.stageId = stageId;
this.taskName = taskName;
this.description = description;
this.progressPercentage = progressPercentage;
this.status = status;
this.startDate = startDate;
this.endDate = endDate;

}
//Hacker level thinking done by me
public void UpdateProgress(int newProgress)
{
if(newProgress >0 && newProgress<=100)
{
this.progressPercentage=newProgress;
}
}


//Getter
//These methods will allow other classes to read the task ID, stage ID, name, and description.
public int getTaskId() {
    return taskId;
}

public int getStageId() {
    return stageId;
}

public String getTaskName() {
    return taskName;
}

public String getDescription() {
    return description;
}
public int getProgressPercentage() {
    return progressPercentage;
}

public String getStatus() {
    return status;
}

public LocalDate getStartDate() {
    return startDate;
}

public LocalDate getEndDate() {
    return endDate;
}

//stter methods
//Supervisor ko task progress or percentage krne dega functions 
public void setProgressPercentage(int progressPercentage) {
    this.progressPercentage = progressPercentage;
}

public void setStatus(String status) {
    this.status = status;
}
//These let the supervisor update a task completion percentage nd status.
//add validation later so progress not below 0 or above 100

}

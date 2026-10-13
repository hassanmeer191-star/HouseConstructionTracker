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


}

import java.time.LocalDateTime;
public class ProgressLog {
private int logId; //unique identify history record
private int taskId; //to identify which task was updated
private int oldProgress; //progress before the update
private int newProgress; //after update
private LocalDateTime updateDate; //to store the date and time of the update
private String remarks; //note abt the work ex concrete pour hgya etc 
private int supervisorId; //to identify which supervisor made the update

public ProgressLog(int logId, int taskId,int oldProgress, int newProgress,LocalDateTime updateDate, String remarks,int supervisorId) 
{
this.logId = logId;
this.taskId = taskId;
this.oldProgress = oldProgress;
this.newProgress = newProgress;
this.updateDate = updateDate;
this.remarks = remarks;
this.supervisorId = supervisorId;
}
//getter These methods let other classes read the history record's ID, related task, and progress before and after the update
public int getLogId() {
    return logId;
}

public int getTaskId() {
    return taskId;
}

public int getOldProgress() {
    return oldProgress;
}

public int getNewProgress() {
    return newProgress;
}
public LocalDateTime getUpdateDate() {
    return updateDate;
}

public String getRemarks() {
    return remarks;
}

public int getSupervisorId() {
    return supervisorId;
}

}
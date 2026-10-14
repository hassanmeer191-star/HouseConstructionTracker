import java.math.BigDecimal;
public class Worker {
private int workerId;           // unique id of the worker
private int projectId;           // project where worker is assigned
private String workerName;       // name of the worker
private String workType;         // mason, electrician, plumber etc
private String phoneNumber;      // worker contact number
private BigDecimal dailyWage;    // daily payment of worker
private String status;           // Active or Inactive

public Worker(int workerId, int projectId, String workerName,String workType, String phoneNumber, BigDecimal dailyWage,String status)
{
this.workerId = workerId;
this.projectId = projectId;
this.workerName = workerName;
this.workType = workType;
this.phoneNumber = phoneNumber;

//setDailyWage(dailyWage);
//setStatus(status);
}
 // Getter methods
public int getWorkerId() {
return workerId;
}

public int getProjectId() {
return projectId;
}

public String getWorkerName() {
return workerName;
}

public String getWorkType() {
return workType;
}

public String getPhoneNumber() {
return phoneNumber;
}

public BigDecimal getDailyWage() {
return dailyWage;
}

public String getStatus() {
return status;
}

}

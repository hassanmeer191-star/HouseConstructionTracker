
import java.math.BigDecimal;
import java.time.LocalDate;

public class Project {

private int projectId;       // unique id store krega diffeent projects ki
private String projectName;  // stores project names
private String address;      // similarly address agya
private int homeownerId;     // pta chlega knsa homeowner ka konsa project h

private int supervisorId;    // used to identify supervisor
private BigDecimal totalBudget; // for storing total budget
private LocalDate startDate;    // construction strt
private LocalDate endDate;      // construction end kab hgy

public Project(int projectId, String projectName, String address,int homeownerId, int supervisorId, BigDecimal totalBudget,
LocalDate startDate, LocalDate endDate) 
{
this.projectId = projectId;
this.projectName = projectName;
this.address = address;
this.homeownerId = homeownerId;
this.supervisorId = supervisorId;
this.startDate = startDate;
this.endDate = endDate;

// Use the setter so the budget check also runs in the constructor
setTotalBudget(totalBudget);
}


// Adding setter methods
public void setProjectName(String projectName) {
this.projectName = projectName;
}

public void setAddress(String address) {
this.address = address;
}

// Check that budget is not negative
public void setTotalBudget(BigDecimal totalBudget) {
if (totalBudget != null&& totalBudget.compareTo(BigDecimal.ZERO) >= 0) 
{
this.totalBudget = totalBudget;
}
}

public void setEndDate(LocalDate endDate) {
this.endDate = endDate;
}



// Now adding getter methods
public int getProjectId() {
return projectId;
}

public String getProjectName() {
return projectName;
}

public String getAddress() {
return address;
}

public int getHomeownerId() {
return homeownerId;
}

public int getSupervisorId() {
return supervisorId;
}

public BigDecimal getTotalBudget() {
return totalBudget;
}

public LocalDate getStartDate() {
return startDate;
}

public LocalDate getEndDate() {
return endDate;
}
}

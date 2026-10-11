
import java.math.BigDecimal;
import java.time.LocalDate;

public class Project {

private int ProjectId;     // unique id store krega diffeent projects ki
private String ProjectName; // stores project names
private String Address;     //similarly address agya 
private int HomeOwnerId;    //pta chlega knsa homeowner ka konsa project h

private int supervisorId;   //used to identify supervisor
private BigDecimal totalBudget; //for storing total budget
private LocalDate startDate;    //construction strt
private LocalDate endDate;    //construction end kab hgy

public Project(int ProjectId, String ProjectName, String Address, int HomeOwnerId, int supervisorId, BigDecimal totalBudget, LocalDate startDate, LocalDate endDate) {
this.ProjectId = ProjectId;
this.ProjectName = ProjectName;
this.Address = Address;
this.HomeOwnerId = HomeOwnerId;
this.supervisorId = supervisorId;
this.totalBudget = totalBudget;
this.startDate = startDate;
this.endDate = endDate;
}

    public static void main(String[] args) {

    }
}

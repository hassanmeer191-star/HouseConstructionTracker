
import java.math.BigDecimal;
import java.time.LocalDate;

public class Worker {

    private int workerId;
    private int projectId;
    private String fullName;
    private String phone;
    private String jobType;
    private LocalDate joiningDate;
    private String wageType;
    private BigDecimal wageAmount;
    private String workerStatus;
    private String performanceRating;
    private String performanceNotes;
    private BigDecimal bonusAmount;

    public Worker(int projectId, String fullName, String phone,
                  String jobType, LocalDate joiningDate,
                  String wageType, BigDecimal wageAmount) {

        this.projectId = projectId;
        this.fullName = fullName;
        this.phone = phone;
        this.jobType = jobType;
        this.joiningDate = joiningDate;
        this.wageType = wageType;
        this.wageAmount = wageAmount;
        this.workerStatus = "Active";
        this.performanceRating = "Average";
        this.performanceNotes = "";
        this.bonusAmount = BigDecimal.ZERO;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getJobType() {
        return jobType;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public String getWageType() {
        return wageType;
    }

    public BigDecimal getWageAmount() {
        return wageAmount;
    }

    public String getWorkerStatus() {
        return workerStatus;
    }

    public void setWorkerStatus(String workerStatus) {
        this.workerStatus = workerStatus;
    }

    public String getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(String performanceRating) {
        this.performanceRating = performanceRating;
    }

    public String getPerformanceNotes() {
        return performanceNotes;
    }

    public void setPerformanceNotes(String performanceNotes) {
        this.performanceNotes = performanceNotes;
    }

    public BigDecimal getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(BigDecimal bonusAmount) {
        this.bonusAmount = bonusAmount;
    }
}
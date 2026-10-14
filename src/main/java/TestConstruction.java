//Testing to see if existing classes functionalities are working correctly
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestConstruction {
public static void main(String[] args) {

ConstructionTask task = new ConstructionTask(100, 10, "Brickwork", "Ground floor brickwork",20, "In Progress"
,LocalDate.now(), null
);

int oldProgress = task.getProgressPercentage();
boolean updated = task.updateProgress(50);

if (updated) {
ProgressLog log = new ProgressLog(
1, task.getTaskId(), oldProgress,
task.getProgressPercentage(), LocalDateTime.now(),
"Brickwork progress updated", 5
);

System.out.println(log.getProgressSummary());
}
}
}
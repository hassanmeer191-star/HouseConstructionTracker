
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProjectDAOTest {

public static void main(String[] args) {

ProjectDAO dao = new ProjectDAO();

try {
List<Map<String, Object>> projects =
dao.getAllProjects();

if (projects.isEmpty()) {
System.out.println("No projects found in database.");
}

for (Map<String, Object> project : projects) {
System.out.println("----------------------------");
System.out.println("Project ID: "
+ project.get("project_id"));
System.out.println("Project Name: "
+ project.get("project_name"));
System.out.println("Homeowner: "
+ project.get("homeowner_name"));
System.out.println("Location: "
+ project.get("location"));
System.out.println("Total Budget: "
+ project.get("total_budget"));
System.out.println("Amount Spent: "
+ project.get("amount_spent"));
System.out.println("Progress: "
+ project.get("progress_percent") + "%");
System.out.println("Status: "
+ project.get("status"));
}

} catch (SQLException e) {
System.out.println("Database test failed.");
System.out.println(e.getMessage());
}
}
}


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProjectDAO {

public List<Map<String, Object>> getAllProjects()
throws SQLException {

List<Map<String, Object>> projects = new ArrayList<>();

String sql = """
SELECT p.project_id, p.project_name, p.location,
   p.homeowner_id, u.full_name AS homeowner_name,
   p.supervisor_id, p.total_budget, p.amount_spent,
   p.progress_percent, p.status,
   p.created_at, p.updated_at
FROM projects p
JOIN users u ON p.homeowner_id = u.user_id
ORDER BY p.project_id
""";

try (Connection con = DatabaseConnection.getConnection();
PreparedStatement ps = con.prepareStatement(sql);
ResultSet rs = ps.executeQuery()) {

while (rs.next()) {
Map<String, Object> project = new LinkedHashMap<>();

project.put("project_id", rs.getInt("project_id"));
project.put("project_name", rs.getString("project_name"));
project.put("location", rs.getString("location"));
project.put("homeowner_id", rs.getInt("homeowner_id"));
project.put("homeowner_name", rs.getString("homeowner_name"));
project.put("supervisor_id", rs.getInt("supervisor_id"));
project.put("total_budget", rs.getBigDecimal("total_budget"));
project.put("amount_spent", rs.getBigDecimal("amount_spent"));
project.put("progress_percent", rs.getInt("progress_percent"));
project.put("status", rs.getString("status"));
project.put("created_at", rs.getTimestamp("created_at"));
project.put("updated_at", rs.getTimestamp("updated_at"));

projects.add(project);
}
}

return projects;
}

public int getHomeownerIdByName(String homeownerName)
throws SQLException {

String sql = """
SELECT user_id
FROM users
WHERE LOWER(TRIM(full_name)) = LOWER(TRIM(?))
AND role = 'Homeowner'
AND is_active = TRUE
""";

try (Connection con = DatabaseConnection.getConnection();
PreparedStatement ps = con.prepareStatement(sql)) {

ps.setString(1, homeownerName);

try (ResultSet rs = ps.executeQuery()) {
if (!rs.next()) {
    throw new SQLException(
            "No active homeowner account found with that name.");
}

int homeownerId = rs.getInt("user_id");

if (rs.next()) {
    throw new SQLException(
            "More than one homeowner has that name. "
            + "Please use a unique homeowner account.");
}

return homeownerId;
}
}
}

public int addProject(
String name,
String location,
int homeownerId,
int supervisorId,
BigDecimal budget) throws SQLException {

String sql = """
INSERT INTO projects
(project_name, location, homeowner_id, supervisor_id,
 total_budget, amount_spent, progress_percent, status)
VALUES (?, ?, ?, ?, ?, 0, 0, 'Planned')
RETURNING project_id
""";

try (Connection con = DatabaseConnection.getConnection()) {
con.setAutoCommit(false);

try {
int projectId;

try (PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setString(1, name);
    ps.setString(2, location);
    ps.setInt(3, homeownerId);
    ps.setInt(4, supervisorId);
    ps.setBigDecimal(5, budget);

    try (ResultSet rs = ps.executeQuery()) {
        rs.next();
        projectId = rs.getInt("project_id");
    }
}

addHistory(
        con, projectId, supervisorId,
        "Created", "Project created: " + name);

con.commit();
return projectId;

} catch (SQLException ex) {
con.rollback();
throw ex;
} finally {
con.setAutoCommit(true);
}
}
}

public void updateProject(
int projectId,
String name,
String location,
int homeownerId,
BigDecimal budget,
BigDecimal spent,
int progress,
String status,
int supervisorId) throws SQLException {

String sql = """
UPDATE projects
SET project_name = ?,
location = ?,
homeowner_id = ?,
total_budget = ?,
amount_spent = ?,
progress_percent = ?,
status = ?,
updated_at = CURRENT_TIMESTAMP
WHERE project_id = ? AND supervisor_id = ?
""";

try (Connection con = DatabaseConnection.getConnection()) {
con.setAutoCommit(false);

try {
int rowsUpdated;

try (PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setString(1, name);
    ps.setString(2, location);
    ps.setInt(3, homeownerId);
    ps.setBigDecimal(4, budget);
    ps.setBigDecimal(5, spent);
    ps.setInt(6, progress);
    ps.setString(7, status);
    ps.setInt(8, projectId);
    ps.setInt(9, supervisorId);

    rowsUpdated = ps.executeUpdate();
}

if (rowsUpdated == 0) {
    throw new SQLException(
            "Project not found or not assigned to this supervisor.");
}

addHistory(
        con, projectId, supervisorId,
        "Updated", "Project details updated: " + name);

con.commit();

} catch (SQLException ex) {
con.rollback();
throw ex;
} finally {
con.setAutoCommit(true);
}
}
}

private void addHistory(
Connection con,
int projectId,
int actorId,
String action,
String details) throws SQLException {

String sql = """
INSERT INTO project_history
(project_id, actor_user_id, action, details)
VALUES (?, ?, ?, ?)
""";

try (PreparedStatement ps = con.prepareStatement(sql)) {
ps.setInt(1, projectId);
ps.setInt(2, actorId);
ps.setString(3, action);
ps.setString(4, details);
ps.executeUpdate();
}
}
}

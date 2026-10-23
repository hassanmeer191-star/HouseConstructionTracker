
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAO {

    // Add a new worker to the database
    public int addWorker(Worker worker) throws SQLException {

        String sql = """
            INSERT INTO workers
            (project_id, full_name, phone, job_type, joining_date,
             wage_type, wage_amount, worker_status,
             performance_rating, performance_notes, bonus_amount)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING worker_id
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, worker.getProjectId());
            ps.setString(2, worker.getFullName());
            ps.setString(3, worker.getPhone());
            ps.setString(4, worker.getJobType());
            ps.setDate(5, Date.valueOf(worker.getJoiningDate()));
            ps.setString(6, worker.getWageType());
            ps.setBigDecimal(7, worker.getWageAmount());
            ps.setString(8, worker.getWorkerStatus());
            ps.setString(9, worker.getPerformanceRating());
            ps.setString(10, worker.getPerformanceNotes());
            ps.setBigDecimal(11, worker.getBonusAmount());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("worker_id");
            }
        }
    }

    // Load workers belonging to one project
    public List<Worker> getWorkersByProject(int projectId)
            throws SQLException {

        List<Worker> workers = new ArrayList<>();

        String sql = """
            SELECT *
            FROM workers
            WHERE project_id = ?
            ORDER BY worker_id
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, projectId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Worker worker = new Worker(
                        rs.getInt("project_id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("job_type"),
                        rs.getDate("joining_date").toLocalDate(),
                        rs.getString("wage_type"),
                        rs.getBigDecimal("wage_amount")
                    );

                    worker.setWorkerId(rs.getInt("worker_id"));
                    worker.setWorkerStatus(
                        rs.getString("worker_status"));
                    worker.setPerformanceRating(
                        rs.getString("performance_rating"));
                    worker.setPerformanceNotes(
                        rs.getString("performance_notes"));
                    worker.setBonusAmount(
                        rs.getBigDecimal("bonus_amount"));

                    workers.add(worker);
                }
            }
        }

        return workers;
    }

    // Update a worker's details
    public void updateWorker(
            int workerId,
            int projectId,
            String fullName,
            String phone,
            String jobType,
            LocalDate joiningDate,
            String wageType,
            BigDecimal wageAmount,
            String workerStatus,
            String performanceRating,
            String performanceNotes,
            BigDecimal bonusAmount) throws SQLException {

        String sql = """
            UPDATE workers
            SET full_name = ?,
                phone = ?,
                job_type = ?,
                joining_date = ?,
                wage_type = ?,
                wage_amount = ?,
                worker_status = ?,
                performance_rating = ?,
                performance_notes = ?,
                bonus_amount = ?,
                updated_at = CURRENT_TIMESTAMP
            WHERE worker_id = ? AND project_id = ?
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, phone);
            ps.setString(3, jobType);
            ps.setDate(4, Date.valueOf(joiningDate));
            ps.setString(5, wageType);
            ps.setBigDecimal(6, wageAmount);
            ps.setString(7, workerStatus);
            ps.setString(8, performanceRating);
            ps.setString(9, performanceNotes);
            ps.setBigDecimal(10, bonusAmount);
            ps.setInt(11, workerId);
            ps.setInt(12, projectId);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException(
                    "Worker not found in the selected project.");
            }
        }
    }
}

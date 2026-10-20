import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SitePhotoDAO {

public boolean savePhoto(SitePhoto photo) {
String sql = "INSERT INTO site_photos "
+ "(project_id, uploaded_by, photo_title, "
+ "construction_stage, description, image_url, "
+ "original_file_name) "
+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

try (Connection connection = DatabaseConnection.getConnection();
PreparedStatement statement = connection.prepareStatement(sql)) {

statement.setInt(1, photo.getProjectId());
statement.setInt(2, photo.getUploadedBy());
statement.setString(3, photo.getPhotoTitle());
statement.setString(4, photo.getConstructionStage());
statement.setString(5, photo.getDescription());
statement.setString(6, photo.getImageUrl());
statement.setString(7, photo.getOriginalFileName());

return statement.executeUpdate() > 0;

} catch (SQLException e) {
System.out.println("Error saving site photo: " + e.getMessage());
return false;
}
}

public ArrayList<SitePhoto> getPhotosByProject(int projectId) {
ArrayList<SitePhoto> photos = new ArrayList<>();

String sql = "SELECT * FROM site_photos "
+ "WHERE project_id = ? ORDER BY posted_at DESC";

try (Connection connection = DatabaseConnection.getConnection();
PreparedStatement statement = connection.prepareStatement(sql)) {

statement.setInt(1, projectId);

try (ResultSet result = statement.executeQuery()) {
while (result.next()) {
SitePhoto photo = new SitePhoto(
result.getInt("project_id"),
result.getInt("uploaded_by"),
result.getString("photo_title"),
result.getString("construction_stage"),
result.getString("description"),
result.getString("image_url"),
result.getString("original_file_name")
);

photo.setPhotoId(result.getInt("photo_id"));

Timestamp timestamp = result.getTimestamp("posted_at");
if (timestamp != null) {
photo.setPostedAt(timestamp.toLocalDateTime());
}

photos.add(photo);
}
}

} catch (SQLException e) {
System.out.println("Error loading site photos: " + e.getMessage());
}

return photos;
}
}

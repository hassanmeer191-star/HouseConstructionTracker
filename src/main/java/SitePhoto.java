import java.time.LocalDateTime;

public class SitePhoto {
private int photoId;
private int projectId;
private int uploadedBy;
private String photoTitle;
private String constructionStage;
private String description;
private String imageUrl;
private String originalFileName;
private LocalDateTime postedAt;

public SitePhoto(int projectId, int uploadedBy,
String photoTitle, String constructionStage,
String description, String imageUrl,
String originalFileName) {

this.projectId = projectId;
this.uploadedBy = uploadedBy;
this.photoTitle = photoTitle;
this.constructionStage = constructionStage;
this.description = description;
this.imageUrl = imageUrl;
this.originalFileName = originalFileName;
}

public int getPhotoId() {
return photoId;
}

public int getProjectId() {
return projectId;
}

public int getUploadedBy() {
return uploadedBy;
}

public String getPhotoTitle() {
return photoTitle;
}

public String getConstructionStage() {
return constructionStage;
}

public String getDescription() {
return description;
}

public String getImageUrl() {
return imageUrl;
}

public String getOriginalFileName() {
return originalFileName;
}

public LocalDateTime getPostedAt() {
return postedAt;
}

public void setPhotoId(int photoId) {
this.photoId = photoId;
}

public void setPostedAt(LocalDateTime postedAt) {
this.postedAt = postedAt;
}
}

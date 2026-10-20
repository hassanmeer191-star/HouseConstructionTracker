
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SitePhotosPanel extends JPanel {

private final int projectId;
private final int supervisorId;
private final SitePhotoDAO photoDAO = new SitePhotoDAO();

private JTextField titleField;
private JTextField stageField;
private JTextArea descriptionArea;
private JLabel imageLabel;
private File selectedImage;
private JPanel galleryPanel;

public SitePhotosPanel(int projectId, int supervisorId) {
this.projectId = projectId;
this.supervisorId = supervisorId;

setLayout(new BorderLayout(15, 15));
setBackground(new Color(245, 247, 251));
setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

JLabel heading = new JLabel("Site Photos");
heading.setFont(new Font("Arial", Font.BOLD, 25));

JLabel subtitle = new JLabel(
"View previous updates or upload new construction photos.");
subtitle.setForeground(Color.GRAY);

JPanel header = new JPanel();
header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
header.setBackground(getBackground());
header.add(heading);
header.add(Box.createVerticalStrut(5));
header.add(subtitle);

add(header, BorderLayout.NORTH);

JPanel uploadPanel = createUploadPanel();

galleryPanel = new JPanel(new GridLayout(0, 2, 15, 15));
galleryPanel.setBackground(getBackground());
galleryPanel.setBorder(
BorderFactory.createEmptyBorder(5, 5, 5, 5));

JScrollPane galleryScroll = new JScrollPane(galleryPanel);
galleryScroll.setBorder(BorderFactory.createTitledBorder(
"Previously Uploaded Photos"));
galleryScroll.getViewport().setBackground(getBackground());

JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
centerPanel.setBackground(getBackground());
centerPanel.add(uploadPanel, BorderLayout.NORTH);
centerPanel.add(galleryScroll, BorderLayout.CENTER);

add(centerPanel, BorderLayout.CENTER);

loadPhotos();
}

private JPanel createUploadPanel() {
JPanel panel = new JPanel(new GridBagLayout());
panel.setBackground(Color.WHITE);
panel.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(new Color(220, 225, 235)),
BorderFactory.createEmptyBorder(12, 12, 12, 12)));

GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(5, 5, 5, 5);
gbc.fill = GridBagConstraints.HORIZONTAL;
gbc.weightx = 1;

titleField = new JTextField(20);
stageField = new JTextField(20);
descriptionArea = new JTextArea(3, 20);
descriptionArea.setLineWrap(true);
descriptionArea.setWrapStyleWord(true);

imageLabel = new JLabel("No image selected");

JButton chooseButton = new JButton("Choose Image");
JButton saveButton = new JButton("Upload Photo");

chooseButton.addActionListener(e -> chooseImage());
saveButton.addActionListener(e -> savePhoto());

int row = 0;

addFormRow(panel, gbc, row++, "Photo Title:", titleField);
addFormRow(panel, gbc, row++, "Construction Stage:", stageField);
addFormRow(panel, gbc, row++, "Description:",
new JScrollPane(descriptionArea));

JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
imagePanel.setBackground(Color.WHITE);
imagePanel.add(chooseButton);
imagePanel.add(imageLabel);

addFormRow(panel, gbc, row++, "Image:", imagePanel);

gbc.gridx = 1;
gbc.gridy = row;
gbc.anchor = GridBagConstraints.WEST;
panel.add(saveButton, gbc);

return panel;
}

private void addFormRow(JPanel panel, GridBagConstraints gbc,
    int row, String label, Component field) {
gbc.gridx = 0;
gbc.gridy = row;
gbc.weightx = 0;
panel.add(new JLabel(label), gbc);

gbc.gridx = 1;
gbc.weightx = 1;
panel.add(field, gbc);
}

private void chooseImage() {
JFileChooser chooser = new JFileChooser();
chooser.setDialogTitle("Select Construction Photo");
chooser.setFileFilter(new FileNameExtensionFilter(
"Image Files (JPG, JPEG, PNG)",
"jpg", "jpeg", "png"));

if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
File file = chooser.getSelectedFile();

try {
if (javax.imageio.ImageIO.read(file) == null) {
JOptionPane.showMessageDialog(this,
    "Please select a valid JPG or PNG image.");
return;
}
} catch (IOException ex) {
JOptionPane.showMessageDialog(this,
"Could not read the selected image.");
return;
}

selectedImage = file;
imageLabel.setText(file.getName());
}
}

private void savePhoto() {
String title = titleField.getText().trim();
String stage = stageField.getText().trim();
String description = descriptionArea.getText().trim();

if (title.isEmpty() || stage.isEmpty()) {
JOptionPane.showMessageDialog(this,
"Please enter the photo title and construction stage.");
return;
}

if (selectedImage == null) {
JOptionPane.showMessageDialog(this,
"Please choose an image first.");
return;
}

Path savedPath = null;

try {
Path folder = Paths.get("site-photos");
Files.createDirectories(folder);

String fileName = System.currentTimeMillis()
+ "_" + selectedImage.getName();

savedPath = folder.resolve(fileName);

Files.copy(selectedImage.toPath(), savedPath,
StandardCopyOption.REPLACE_EXISTING);

SitePhoto photo = new SitePhoto(
projectId,
supervisorId,
title,
stage,
description,
savedPath.toString(),
selectedImage.getName());

if (photoDAO.savePhoto(photo)) {
JOptionPane.showMessageDialog(this,
"Photo uploaded successfully.");

titleField.setText("");
stageField.setText("");
descriptionArea.setText("");
selectedImage = null;
imageLabel.setText("No image selected");

loadPhotos();
} else {
Files.deleteIfExists(savedPath);
JOptionPane.showMessageDialog(this,
"Could not save photo details to the database.");
}

} catch (IOException ex) {
JOptionPane.showMessageDialog(this,
"Could not save image: " + ex.getMessage());
}
}

private void loadPhotos() {
galleryPanel.removeAll();

List<SitePhoto> photos = photoDAO.getPhotosByProject(projectId);

if (photos.isEmpty()) {
JLabel emptyLabel = new JLabel(
"No photos uploaded for this project yet.");
emptyLabel.setForeground(Color.GRAY);
galleryPanel.setLayout(new BorderLayout());
galleryPanel.add(emptyLabel, BorderLayout.NORTH);
} else {
galleryPanel.setLayout(new GridLayout(0, 2, 15, 15));

for (SitePhoto photo : photos) {
galleryPanel.add(createPhotoCard(photo));
}
}

galleryPanel.revalidate();
galleryPanel.repaint();
}

private JPanel createPhotoCard(SitePhoto photo) {
JPanel card = new JPanel(new BorderLayout(8, 8));
card.setBackground(Color.WHITE);
card.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(new Color(220, 225, 235)),
BorderFactory.createEmptyBorder(10, 10, 10, 10)));

JLabel imageView = new JLabel();
imageView.setHorizontalAlignment(SwingConstants.CENTER);
imageView.setPreferredSize(new Dimension(250, 180));

File imageFile = new File(photo.getImageUrl());

if (imageFile.exists()) {
ImageIcon original = new ImageIcon(photo.getImageUrl());
Image scaled = original.getImage().getScaledInstance(
250, 180, Image.SCALE_SMOOTH);
imageView.setIcon(new ImageIcon(scaled));
} else {
imageView.setText("Image file not found");
}

JLabel title = new JLabel(photo.getPhotoTitle());
title.setFont(new Font("Arial", Font.BOLD, 16));

JTextArea details = new JTextArea();
details.setEditable(false);
details.setLineWrap(true);
details.setWrapStyleWord(true);
details.setOpaque(false);

String postedDate = "Date unavailable";
if (photo.getPostedAt() != null) {
postedDate = photo.getPostedAt().format(
DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
}

details.setText(
"Stage: " + photo.getConstructionStage()
+ "\nDescription: "
+ (photo.getDescription() == null
|| photo.getDescription().isBlank()
? "No description" : photo.getDescription())
+ "\nUploaded: " + postedDate);

JPanel textPanel = new JPanel(new BorderLayout(5, 5));
textPanel.setBackground(Color.WHITE);
textPanel.add(title, BorderLayout.NORTH);
textPanel.add(details, BorderLayout.CENTER);

card.add(imageView, BorderLayout.NORTH);
card.add(textPanel, BorderLayout.CENTER);

return card;
}
}

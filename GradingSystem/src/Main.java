import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

class StudentGradeEvaluator extends JFrame {
    private JTextField nameField;
    private Map<String, JTextField> courseFields;
    private JTextArea reportArea;

    private final String[] courseList = {
            "Critical Thinking and Logical Reasoning",
            "Circuit Theory",
            "Technical Communication Skills",
            "Cyber Law",
            "Engineering Drawings",
            "Basic Electronics (Semiconductors)",
            "Engineering Mathematics",
            "Programming in C++ (Arduino)"
    };

    public StudentGradeEvaluator() {
        // Setup Window Properties
        setTitle("Student Report Card & Grade Evaluation System");
        setSize(650, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel for Student Name
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        topPanel.add(new JLabel("Student Full Name:"));
        nameField = new JTextField(25);
        topPanel.add(nameField);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel for Courses & Score Inputs (Scrollable)
        JPanel coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));
        coursesPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        courseFields = new LinkedHashMap<>();
        for (String course : courseList) {
            JPanel rowPanel = new JPanel(new BorderLayout(10, 5));
            JLabel label = new JLabel(course);
            label.setPreferredSize(dims(280, 25));
            JTextField field = new JTextField(5);
            field.setMaximumSize(dims(100, 25));

            rowPanel.add(label, BorderLayout.WEST);
            rowPanel.add(field, BorderLayout.EAST);
            coursesPanel.add(rowPanel);
            coursesPanel.add(Box.createRigidArea(new Dimension(0, 5)));

            courseFields.put(course, field);
        }

        JScrollPane inputScroll = new JScrollPane(coursesPanel);
        add(inputScroll, BorderLayout.CENTER);

        // Bottom Panel for Buttons and Report Display
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // Button Panel holding Generate and Save buttons side-by-side
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton generateButton = new JButton("Generate Report Card");
        generateButton.setFont(new Font("Arial", Font.BOLD, 13));

        JButton saveButton = new JButton("Save as Text File");
        saveButton.setFont(new Font("Arial", Font.BOLD, 13));

        buttonPanel.add(generateButton);
        buttonPanel.add(saveButton);
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        reportArea = new JTextArea(11, 50);
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane reportScroll = new JScrollPane(reportArea);
        bottomPanel.add(reportScroll, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        // Generate Button Action Event
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReportCard();
            }
        });

        // Save Button Action Event
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveReportToFile();
            }
        });
    }

    private Dimension dims(int w, int h) {
        return new Dimension(w, h);
    }

    private void generateReportCard() {
        String studentName = nameField.getText().trim();
        if (studentName.isEmpty()) {
            reportArea.setText("ERROR: Please enter the student's full name before generating the report card.");
            return;
        }

        StringBuilder report = new StringBuilder();
        report.append("=======================================================================\n");
        report.append("                   GHANA COMMUNICATION TECHNOLOGY UNIVERSITY\n");
        report.append("                        OFFICIAL ACADEMIC REPORT CARD\n");
        report.append("=======================================================================\n");
        report.append(" Student Name : ").append(studentName).append("\n");
        report.append(" Date Issued  : ").append(LocalDate.now()).append("\n");
        report.append("-----------------------------------------------------------------------\n");
        report.append(String.format(" %-40s | %-6s | %-3s | %-15s\n", "Course Title", "Score", "Gr", "Remark"));
        report.append("-----------------------------------------------------------------------\n");

        double totalScore = 0.0;
        int courseCount = courseList.length;

        for (String course : courseList) {
            JTextField field = courseFields.get(course);
            String valText = field.getText().trim();

            if (valText.isEmpty()) {
                reportArea.setText("ERROR: Missing score for course: " + course);
                return;
            }

            try {
                double score = Double.parseDouble(valText);
                if (score < 0.0 || score > 100.0) {
                    reportArea.setText("ERROR: Score for '" + course + "' must be between 0 and 100.");
                    return;
                }

                totalScore += score;
                String grade;
                String remark;

                if (score >= 70.0) {
                    grade = "A";
                    remark = "Pass (Excellent)";
                } else if (score >= 60.0) {
                    grade = "B";
                    remark = "Pass (Very Good)";
                } else if (score >= 50.0) {
                    grade = "C";
                    remark = "Pass (Credit)";
                } else if (score >= 45.0) {
                    grade = "D";
                    remark = "Pass (Pass)";
                } else {
                    grade = "F";
                    remark = "Fail";
                }

                String displayTitle = course.length() > 39 ? course.substring(0, 36) + "..." : course;
                report.append(String.format(" %-40s | %6.1f | %-3s | %-15s\n", displayTitle, score, grade, remark));

            } catch (NumberFormatException ex) {
                reportArea.setText("ERROR: Invalid numeric format entered for course: " + course);
                return;
            }
        }

        double cumulativeAverage = totalScore / courseCount;
        String overallStatus = cumulativeAverage >= 45.0 ? "PASS" : "FAIL";

        report.append("-----------------------------------------------------------------------\n");
        report.append(String.format(" Cumulative Average Score : %.2f%%\n", cumulativeAverage));
        report.append(String.format(" Overall Academic Status  : %s\n", overallStatus));
        report.append("=======================================================================\n");

        reportArea.setText(report.toString());
    }

    private void saveReportToFile() {
        String reportText = reportArea.getText();
        if (reportText.isEmpty() || reportText.startsWith("ERROR")) {
            JOptionPane.showMessageDialog(this,
                    "Please generate a valid report card first before saving.",
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report Card as Text File");
        fileChooser.setSelectedFile(new File("Academic_Report_Card.txt"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write(reportText);
                JOptionPane.showMessageDialog(this,
                        "Report successfully saved to:\n" + fileToSave.getAbsolutePath(),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "An error occurred while saving the file: " + ex.getMessage(),
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentGradeEvaluator().setVisible(true);
            }
        });
    }
}

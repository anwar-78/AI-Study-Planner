import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainGUI {

    static List<Subject> subjects = new ArrayList<>();
    static DefaultTableModel model;

    public static void main(String[] args) {

        JFrame frame = new JFrame("AI Study Planner");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JTextField nameField = new JTextField();
        JComboBox<String> diffBox = new JComboBox<>(new String[]{"Easy","Medium","Hard"});
        JTextField dateField = new JTextField("2026-05-01");

        JButton addBtn = new JButton("Add");
        JButton genBtn = new JButton("Generate Plan");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");

        panel.add(new JLabel("Subject"));
        panel.add(nameField);

        panel.add(new JLabel("Difficulty"));
        panel.add(diffBox);

        panel.add(new JLabel("Exam Date"));
        panel.add(dateField);

        panel.add(addBtn);
        panel.add(genBtn);
        panel.add(saveBtn);
        panel.add(loadBtn);

        model = new DefaultTableModel(new String[]{"Subject","Hours"},0);
        JTable table = new JTable(model);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add
        addBtn.addActionListener(e -> {
            subjects.add(new Subject(
                nameField.getText(),
                diffBox.getSelectedItem().toString(),
                LocalDate.parse(dateField.getText())
            ));
            JOptionPane.showMessageDialog(frame,"Added");
        });

        // Generate
        genBtn.addActionListener(e -> {
            model.setRowCount(0);
            Map<String,Integer> plan = Planner.generatePlan(subjects);

            for (String s : plan.keySet()) {
                model.addRow(new Object[]{s, plan.get(s)});
            }
        });

        // Save
        saveBtn.addActionListener(e -> {
            FileManager.save(subjects);
            JOptionPane.showMessageDialog(frame,"Saved");
        });

        // Load
        loadBtn.addActionListener(e -> {
            subjects = FileManager.load();
            JOptionPane.showMessageDialog(frame,"Loaded");
        });

        // Reminder start
        Reminder.startReminder();

        frame.setVisible(true);
    }
}
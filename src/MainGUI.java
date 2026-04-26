import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;

public class MainGUI {

    static List<Subject> subjects = new ArrayList<>();
    static DefaultTableModel model;

    public static void main(String[] args) {

        JFrame frame = new JFrame("AI Study Planner");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 🔷 HEADER
        JLabel header = new JLabel("AI-Based Study Planner", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setOpaque(true);
        header.setBackground(new Color(40, 70, 130));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 60));
        frame.add(header, BorderLayout.NORTH);

        // 🔷 LEFT PANEL (FORM)
        // 🔷 LEFT PANEL (FORM - FIXED & CLEAN)
JPanel left = new JPanel();
left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
left.setBorder(BorderFactory.createTitledBorder("Add Subject"));
left.setPreferredSize(new Dimension(260, 0));

// helper to make one row (label + field)
java.util.function.BiFunction<String, JComponent, JPanel> row = (labelText, comp) -> {
    JPanel r = new JPanel(new BorderLayout(8, 0));
    JLabel lab = new JLabel(labelText);
    lab.setPreferredSize(new Dimension(100, 30));
    comp.setPreferredSize(new Dimension(140, 30));
    r.add(lab, BorderLayout.WEST);
    r.add(comp, BorderLayout.CENTER);
    return r;
};

JTextField nameField = new JTextField();
JComboBox<String> diffBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
JTextField dateField = new JTextField("2026-05-01");

// rows
left.add(Box.createVerticalStrut(10));
left.add(row.apply("Subject Name", nameField));
left.add(Box.createVerticalStrut(10));
left.add(row.apply("Difficulty", diffBox));
left.add(Box.createVerticalStrut(10));
left.add(row.apply("Exam Date", dateField));
left.add(Box.createVerticalStrut(20));

// button centered
JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
JButton addBtn = new JButton("Add Subject");
addBtn.setBackground(new Color(0, 102, 255));
addBtn.setForeground(Color.WHITE);
addBtn.setPreferredSize(new Dimension(150, 40));
btnWrap.add(addBtn);

left.add(btnWrap);
left.add(Box.createVerticalGlue());

frame.add(left, BorderLayout.WEST);

        // 🔷 CENTER TABLE
        model = new DefaultTableModel(new String[]{"Subject", "Hours"}, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔷 RIGHT PANEL
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(BorderFactory.createTitledBorder("Actions"));
        right.setPreferredSize(new Dimension(180, 0));

        JButton genBtn = new JButton("Generate Plan");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");

        genBtn.setBackground(new Color(0, 200, 0));
        saveBtn.setBackground(new Color(255, 153, 0));
        loadBtn.setBackground(new Color(200, 0, 200));

        genBtn.setForeground(Color.WHITE);
        saveBtn.setForeground(Color.WHITE);
        loadBtn.setForeground(Color.WHITE);

        genBtn.setMaximumSize(new Dimension(150, 50));
        saveBtn.setMaximumSize(new Dimension(150, 50));
        loadBtn.setMaximumSize(new Dimension(150, 50));

        right.add(genBtn);
        right.add(Box.createVerticalStrut(20));
        right.add(saveBtn);
        right.add(Box.createVerticalStrut(20));
        right.add(loadBtn);

        frame.add(right, BorderLayout.EAST);

        // 🔷 LOGIC

        addBtn.addActionListener(e -> {
            subjects.add(new Subject(
                    nameField.getText(),
                    diffBox.getSelectedItem().toString(),
                    LocalDate.parse(dateField.getText())
            ));
            JOptionPane.showMessageDialog(frame, "Added!");
        });

        genBtn.addActionListener(e -> {
            model.setRowCount(0);
            Map<String, Integer> plan = Planner.generatePlan(subjects);

            for (String s : plan.keySet()) {
                model.addRow(new Object[]{s, plan.get(s)});
            }
        });

        saveBtn.addActionListener(e -> {
            FileManager.save(subjects);
            JOptionPane.showMessageDialog(frame, "Saved!");
        });

        loadBtn.addActionListener(e -> {
            subjects = FileManager.load();
            JOptionPane.showMessageDialog(frame, "Loaded!");
        });

        Reminder.startReminder();

        frame.setVisible(true);
    }
}
import javax.swing.*;

public class Reminder {

    public static void startReminder() {
        Timer timer = new Timer(60000, e -> {
            JOptionPane.showMessageDialog(null, "⏰ Time to Study!");
        });

        timer.start();
    }
}
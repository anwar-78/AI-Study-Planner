import java.time.temporal.ChronoUnit;
import java.time.*;

public class Subject {
    String name;
    String difficulty;
    LocalDate examDate;

    public Subject(String name, String difficulty, LocalDate examDate) {
        this.name = name;
        this.difficulty = difficulty;
        this.examDate = examDate;
    }

    public int getDaysLeft() {
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), examDate);
    }
}
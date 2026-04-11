import java.util.*;

public class Planner {

    public static int getHours(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "hard": return 3;
            case "medium": return 2;
            case "easy": return 1;
            default: return 1;
        }
    }

    public static Map<String, Integer> generatePlan(List<Subject> subjects) {
        Map<String, Integer> plan = new HashMap<>();

        for (Subject s : subjects) {
            int hours = getHours(s.difficulty);

            int days = s.getDaysLeft();

            if (days <= 3) hours += 2;
            else if (days <= 7) hours += 1;

            plan.put(s.name, hours);
        }

        return plan;
    }
}
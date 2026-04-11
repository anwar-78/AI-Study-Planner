import java.io.*;
import java.util.*;
import java.time.*;

public class FileManager {

    public static void save(List<Subject> subjects) {
        try (PrintWriter pw = new PrintWriter("data.txt")) {
            for (Subject s : subjects) {
                pw.println(s.name + "," + s.difficulty + "," + s.examDate);
            }
        } catch (Exception e) {
            System.out.println("Error saving");
        }
    }

    public static List<Subject> load() {
        List<Subject> list = new ArrayList<>();

        try (Scanner sc = new Scanner(new File("data.txt"))) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                list.add(new Subject(p[0], p[1], LocalDate.parse(p[2])));
            }
        } catch (Exception e) {
            System.out.println("No file found");
        }

        return list;
    }
}
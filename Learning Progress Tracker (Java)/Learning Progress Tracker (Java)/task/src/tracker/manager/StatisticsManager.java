package tracker.manager;

import tracker.model.Student;

import java.util.*;

public class StatisticsManager {

    private StudentManager studentManager;
    private HashMap<Integer, Student> students;
    private Map<String, Integer> courseEnrollment;
    private Map<String, Integer> courseActivity;
    private Map<String, Integer> courseSubmissions;
    private Map<String, Integer> coursePoints;

    public StatisticsManager(StudentManager studentManager) {
        this.studentManager = studentManager;
        init();
    }

    private void init() {
        this.students = studentManager.getStudents();
        this.courseEnrollment = studentManager.getCourseEnrollment();
        this.courseActivity = studentManager.getCourseActivity();
        this.courseSubmissions = studentManager.getCourseSubmissions();
        this.coursePoints = studentManager.getCoursePoints();
    }
    public void displayStatistics(){
        findPopularity();
        findActivity();
        findDifficulty();

    }

    public void findPopularity() {
        boolean hasEnrollment = courseEnrollment.values().stream().anyMatch(value -> value > 0);
        if (!hasEnrollment) {
            System.out.println("Most Popular: n/a");
            System.out.println("Least Popular: n/a");
            return;
        }
        List<String> mostPopular = new ArrayList<>();
        List<String> leastPopular = new ArrayList<>();
        int maxEnrollment = 0;
        int minEnrollment = Integer.MAX_VALUE;

        for (int count : courseEnrollment.values()) {
            if (count > 0 && count > maxEnrollment) {
                maxEnrollment = count;
            }
            if (count < minEnrollment) {
                minEnrollment = count;
            }
        }

        for (Map.Entry<String, Integer> entry : courseEnrollment.entrySet()) {
            if (entry.getValue() == maxEnrollment) {
                mostPopular.add(entry.getKey());
            }
            if (entry.getValue() == minEnrollment && minEnrollment != maxEnrollment) {
                leastPopular.add(entry.getKey());
            }
        }

        System.out.println("Most Popular: " + (mostPopular.isEmpty() ? "n/a" : String.join(", ", mostPopular)));
        System.out.println("Least Popular: " + (leastPopular.isEmpty() ? "n/a" : String.join(", ", leastPopular)));
    }

    public void findActivity(){
        boolean hasActivity = courseActivity.values().stream().anyMatch(value -> value > 0);
        if (!hasActivity) {
            System.out.println("Highest Activity: n/a");
            System.out.println("Lowest Activity: n/a");
            return;
        }

        List<String> mostActive = new ArrayList<>();
        List<String> leastActive = new ArrayList<>();
        int maxActivity = 0;
        int minActivity = Integer.MAX_VALUE;

        for (int count : courseActivity.values()) {
            if (count > maxActivity) {
                maxActivity = count;
            }
            if (count > 0 && count < minActivity) {
                minActivity = count;
            }
        }

        for (Map.Entry<String, Integer> entry : courseActivity.entrySet()) {
            if (entry.getValue() == maxActivity) {
                mostActive.add(entry.getKey());
            }
            if (entry.getValue() == minActivity && minActivity != maxActivity) {
                leastActive.add(entry.getKey());
            }
        }

        System.out.println("Highest Activity: " + (mostActive.isEmpty() ? "n/a" : String.join(", ", mostActive)));
        System.out.println("Lowest Activity: " + (leastActive.isEmpty() ? "n/a" : String.join(", ", leastActive)));
    }

    public void findDifficulty() {
        boolean hasSubmissions = courseSubmissions.values().stream().anyMatch(value -> value > 0);
        if (!hasSubmissions) {
            System.out.println("Easiest Course: n/a");
            System.out.println("Hardest Course: n/a");
            return;
        }

        double maxAverage = Double.MIN_VALUE;
        double minAverage = Double.MAX_VALUE;
        String easiestCourse = null;
        String hardestCourse = null;

        for (String course : courseSubmissions.keySet()) {
            if (courseSubmissions.get(course) == 0) {
                continue;
            }

            double average = (double) coursePoints.get(course) / courseSubmissions.get(course);

            if (average > maxAverage) {
                maxAverage = average;
                easiestCourse = course;
            }

            if (average < minAverage) {
                minAverage = average;
                hardestCourse = course;
            }
        }

        System.out.println("Easiest Course: " + (easiestCourse != null ? easiestCourse : "n/a"));
        System.out.println("Hardest Course: " + (hardestCourse != null ? hardestCourse : "n/a"));
    }

    public void displayTopStudents(String course) {
        System.out.println(course);
        System.out.println("id\tpoints\tcompleted");

        List<Map.Entry<Integer, Integer>> studentsWithPoints = new ArrayList<>();

        for (Map.Entry<Integer, Student> entry : students.entrySet()) {
            int id = entry.getKey();
            Student student = entry.getValue();
            int points = student.getCredits().getOrDefault(course, 0);
            if (points > 0) {
                studentsWithPoints.add(new AbstractMap.SimpleEntry<>(id, points));
            }
        }

        studentsWithPoints.sort((a, b) -> {
            int pointCompare = b.getValue().compareTo(a.getValue());
            if (pointCompare == 0) {
                return a.getKey().compareTo(b.getKey());
            }
            return pointCompare;
        });

        int totalPoints = switch (course) {
            case "Java" -> 600;
            case "DSA" -> 400;
            case "Databases" -> 480;
            case "Spring" -> 550;
            default -> 0;
        };

        for (Map.Entry<Integer, Integer> entry : studentsWithPoints) {
            int id = entry.getKey();
            int points = entry.getValue();
            double completion = 100.0 * points / totalPoints;
            System.out.printf("%d\t%d\t%.1f%%%n", id, points, completion);
        }
    }

}

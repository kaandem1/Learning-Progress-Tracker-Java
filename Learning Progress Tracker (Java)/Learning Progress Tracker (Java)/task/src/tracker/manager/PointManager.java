package tracker.manager;

import tracker.model.Student;

import java.util.HashMap;
import java.util.Map;

public class PointManager {
    private StudentManager studentManager;
    private HashMap<Integer, Student> students;
    private Map<String, Integer> courseEnrollment;
    private Map<String, Integer> courseActivity;
    private Map<String, Integer> courseSubmissions;
    private Map<String, Integer> coursePoints;

    public PointManager(StudentManager studentManager) {
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
    public void processPoints(String points) {
        String[] parts = points.trim().split("\\s+");
        if (parts.length != 5) {
            System.out.println("Incorrect points format.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            System.out.println("No student is found for id=" + parts[0] + ".");
            return;
        }

        if (!students.containsKey(id)) {
            System.out.printf("No student is found for id=%d.\n", id);
            return;
        }

        Student student = students.get(id);
        Map<String, Integer> currentCredits = student.getCredits();

        String[] courses = {"Java", "DSA", "Databases", "Spring"};

        for (int i = 0; i < courses.length; i++) {
            int score;
            try {
                score = Integer.parseInt(parts[i + 1]);
                if(score > 0)
                {
                    int currentActivity = courseActivity.get(courses[i]);
                    courseActivity.put(courses[i],currentActivity + 1);
                    if(student.getCredits().get(courses[i]) == 0){
                        int currentEnrolment = courseEnrollment.get(courses[i]);
                        courseEnrollment.put(courses[i],currentEnrolment+1);
                    }
                    int currentSubmissions = courseSubmissions.get(courses[i]);
                    courseSubmissions.put(courses[i],currentSubmissions + 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect points format.");
                return;
            }

            if (score < 0) {
                System.out.println("Incorrect points format.");
                return;
            }

            int totalScore = currentCredits.get(courses[i]) + score;
            currentCredits.put(courses[i], totalScore);
            coursePoints.put(courses[i], coursePoints.getOrDefault(courses[i], 0) + score);
        }

        student.setCredits(currentCredits);
        System.out.println("Points updated.");
    }
}

package tracker.manager;

import tracker.model.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputHandler {
    private StudentManager studentManager;
    private PointManager pointManager;
    private StatisticsManager statisticsManager;
    private NotificationManager notificationManager;

    private HashMap<Integer, Student> students;
    private Map<String, Integer> courseEnrollment;
    private Map<String, Integer> courseActivity;
    private Map<String, Integer> courseSubmissions;
    private Map<String, Integer> coursePoints;

    public InputHandler() {
        studentManager = new StudentManager();
        pointManager = new PointManager(studentManager);
        statisticsManager = new StatisticsManager(studentManager);
        notificationManager = new NotificationManager(studentManager);

        students = studentManager.getStudents();
        courseEnrollment = studentManager.getCourseEnrollment();
        courseActivity = studentManager.getCourseActivity();
        courseSubmissions = studentManager.getCourseSubmissions();
        coursePoints = studentManager.getCoursePoints();

        initCourses();
    }
    public void start() {
        initCourses();
        System.out.println("Learning Progress Tracker");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String str = scanner.nextLine();
            if (str.equals("exit")) {
                System.out.println("Bye!");
                break;
            } else if (str.equals("add students")) {
                System.out.println("Enter student credentials or 'back' to return:");
                while (true) {
                    str = scanner.nextLine();
                    if (str.equals("back")) {
                        System.out.printf("Total %d students have been added.\n", studentManager.getStudentCount());
                        break;
                    }
                    studentManager.processCredentials(str);
                }
            } else if (str.equals("back")) {
                System.out.println("Enter 'exit' to exit the program.");
            } else if(str.equals("list")){
                if(students.isEmpty()){
                    System.out.println("No students found.");
                    break;
                }
                System.out.println("Students:");
                for (Integer id : students.keySet()) {
                    System.out.println(id);
                }
            } else if(str.equals("add points")){
                System.out.println("Enter an id and points or 'back' to return:");
                while (true){
                    str = scanner.nextLine();
                    if (str.equals("back")) {
                        break;
                    }
                    studentManager.processPoints(str);
                }
            } else if(str.equals("find")){
                System.out.println("Enter an id and points or 'back' to return:");
                while (true){
                    str = scanner.nextLine();
                    if (str.equals("back")) {
                        break;
                    }
                    studentManager.printDetails(str);
                }
            } else if(str.equals("statistics")){
                System.out.println("Type the name of a course to see details or 'back' to quit");
                statisticsManager.displayStatistics();
                while (true){
                    str = scanner.nextLine();
                    if (str.equals("back")) {
                        break;
                    }
                    if (str.equals("Java") || str.equals("DSA") || str.equals("Databases") || str.equals("Spring")) {
                        statisticsManager.displayTopStudents(str);
                    } else {
                        System.out.println("Unknown course.");
                    }
                }
            } else if (str.equals("notify")) {
                notificationManager.notifyFunc();
            } else if (str.trim().isEmpty()) {
                System.out.println("No input");
            } else {
                System.out.println("Unknown command!");
            }
        }
    }
    private void initCourses(){
        courseEnrollment.put("Java", 0);
        courseEnrollment.put("DSA", 0);
        courseEnrollment.put("Databases", 0);
        courseEnrollment.put("Spring", 0);

        courseActivity.put("Java", 0);
        courseActivity.put("DSA", 0);
        courseActivity.put("Databases", 0);
        courseActivity.put("Spring", 0);

        courseSubmissions.put("Java", 0);
        courseSubmissions.put("DSA", 0);
        courseSubmissions.put("Databases", 0);
        courseSubmissions.put("Spring", 0);

        coursePoints.put("Java", 0);
        coursePoints.put("DSA", 0);
        coursePoints.put("Databases", 0);
        coursePoints.put("Spring", 0);
    }

}

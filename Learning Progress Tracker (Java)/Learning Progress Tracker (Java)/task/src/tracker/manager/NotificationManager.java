package tracker.manager;

import tracker.model.Student;

import java.util.HashMap;
import java.util.Map;

public class NotificationManager {
    private StudentManager studentManager;
    private HashMap<Integer, Student> students;

    public NotificationManager(StudentManager studentManager) {
        this.studentManager = studentManager;
        this.students = studentManager.getStudents();
    }
    public void notifyFunc(){
        int java = 600;
        int dsa = 400;
        int db = 480;
        int spring =  550;
        int notifCount = 0;
        for (Map.Entry<Integer, Student> entry : students.entrySet()) {
            boolean notiflag = false;
            Student student = entry.getValue();
            Map<String, Integer> map = student.getCredits();
            Map<String, Boolean> notifMap = student.getNotifFlags();
            if(map.get("Java") >= java && !notifMap.get("Java")){
                buildNotification(student, "Java");
                notifMap.replace("Java", false, true);
                notiflag = true;
            }
            if(map.get("DSA") >= dsa && !notifMap.get("DSA")){
                buildNotification(student, "DSA");
                notifMap.replace("DSA", false, true);
                notiflag = true;
            }
            if(map.get("Databases") >= db && !notifMap.get("Databases")){
                buildNotification(student, "Databases");
                notifMap.replace("Databases", false, true);
                notiflag = true;
            }
            if(map.get("Spring") >= spring && !notifMap.get("Spring")){
                buildNotification(student, "Spring");
                notifMap.replace("Spring", false, true);
                notiflag = true;
            }
            if(notiflag){
                notifCount++;
            }
        }
        System.out.println("Total " + notifCount + " students have been notified.");
    }
    public void buildNotification(Student student, String courseName) {
        System.out.println("To: " + student.getEmail());
        System.out.println("Re: Your Learning Progress");
        System.out.println("Hello, " + student.getFirstName() + " " + student.getLastName() +
                "! You have accomplished our " + courseName + " course!");
    }
}

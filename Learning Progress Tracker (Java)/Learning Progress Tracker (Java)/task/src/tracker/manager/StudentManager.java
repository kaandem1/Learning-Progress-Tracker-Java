package tracker.manager;

import tracker.model.Student;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentManager {
    private int studentCount = 0;
    private int nextStudentID = 1;

    private HashMap<Integer, Student> students = new HashMap<>();
    private Set<String> existingEmails = new HashSet<>();
    Map<String, Integer> courseEnrollment = new HashMap<>();
    Map<String, Integer> courseActivity = new HashMap<>();
    Map<String, Integer> courseSubmissions = new HashMap<>();
    Map<String, Integer> coursePoints  = new HashMap<>();

    public Map<String, Integer> getCourseEnrollment() {
        return courseEnrollment;
    }

    public void setCourseEnrollment(Map<String, Integer> courseEnrollment) {
        this.courseEnrollment = courseEnrollment;
    }

    public Map<String, Integer> getCourseActivity() {
        return courseActivity;
    }

    public void setCourseActivity(Map<String, Integer> courseActivity) {
        this.courseActivity = courseActivity;
    }

    public Map<String, Integer> getCourseSubmissions() {
        return courseSubmissions;
    }

    public void setCourseSubmissions(Map<String, Integer> courseSubmissions) {
        this.courseSubmissions = courseSubmissions;
    }

    public Map<String, Integer> getCoursePoints() {
        return coursePoints;
    }

    public void setCoursePoints(Map<String, Integer> coursePoints) {
        this.coursePoints = coursePoints;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public int getNextStudentID() {
        return nextStudentID;
    }

    public void setNextStudentID(int nextStudentID) {
        this.nextStudentID = nextStudentID;
    }

    public HashMap<Integer, Student> getStudents() {
        return students;
    }

    public void setStudents(HashMap<Integer, Student> students) {
        this.students = students;
    }

    public Set<String> getExistingEmails() {
        return existingEmails;
    }

    public void setExistingEmails(Set<String> existingEmails) {
        this.existingEmails = existingEmails;
    }

    public boolean isValidEmail(String email) {
        email = email.trim();
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9]{1,}$");
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public boolean isNewEmail(String email){
        if(existingEmails.contains(email)){
            return false;
        }
        else return true;
    }

    public boolean isValidName(String name) {

        String[] parts = name.split("\\s+");
        for (String part : parts) {
            if (!part.matches("^[A-Za-z]{1}[A-Za-z'-]{0,}[A-Za-z]{1}$")) {
                return false;
            }
        }

        return name.matches("^[A-Za-z]+([\\s'-][A-Za-z]+)*$");
    }
    public void processCredentials(String credentials) {
        String[] parts = credentials.trim().split("\\s+");
        if (parts.length < 3) {
            System.out.println("Incorrect credentials.");
            return;
        }

        String firstName = parts[0];
        String lastName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1));
        String email = parts[parts.length - 1];

        if(!isNewEmail(email)){
            System.out.println("This email is already taken.");
            return;
        }

        if (!isValidName(firstName)) {
            System.out.println("Incorrect first name.");
        } else if (!isValidName(lastName)) {
            System.out.println("Incorrect last name.");
        } else if (!isValidEmail(email)) {
            System.out.println("Incorrect email.");
        } else {
            Student student = new Student(nextStudentID, firstName, lastName, email);
            students.put(nextStudentID, student);
            existingEmails.add(email);
            System.out.println("The student has been added.");
            studentCount++;
            nextStudentID++;
        }
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


    public void printDetails(String input){
        int id = Integer.parseInt(input);
        if (!students.containsKey(id)) {
            System.out.printf("No student is found for id=%d.\n", id);
            return;
        }
        Student student = students.get(id);
        Map<String, Integer> map = student.getCredits();
        System.out.printf(id + " " + "points: Java=%d; DSA=%d; Databases=%d; Spring=%d",
                map.get("Java"),
                map.get("DSA"),
                map.get("Databases"),
                map.get("Spring"));
    }
}

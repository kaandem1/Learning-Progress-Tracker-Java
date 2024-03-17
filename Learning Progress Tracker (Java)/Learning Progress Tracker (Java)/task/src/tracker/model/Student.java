package tracker.model;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private int id =0;
    private String firstName;
    private String lastName;
    private String email;
    private Map<String, Integer> credits = new HashMap<>();
    private Map<String, Boolean> notifFlags = new HashMap<>();



    public Student(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        initializeCredits();
        initializenotifFlags();
    }

    private void initializeCredits() {
        credits.put("Java", 0);
        credits.put("DSA", 0);
        credits.put("Databases", 0);
        credits.put("Spring", 0);
    }

    private void initializenotifFlags() {
        notifFlags.put("Java", false);
        notifFlags.put("DSA", false);
        notifFlags.put("Databases", false);
        notifFlags.put("Spring", false);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Integer> getCredits() {
        return credits;
    }

    public void setCredits(Map<String, Integer> credits) {
        this.credits = credits;
    }

    public Map<String, Boolean> getNotifFlags() {
        return notifFlags;
    }

    public void setNotifFlags(Map<String, Boolean> notifFlags) {
        this.notifFlags = notifFlags;
    }
}

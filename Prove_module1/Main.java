import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Stretch Challenge: Abstract base class demonstrating inheritance
abstract class User {
    private final int id; // Immutable variable
    private String name;  // Mutable variable

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String getRole();
}

// Student class extending User
class Student extends User {
    public static final double PASSING_GRADE = 60.0; // Immutable constant
    private final List<Double> grades = new ArrayList<>(); // Mutable collection

    public Student(int id, String name) {
        super(id, name);
    }

    // Add grade with conditional validation
    public void addGrade(double grade) {
        if (grade >= 0.0 && grade <= 100.0) {
            grades.add(grade);
        } else {
            System.out.println("Error: Grade must be between 0.0 and 100.0.");
        }
    }

    // Calculate average using a loop and arithmetic expression
    public double calculateAverage() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (double g : grades) { // Loop
            sum += g;             // Arithmetic addition
        }
        return sum / grades.size(); // Arithmetic division
    }

    // Conditional branches to determine letter grade
    public String getLetterGrade() {
        double avg = calculateAverage();
        if (avg >= 90.0) return "A";
        else if (avg >= 80.0) return "B";
        else if (avg >= 70.0) return "C";
        else if (avg >= 60.0) return "D";
        else return "F";
    }

    // Comparison expression for pass/fail evaluation
    public boolean isPassing() {
        return calculateAverage() >= PASSING_GRADE;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    // Display formatted student report
    public void displayReport() {
        System.out.printf("ID: %d | Name: %s | Grades: %s | Avg: %.2f | Grade: %s | Status: %s%n",
                getId(), getName(), grades, calculateAverage(), getLetterGrade(),
                isPassing() ? "PASS" : "FAIL");
    }
}

// Gradebook class managing student collections
class Gradebook {
    private final List<Student> students = new ArrayList<>();         // ArrayList collection
    private final Map<Integer, Student> studentMap = new HashMap<>();   // HashMap collection

    // Add student with uniqueness check
    public void addStudent(Student student) {
        if (!studentMap.containsKey(student.getId())) {
            students.add(student);
            studentMap.put(student.getId(), student);
        } else {
            System.out.println("Error: Student ID already exists.");
        }
    }

    public Student getStudent(int id) {
        return studentMap.get(id);
    }

    // Display all student records using a loop
    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        System.out.println("\n--- Student Reports ---");
        for (Student s : students) {
            s.displayReport();
        }
    }
}

// Main class providing interactive console interface
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Gradebook gradebook = new Gradebook();

        // Sample initial data for testing
        Student s1 = new Student(101, "Alice");
        s1.addGrade(85.5);
        s1.addGrade(92.0);
        gradebook.addStudent(s1);

        Student s2 = new Student(102, "Bob");
        s2.addGrade(55.0);
        s2.addGrade(62.5);
        gradebook.addStudent(s2);

        boolean running = true;
        // While loop for continuous menu interaction
        while (running) {
            displayMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": // Register new student
                    try {
                        System.out.print("Enter ID: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine().trim();
                        gradebook.addStudent(new Student(id, name));
                        System.out.println("Student registered successfully.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid ID.");
                    }
                    break;
                case "2": // Add grade to student
                    try {
                        System.out.print("Enter Student ID: ");
                        int targetId = Integer.parseInt(scanner.nextLine().trim());
                        Student s = gradebook.getStudent(targetId);
                        if (s != null) {
                            System.out.print("Enter Grade (0-100): ");
                            double grade = Double.parseDouble(scanner.nextLine().trim());
                            s.addGrade(grade);
                            System.out.println("Grade added successfully.");
                        } else {
                            System.out.println("Error: Student not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid input number.");
                    }
                    break;
                case "3": // Display all reports
                    gradebook.displayAll();
                    break;
                case "4": // Exit
                    System.out.println("Exiting program. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-4.");
            }
        }
        scanner.close();
    }

    // Method to display menu options
    private static void displayMenu() {
        System.out.println("\n=== Student Gradebook Menu ===");
        System.out.println("1. Add Student");
        System.out.println("2. Add Grade");
        System.out.println("3. Display All Reports");
        System.out.println("4. Exit");
    }
}

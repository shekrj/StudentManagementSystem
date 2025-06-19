import java.util.*;

// Base User Class
abstract class User {
    protected String name;
    protected int age;
    protected String email;

    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
}

// Admin Class
class Admin extends User {
    private List<Course> courses;
    private List<Student> students;

    public Admin(String name, int age, String email) {
        super(name, age, email);
        courses = new ArrayList<>();
        students = new ArrayList<>();
    }

    public void addCourse(String courseName) {
        courses.add(new Course(courseName));
        System.out.println("Course added: " + courseName);
    }

    public void addSubjectToCourse(String courseName, String subjectName) {
        for (Course course : courses) {
            if (course.getName().equalsIgnoreCase(courseName)) {
                course.addSubject(subjectName);
                System.out.println("Subject added to " + courseName);
                return;
            }
        }
        System.out.println("Course not found!");
    }

    public void viewStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void viewExamResults() {
        for (Student student : students) {
            student.printExamResult();
        }
    }

    public void registerStudent(Student student) {
        students.add(student);
    }

    public List<Course> getCourses() {
        return courses;
    }
}

// Student Class
class Student extends User {
    private Course enrolledCourse;
    private List<String> selectedSubjects;
    private int score;
    private boolean examTaken;

    public Student(String name, int age, String email) {
        super(name, age, email);
        selectedSubjects = new ArrayList<>();
        score = 0;
        examTaken = false;
    }

    public void viewCourses(List<Course> courses) {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            System.out.println("- " + course.getName());
        }
    }

    public void selectCourse(List<Course> courses, String courseName) {
        for (Course course : courses) {
            if (course.getName().equalsIgnoreCase(courseName)) {
                enrolledCourse = course;
                System.out.println("Enrolled in: " + courseName);
                return;
            }
        }
        System.out.println("Course not found!");
    }

    public void chooseSubjects(List<String> subjectNames) {
        if (enrolledCourse == null) {
            System.out.println("Enroll in a course first.");
            return;
        }
        for (String subject : subjectNames) {
            if (enrolledCourse.getSubjects().contains(subject)) {
                selectedSubjects.add(subject);
            }
        }
        System.out.println("Selected Subjects: " + selectedSubjects);
    }

    public void takeExam() {
        Scanner scanner = new Scanner(System.in);
        score = 0;
        String[][] questions = {
            {"What is Java?", "Programming Language", "Snake", "Car", "Food", "1"},
            {"Which company developed Java?", "Microsoft", "Sun Microsystems", "Apple", "Google", "2"},
            {"Which is not a Java keyword?", "class", "if", "for", "then", "4"},
            {"What is JVM?", "Java Virtual Machine", "Just Very Mad", "Java Valuable Method", "None", "1"},
            {"Which method is entry point in Java app?", "start()", "run()", "main()", "init()", "3"}
        };

        for (String[] q : questions) {
            System.out.println(q[0]);
            System.out.println("1. " + q[1]);
            System.out.println("2. " + q[2]);
            System.out.println("3. " + q[3]);
            System.out.println("4. " + q[4]);
            System.out.print("Your answer: ");
            int ans = scanner.nextInt();
            if (ans == Integer.parseInt(q[5])) {
                score++;
            }
        }
        examTaken = true;
    }

    public void printExamResult() {
        if (!examTaken) {
            System.out.println(name + " has not taken the exam yet.");
            return;
        }
        System.out.println(name + "'s Score: " + score + "/5 => " + (score >= 3 ? "Pass" : "Fail"));
    }

    public String toString() {
        return name + " | Age: " + age + " | Email: " + email;
    }
}

// Course Class
class Course {
    private String name;
    private List<String> subjects;

    public Course(String name) {
        this.name = name;
        this.subjects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addSubject(String subjectName) {
        subjects.add(subjectName);
    }

    public List<String> getSubjects() {
        return subjects;
    }
}

// Main Driver Class
public class StudentManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("Admin", 30, "admin@example.com");

        while (true) {
            System.out.println("\n1. Admin Login\n2. Student Login\n3. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    while (true) {
                        System.out.println("\nAdmin Menu:\n1. Add Course\n2. Add Subject\n3. View Students\n4. View Results\n5. Back");
                        int op = scanner.nextInt();
                        scanner.nextLine();
                        if (op == 1) {
                            System.out.print("Course name: ");
                            admin.addCourse(scanner.nextLine());
                        } else if (op == 2) {
                            System.out.print("Course name: ");
                            String course = scanner.nextLine();
                            System.out.print("Subject name: ");
                            String subject = scanner.nextLine();
                            admin.addSubjectToCourse(course, subject);
                        } else if (op == 3) {
                            admin.viewStudents();
                        } else if (op == 4) {
                            admin.viewExamResults();
                        } else break;
                    }
                    break;

                case 2:
                    scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    Student student = new Student(name, age, email);
                    admin.registerStudent(student);

                    while (true) {
                        System.out.println("\nStudent Menu:\n1. View Courses\n2. Select Course\n3. Choose Subjects\n4. Take Exam\n5. View Result\n6. Back");
                        int op = scanner.nextInt();
                        scanner.nextLine();
                        if (op == 1) {
                            student.viewCourses(admin.getCourses());
                        } else if (op == 2) {
                            System.out.print("Enter course name: ");
                            student.selectCourse(admin.getCourses(), scanner.nextLine());
                        } else if (op == 3) {
                            System.out.println("Enter subjects (comma separated): ");
                            String[] subs = scanner.nextLine().split(",");
                            List<String> subList = new ArrayList<>();
                            for (String s : subs) subList.add(s.trim());
                            student.chooseSubjects(subList);
                        } else if (op == 4) {
                            student.takeExam();
                        } else if (op == 5) {
                            student.printExamResult();
                        } else break;
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return;
            }
        }
    }
}

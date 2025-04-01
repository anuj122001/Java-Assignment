import java.io.*;
import java.util.*;

public class SerializationTest {

    public static class Address implements Serializable {
        private String city;
        private String state;
        private int pinCode;
        private String country;

        // Constructor
        public Address(String city, String state, int pinCode, String country) {
            this.city = city;
            this.state = state;
            this.pinCode = pinCode;
            this.country = country;
        }

        // Getters and Setters (if needed)
        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getPinCode() {
            return pinCode;
        }

        public void setPinCode(int pinCode) {
            this.pinCode = pinCode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public static class Student implements Serializable {
        private static final long serialVersionUID = 1L; // For backward compatibility

        private String firstName;
        private Date dateOfBirth;
        private Address address;

        public Student(String firstName, String dateOfBirth, Address address) {
            this.firstName = firstName;
            try {
                this.dateOfBirth = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
            } catch (Exception e) {
                this.dateOfBirth = null;
            }
            this.address = address;
        }

        // Constructor (for the new format with Date type)
        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject(); // Perform default serialization
            // Manually handle writing Date as a String
            out.writeObject(new java.text.SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth));
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject(); // Perform default deserialization
            // Manually handle reading Date as a String
            String dateString = (String) in.readObject();
            try {
                this.dateOfBirth = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            } catch (Exception e) {
                this.dateOfBirth = null;
            }
        }

        // Getters and Setters (if needed)
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public Date getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

    public static void main(String[] args) {

        String fileName = "students.ser";

        Address address1 = new Address("New York", "NY", 10001, "USA");
        Address address2 = new Address("Los Angeles", "CA", 90001, "USA");
        Address address3 = new Address("London", "England", 10002, "UK");
        Address address4 = new Address("Toronto", "Ontario", 20001, "Canada");

        Student student1 = new Student("John", "1990-05-15", address1);
        Student student2 = new Student("Mary", "1988-11-22", address2);
        Student student3 = new Student("Peter", "1995-07-30", address3);
        Student student4 = new Student("Susan", "2000-01-10", address4);

        List<Student> students = Arrays.asList(student1, student2, student3, student4);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);
            System.out.println("Serialization successful: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deserializationTest(String[] args) {
        // Ensure that args are provided
        if (args.length == 0) {
            System.err.println("Error: No file name provided for deserialization.");
            System.exit(1);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(args[0]))) {
            List<Student> students = (List<Student>) ois.readObject();
            for (Student student : students) {
                System.out.println("First Name: " + student.getFirstName());
                System.out.println("Date of Birth: " + student.getDateOfBirth());
                System.out.println("Address: " + student.getAddress().getCity() + ", " +
                        student.getAddress().getState() + " " + student.getAddress().getPinCode() +
                        ", " + student.getAddress().getCountry());
                System.out.println();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

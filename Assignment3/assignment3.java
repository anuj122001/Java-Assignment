package Assignment3;

import java.io.*;
import java.util.*;

public class assignment3 {
    private static final String DATA_FILE = "employees.txt";
    private static List<Worker> workersList = new ArrayList<>();

    public static void main(String[] args) {
        loadWorkersFromFile();
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n1. Add Worker");
            System.out.println("2. Remove Worker");
            System.out.println("3. Find Worker");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addWorker(scanner);
                    break;
                case 2:
                    removeWorker(scanner);
                    break;
                case 3:
                    findWorkers(scanner);
                    break;
                case 4:
                    saveWorkersToFile();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid selection. Try again.");
            }
        }
    }

    private static void addWorker(Scanner scanner) {
        System.out.print("Worker ID: ");
        int workerId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Email: ");
        String mail = scanner.nextLine();
        System.out.print("Years Old: ");
        int yearsOld = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Birth Date (YYYY-MM-DD): ");
        String birthDate = scanner.nextLine();

        Worker worker = new Worker(workerId, fullName, mail, yearsOld, birthDate);
        workersList.add(worker);
        saveWorkersToFile();
        System.out.println("Worker added successfully.");
    }

    private static void removeWorker(Scanner scanner) {
        System.out.print("Enter Worker ID to remove: ");
        int workerId = scanner.nextInt();
        scanner.nextLine();

        boolean removed = workersList.removeIf(worker -> worker.getWorkerId() == workerId);
        if (removed) {
            saveWorkersToFile();
            System.out.println("Worker removed successfully.");
        } else {
            System.out.println("Worker ID not found.");
        }
    }

    private static void findWorkers(Scanner scanner) {
        System.out.print("Enter search term (name/email): ");
        String keyword = scanner.nextLine().toLowerCase();
        System.out.print("Sort by (name/age/dob): ");
        String sortField = scanner.nextLine().toLowerCase();
        System.out.print("Sort order (asc/desc): ");
        String order = scanner.nextLine().toLowerCase();

        List<Worker> matches = new ArrayList<>();
        for (Worker worker : workersList) {
            if (worker.getFullName().toLowerCase().contains(keyword)
                    || worker.getMail().toLowerCase().contains(keyword)) {
                matches.add(worker);
            }
        }

        Comparator<Worker> comparator;
        switch (sortField) {
            case "name":
                comparator = Comparator.comparing(Worker::getFullName);
                break;
            case "age":
                comparator = Comparator.comparingInt(Worker::getYearsOld);
                break;
            case "dob":
                comparator = Comparator.comparing(Worker::getBirthDate);
                break;
            default:
                System.out.println("Invalid sorting field.");
                return;
        }

        if ("desc".equals(order)) {
            comparator = comparator.reversed();
        }

        matches.sort(comparator);
        if (matches.isEmpty()) {
            System.out.println("No workers found.");
        } else {
            System.out.println("\nSearch Results:");
            for (Worker worker : matches) {
                System.out.println(worker);
            }
        }
    }

    private static void loadWorkersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(",");
                if (info.length == 5) {
                    int workerId = Integer.parseInt(info[0]);
                    String fullName = info[1];
                    String mail = info[2];
                    int yearsOld = Integer.parseInt(info[3]);
                    String birthDate = info[4];
                    workersList.add(new Worker(workerId, fullName, mail, yearsOld, birthDate));
                }
            }
        } catch (IOException e) {
            System.out.println("No previous worker records found.");
        }
    }

    private static void saveWorkersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Worker worker : workersList) {
                writer.write(worker.toCsv());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving worker data.");
        }
    }
}

class Worker {
    private int workerId;
    private String fullName;
    private String mail;
    private int yearsOld;
    private String birthDate;

    public Worker(int workerId, String fullName, String mail, int yearsOld, String birthDate) {
        this.workerId = workerId;
        this.fullName = fullName;
        this.mail = mail;
        this.yearsOld = yearsOld;
        this.birthDate = birthDate;
    }

    public int getWorkerId() {
        return workerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMail() {
        return mail;
    }

    public int getYearsOld() {
        return yearsOld;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String toCsv() {
        return workerId + "," + fullName + "," + mail + "," + yearsOld + "," + birthDate;
    }

    @Override
    public String toString() {
        return "ID: " + workerId + ", Name: " + fullName + ", Email: " + mail + ", Age: " + yearsOld + ", DOB: "
                + birthDate;
    }
}
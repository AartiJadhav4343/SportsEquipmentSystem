package app;

import exception.*;	
import model.*;
import service.*;
import util.OverdueChecker;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        EquipmentService equipmentService = new EquipmentService();
        MemberService memberService = new MemberService();
        IssueService issueService = new IssueService(equipmentService, memberService);

        // sample data
        equipmentService.addEquipment(new Equipment(1, "Cricket Bat", "Cricket", 3));
        equipmentService.addEquipment(new Equipment(2, "Football", "Football", 2));
        equipmentService.addEquipment(new Equipment(3, "Badminton Racket", "Badminton", 4));
        memberService.registerMember(new Student(100, "Aarti"));
        memberService.registerMember(new Staff(201, "Mr. Peter"));

        OverdueChecker checker = new OverdueChecker(issueService);
        checker.start();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1:
                        addEquipment(equipmentService);
                        break;
                    case 2:
                        equipmentService.removeEquipment(readInt("Equipment ID to remove: "));
                        System.out.println("Equipment removed.");
                        break;
                    case 3:
                        searchEquipment(equipmentService);
                        break;
                    case 4:
                        registerMember(memberService);
                        break;
                    case 5:
                        IssueRecord rec = issueService.issueEquipment(
                                readInt("Member ID: "), readInt("Equipment ID: "));
                        System.out.println("Issued! " + rec);
                        break;
                    case 6:
                        long fine = issueService.returnEquipment(readInt("Record ID: "));
                        System.out.println("Returned successfully. Fine: Rs. " + fine);
                        break;
                    case 7:
                        printList(equipmentService.getAvailableEquipment(), "No equipment available.");
                        break;
                    case 8:
                        printList(issueService.getIssuedRecords(), "No items currently issued.");
                        break;
                    case 9:
                        printList(issueService.getMemberHistory(readInt("Member ID: ")),
                                "No history found.");
                        break;
                    case 10:
                        printList(equipmentService.getAllEquipment(), "No equipment in system.");
                        break;
                    case 11:
                        addStudent(memberService);
                        break;
                    case 12:
                        printList(memberService.getAllMembers(), "No members registered.");
                        break;
                    case 13:
                        printList(memberService.getStudents(), "No students registered.");
                        break;
                    case 14:
                        printList(memberService.getStaff(), "No staff registered.");
                        break;
                    case 0:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (MemberNotFoundException | EquipmentNotFoundException
                     | EquipmentNotAvailableException | IssueLimitExceededException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        checker.stopChecker();
    }

    private static void printMenu() {
        System.out.println("\n===== SPORTS EQUIPMENT MANAGEMENT =====");
        System.out.println("1. Add equipment");
        System.out.println("2. Remove equipment");
        System.out.println("3. Search equipment");
        System.out.println("4. Register member (Student/Staff)");
        System.out.println("5. Issue equipment");
        System.out.println("6. Return equipment");
        System.out.println("7. Show available equipment");
        System.out.println("8. Show issued items");
        System.out.println("9. Member history");
        System.out.println("10. Show all equipment");
        System.out.println("11. Add student");
        System.out.println("12. Show all members");
        System.out.println("13. Show student list");
        System.out.println("14. Show staff list");
        System.out.println("0. Exit");
    }

    private static void addEquipment(EquipmentService service) {
        int id = readInt("ID: ");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Category: ");
        String category = sc.nextLine();
        int qty = readInt("Quantity: ");
        service.addEquipment(new Equipment(id, name, category, qty));
        System.out.println("Equipment added.");
    }

    private static void searchEquipment(EquipmentService service) {
        System.out.print("Name keyword: ");
        String keyword = sc.nextLine();
        printList(service.searchByName(keyword), "No match found.");
    }

    private static void registerMember(MemberService service) {
        int id = readInt("Member ID: ");
        System.out.print("Name: ");
        String name = sc.nextLine();
        int type = readInt("Type (1 = Student, 2 = Staff): ");
        if (type == 1) {
            service.registerMember(new Student(id, name));
        } else if (type == 2) {
            service.registerMember(new Staff(id, name));
        } else {
            System.out.println("Invalid type.");
            return;
        }
        System.out.println("Member registered.");
    }

    private static void addStudent(MemberService service) {
        int id = readInt("Student ID: ");
        System.out.print("Student Name: ");
        String name = sc.nextLine();
        service.registerMember(new Student(id, name));
        System.out.println("Student added successfully: " + id + " | " + name);
    }

    private static <T> void printList(List<T> list, String emptyMsg) {
        if (list.isEmpty()) {
            System.out.println(emptyMsg);
            return;
        }
        for (T item : list) {
            System.out.println(item);
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    public MainMenu() {
    }

    private Scanner scanner = new Scanner(System.in);
    private static HotelResource hotelResource = HotelResource.getInstance();
    private static AdminResource adminResource = AdminResource.getInstance();

    public void displayMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("-----------------------\n" +
                    "Welcome to the Hotel Reservation Application\n" +
                    "-------------------------------");
            System.out.println("Main Menu:");
            System.out.println("-------------------------------");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.print("Please enter your choice: \n");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.nextLine(); // Clear the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    findAndReserveRoom();
                    break;
                case 2:
                    seeMyReservations();
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.displayMenu();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Thank you for using the application!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

        }
    }

    private void findAndReserveRoom() {
        String bookAnswer;
        String accountAnswer;

        boolean validDate = false;
        while (!validDate) {
            try {
                System.out.println("Please enter the check-in date (yyyy-MM-dd): ");
                scanner.nextLine();
                String checkInDateString = scanner.nextLine();

                System.out.println("Please enter the check-out date (yyyy-MM-dd): ");
                String checkOutDateString = scanner.nextLine();

                final String PATTERN = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(PATTERN);
                LocalDate checkInDate = LocalDate.parse(checkInDateString, dateFormatter);
                LocalDate checkOutDate = LocalDate.parse(checkOutDateString, dateFormatter);

                Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
                validDate = true;

                if (availableRooms.isEmpty()) {
                    System.out.println("No available rooms for the selected dates.");
                } else {
                    System.out.println("Available rooms:");
                    for (IRoom room : availableRooms) {
                        System.out.println(room);
                        System.out.println("-------------");
                    }
                    do {
                        System.out.println("Would you like to book a room? y/n");
                        bookAnswer = scanner.next().toLowerCase().trim();
                        if (bookAnswer.equals("y")) {
                            do {
                                System.out.println("Do you have an account with us? y/n");
                                accountAnswer = scanner.next().toLowerCase().trim();
                                if (accountAnswer.equals("y")) {
                                    scanner.nextLine();
                                    System.out.println("Enter Email format: name@domain.com");
                                    String email = scanner.next();
                                    Customer customer = hotelResource.getCustomer(email);

                                    System.out.println("Please enter the room number to reserve: ");
                                    String roomNumber = scanner.next();
//                                    availableRooms.forEach(System.out::println);

                                    IRoom selectedRoom = hotelResource.getRoom(roomNumber);
                                    if (selectedRoom == null) {
                                        System.out.println("Invalid room number.");
                                    } else {
                                        hotelResource.bookARoom(email, selectedRoom, checkInDate, checkOutDate);
                                        System.out.println("Reservation successful.");
                                    }
                                } else if (accountAnswer.equals("n")) {
                                    System.out.println("You have to create an account first!");
                                    createAccount();
                                } else {
                                    System.out.println("Invalid input!");
                                }
                            } while (!accountAnswer.equals("y") && !accountAnswer.equals("n"));
                        } else if (bookAnswer.equals("n")) {
                            displayMenu();
                        } else {
                            System.out.println("Invalid input!");
                        }
                    } while (!bookAnswer.equals("y") && !bookAnswer.equals("n"));

                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please check:\n" +
                        "1. enter the date in the format yyyy-MM-dd.\n" +
                        "2. check-out date should be greater than check-in date.\n" +
                        "3. check-in date should not be earlier than today.");
            }
        }

    }

    private void seeMyReservations() {
        scanner.nextLine(); // 另起一行
        System.out.println("Please enter your email: ");
        String email = scanner.nextLine();

        Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
        if (reservations.isEmpty()) {
            System.out.println("No reservations found for the provided email.");
        } else {
            System.out.println("Your reservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    private void createAccount() {
        scanner.nextLine(); // 空一行
        System.out.println("Please enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your last name: ");
        String lastName = scanner.nextLine();
        Boolean isvalid = false;
        while (!isvalid){
            try {
                System.out.println("Please enter your email: ");
                String email = scanner.nextLine();
                hotelResource.createACustomer(email, firstName, lastName);
                System.out.println("Account created successfully.");
                isvalid = true;
            } catch (IllegalArgumentException e){
                System.out.println("Invalid email format");
            }
        }

    }

//    public static void main(String[] args) {
//        MainMenu mainMenu = new MainMenu();
//        mainMenu.displayMenu();
//    }
}

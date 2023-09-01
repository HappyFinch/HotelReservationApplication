import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.*;

public class AdminMenu {
    private Scanner scanner = new Scanner(System.in);

    public AdminMenu() {
    }

    private static AdminResource adminResource = AdminResource.getInstance();

    public void displayMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("-------------------\n" +
                    "Admin Menu:");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
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
                case 1 -> seeAllCustomers();
                case 2 -> seeAllRooms();
                case 3 -> seeAllReservations();
                case 4 -> addRoom();
                case 5 -> {
                    exit = true;
                    System.out.println("Returning to Main Menu.");
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.displayMenu();
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customer yet");
        } else {
            for (Customer c : customers) {
                System.out.println(c);
            }
        }
    }

    private void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms yet");
        } else {
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        }
    }

    private void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    private void addRoom() {
        boolean numberFlag = false;
        boolean priceFlag = false;
        boolean typeFlag = false;
        boolean ynFlag = false;

        Double roomPrice;
        String roomNumber;
        int roomNumInt;

//        System.out.println("Enter room number:");
//        String roomNumber = scanner.nextLine();
//        scanner.nextLine();

        while (!numberFlag){
            System.out.println("Enter room number:");
            try{
                roomNumInt = scanner.nextInt();
                numberFlag = true;
                roomNumber = String.valueOf(roomNumInt);

            } catch (InputMismatchException e){
                System.out.println("invalid option!");
                scanner.nextLine();
                continue;
            }
            while (!priceFlag){
                System.out.println("Enter price per night:");
//            Double roomPrice;
                try{
                    roomPrice = scanner.nextDouble();
                    priceFlag = true;
                } catch (InputMismatchException e){
                    System.out.println("invalid option!");
                    scanner.nextLine();
                    continue;
                }
                while (!typeFlag){
                    try {
                        System.out.println("Enter room type: 1 - Single bed, 2 - Double bed");
                        int roomtypeint = scanner.nextInt();
                        RoomType roomType= null;
                        if(roomtypeint==1){
                            roomType = RoomType.SINGLE;
                        }else if(roomtypeint== 2){
                            roomType = RoomType.DOUBLE;
                        }else {
                            throw new InputMismatchException();
                        }
                        typeFlag = true;
                        IRoom room = new Room(roomNumber, roomPrice, roomType);
                        List<IRoom> rooms = new ArrayList<>();
                        rooms.add(room);
                        adminResource.addRoom(rooms);

                    } catch (InputMismatchException e){
                        System.out.println("invalid option!");
                        scanner.nextLine();
                        continue;
                    }

                    while (!ynFlag){
                        System.out.println("Would you like to add another room? y/n");
                        scanner.nextLine();
                        String ynAnswer = scanner.nextLine().toLowerCase().trim();
                        if (ynAnswer.equals("y")){
                            addRoom();
                            ynFlag = true;
                        }else if (ynAnswer.equals("n")){
                            ynFlag = true;
                            displayMenu();
                        }else{
                            System.out.println("invalid input!");
                        }
                    }
                }
            }
    }}

//    public static void main(String[] args) {
//        AdminMenu adminMenu = new AdminMenu();
//        adminMenu.displayMenu();
//    }
}

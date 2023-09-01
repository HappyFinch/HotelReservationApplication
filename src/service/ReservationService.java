package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.time.LocalDate;
import java.util.*;

public class ReservationService {
    private static ReservationService instance = null;
    private Map<String, IRoom> rooms = new HashMap();
    // 因为ReservationService是单例模式，所以这里的Set<IRoom> roomSet加不加static都行，因为都只会生成一个set
    private List<Reservation> reservationList = new ArrayList<>();
    /*
     * 选择使用 `ArrayList` 还是 `LinkedList` 取决于你的应用需求和预期的操作。
     * 在这种情况下，选择使用 `ArrayList` 似乎更合适，因为 `ArrayList` 对于顺序访问和随机访问的性能要优于 `LinkedList`。
     ** ArrayList：**
     * 内部是一个基于数组的数据结构，它可以更快地进行随机访问（通过索引），因为它可以直接根据索引计算元素的位置。但是，在插入和删除元素时可能需要移动大量元素，
     * 因为需要保持连续的内存空间。
     ** LinkedList：** 内部是一个基于链表的数据结构，它在插入和删除元素时性能较好，因为它只需要调整链表中的指针。但是，随机访问的性能不如
     * `ArrayList`，因为必须从头开始遍历链表来找到特定位置的元素。
     * 在 `ReservationService` 类中，由于需要对预订进行遍历（`printAllReservations()` 和
     * `getCustomersReservation()` 方法），而随机访问元素的需求较大，所以使用 `ArrayList` 可能更合适。
     * 然而，如果代码更多地涉及插入和删除操作，并且对于预订的顺序访问不是主要关注点，那么 `LinkedList`
     * 可能会更适合。在现实情况中，还需要进行性能测试来决定使用哪种数据结构，以便满足特定需求。
     */

    private ReservationService() {
        // Private constructor to prevent external instantiation
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Collection<IRoom> getAllRooms(){
        return rooms.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationList.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        if (reservationList == null) {
            return rooms.values();
        }
        Collection<IRoom> availableRooms = new ArrayList<>(rooms.values());
        HashSet<IRoom> unavailableRooms = new HashSet<>();
        for (Reservation reservation : reservationList) {
            if (!(!reservation.getCheckInDate().isBefore(checkOutDate) && !reservation.getCheckOutDate().isAfter(checkInDate))) {
                IRoom nowRoom = reservation.getRoom();
                unavailableRooms.add(nowRoom);
            }
        }
        availableRooms.removeAll(unavailableRooms);
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    public void printAllReservations() {
        if (reservationList.isEmpty()) {
            System.out.println("No reservation yet.");
        }else {
                System.out.println(reservationList);
        }
    }
}



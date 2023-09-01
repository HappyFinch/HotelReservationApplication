package service;

import model.Customer;

import java.util.*;

public class CustomerService {
    private static CustomerService instance = null;
    private static Map<String, Customer> customerMap = new HashMap<>();

    private CustomerService() {
        // Private constructor to prevent external instantiation
    }

    public static CustomerService getInstance() {
        // 懒汉模式创建单例
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName,lastName,email);
        customerMap.put(email, customer);
    }

    public Customer getCustomer(String customerEmail) {
        return customerMap.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customerMap.values();
        // customerMap.values() 直接返回的就是Collection<Customer>类型
    }
}

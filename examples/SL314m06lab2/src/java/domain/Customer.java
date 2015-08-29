/*Exa
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.util.HashMap;

/**
 *
 * @author Simon
 */
public class Customer {
    private String name;
    public static final int HEAD_OFFICE = 0;
    public static final int BILLING = 1;
    public static final int DELIVERY = 2;
    private final Address [] addresses = new Address[3];

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Address getOfficeAddress() {
        return addresses[HEAD_OFFICE];
    }

    public Address getBillingAddress() {
        return addresses[BILLING];
    }

    public Address getDeliveryAddress() {
        return addresses[DELIVERY];
    }

    public Address[] getAddresses() {
        return addresses;
    }
    
    public Customer(String name, Address office, Address billing, Address delivery ){
        this.name = name;
        addresses[HEAD_OFFICE] = office;
        addresses[BILLING] = billing;
        addresses[DELIVERY] = delivery;
    }

    //Pseudo-database for testing
    private static final HashMap<Integer, Customer> customers =
            new HashMap<Integer, Customer>();
    static {
        customers.put(1, new Customer("Fred Bloggs",
        new Address("1750 Crumlin Road",
                "", "London", "Ontario", "N5B 3V6"),
        new Address("6780 Collier Drive",
                "", "Paris", "Texas", "75462"),
        new Address("1 Airport Road",
                "", "Manchester", "New Hampshire", "03103")));
    }

    public static Customer getCustomer(int id) {
        return customers.get(id);
    }
}

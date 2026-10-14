package ITSD.Entity;

import ITSD.Repository.IEntityIntPK;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements IEntityIntPK {

    @Id
    @Column(name="CustomerID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String customerName;
    String emailAddress;
    boolean vegetarian;

    @OneToMany(mappedBy = "customer")
    List<CustomerOrder> items = new ArrayList<>();

    public Customer() { }

    public Customer(String customerName, boolean vegetarian) {
        this.customerName = customerName;
        this.vegetarian = vegetarian;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }
    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    @Override
    public String toString() {
        return "customerName=" + customerName;
    }
}

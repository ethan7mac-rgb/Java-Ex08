package ITSD.Entity;

import ITSD.Repository.IEntityIntPK;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CustomerOrder implements IEntityIntPK {

    @Id
    @Column(name="CustomerOrderID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int tipPercent;
    float subTotal;
    float tipAmount;
    int taxPercent = 15;
    float taxAmount;
    float grandTotal;

    @ManyToOne
    @JoinColumn(name = "CustomerID") // this is a database column name
    Customer customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    List<OrderItem> items = new ArrayList<>();

    public CustomerOrder() {

    }
    public CustomerOrder(Customer customer, int tipPercent) {
        this.customer = customer;
        this.tipPercent = tipPercent;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getTipPercent() {
        return tipPercent;
    }
    public void setTipPercent(int tipPercent) {
        this.tipPercent = tipPercent;
    }

    public float getSubTotal() {
        return subTotal;
    }
    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public float getTipAmount() {
        return tipAmount;
    }
    public void setTipAmount(float tipAmount) {
        this.tipAmount = tipAmount;
    }

    public int getTaxPercent() {
        return taxPercent;
    }
    public void setTaxPercent(int taxPercent) {
        this.taxPercent = taxPercent;
    }

    public float getTaxAmount() {
        return taxAmount;
    }
    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public float getGrandTotal() {
        return grandTotal;
    }
    public void setGrandTotal(float grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public List<OrderItem> getItems() {
        return items;
    }

    public void calculateTotals() {
        subTotal = 0f;
        if (items != null) {
            for (OrderItem oi : items) {
                if (oi.getMenuItem() != null) {
                    subTotal += (float) (oi.getQuantity() * oi.getMenuItem().getPrice());
                }
            }
        }
        tipAmount = subTotal * tipPercent / 100f;
        taxAmount = subTotal * taxPercent / 100f;
        grandTotal = subTotal + tipAmount + taxAmount;
    }

    @Override
    public String toString() {
        return "orderID=" + id +
                ", tipPercent=" + tipPercent +
                ", subTotal=" + subTotal;
    }
}

package entity;

public class Order implements SuperEntity {

    private String orderID;
    private String orderDate;
    private String customerID;

    public Order() {
    }

    public Order(String orderID, String orderDate, String customerID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customerID = customerID;
    }

    public Order(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "Order [orderID=" + orderID + ", orderDate=" + orderDate + ", customerID=" + customerID + "]";
    }

}

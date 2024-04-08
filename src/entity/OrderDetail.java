package entity;

public class OrderDetail implements SuperEntity {

    private String orderID;
    private String code;
    private int qty;
    private Double unitPrice;

    public OrderDetail() {
    }

    public OrderDetail(String orderID, String code, int qty, Double unitPrice) {
        this.orderID = orderID;
        this.code = code;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail [orderID=" + orderID + ", code=" + code + ", qty=" + qty + ", unitPrice=" + unitPrice + "]";
    }

}

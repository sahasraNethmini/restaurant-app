package view.tm;

import javafx.scene.control.Button;

public class OrderTM {

    private String code;
    private String description;
    private Double unitPrice;
    private int qtyOnHand;
    private int qty;
    private Double total;
    private Button btnRemove;

    public OrderTM() {
    }

    public OrderTM(String code, String description, Double unitPrice, int qtyOnHand, int qty, Double total, Button btnRemove) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.qty = qty;
        this.total = total;
        this.btnRemove = btnRemove;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Button getBtnRemove() {
        return btnRemove;
    }

    public void setBtnRemove(Button btnRemove) {
        this.btnRemove = btnRemove;
    }

    @Override
    public String toString() {
        return "OrderTM [code=" + code + ", description=" + description + ", unitPrice=" + unitPrice + ", qtyOnHand="
                + qtyOnHand + ", qty=" + qty + ", total=" + total + ", btnRemove=" + btnRemove + "]";
    }

}

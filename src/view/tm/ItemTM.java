package view.tm;

import javafx.scene.control.Button;

public class ItemTM {

    private String code;
    private String description;
    private Double unitPrice;
    private int qtyOnHand;
    private Button btnDelete;

    public ItemTM() {
    }

    public ItemTM(String code, String description, Double unitPrice, int qtyOnHand, Button btnDelete) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.btnDelete = btnDelete;
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

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "ItemTM [code=" + code + ", description=" + description + ", unitPrice=" + unitPrice + ", qtyOnHand="
                + qtyOnHand + ", btnDelete=" + btnDelete + "]";
    }

}

package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import util.Validator;
import view.tm.OrderTM;

public class OrderController {

    @FXML
    private ComboBox<String> cmbCustomerID;

    @FXML
    private ComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<OrderTM, String> colCode;

    @FXML
    private TableColumn<OrderTM, String> colDescription;

    @FXML
    private TableColumn<OrderTM, Integer> colQTY;

    @FXML
    private TableColumn<OrderTM, Integer> colQTYOnHand;

    @FXML
    private TableColumn<OrderTM, Button> colRemove;

    @FXML
    private TableColumn<OrderTM, Double> colTotal;

    @FXML
    private TableColumn<OrderTM, Double> colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblSubTotal;

    @FXML
    private Label lblContact;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblName;

    @FXML
    private Label lblQTYOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<OrderTM> tblOrder;

    @FXML
    private TextField txtBuyingQTY;

    @FXML
    private TextField txtOrderID;

    @FXML
    private Label lblTime;

    CustomerBo customerBo;
    ItemBo itemBo;
    OrderBo bo;

    private String generateNewOrdrID() throws Exception {

        ArrayList<OrderDTO> orderIDList;

        try {

            orderIDList = bo.getAllOrderID();

            OrderDTO lastID = orderIDList.get(orderIDList.size() - 1); 
            int intID = Integer.parseInt(lastID.getOrderID().replace("OID", ""));
            int newID = intID + 1;
            return "OID%03d".formatted(newID); 

        } catch (Exception e) {
            Alert alert1 = new Alert(AlertType.ERROR, "SQL Exception" + e.getMessage());
            alert1.showAndWait();
        }
        return null;
    }

    public void initialize() throws Exception {

        customerBo = BoFactory.getInstance().getBo(BoFactory.BoType.CUSTOMER);
        itemBo = BoFactory.getInstance().getBo(BoFactory.BoType.ITEM);
        bo = BoFactory.getInstance().getBo(BoFactory.BoType.ORDER);

        lblDate.setText(String.valueOf(LocalDate.now()));

        lblTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        Timeline t1Time = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), 
                ae -> lblTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        t1Time.getKeyFrames().add(frame);
        t1Time.setCycleCount(Animation.INDEFINITE);
        t1Time.play();

        loadCustomerID();
        loadItemCode();
        txtOrderID.setText(generateNewOrdrID());
    }

    ObservableList<OrderTM> tmList = FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTYOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

        try {

            String code = cmbItemCode.getValue();
            String description = lblDescription.getText();
            Integer qtyOnHand = Integer.parseInt(lblQTYOnHand.getText());
            Double unitPrice = Double.parseDouble(lblUnitPrice.getText());
            Integer qty = Integer.parseInt(txtBuyingQTY.getText());
            Double total = (qty * unitPrice);
            Button btnRemove = new Button("Remove");
            btnRemove.setMaxSize(100, 50);
            btnRemove.setCursor(Cursor.HAND);
            btnRemove.setStyle("-fx-background-color : #c0392b");
            btnRemove.setTextFill(Color.web("#ecf0f1"));

            if (!tmList.isEmpty()) {
                for (int i = 0; i < tblOrder.getItems().size(); i++) {

                    if (colCode.getCellData(i).equals(code)) {
                        int tempQty = colQTY.getCellData(i);
                        tempQty += qty;

                        if (tempQty <= Double.parseDouble(lblQTYOnHand.getText())) {
                            total = (tempQty * unitPrice);
                            tmList.get(i).setQty(tempQty);
                            tmList.get(i).setTotal(total);
                            getTotal();
                            tblOrder.refresh();
                            return;
                        }
                    }
                }
            }

            OrderTM orderTM = new OrderTM(code, description, unitPrice, qtyOnHand, qty, total, btnRemove);
            tmList.add(orderTM);

            btnRemove.setOnAction((e) -> {
                ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ! ", ok, no);

                Optional<ButtonType> result = alert.showAndWait();

                try {
                    if (result.orElse(no) == ok) {
                        tmList.remove(orderTM);
                        getTotal();
                        tblOrder.refresh();
                    }

                } catch (Exception e1) {
                    Alert alert1 = new Alert(AlertType.ERROR, "SQL Exception" + e1.getMessage());
                    alert1.showAndWait();
                }
            });

            tblOrder.setItems(tmList);
            getTotal();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

        try {
            boolean isSaved = bo.saveOrder(getOrder(), getOrderDetail());

            if (isSaved) {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Order is Saved");
                alert.show();

                for (TextField txt : new TextField[] {txtBuyingQTY, txtOrderID}) {
                    txt.clear();
                }
                
                for (Label label : new Label[] {lblContact, lblDescription, lblName, lblQTYOnHand, lblUnitPrice, lblSubTotal}) {
                    label.setText(null);
                }

                cmbCustomerID.setValue(null);
                cmbItemCode.setValue(null);

                tblOrder.getItems().clear();
                txtOrderID.setText(generateNewOrdrID());

            } else {
                Alert alert = new Alert(AlertType.ERROR, "Order is not Saved");
                alert.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OrderDTO getOrder() {

        String orderID = txtOrderID.getText();
        String orderDate = String.valueOf(LocalDate.now());
        String customerID = cmbCustomerID.getValue();

        return new OrderDTO(orderID, orderDate, customerID);
    }

    public ArrayList<OrderDetailDTO> getOrderDetail() {
        String orderID = txtOrderID.getText();

        ArrayList<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            OrderTM orderTM = tmList.get(i);
            orderDetailDTOs
                    .add(new OrderDetailDTO(orderID, orderTM.getCode(), orderTM.getQty(), orderTM.getUnitPrice()));
        }
        return orderDetailDTOs;
    }

    @FXML
    void cmbCustomerIDOnAction(ActionEvent event) {

        String id = cmbCustomerID.getValue();

        try {

            CustomerDTO dto = customerBo.getCustomer(id);

            if (dto != null) {
                lblName.setText(dto.getName());
                lblContact.setText(String.valueOf(dto.getContact()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void cmbItemCodeOnAction(ActionEvent event) {

        String code = cmbItemCode.getValue();

        try {

            ItemDTO dto = itemBo.getItem(code);

            if (dto != null) {
                lblDescription.setText(dto.getDescription());
                lblQTYOnHand.setText(String.valueOf(dto.getQtyOnHand()));
                lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadCustomerID() {

        ObservableList<String> customerIDList = FXCollections.observableArrayList();

        try {

            ArrayList<CustomerDTO> allCustomerID = customerBo.getAllCustomer();

            for (CustomerDTO dto : allCustomerID) {
                customerIDList.add(dto.getId());
            }
            cmbCustomerID.setItems(customerIDList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadItemCode() {

        ObservableList<String> itemCodeList = FXCollections.observableArrayList();

        try {

            ArrayList<ItemDTO> allItemCode = itemBo.getAllItem();

            for (ItemDTO dto : allItemCode) {
                itemCodeList.add(dto.getCode());
            }
            cmbItemCode.setItems(itemCodeList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void txtIdOnKeyReleased(KeyEvent event) {

        if (Validator.validateTextField(txtOrderID, "^[0-9]{1,}$")) {
            txtOrderID.setStyle("-fx-focus-color:green");

        } else {
            txtOrderID.setStyle("-fx-focus-color:red");
        }

    }

    private void getTotal() {

        double total = 0.0;

        for (OrderTM orderTM : tmList) {

            total += orderTM.getTotal();
        }
        lblSubTotal.setText(String.valueOf(total));
    }

}

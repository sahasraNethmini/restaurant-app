package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import bo.BoFactory;
import bo.custom.ItemBo;
import dto.ItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import util.Validator;
import view.tm.ItemTM;

public class ItemController {

    @FXML
    private TableColumn<ItemTM, String> colCode;

    @FXML
    private TableColumn<ItemTM, Button> colDelete;

    @FXML
    private TableColumn<ItemTM, String> colDescription;

    @FXML
    private TableColumn<ItemTM, Integer> colQTY;

    @FXML
    private TableColumn<ItemTM, Double> colUnitPrice;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtQTY;

    @FXML
    private TextField txtUnitPrice;
    
    @FXML
    private VBox vBoxTextFields;

    @FXML
    private HBox btnSaveUpdate;

        @FXML
    private AnchorPane container;

    ItemBo bo;

    public void initialize() {

        bo = BoFactory.getInstance().getBo(BoFactory.BoType.ITEM);

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        getItems();
        vBoxTextFields.setDisable(true);
        btnSaveUpdate.setDisable(true);

    }

    private String generateNewItemCode(){

        ObservableList<ItemTM> itemList = tblItem.getItems();

        if (itemList.isEmpty()) return "IC001";

       ItemTM lastItem = itemList.get(itemList.size() - 1);
       int lastId = Integer.parseInt(lastItem.getCode().replace("IC", ""));
       int newID = lastId + 1;
        return "IC%03d".formatted(newID);
    }

    @FXML
    void btnNewItemOnAction(ActionEvent event) {
        vBoxTextFields.setDisable(false);
        btnSaveUpdate.setDisable(false);
        txtItemCode.setText(generateNewItemCode());
        txtDescription.requestFocus();
    }

    public void getItems() {

        try {

            ArrayList<ItemDTO> allItems = bo.getAllItem();
            ObservableList<ItemTM> tmList = FXCollections.observableArrayList();

            for (ItemDTO item : allItems) {
                Button btnDelete = new Button("Delete");
                btnDelete.setMaxSize(100, 50);
                btnDelete.setCursor(Cursor.HAND);
                btnDelete.setStyle("-fx-background-color : #c0392b");
                btnDelete.setTextFill(Color.web("#ecf0f1"));

                ItemTM itemTM = new ItemTM(
                        item.getCode(),
                        item.getDescription(),
                        item.getUnitPrice(),
                        item.getQtyOnHand(), btnDelete);

                tmList.add(itemTM);

                btnDelete.setOnAction((e) -> {
                    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(AlertType.CONFIRMATION, "Are You Sure !  ", ok, no);

                    Optional<ButtonType> result = alert.showAndWait();

                    try {

                        if (result.orElse(no) == ok) {

                            if (bo.deleteItem(itemTM.getCode())) {

                                new Alert(AlertType.CONFIRMATION, "Item is Deleted ! ").show();
                                getItems();
                                return;
                            }
                        }

                    } catch (Exception e2) {
                        new Alert(AlertType.ERROR, "Exception Delete" + e2.getMessage()).show();
                    }

                });
            }

            tblItem.setItems(tmList);       

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, "Exception" + e.getMessage());
            alert.show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        boolean validate = true;

        for (Node node : vBoxTextFields.lookupAll(".error")) {
            node.getStyleClass().remove("error");
        }

        if (txtQTY.getText().strip().isEmpty()) {
            txtQTY.setStyle("-fx-border-color:red");
            txtQTY.requestFocus();
            validate = false;
        } 
        if (txtUnitPrice.getText().strip().isEmpty()) {
            txtUnitPrice.setStyle("-fx-border-color:red");
            txtUnitPrice.requestFocus();
            validate = false;
        }
        if (txtDescription.getText().isEmpty()) {
            txtDescription.setStyle("-fx-border-color:red");
            txtDescription.requestFocus();
            validate = false;
        }
        if (txtItemCode.getText().isEmpty()) {
            txtItemCode.setStyle("-fx-border-color:red");
            txtItemCode.requestFocus();
            validate = false;
        }
        
        if (!validate) return; 

        try {

            String code = txtItemCode.getText();
            String description = txtDescription.getText();
            Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            int qtyOnHand = Integer.parseInt(txtQTY.getText());

            ItemDTO itemDTO = new ItemDTO(code, description, unitPrice, qtyOnHand);

            boolean isSaved = bo.saveItem(itemDTO);

            if (isSaved) {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Item is Saved");
                alert.show();
                getItems();

                for (TextField textField : new TextField[] {txtDescription, txtItemCode, txtQTY, txtUnitPrice}) {
                    textField.clear();
                }

                vBoxTextFields.setDisable(true);

            } else {
                Alert alert = new Alert(AlertType.ERROR, "Item is not Saved");
                alert.show();
            }

        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR, "SQL Exception" + e.getMessage());
            alert.showAndWait();

        } catch (ClassNotFoundException e1) {
            Alert alert = new Alert(AlertType.ERROR, "Class Not Found Exception" + e1.getMessage());
            alert.showAndWait();

        } catch (Exception e8) {
            Alert alert = new Alert(AlertType.ERROR, "Exception" + e8.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        try {

            String code = txtItemCode.getText();
            String description = txtDescription.getText();
            Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            int qtyOnHand = Integer.parseInt(txtQTY.getText());

            ItemDTO itemDTO = new ItemDTO(code, description, unitPrice, qtyOnHand);

            boolean isUpdated = bo.updateItem(itemDTO);

            if (isUpdated) {
                new Alert(AlertType.CONFIRMATION, " Item is Updated ").showAndWait();

                for (TextField txt : new TextField[] {txtDescription, txtItemCode, txtQTY, txtUnitPrice}) {
                    txt.clear();
                }
                txtItemCode.setText(generateNewItemCode());
                getItems();

            } else {
                new Alert(AlertType.ERROR, "Item is Not Updated ").showAndWait();
            }

        } catch (SQLException e7) {
            Alert alert = new Alert(AlertType.ERROR, "SQL Exception" + e7.getMessage());
            alert.showAndWait();

        } catch (ClassNotFoundException e6) {
            Alert alert = new Alert(AlertType.ERROR, "Class Not Found Exception" + e6.getMessage());
            alert.showAndWait();

        } catch (Exception e9) {
            Alert alert = new Alert(AlertType.ERROR, "Exception" + e9.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void txtItemCodeOnAction(ActionEvent event) {

        try {

            String code = txtItemCode.getText();
            ItemDTO dto = bo.getItem(code);

            if (dto != null) {

                txtDescription.setText(dto.getDescription());
                txtUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
                txtQTY.setText(String.valueOf(dto.getQtyOnHand()));

            } else {
                new Alert(AlertType.ERROR, "Item Not Found, Please check the Item Code and try again !").show();
            }

        } catch (Exception e3) {
            Alert alert = new Alert(AlertType.ERROR, "Exeption Search" + e3.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void descriptionOnKeyRelesed(KeyEvent event) {
        
        if (Validator.validateTextField(txtDescription, "^[A-z| ]{1,}$")
                &&(!(txtDescription.getText().strip().length() < 4))) {
            txtDescription.setStyle("-fx-focus-color:green");
        } else {
            txtDescription.setStyle("-fx-focus-color:red");
        }
    }

    @FXML
    void itemCodeOnKeyRelesed(KeyEvent event) {
        if (Validator.validateTextField(txtItemCode, "^[A-z| ]{1,}$,^[0-9]{1,}$")
            &&(txtItemCode.getText().strip().length() == 5 )) {
        txtItemCode.setStyle("-fx-focus-color:green");
        } else {
        txtItemCode.setStyle("-fx-focus-color:red");
        }
    }

    @FXML
    void unitPriceOnKeyRelesed(KeyEvent event) {
        if (Validator.validateTextField(txtUnitPrice, "^[0-9]{1,}$")) {
            txtUnitPrice.setStyle("-fx-focus-color:green");
        }else {
            txtUnitPrice.setStyle("-fx-focus-color:red");
        }
    }

    @FXML
    void qtyOnKeyRelesed(KeyEvent event) {
        if (Validator.validateTextField(txtQTY, "^[0-9]{1,}$")) {
            txtQTY.setStyle("-fx-focus-color:green");
        }else {
            txtQTY.setStyle("-fx-focus-color:red");
        }
    }
}

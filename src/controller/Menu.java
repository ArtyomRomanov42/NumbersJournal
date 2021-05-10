package controller;

import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Contact;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    @FXML
    public Button button_save;
    public Label popup;
    public Button button_upd;
    public AnchorPane insert_anchorpane;
    @FXML
    private TableView<Contact> tableView;
    public TextField mName;
    public TextField mPhone;

    Contact contact;
    @FXML
    TableColumn<Contact, String> column_phone;
    @FXML
    public Button button_del;


    private Database connect;
    @FXML
    TableColumn<Contact, String> column_username;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        connect = new Database();
        tableInit();

        button_save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addContact();
            }
        });

        button_del.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                contactDelete();
            }
        });
        button_upd.setOnAction(event -> contactUpdate());

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0) {
                edit();
            }
        });

    }

    private void addContact() {
        String name = mName.getText();
        String phone = mPhone.getText();

        if (popup.isVisible()) {
            popup.setVisible(false);
        }

        if (!name.isEmpty() && !phone.isEmpty()) {
            contact = new Contact(name, phone);

            connect.contactCreate(contact);
            tableInit();

            mPhone.clear();
            mName.clear();
        } else {
            if (name.isEmpty() && phone.isEmpty()) {
                popup.setText("*Insert Info");
                popup.setVisible(true);
            } else if (name.isEmpty()) {
                popup.setText("*Insert Name");
                popup.setVisible(true);
            } else {
                popup.setText("*Insert Phone Number");
                popup.setVisible(true);
            }

        }
    }

    public void edit() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            column_username.setCellValueFactory(cellData -> cellData.getValue().getNameValue());
            column_phone.setCellValueFactory(cellData -> cellData.getValue().getPhoneValue());

            Contact contact = tableView.getSelectionModel().getSelectedItem();
            mPhone.setText(contact.getPhoneValue().getValue());
            mName.setText(contact.getNameValue().getValue());
        }
    }

    private void contactDelete() {

        if (tableView.getSelectionModel().getSelectedItem() == null) {
            System.out.println("No item selected");
        } else {
            column_username.setCellValueFactory(cellData -> cellData.getValue().getNameValue());
            column_phone.setCellValueFactory(cellData -> cellData.getValue().getPhoneValue());

            Contact contact = tableView.getSelectionModel().getSelectedItem();
            String phone = contact.getPhoneValue().getValue();

            tableView.getItems().removeAll(contact);

            mPhone.clear();
            mName.clear();

            String sql = "DELETE FROM peoples WHERE phone = ?";
            connect.execQuery(sql, phone);

            tableInit();
        }


    }

    private void contactUpdate() {

        if (tableView.getSelectionModel().getSelectedItem() == null) {
            System.out.println("No item selected");
        } else {
            String name = mName.getText();
            String phone = mPhone.getText();

            mPhone.clear();
            mName.clear();

            contact = new Contact(name, phone);

            connect.contactUpdate(contact);

        }
        tableInit();

    }


    @FXML
    private void tableInit() {
        column_username.setCellValueFactory(cellData -> cellData.getValue().getNameValue());
        column_phone.setCellValueFactory(cellData -> cellData.getValue().getPhoneValue());

        ObservableList<Contact> contactList = null;
        try {
            contactList = Menu.getAllData();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        populateTable(contactList);

    }

    private static ObservableList<Contact> getAllData() throws ClassNotFoundException, SQLException {
        //запрос
        String sql = "SELECT name, phone FROM peoples";

        try {
            ResultSet rs = Database.dbExecute(sql);
            return getContactObjects(rs);
        } catch (SQLException e) {
            System.out.println("Didn't work");
            e.printStackTrace();
            throw e;
        }


    }

    private static ObservableList<Contact> getContactObjects(ResultSet rs) throws SQLException {

        try {
            ObservableList<Contact> contactList = FXCollections.observableArrayList();

            while (rs.next()) {
                Contact contact = new Contact();

                contact.setNameProp(rs.getString("name"));
                contact.setPhoneNumber(rs.getString("phone"));
                contactList.add(contact);
            }
            return contactList;
        } catch (SQLException e) {
            System.out.println("Problem 1");
            e.printStackTrace();
            throw e;
        }

    }

    private void populateTable(ObservableList<Contact> contactList) {
        tableView.setItems(contactList);
    }
}
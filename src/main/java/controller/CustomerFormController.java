package controller;

import bo.BOFactory;
import bo.custom.CustomerBo;
import dto.CustomerDto;
import dto.Tm.CustomerTm;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomerFormController {
    public TextField txtCusAddress;
    public JFXButton btnClear;
    public Label customerCount;
    public Label lblDate;
    public Label lblTime;
    @FXML
    private JFXButton btnDeleteCustomer;

    @FXML
    private JFXButton btnSaveCustomer;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colCustomerAddress;

    @FXML
    private TableColumn<?, ?> colCustomerContact;

    @FXML
    private TableColumn<?, ?> colCustomerID;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colCustomerPets;


    @FXML
    private TableView<CustomerTm> tblCustomer;


    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtCustomerFname;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextField txtCustomerLname;
//    private CustomerDao dao = new CustomerDaoImpl();
    private CustomerBo bo = (CustomerBo) BOFactory.getBOFactory().getBo(BOFactory.BoTypes.CUSTOMER);
    public void initialize(){
        setCellValueFactory();
        loadAllCustomer();
        lblTime.setText(String.valueOf(LocalDate.now()));
        lblDate.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        try {
            customerCount.setText(String.valueOf(bo.getCount()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllCustomer() {


        ObservableList<CustomerTm> oblist = FXCollections.observableArrayList();

        try {
            List<CustomerDto> list = bo.getAllCustomer();

            for (CustomerDto d : list) {
                oblist.add(
                  new CustomerTm(
                          d.getCustomerId(),
                          d.getCustomerName(),
                          d.getCustomerAddress(),
                          d.getCustomerContact(),
                          d.getCustomerPet()
                  )
                );
            }
            tblCustomer.setItems(oblist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void setCellValueFactory() {
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colCustomerContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtCustomerID.getText();
        String name = txtCustomerFname.getText()+"  "+txtCustomerLname.getText();
        String address = txtCusAddress.getText();
        String contact = txtContactNo.getText();
        try {
            if(bo.updateCustomer(new CustomerDto(id,name,address,contact))){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated Successfully").show();
                loadAllCustomer();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void customerContactSearch(ActionEvent event) {
        String contact = txtCustomerFname.getText();
        try {
            CustomerDto customerDto = bo.searchCustomerByContact(contact);
            if (customerDto != null){
                String [] name = customerDto.getCustomerName().split("  ");
                txtCustomerID.setText(customerDto.getCustomerId());
                txtCustomerFname.setText(name[0]);
                txtCustomerLname.setText(name[1]);
                txtCusAddress.setText(customerDto.getCustomerAddress());
                txtContactNo.setText(customerDto.getCustomerContact());
            }else{
                new Alert(Alert.AlertType.ERROR,"Customer Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    @FXML
    void customerDeleteOnAction(ActionEvent event) {
        String id = txtCustomerID.getText();
        boolean isDeleted = false;
        try {
            isDeleted = bo.deleteCustomer(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted !").show();
                clearFields();
                loadAllCustomer();
                customerCount.setText(String.valueOf(bo.getCount()));
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Customer Not Deleted !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void customerLnameSearchOnAction(ActionEvent event) {
        String Lname = txtCustomerLname.getText();
        try {
            CustomerDto customerDto = bo.searchCustomerByLastname(Lname);
            if (customerDto != null){
                String [] name = customerDto.getCustomerName().split("  ");
                txtCustomerID.setText(customerDto.getCustomerId());
                txtCustomerFname.setText(name[0]);
                txtCustomerLname.setText(name[1]);
                txtCusAddress.setText(customerDto.getCustomerAddress());
                txtContactNo.setText(customerDto.getCustomerContact());
            }else{
                new Alert(Alert.AlertType.ERROR,"Customer Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void customerSaveOnaction(ActionEvent event)  {
        String Id = txtCustomerID.getText();
        String Fname = txtCustomerFname.getText();
        String Lname = txtCustomerLname.getText();
        String Address = txtCusAddress.getText();
        String contact = txtContactNo.getText();
        String fullname = Fname+" "+Lname;

        try {

            if(bo.existCustomer(Id)){
                new Alert(Alert.AlertType.ERROR,"Customer Id already exists !!!").show();
                return;
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try{
            var dto = new CustomerDto(Id,fullname,Address,contact);
            boolean isSaved = bo.saveCustomer(dto);
            tblCustomer.refresh();
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved Successfully").show();
                clearFields();
                loadAllCustomer();
                customerCount.setText(String.valueOf(bo.getCount()));
            }
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



    private void clearFields() {
        txtCustomerID.clear();
        txtCusAddress.clear();
        txtCustomerFname.clear();
        txtCustomerLname.clear();
        txtContactNo.clear();
        
    }

    @FXML
    void customerSearchOnaction(ActionEvent event) {
        String id = txtCustomerID.getText();
        try {
            CustomerDto customerDto = bo.searchCustomer(id);
            if (customerDto != null){
                String [] name = customerDto.getCustomerName().split("  ");
                System.out.println(customerDto.getCustomerName());
                txtCustomerID.setText(customerDto.getCustomerId());
                txtCustomerFname.setText(name[0]);
                txtCustomerLname.setText(name[1]);
                txtCusAddress.setText(customerDto.getCustomerAddress());
                txtContactNo.setText(customerDto.getCustomerContact());
            }else{
                new Alert(Alert.AlertType.ERROR,"Customer Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtCustomerFnameSearch(ActionEvent event) {
        String Fname = txtCustomerFname.getText();
        try {
            CustomerDto customerDto = bo.searchCustomerByFirstname(Fname);
            if (customerDto != null){
                String [] name = customerDto.getCustomerName().split("  ");
                txtCustomerID.setText(customerDto.getCustomerId());
                txtCustomerFname.setText(name[0]);
                txtCustomerLname.setText(name[1]);
                txtCusAddress.setText(customerDto.getCustomerAddress());
                txtContactNo.setText(customerDto.getCustomerContact());
            }else{
                new Alert(Alert.AlertType.ERROR,"Customer Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
}

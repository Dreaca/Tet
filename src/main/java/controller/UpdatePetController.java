package controller;

import bo.BOFactory;
import bo.custom.PetBo;
import dto.PetDto;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

@NoArgsConstructor
public class UpdatePetController implements Initializable {
    public AnchorPane root;
    public TextField txtPetName;
    public TextField txtOwner;
    public ComboBox cmbBreed;
    public ComboBox cmbGender;
    public TextField txtColor;
    public Label lblPetID;
    public String  petId;

//    private PetModel model = new PetModel();
    private PetBo bo = (PetBo) BOFactory.getBOFactory().getBo(BOFactory.BoTypes.PET);

    private void loadData() throws SQLException {
        cmbBreed.getItems().addAll("Dog","Cat","Bird","Other");
        cmbGender.getItems().addAll("Male","Female");
    }

    public void doneOnAction() throws SQLException {
        String id = lblPetID.getText();
        String name = txtPetName.getText();
        String breed = (String) cmbBreed.getValue();
        String gender = cmbGender.getValue().toString();
        String ownerid = bo.getCustomerId(txtOwner.getText());
        String color = txtColor.getText();

        var dto = new PetDto(id,name,0,breed,gender,ownerid,color);
        try {
            if (bo.updatePet(dto)) {
                new Alert(Alert.AlertType.CONFIRMATION,"Pet updated").show();
                Stage stage = (Stage) root.getScene().getWindow();
                stage.close();
            }
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public void cancelOnAction(){
        Stage window = (Stage) root.getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPetId(String petId) {
        this.petId = petId;
        lblPetID.setText(petId);
    }
}

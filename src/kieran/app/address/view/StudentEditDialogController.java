package kieran.app.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kieran.app.address.model.Student;
import kieran.app.address.util.DateUtil;


 // Edit details of a Student.

public class StudentEditDialogController {

    @FXML
    private TextField studentNameField;
    @FXML
    private TextField parentField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField commentsField;


    private Stage dialogStage;
    private Student student;
    private boolean okClicked = false;

  
    @FXML
    private void initialize() {
    }

   
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    
    //  Sets the Student to be edited.
     
    public void setStudent(Student student) {
        this.student = student;

        studentNameField.setText(student.getStudentName());
        parentField.setText(student.getParent());
        addressField.setText(student.getAddress());
        phoneNumField.setText(Integer.toString(student.getPhoneNum()));
        emailField.setText(student.getEmail());
        birthdayField.setText(DateUtil.format(student.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
        commentsField.setText(student.getComments());
    }

    
     //Returns true if the user clicked OK, false otherwise.
     
    public boolean isOkClicked() {
        return okClicked;
    }

    
     // Called when the user clicks OK.
     
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            student.setStudentName(studentNameField.getText());
            student.setParent(parentField.getText());
            student.setAddress(addressField.getText());
            student.setPhoneNum(Integer.parseInt(phoneNumField.getText()));
            student.setEmail(emailField.getText());
            student.setBirthday(DateUtil.parse(birthdayField.getText()));
            student.setComments(commentsField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }
    
    // Called when the user clicks cancel.
     
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /*
      Validates the user input in the text fields.
      
      return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (studentNameField.getText() == null || studentNameField.getText().length() == 0) {
            errorMessage += "No valid Student name!\n"; 
        }
        if (parentField.getText() == null || parentField.getText().length() == 0) {
            errorMessage += "No valid Paren name!\n"; 
        }
        if (addressField.getText() == null || addressField.getText().length() == 0) {
            errorMessage += "No valid Address!\n"; 
        }

        if (phoneNumField.getText() == null || phoneNumField.getText().length() == 0) {
            errorMessage += "No valid phone number!\n"; 
        } else {
              try {
                Integer.parseInt(phoneNumField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid phone number!\n"; 
            }
        }

        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "No valid email!\n"; 
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
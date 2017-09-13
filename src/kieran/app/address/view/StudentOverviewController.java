package kieran.app.address.view;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kieran.app.address.MainApp;
import kieran.app.address.model.Payment;
import kieran.app.address.model.Student;
import kieran.app.address.util.DateUtil;
import kieran.app.address.util.Email;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class StudentOverviewController {
    @FXML TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> studentNameColumn;
    @FXML
    private TableColumn<Student, String> parentColumn;

    @FXML
    private Label studentNameLabel;
    @FXML
    private Label parentLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label phoneNumLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label commentsLabel;


    Payment payment;
    Student student;
    EmailController mEmailController;
    
    // Reference to the main application.
    private MainApp mainApp;

   
    public StudentOverviewController() {
    }

//      Initializes the controller class. This method is automatically called
//      after the fxml file has been loaded.

  
    @FXML
    private void initialize() {
        // Initialize the student table with the two columns.
        studentNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().studentNameProperty());
        parentColumn.setCellValueFactory(
                cellData -> cellData.getValue().parentProperty());

        // Clear student details.
        showStudentDetails(null);

        // Listen for selection changes and show the student details when changed.
        studentTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showStudentDetails(newValue));
    }

 
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add list data to the table
        studentTable.setItems(mainApp.getStudentData());
        
    
             
    }
    
  
    private void showStudentDetails(Student student) {
        if (student != null) {
            // Fill the labels with info from the student object.
            studentNameLabel.setText(student.getStudentName());
            parentLabel.setText(student.getParent());
            addressLabel.setText(student.getAddress());
            phoneNumLabel.setText("0"+Integer.toString(student.getPhoneNum()));
            emailLabel.setText(student.getEmail());
            birthdayLabel.setText(DateUtil.format(student.getBirthday()));
            commentsLabel.setText(student.getComments());
                 
            
        } else {
            // Student is null, remove all the text.
            studentNameLabel.setText("");
            parentLabel.setText("");
            addressLabel.setText("");
            phoneNumLabel.setText("");
            emailLabel.setText("");
            birthdayLabel.setText("");
            commentsLabel.setText("");
                        
        }
    }
    
    private void showMoreDetails(Student student) {
    	
    	
    	
    	 Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
		selectedStudent.getPaymentObj();
		
				
   	      if (selectedStudent.getPaymentObj().getPayedLessons() <= 1){
		
		 Email.sendEmail(selectedStudent.getEmail(),"Payment Reminder","Dear parents just a frendly reminder that payment for your piano lessons is now due ","viktoriaryan3@gmail.com","viktoriaryan3@gmail.com","karenvika13");
		
	 }
		
}
    
     
     
   // Called when the user clicks on the delete button.
    @FXML
    private void handleDeleteStudent() {
        int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            studentTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No student Selected");
            alert.setContentText("Please select a student in the table.");

            alert.showAndWait();
        }
    }
    
    /*
      Called when the user clicks the new button. Opens a dialog to edit
      details for a new student.
     */
    @FXML
    private void handleNewStudent() {
        Student tempStudent
        = new Student();
        boolean okClicked = mainApp.showStudentEditDialog(tempStudent);
        if (okClicked) {
            mainApp.getStudentData().add(tempStudent);
        }
    }

    /*
      Called when the user clicks the edit button. Opens a dialog to edit
      details for the selected student.
     */
    @FXML
    private void handleEditStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            boolean okClicked = mainApp.showStudentEditDialog(selectedStudent);
            if (okClicked) {
                showStudentDetails(selectedStudent);
            }

        } else {
            //If no Student selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select a student in the table.");

            alert.showAndWait();
        }
    }
    @FXML
    private void handleMoreDetails() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
       
        if (selectedStudent != null) {
            boolean okClicked = mainApp.showMoreDetailsDialog( selectedStudent);

            if (okClicked) {
                showMoreDetails(selectedStudent);       	
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select a student in the table.");

            alert.showAndWait();
        }

    
    }   
 @FXML
    private void handleSendEmail(){
    	 mainApp.showEmailNotificationDialog();
    	 
  	    	}
 
 	 
 }
 
 
       	
 
    

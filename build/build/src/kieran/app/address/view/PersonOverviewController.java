package kieran.app.address.view;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kieran.app.address.MainApp;
import kieran.app.address.model.Payment;
import kieran.app.address.model.Person;
import kieran.app.address.util.DateUtil;
import kieran.app.address.util.Email;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class PersonOverviewController {
    @FXML TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> studentNameColumn;
    @FXML
    private TableColumn<Person, String> parentColumn;

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
    Person person;
    EmailController mEmailController;
    
    // Reference to the main application.
    private MainApp mainApp;

   
    public PersonOverviewController() {
    }

//      Initializes the controller class. This method is automatically called
//      after the fxml file has been loaded.

  
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        studentNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().studentNameProperty());
        parentColumn.setCellValueFactory(
                cellData -> cellData.getValue().parentProperty());

        // Clear student details.
        showPersonDetails(null);

        // Listen for selection changes and show the student details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

 
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add list data to the table
        personTable.setItems(mainApp.getPersonData());
        
    
             
    }
    
  
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            studentNameLabel.setText(person.getStudentName());
            parentLabel.setText(person.getParent());
            addressLabel.setText(person.getAddress());
            phoneNumLabel.setText(Integer.toString(person.getPhoneNum()));
            emailLabel.setText(person.getEmail());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
            commentsLabel.setText(person.getComments());
                 
            
        } else {
            // Person is null, remove all the text.
            studentNameLabel.setText("");
            parentLabel.setText("");
            addressLabel.setText("");
            phoneNumLabel.setText("");
            emailLabel.setText("");
            birthdayLabel.setText("");
            commentsLabel.setText("");
                        
        }
    }
    
    private void showMoreDetails(Person person) {
    	
    	
    	
    	 Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		selectedPerson.getPaymentObj();
		
				
   	      if (selectedPerson.getPaymentObj().getPayedLessons() <= 1){
		
		 Email.sendEmail(selectedPerson.getEmail(),"Payment Reminder","Dear parents just a frendly reminder that payment for your piano lessons is now due ","viktoriaryan3@gmail.com","viktoriaryan3@gmail.com","karenvika13");
		
	 }
		
}
    
     
     
   // Called when the user clicks on the delete button.
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
    /*
      Called when the user clicks the new button. Opens a dialog to edit
      details for a new student.
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /*
      Called when the user clicks the edit button. Opens a dialog to edit
      details for the selected student.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            //If no Student selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    @FXML
    private void handleMoreDetails() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
       
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showMoreDetailsDialog( selectedPerson);

            if (okClicked) {
                showMoreDetails(selectedPerson);       	
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }

    
    }   
 @FXML
    private void handleSendEmail(){
    	 mainApp.showEmailNotificationDialog();
    	 
  	    	}
 
 	 
 }
 
 
       	
 
    

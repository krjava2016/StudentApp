package kieran.app.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kieran.app.address.MainApp;
import kieran.app.address.model.Payment;
import kieran.app.address.model.Student;


//To get more details

public class MoreDetailsController {

    @FXML
    private TextField startDateField;
    @FXML
    private TextField lessonDurationField;
    @FXML
    private TextField costOfLessonField;
    @FXML
    private TextField attendedLessonsField;
    @FXML
    private TextField payedLessonsField;
    @FXML
    private TextField paymentField;


    private Stage dialogStage;
    private Payment payment;
    private boolean okClicked = false;
    Student student;
    public MoreDetailsController() {
		// TODO Auto-generated constructor stub
	}

    /*
      Initializes the controller class. This method is automatically called
      after the fxml file has been loaded.
     */
//    @FXML
//    private void initialize() {
//    }

    
    
    public void setMainApp(MainApp mainApp) {
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /*
     Sets the student more details to be edited in the dialog.
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
        


        startDateField.setText(payment.getStartDate());
        lessonDurationField.setText(payment.getLessonDuration());
        costOfLessonField.setText(payment.getCostOfLesson());
        attendedLessonsField.setText(Integer.toString(payment.getAttendedLessons()));
        payedLessonsField.setText(Integer.toString(payment.getPayedLessons()));
       // paymentField.setText(payment.getPayment());
        paymentField.setPromptText("Please input No. of lessons to be paid");
      
    }
    
    public void setStudent() {
    	
//    	startDateField.setText(payment.getStartDate());
//        lessonDurationField.setText(payment.getLessonDuration());
//        costOfLessonField.setText(payment.getCostOfLesson());
//        attendedLessonsField.setText(Integer.toString(payment.getAttendedLessons()));
//        payedLessonsField.setText(Integer.toString(payment.getPayedLessons()));
//        paymentField.setText(Integer.toString(payment.getPayment()));
	}

   
     // Returns true if the user clicked OK, false otherwise.
    
    public boolean isOkClicked() {
        return okClicked;
    }

    
     //Called when the user clicks ok.
     
    @FXML
    private void handleOk() {
    	
    	 
        if (isInputValid()) {
            payment.setStartDate(startDateField.getText());
            payment.setLessonDuration(lessonDurationField.getText());
            payment.setCostOfLesson(costOfLessonField.getText());
            payment.setAttendedLessons(Integer.parseInt(attendedLessonsField.getText()));
            payment.setPayedLessons(Integer.parseInt(payedLessonsField.getText()));
            payment.setPayment(paymentField.getText());
            
           
            okClicked = true;
            dialogStage.close();
        }
    }

    
     //Called when the user clicks cancel.
     
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    @FXML
    private void handleAttended(){
    	payment.setAttendedLessons(payment.getAttendedLessons()+1);
    	attendedLessonsField.setText(Integer.toString(payment.getAttendedLessons()));
    	payment.setPayedLessons(payment.getPayedLessons()-1);
    	 payedLessonsField.setText(Integer.toString(payment.getPayedLessons()));
    	
    	 	 

    }
    
    @FXML
    private void handlePayment(){
    	payment.setPayment(paymentField.getText());
    	payment.setPayedLessons(payment.getPayedLessons()+Integer.parseInt(payment.getPayment()));
    	payedLessonsField.setText(Integer.toString(payment.getPayedLessons()));
    	paymentField.setPromptText("Please input No. of lessons payed");
    	
    	    	
    }

  
    private boolean isInputValid() {
        String errorMessage = "";

        if (startDateField.getText() == null || startDateField.getText().length() == 0) {
            errorMessage += "No valid start date!\n"; 
        }
        if (lessonDurationField.getText() == null || lessonDurationField.getText().length() == 0) {
            errorMessage += "No valid lesson duration!\n"; 
        }
        if (costOfLessonField.getText() == null || costOfLessonField.getText().length() == 0) {
            errorMessage += "No valid cost of lesson!\n"; 
        }

        if (attendedLessonsField.getText() == null || attendedLessonsField.getText().length() == 0) {
            errorMessage += "No valid attended lessons!\n"; 
        } else {
           
            try {
                Integer.parseInt(attendedLessonsField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid attended lessons!\n"; 
            }
        }
        
        if (payedLessonsField.getText() == null || payedLessonsField.getText().length() == 0) {
            errorMessage += "No valid payed lessons!\n"; 
        } else {
            // try to parse the payedLessons code into an int.
            try {
                Integer.parseInt(payedLessonsField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid payed lessons!\n"; 
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
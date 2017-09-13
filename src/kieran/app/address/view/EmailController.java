package kieran.app.address.view;

import javafx.fxml.FXML;

import kieran.app.address.MainApp;
import kieran.app.address.model.Student;
import kieran.app.address.util.Email;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmailController {
	
		private Stage dialogStage;
	    Student student;
	    MainApp mainApp;
	    String text;	
	    String header;
	   	    
	     public EmailController() {
			// TODO Auto-generated constructor stub
		}
	    
	
	 
	  @FXML
	   TextArea emailTxt;
	  @FXML
	  TextField emailHeader;
	 
	  
	  
	  public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	  }
	 
	
	    
	    public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }

	   
	    @FXML
	    private void handleSend() {
	    	
	    
	    text = emailTxt.getText();
	    header = emailHeader.getText();
	   
	   
	   for( Student student : mainApp.getStudentData()){
   		
   		
		 Email.sendEmail(student.getEmail(),header, text ,"your@gmail.com","your@gmail.com","pass");

	   }
	   
	       	 dialogStage.close();
	    
	   
	    	}
	        
		    
	    //Called when the user clicks cancel.
	     
	    @FXML
	    private void handleCancel() {
	        dialogStage.close();
	    }
	    
	  
	  
}

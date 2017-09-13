package kieran.app.address;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.MonthDay;
import java.util.Date;
import java.util.Timer;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kieran.app.address.model.Student;
import kieran.app.address.model.StudentListWrapper;
import kieran.app.address.util.Email;
import kieran.app.address.util.OldTime;
import kieran.app.address.view.EmailController;
import kieran.app.address.view.MoreDetailsController;

import kieran.app.address.view.RootLayoutController;
import kieran.app.address.view.StudentEditDialogController;
import kieran.app.address.view.StudentOverviewController;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    Student student;
    MainApp mainApp;
    boolean wasRun;
    Date date = new Date();
    Timer timer = new Timer();
    OldTime oldTime = new OldTime();
   Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
    
     ObservableList<Student> studentData = FXCollections.observableArrayList();
    


    public MainApp() {
        // Add some data to list
        studentData.add(new Student("Ruth More", "Stephen"));
        studentData.add(new Student("Ruth Ryan", "Vika"));
        studentData.add(new Student("Biaina ", "Vika"));
        studentData.add(new Student("Jesy", "Kriss"));
        studentData.add(new Student("Helen Koloyan", "Dery"));
        studentData.add(new Student("Lidia", "Vova"));
        studentData.add(new Student("Anna More", "Baty"));
        
        
        
      
    }


    
//      Returns the data as an observable list of students. 
     
    
    public ObservableList<Student> getStudentData() {
        return studentData;
    }

public void sendEmail() throws IOException {
	
	//System.out.println(timestamp.getTime() + " old > new  " +(oldTime.getOldTime()+86400000));

   if (timestamp.getTime()>(oldTime.getOldTime()+86400000)){
	   
    	for( Student student : studentData ){
	
    	  if(student.getBirthday().getDayOfMonth() -1 == MonthDay.now().getDayOfMonth() && student.getBirthday().getMonthValue() == MonthDay.now().getMonthValue()){
         	  
     		 Email.sendEmail("viktoriaryan3@gmail.com","Biorhtday Reminder","Dear Viktoria tommorow is "+student.getStudentName()+ " birthday"  ,"viktoriaryan3@gmail.com","viktoriaryan3@gmail.com","karenvika13");
     		 oldTime.setTimeTxt();
       }
		
	}
   }
}


public void sendEmailNotification() {
	
		
	//for( Student student : studentData){
		
		//System.out.println(student.getEmail());
		  
 		 //Email.sendEmail(student.getEmail(),"Reminder", "" ,"krjava2016@gmail.com","krjava2016@gmail.com","23Google75");
       }


    

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("StudentApp");

        initRootLayout();

        showStudentOverview();
			
		//sendEmail(" have a birthday tommorow");
       
		
       
    }
       
       
  

    /*
     Initializes the root layout and tries to load the last opened
     student file.
     */
     
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened student file.
        File file = getStudentFilePath();
        if (file != null) {
            loadStudentDataFromFile(file);
        }
    }
  
    public void showStudentOverview() {
        try {
            // Load student overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/studentOverview.fxml"));
            AnchorPane studentOverview = (AnchorPane) loader.load();

            // Set student overview into the center of root layout.
            rootLayout.setCenter(studentOverview);

            // Give the controller access to the main app.
            StudentOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//     Returns the main stage.
    
   
    public Stage getPrimaryStage() {
        return primaryStage;
    }

   
    /*
     pens a dialog to edit details for the specified student. If the user
     clicks OK, the changes are saved into the provided student object and true
     is returned.
   */
    public boolean showStudentEditDialog(Student student) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StudentEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("StudentApp");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the student into the controller.
            StudentEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setStudent(student);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /*
      Returns the student file preference, i.e. the file that was last opened.
     
     */
    public File getStudentFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /*
      Sets the file path of the currently loaded file. The path is persisted in
      the OS specific registry.
     */
    public void setStudentFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("StudentApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("StudentApp");
        }
    }
    
    
    /*
      Loads student data from the specified file. The current student data will
      be replaced.
     */
    public void loadStudentDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(StudentListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            StudentListWrapper wrapper = (StudentListWrapper) um.unmarshal(file);

            studentData.clear();
            studentData.addAll(wrapper.getStudent());

            // Save the file path to the registry.
            setStudentFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    
//      Saves the current student data to the specified file.
     
    public void saveStudentDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(StudentListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our student data.
            StudentListWrapper wrapper = new StudentListWrapper();
            wrapper.setStudent(studentData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setStudentFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
    
    public boolean showMoreDetailsDialog(Student student) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MoreDetailsDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("More details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set controller.
            MoreDetailsController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPayment(student.getPaymentObj());
            controller.setMainApp(this);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
           
            
            
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
 
    
    public void showEmailNotificationDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EmailNotificationDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Email Notification");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set controller.
            EmailController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            
            
            //controller.setPayment(student.getPaymentObj());
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            //return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
    @Override
    public void stop(){
    	
    	
    	 System.exit(0);
    	
    }
    
    
    public static void main(String[] args) {
		launch(args);
	}
    
}
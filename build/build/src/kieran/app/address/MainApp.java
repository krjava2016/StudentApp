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
import kieran.app.address.model.Person;
import kieran.app.address.model.PersonListWrapper;
import kieran.app.address.util.Email;
import kieran.app.address.util.OldTime;
import kieran.app.address.view.EmailController;
import kieran.app.address.view.MoreDetailsController;
import kieran.app.address.view.PersonEditDialogController;
import kieran.app.address.view.PersonOverviewController;
import kieran.app.address.view.RootLayoutController;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    Person person;
    MainApp mainApp;
    boolean wasRun;
    Date date = new Date();
    Timer timer = new Timer();
    OldTime oldTime = new OldTime();
   Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
    
     ObservableList<Person> personData = FXCollections.observableArrayList();
    


    public MainApp() {
        // Add some data to list
        personData.add(new Person("Ruth More", "Stephen"));
        personData.add(new Person("Ruth Ryan", "Vika"));
        personData.add(new Person("Biaina ", "Vika"));
        personData.add(new Person("Jesy", "Kriss"));
        personData.add(new Person("Helen Koloyan", "Dery"));
        personData.add(new Person("Lidia", "Vova"));
        personData.add(new Person("Anna More", "Baty"));
        
        
        
      
    }


    
//      Returns the data as an observable list of Persons. 
     
    
    public ObservableList<Person> getPersonData() {
        return personData;
    }

public void sendEmail() throws IOException {
	
	//System.out.println(timestamp.getTime() + " old > new  " +(oldTime.getOldTime()+86400000));

   if (timestamp.getTime()>(oldTime.getOldTime()+86400000)){
	   
    	for( Person person : personData ){
	
    	  if(person.getBirthday().getDayOfMonth() -1 == MonthDay.now().getDayOfMonth() && person.getBirthday().getMonthValue() == MonthDay.now().getMonthValue()){
         	  
     		 Email.sendEmail("viktoriaryan3@gmail.com","Biorhtday Reminder","Dear Viktoria tommorow is "+person.getStudentName()+ " birthday"  ,"viktoriaryan3@gmail.com","viktoriaryan3@gmail.com","karenvika13");
     		 oldTime.setTimeTxt();
       }
		
	}
   }
}


public void sendEmailNotification() {
	
		
	//for( Person person : personData){
		
		//System.out.println(person.getEmail());
		  
 		 //Email.sendEmail(person.getEmail(),"Reminder", "" ,"krjava2016@gmail.com","krjava2016@gmail.com","23Google75");
       }


    

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("StudentApp");

        initRootLayout();

        showPersonOverview();
			
		//sendEmail(" have a birthday tommorow");
       
		
       
    }
       
       
  

    /*
     Initializes the root layout and tries to load the last opened
     person file.
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

        // Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }
  
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
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
     pens a dialog to edit details for the specified person. If the user
     clicks OK, the changes are saved into the provided person object and true
     is returned.
   */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Student");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /*
      Returns the person file preference, i.e. the file that was last opened.
     
     */
    public File getPersonFilePath() {
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
    public void setPersonFilePath(File file) {
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
      Loads person data from the specified file. The current person data will
      be replaced.
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // Save the file path to the registry.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    
//      Saves the current person data to the specified file.
     
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
    
    public boolean showMoreDetailsDialog(Person person) {
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
            controller.setPayment(person.getPaymentObj());
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
            
            
            //controller.setPayment(person.getPaymentObj());
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
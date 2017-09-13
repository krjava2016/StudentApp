package kieran.app.address.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import kieran.app.address.MainApp;

/*
  The controller for the root layout. The root layout provides the basic
  application layout containing a menu bar and space where other JavaFX
  elements can be placed.

 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

  
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    
     // Creates an empty address book.
     
    @FXML
    private void handleNew() {
        mainApp.getStudentData().clear();
        mainApp.setStudentFilePath(null);
    }

    
      //Opens a FileChooser to let the user select an address book to load.
     
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadStudentDataFromFile(file);
        }
    }

    /*
      Saves the file to the student file that is currently open. If there is no
      open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File studentFile = mainApp.getStudentFilePath();
        if (studentFile != null) {
            mainApp.saveStudentDataToFile(studentFile);
        } else {
            handleSaveAs();
        }
    }

    
    // Opens a FileChooser to let the user select a file to save to.
     
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveStudentDataToFile(file);
        }
    }

    
    // Opens an about.
     
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("StudentApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Kieran Ryan / Java Project 2017");

        alert.showAndWait();
    }

    
    // Closes the application.
     
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
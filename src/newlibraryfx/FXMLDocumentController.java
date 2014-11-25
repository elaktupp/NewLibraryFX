/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newlibraryfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kimmo
 */
public class FXMLDocumentController implements Initializable {
    
    private Library library;
    private User user;

    enum Operation {
        LOAN,
        RETURN,
        RESERVE,
        CANCEL
    };
    
    
    private Operation operation;
    
    // COMMON, ATTRIBUTES
    @FXML
    private HBox theUI;

    @FXML
    private VBox theIsbnQuery;
    
    @FXML
    private TextField textIsbn;
   
    // WELCOME, ATTRIBUTES
    @FXML
    private TitledPane paneWelcome;
        
    @FXML
    private TextField textFirstName;
    
    @FXML
    private TextField textLastName;

    @FXML
    private TextField textBirthday;
    
    @FXML
    private TextField textPassword;

    // CLIENT, ATTRIBUTES
    @FXML
    private TitledPane paneClient;
    
    @FXML
    private TextField textSearchIsbn;
    
    @FXML
    private TextField textSearchTitle;
    
    // ADMINISTRATOR, ATTRIBUTES
    @FXML
    private TitledPane paneAdmin;

    // RESULTS DISPLAY
    @FXML
    private TextArea textDisplay;
    
    // COMMON HANDLERS
    @FXML
    private void handleButtonSignOutAction(ActionEvent event) {
        userSignedOut();
    }
    
    @FXML
    private void handleButtonIsbnReady(ActionEvent event) {
        theIsbnQuery.setVisible(false);
        theUI.setDisable(false);

        if (textIsbn.getText().isEmpty() == false) {
            
            switch (operation) {
                case LOAN:
                    textDisplay.setText(library.loanItem(user,textIsbn.getText()));
                    break;
                case RETURN:
                    break;
                case RESERVE:
                    break;
                case CANCEL:
                    break;
                default:
                    break;
            }
            
        }
    }
    
    // WELCOME HANDLERS
    @FXML
    private void handleButtonSignInAction(ActionEvent event) {
        if (library.isAdmin(textFirstName.getText(),
                            textLastName.getText(),
                            textPassword.getText())) {
            adminSignedIn();
            user = new User(textFirstName.getText(),
                            textLastName.getText(),
                            textBirthday.getText(),
                            true);
        } else {
            userSignedIn();
            user = new User(textFirstName.getText(),
                            textLastName.getText(),
                            textBirthday.getText(),
                            false);
        }
        textFirstName.clear();
        textLastName.clear();
        textBirthday.clear();
        textPassword.clear();
    }
    
    // CLIENT HANDLERS
    @FXML
    private void handleButtonLoanItem(ActionEvent event) {
        theUI.setDisable(true);
        theIsbnQuery.setVisible(true);
        operation = Operation.LOAN;
    }
    
    @FXML
    private void handleButtonReturnItem(ActionEvent event) {
        
    }
    
    @FXML
    private void handleButtonReserveItem(ActionEvent event) {
        
    }
    
    @FXML
    private void handleButtonCancelItemReservation(ActionEvent event) {
        
    }
    
    @FXML
    private void handleButtonSearch(ActionEvent event) {
        String isbn = textSearchIsbn.getText();
        String title = textSearchTitle.getText();
        
        if (isbn.isEmpty() && title.isEmpty()) {
            textDisplay.setText("Enter either ISBN or Title for the search.\n");
        } else if (isbn.isEmpty() == false && title.isEmpty() == false) {
            textDisplay.setText("Enter either ISBN or Title, not both.\n");
        } else if (isbn.isEmpty()) {
            textDisplay.setText(library.searchAndPrintWithTitle(title));
        } else {
            textDisplay.setText(library.searchAndPrintWithIsbn(isbn));
        }
    }
    
    // ADMIN HANDLERS
    @FXML
    private void handleButtonListAllItems(ActionEvent event) {
        textDisplay.setText(library.printAllItems());
    }
    
    @FXML
    private void handleButtonListAllLoans(ActionEvent event) {
        textDisplay.setText(library.printLoanedItems());
    }

    @FXML
    private void handleButtonListAllOverdueLoans(ActionEvent event) {
        textDisplay.setText(library.printOverdueLoans());
    }
    
    @FXML
    private void handleButtonListAllReservations(ActionEvent event) {
        textDisplay.setText(library.printReservedItems());
    }
    
    @FXML
    private void handleButtonAddNewItem(ActionEvent event) {
        textDisplay.setText(library.printLoanedItems());
    }
    
    @FXML
    private void handleButtonListRemoveItem(ActionEvent event) {
        textDisplay.setText(library.printLoanedItems());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        userSignedOut(); // sets initial state for panes

        theIsbnQuery.setVisible(false);
        
        library = new Library(true); // true sets test mode!!!
        
        
    }    
    
    // UTILITIES
    private void userSignedIn() {
        paneWelcome.setExpanded(false);
        paneWelcome.setDisable(true);
        paneClient.setDisable(false);
        paneClient.setExpanded(true);
    }
    
    private void adminSignedIn() {
        paneWelcome.setExpanded(false);
        paneWelcome.setDisable(true);
        paneClient.setDisable(false);
        paneClient.setExpanded(false);
        paneAdmin.setDisable(false);
        paneAdmin.setExpanded(true);
    }
    
    private void userSignedOut() {
        paneClient.setDisable(true);
        paneClient.setExpanded(false);
        paneAdmin.setDisable(true);
        paneAdmin.setExpanded(false);
        paneWelcome.setDisable(false);
        paneWelcome.setExpanded(true);
    }
    
    
}

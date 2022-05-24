package dk.sdu.se_f22.searchmodule.misspellings;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MisspellingsController {

    private Misspellings misspelling;

    @FXML
    private TextField misInput, corInput, deleteInput, updateInput, updateCorrectionInput, filterInput;

    @FXML
    private TextArea outputAdd, outputDelete, outputUpdate, outputFilter;

    @FXML
    private Button show1, show2, show3, show4;

    public void initialize(){
        outputAdd.setText("");
        outputDelete.setText("");
        deleteInput.setText("");
        misspelling = new Misspellings();
    }

    public void addMis(ActionEvent event){
        if (!misInput.getText().isEmpty() && !corInput.getText().isEmpty()){
            if (misspelling.addMisspelling(misInput.getText(), corInput.getText())) {
                outputAdd.setText("The misspelling: \"" + misInput.getText() + "\" has been added. " +
                        "The correct spelling is: \"" + corInput.getText() + "\"\n\n");
            } else {
                outputAdd.setText("There was an error adding the misspelling.\n" +
                        "It probably already exists or a space character was used.\n\n");
            }
        } else if ((!misInput.getText().isEmpty() && corInput.getText().isEmpty()) || (misInput.getText().isEmpty() && !corInput.getText().isEmpty())){
            outputAdd.setText("Need input in both fields.\n\n");
        } else {
            outputAdd.setText("Need input.\n\n");
        }

    }

    public void deleteMis(ActionEvent event){
        if (!deleteInput.getText().isEmpty()) {
            if (misspelling.deleteMisspelling(deleteInput.getText())){
                outputDelete.setText("The misspelling: \"" + deleteInput.getText() + "\" has been deleted\n\n");
            } else {
                outputDelete.setText("There was an error deleting the misspelling.\n" +
                        "It possibly does not exist or there has been a spelling mistake.\n\n");
            }

        } else {
            outputDelete.setText("Need input.\n\n");
        }
    }

    public void updateMis(ActionEvent event){
        if (!updateInput.getText().isEmpty() && !updateCorrectionInput.getText().isEmpty()){
            if (misspelling.updateMisspelling(updateCorrectionInput.getText(), updateInput.getText())) {
                outputUpdate.setText("The misspelling: \"" + updateCorrectionInput.getText() + "\" has been updated. " +
                        "The the new (mis)spelling is: \"" + updateInput.getText() + "\"\n\n");
            } else {
                outputUpdate.setText("There was an error updating the misspelling.\n" +
                        "It possibly does not exist or there has been a spelling mistake.\n\n");
            }
        } else if ((!updateInput.getText().isEmpty() || updateCorrectionInput.getText().isEmpty()) || (updateInput.getText().isEmpty() || !updateCorrectionInput.getText().isEmpty())) {
            outputUpdate.setText("Need input in both fields.\n\n");
        } else  {
            outputUpdate.setText("Need input.\n\n");
        }
    }

    public void filterSearch(ActionEvent event){
        if (!filterInput.getText().isEmpty()){

            String[] stringArray = filterInput.getText().split(" ");
            ArrayList<String> arrayList = new ArrayList<>();

            for (String wordsIn: stringArray) {
                arrayList.add(wordsIn);
            }

            misspelling.filter(arrayList);
            String outputString = "";
            for (String wordsOut: arrayList) {
                outputString += wordsOut + " ";
            }
            outputFilter.setText(outputString);
        } else {
            outputFilter.setText("Need input.");
        }
    }

    public void showMisspellings(ActionEvent event){

        Object source = event.getSource();

        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM misspellings");
            ResultSet stmtResultSet = stmt.executeQuery();

            if (source == show1){
                outputAdd.setText("");
                while (stmtResultSet.next()) {
                    outputAdd.appendText("The misspelling: \""
                            + stmtResultSet.getString("wrong")
                            + "\" has the correct spelling: \""
                            + stmtResultSet.getString("correct")
                            + "\"\n");
                }
            }
            if (source == show2){
                outputDelete.setText("");
                while (stmtResultSet.next()) {
                    outputDelete.appendText("The misspelling: \""
                            + stmtResultSet.getString("wrong")
                            + "\" has the correct spelling: \""
                            + stmtResultSet.getString("correct")
                            + "\"\n");
                }
            }
            if (source == show3){
                outputUpdate.setText("");
                while (stmtResultSet.next()) {
                    outputUpdate.appendText("The misspelling: \""
                            + stmtResultSet.getString("wrong")
                            + "\" has the correct spelling: \""
                            + stmtResultSet.getString("correct")
                            + "\"\n");
                }
            }
            if (source == show4){
                outputFilter.setText("");
                while (stmtResultSet.next()) {
                    outputFilter.appendText("The misspelling: \""
                            + stmtResultSet.getString("wrong")
                            + "\" has the correct spelling: \""
                            + stmtResultSet.getString("correct")
                            + "\"\n");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

            String errorMessage = "There was an error loading the misspellings from the database.";

            if (source == show1){
                outputAdd.setText(errorMessage);
            }
            if (source == show2){
                outputAdd.setText(errorMessage);
            }
            if (source == show3){
                outputAdd.setText(errorMessage);
            }
            if (source == show4){
                outputAdd.setText(errorMessage);
            }
        }
    }

}

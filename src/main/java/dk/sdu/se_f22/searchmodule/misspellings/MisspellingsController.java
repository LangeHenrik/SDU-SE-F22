package dk.sdu.se_f22.searchmodule.misspellings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class MisspellingsController {

    private Misspellings misspelling;

    @FXML
    private TextField misInput, corInput, deleteInput, updateInput, updateCorrectionInput, filterInput;

    @FXML
    private TextArea outputAdd, outputDelete, outputUpdate, outputFilter;

    public void initialize(){
        outputAdd.setText("");
        outputDelete.setText("");
        deleteInput.setText("");
        misspelling = new Misspellings();
    }

    public void addMis(ActionEvent e){
        if (!misInput.getText().isEmpty() && !corInput.getText().isEmpty()){
            if (misspelling.addMisspelling(misInput.getText(), corInput.getText())) {
                outputAdd.appendText("The misspelling: \"" + misInput.getText() + "\" has been added. " +
                        "The correct spelling is: \"" + corInput.getText() + "\"\n\n");
            } else {
                outputAdd.appendText("There was an error adding the misspelling.\n" +
                        "It probably already exists or a space character was used.\n\n");
            }
        } else if ((!misInput.getText().isEmpty() && corInput.getText().isEmpty()) || (misInput.getText().isEmpty() && !corInput.getText().isEmpty())){
            outputAdd.appendText("Need input in both fields.\n\n");
        } else {
            outputAdd.appendText("Need input.\n\n");
        }

    }

    public void deleteMis(ActionEvent e){
        if (!deleteInput.getText().isEmpty()) {
            if (misspelling.deleteMisspelling(deleteInput.getText())){
                outputDelete.appendText("The misspelling: \"" + deleteInput.getText() + "\" has been deleted\n");
            } else {
                outputDelete.appendText("There was an error deleting the misspelling.\n" +
                        "It possibly does not exist or there has been a spelling mistake.\n\n");
            }

        } else {
            outputDelete.appendText("Need input.\n\n");
        }
    }

    public void updateMis(ActionEvent e){
        if (!updateInput.getText().isEmpty() && !updateCorrectionInput.getText().isEmpty()){
            if (misspelling.updateMisspelling(updateCorrectionInput.getText(), updateInput.getText())) {
                outputUpdate.appendText("The misspelling: \"" + updateInput.getText() + "\" has been updated. " +
                        "The the new (mis)spelling is: \"" + updateCorrectionInput.getText() + "\"\n\n");
            } else {
                outputUpdate.appendText("There was an error updating the misspelling.\n" +
                        "It possibly does not exist or there has been a spelling mistake.\n\n");
            }
        } else if ((!updateInput.getText().isEmpty() || updateCorrectionInput.getText().isEmpty()) || (updateInput.getText().isEmpty() || !updateCorrectionInput.getText().isEmpty())) {
            outputUpdate.appendText("Need input in both fields.\n\n");
        } else  {
            outputUpdate.appendText("Need input.\n\n");
        }
    }

    public void filterSearch(ActionEvent e){
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


}

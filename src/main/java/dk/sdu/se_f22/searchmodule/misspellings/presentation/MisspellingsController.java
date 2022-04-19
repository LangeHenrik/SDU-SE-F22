package dk.sdu.se_f22.searchmodule.misspellings.presentation;

import dk.sdu.se_f22.searchmodule.misspellings.Misspellings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class MisspellingsController {
    private Misspellings misspelling;

    @FXML
    Button add, update, delete, filter;

    @FXML
    TextField misInput, corInput, misUpInput, filterInput;

    @FXML
    TextArea output, outputFilter;

    @FXML
    Label lMis, lCor, lUpMis, lOut, lOutFilter, lFilterInput;

    public void addMis(){
        if (!misInput.getText().isEmpty() && !corInput.getText().isEmpty()){
            misspelling.addMisspelling(misInput.getText(), corInput.getText());
            output.setText("The misspelling: "+misInput.getText()+" has been added. The correct spelling is: "+corInput.getText());
        } else{
            output.setText("Write a misspelling and it's correct spelling");
        }

    }

    public void updateMis(){
        if (!misInput.getText().isEmpty() && !misUpInput.getText().isEmpty()){
            misspelling.updateMisspelling(misInput.getText(), misUpInput.getText());
            output.setText("The misspelling: "+misInput.getText()+" has been updated. The the new spelling is: "+misUpInput.getText());
        }else if (!corInput.getText().isEmpty()){
            output.setText("You have to write what misspelling you want to update and what the new updated misspelling is");
        }
        else{
            output.setText("Write what misspelling you want to update and what misspelling it should be instead");
        }
    }

    public void deleteMis(){
        if (!misInput.getText().isEmpty() && misUpInput.getText().isEmpty() && corInput.getText().isEmpty()) {
            misspelling.deleteMisspelling(misInput.getText());
            output.setText("The misspelling: " + misUpInput.getText() + " has been deleted");
        }else{
            output.setText("You only have to write what misspelling you want to delete");
        }
    }

    public void filterSearch(){
        if (!filterInput.getText().isEmpty()){
            ArrayList<String> arrayList = new ArrayList();
            for (String wordsIn: arrayList) {
                filterInput.getText().split(" ");
                arrayList.add(wordsIn);
            }
            misspelling.filter(arrayList);
            for (String wordsOut: arrayList) {
                outputFilter.setText(wordsOut);
            }
        }
    }


}

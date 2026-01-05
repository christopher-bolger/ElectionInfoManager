package electionInfoManager.view;

import electionInfoManager.model.election.Politician;
import electionInfoManager.model.javafx.Insertable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class createNewPolitician extends Insertable {
    public AnchorPane root;
    public TextField nameField;
    public ComboBox<String> affiliationField;
    public ComboBox<String> countyField;
    public TextField photoURLField;
    public DatePicker datePickerField;
    public Button saveButton;
    public Button cancel;
    private Politician politician;

    public void initialize(){
        affiliationField.getItems().addAll(Politician.ELECTION_PARTIES);
        countyField.getItems().addAll(Politician.COUNTIES);
    }

    @Override
    public AnchorPane getRoot() {
        return root;
    }

    public Politician getResult(){
        createPolitician();
        return politician;
    }

    @Override
    public void edit(Object object) {
        if(object instanceof Politician){
            politician = (Politician) object;
            nameField.setText(politician.getName());
            affiliationField.setValue(politician.getAffiliation());
            countyField.setValue(politician.getCounty());
            photoURLField.setText(politician.getPhotoURL());
            datePickerField.setValue(politician.getDOB());
        }
    }

    public void createPolitician() {
        String name = nameField.getText();
        String affiliation = affiliationField.getValue();
        String county = countyField.getValue();
        String photoURL = photoURLField.getText();
        LocalDate date = LocalDate.of(datePickerField.getValue().getYear(), datePickerField.getValue().getMonthValue(), datePickerField.getValue().getDayOfMonth());
        if(name != null && affiliation != null && county != null && photoURL != null)
            politician = new Politician(name, affiliation, county, photoURL, date);
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}

package electionInfoManager.view;

import electionInfoManager.model.election.Election;
import electionInfoManager.model.javafx.Insertable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class createNewElection extends Insertable {
    public AnchorPane root;
    public ComboBox<String> typeField;
    public TextField locationField;
    public TextField winnersField;
    public DatePicker datePickerField;

    public void initialize(){
        typeField.getItems().addAll(Election.ELECTION_TYPES);
    }

    @Override
    public AnchorPane getRoot() {
        return root;
    }

    @Override
    public Object getResult() {
        LocalDate date = LocalDate.of(datePickerField.getValue().getYear(), datePickerField.getValue().getMonthValue(), datePickerField.getValue().getDayOfMonth());
        String location = locationField.getText();
        int winners = Integer.parseInt(winnersField.getText());
        String type = typeField.getValue();
        if(date != null && location != null && type != null)
            return new Election(type, date, winners, location);
        return null;
    }

    @Override
    public void edit(Object object) {
        if(object instanceof Election) {
            Election election = (Election) object;
            datePickerField.setValue(election.getDate());
            locationField.setText(election.getElectionLocation());
            winnersField.setText(Integer.toString(election.getWinners()));
            typeField.setValue(election.getElectionType());
        }
    }
}

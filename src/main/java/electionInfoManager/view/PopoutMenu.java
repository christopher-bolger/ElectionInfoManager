package electionInfoManager.view;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import electionInfoManager.model.javafx.Insertable;

public class PopoutMenu{
    public BorderPane borderPlane;
    public Button saveButton;
    public Button cancelButton;
    public boolean cancelled = true;
    Object object;
    Insertable inserted;


    public void initialize(Insertable insertion) {
        borderPlane.setCenter(insertion.getRoot());
        inserted = insertion;
    }
    public void saveResults(ActionEvent actionEvent) {
        object = getResult();
        cancelled = false;
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public Object getResult(){
        return inserted.getResult();
    }
}

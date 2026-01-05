package electionInfoManager.view;

import electionInfoManager.controller.ElectionInfoManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class initialPopup{
    public Button loadButton;
    public Button newButton;
    public Text middleText;
    ElectionInfoManager electionInfoManager;

    public void openExplorer(ActionEvent actionEvent) throws Exception {
        electionInfoManager = new ElectionInfoManager("name");
        FileChooser file = new FileChooser();
        file.setTitle("Open XML File");
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File selected = file.showOpenDialog(loadButton.getScene().getWindow());
        if (selected != null) {
            electionInfoManager.load(selected);
        }
    }

    public void close(){
        Stage stage = (Stage) loadButton.getScene().getWindow();
        stage.close();
    }

    public void loadFile(ActionEvent actionEvent) throws Exception {
        openExplorer(actionEvent);
        close();
    }

    public void newManager(ActionEvent actionEvent) {
        close();
    }

    public ElectionInfoManager getResult() {
        return electionInfoManager;
    }
}


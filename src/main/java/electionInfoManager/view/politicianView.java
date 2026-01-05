package electionInfoManager.view;

import electionInfoManager.model.election.Politician;
import electionInfoManager.model.javafx.Insertable;
import electionInfoManager.utility.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class politicianView extends Insertable {
    public AnchorPane root;
    public ImageView imageViewer;
    public ListView<String> politicianDetailsListView;
    public Politician politician;

    private void updateList() throws IOException {
        politicianDetailsListView.getItems().clear();
        ObservableList<String> details = FXCollections.observableArrayList();
        details.add("Name: " + politician.getName());
        details.add("County: " + politician.getCounty());
        details.add("Affiliation: " + politician.getAffiliation());
        details.add("DOB: " + politician.getDOB().toString());
        politicianDetailsListView.getItems().addAll(details);
        String url = politician.getPhotoURL();
        Image photo = Utilities.loadImageWithUA(url);
        imageViewer.setImage(photo);
        imageViewer.setFitWidth(200);
        imageViewer.setFitHeight(150);
        imageViewer.setPreserveRatio(true);
        imageViewer.setSmooth(true);
    }

    @Override
    public AnchorPane getRoot() {
        return root;
    }

    @Override
    public Object getResult() {
        return null;
    }

    @Override
    public void edit(Object object) throws IOException {
        if(object instanceof Politician) {
            politician = (Politician) object;
            updateList();
        }
    }
}

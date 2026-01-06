package electionInfoManager.view;

import electionInfoManager.model.election.Election;
import electionInfoManager.model.election.ElectionEntry;
import electionInfoManager.model.election.Politician;
import electionInfoManager.model.javafx.Insertable;
import electionInfoManager.model.linkedlist.LinkedList;
import electionInfoManager.utility.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class politicianView extends Insertable {
    public AnchorPane root;
    public ImageView imageViewer;
    public ListView<String> politicianDetailsListView;
    public Politician politician;
    public Button viewElectionButton;
    public TableColumn<Election, String> electionType;
    public TableColumn<Election, String> electionDate;
    public TableColumn<Election, String> electionLocation;
    public TableColumn<Election, String> electionWinners;
    public Election selectedElection;
    public LinkedList<Election> elections;
    public LinkedList<Politician> politicians;
    public TableView<Election> electionTable;

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
        electionType.setCellValueFactory(new PropertyValueFactory<>("electionType"));
        electionWinners.setCellValueFactory(new PropertyValueFactory<>("winners"));
        electionLocation.setCellValueFactory(new PropertyValueFactory<>("electionLocation"));
        electionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        electionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        updateElectionTable();
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

    public void setElections(LinkedList<Election> e){
        if(e != null && !e.isEmpty())
            elections = e;
    }


    public LinkedList<Election> getElectionsWithPolitician(){
        LinkedList<Election> electionList = new LinkedList<>();
        for(Election e : elections) {
            if (e.containsPolitician(politician))
                electionList.add(e);
            System.out.println(e);
        }
        return electionList;
    }

    public void updateElectionTable(){
        ObservableList<Election> result = FXCollections.observableArrayList();
        result.addAll(getElectionsWithPolitician());
        electionTable.setItems(result);
    }

    public void viewSelectedElection(ActionEvent actionEvent) throws IOException {
        if(selectedElection == null)
            return;
        String path = "/electionView.fxml";

        FXMLLoader insertLoader = new FXMLLoader(getClass().getResource(path));
        Node insertNode = insertLoader.load();
        Insertable insert =  insertLoader.getController();
        if(insert instanceof electionView) {
            ((electionView) insert).setList(politicians);
            ((electionView) insert).setElections(elections);
        }

        FXMLLoader skeletonLoader = new FXMLLoader(getClass().getResource("/popoutSkeletonViews.fxml"));
        Parent skeletonRoot = skeletonLoader.load();
        PopoutMenu skeletonController = skeletonLoader.getController();

        skeletonController.initialize(insert); // Pass controller here
        insert.edit(selectedElection);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(skeletonRoot));
        stage.showAndWait();
    }

    public void setPoliticians(LinkedList<Politician> p){
        if(p != null && !p.isEmpty())
            politicians = p;
    }

    public void updateSelection(MouseEvent mouseEvent) {
        selectedElection = electionTable.getSelectionModel().getSelectedItem();
    }
}

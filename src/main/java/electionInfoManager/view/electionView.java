package electionInfoManager.view;

import electionInfoManager.controller.PoliticianManager;
import electionInfoManager.model.election.Election;
import electionInfoManager.model.election.ElectionEntry;
import electionInfoManager.model.election.Politician;
import electionInfoManager.model.javafx.Insertable;
import electionInfoManager.model.linkedlist.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class electionView extends Insertable {
    public AnchorPane root;
    public ListView<String> electionDetailsListView;
    public LinkedList<Politician> politicians;
    public Election election;
    public TableView<ElectionEntry> winnersView;
    public TableColumn<ElectionEntry, String> nameColumn;
    public TableColumn<ElectionEntry, String> affiliationColumn;
    public TableColumn<ElectionEntry, Integer> positionColumn;
    public TableColumn<ElectionEntry, Integer> votesColumn;

    public void initialize(){
        winnersView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    @Override
    public AnchorPane getRoot() {
        return root;
    }

    public void updateList(){
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Type: " + election.getElectionType());
        list.add("Date: " + election.getDate().toString());
        list.add("Location: " + election.getElectionLocation());
        list.add("Total Winners: " + election.getWinners());
        electionDetailsListView.setItems(list);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        affiliationColumn.setCellValueFactory(new PropertyValueFactory<>("affiliation"));
        votesColumn.setCellValueFactory(new PropertyValueFactory<>("votes"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        if(!election.isEmpty())
            for(ElectionEntry e : election.calculateResults())
                winnersView.getItems().add(e);
    }

    @Override
    public Object getResult() {
        return election;
    }

    @Override
    public void edit(Object object) throws IOException {
        if(object instanceof Election)
            election = (Election) object;
        updateList();
    }

    public void setList(LinkedList<Politician> p) {
        if(p != null)
            this.politicians = p;
    }

    public void openCandidateList(ActionEvent actionEvent) throws IOException {
        FXMLLoader insertLoader = new FXMLLoader(getClass().getResource("/addPoliticiansToElection.fxml"));
        Node insertNode = insertLoader.load();
        addPoliticiansToElection insert =  insertLoader.getController();

        FXMLLoader skeletonLoader = new FXMLLoader(getClass().getResource("/popoutSkeleton.fxml"));
        Parent skeletonRoot = skeletonLoader.load();
        PopoutMenu skeletonController = skeletonLoader.getController();

        skeletonController.initialize(insert); // Pass controller here
        insert.edit(politicians);
        insert.setElection(election);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(skeletonRoot));
        stage.showAndWait();
        election = skeletonController.cancelled ? election : (Election) insert.getResult();
    }

    public void viewSelectedCandidate(ActionEvent actionEvent) throws IOException {
        String path = "/politicianView.fxml";
        FXMLLoader insertLoader = new FXMLLoader(getClass().getResource(path));
        Node insertNode = insertLoader.load();
        Insertable insert =  insertLoader.getController();

        FXMLLoader skeletonLoader = new FXMLLoader(getClass().getResource("/popoutSkeleton.fxml"));
        Parent skeletonRoot = skeletonLoader.load();
        PopoutMenu skeletonController = skeletonLoader.getController();

        skeletonController.initialize(insert); // Pass controller here
        insert.edit(winnersView.getSelectionModel().getSelectedItem().getPolitician());

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(skeletonRoot));
        stage.showAndWait();
    }
}

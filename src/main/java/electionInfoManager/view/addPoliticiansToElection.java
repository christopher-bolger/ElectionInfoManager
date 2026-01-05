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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class addPoliticiansToElection extends Insertable {
    public AnchorPane root;
    public LinkedList<Politician> result = new LinkedList<>();
    public LinkedList<Politician> list;
    public Politician additionSelection;
    public ElectionEntry removalSelection;
    public Election election;
    public TextField votesField;
    public TextField politicianSearchField;
    public TableView<Politician> politicianView;
    public TextField candidateSearchField;
    public TableView<ElectionEntry> candidateView;
    public TableColumn<Politician, String> politicianNameColumn;
    public TableColumn<Politician, String> politicianAffiliationColumn;
    public TableColumn<Politician, String> politicianCountyColumn;
    public TableColumn<ElectionEntry, Integer> candidateVoteColumn;
    public TableColumn<ElectionEntry, String> candidateNameColumn;
    public TableColumn<ElectionEntry, String> candidateAffiliationColumn;
    public TableColumn<ElectionEntry, String> candidateCountyColumn;
    public TextField candidateVotes;
    public Button removeCandidateButton;
    public Button updateCandidateVotesButton;

    @Override
    public AnchorPane getRoot() {
        return root;
    }

    public void setTables(){
        politicianNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        politicianAffiliationColumn.setCellValueFactory(new PropertyValueFactory<>("affiliation"));
        politicianCountyColumn.setCellValueFactory(new PropertyValueFactory<>("county"));
        candidateVoteColumn.setCellValueFactory(new PropertyValueFactory<>("votes"));
        candidateAffiliationColumn.setCellValueFactory(new PropertyValueFactory<>("affiliation"));
        candidateCountyColumn.setCellValueFactory(new PropertyValueFactory<>("county"));
        candidateNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        candidateView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        politicianView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    public void searchPoliticians(){

    }

    public void searchCandidates(){

    }

    public void updateLists(){
        ObservableList<Politician> politicians = FXCollections.observableArrayList();
        politicians.addAll(list);
        politicianView.getItems().clear();
        politicianView.setItems(politicians);
        updateCandidates();
    }

    public void updateCandidates(){
        ObservableList<ElectionEntry> candidates = FXCollections.observableArrayList();
        for(ElectionEntry e : election.getCandidates())
            if(e != null)
                candidates.add(e);
        candidateView.getItems().clear();
        candidateView.setItems(candidates);
    }

    @Override
    public Object getResult() {
        return election;
    }

    @Override
    public void edit(Object object) throws IOException {
        list = (LinkedList<Politician>) object;
    }

    public void setElection(Election e){
        if(e != null)
            election = e;
        setTables();
        updateLists();
    }

    public void addSelected(ActionEvent actionEvent) {
        election.add(additionSelection, Integer.parseInt(votesField.getText()));
        updateCandidates();
    }

    public void updateAdditionSelection(MouseEvent mouseEvent) {
        additionSelection = politicianView.getSelectionModel().getSelectedItem();
    }

    public void updateRemovalSelection(MouseEvent mouseEvent) {
        ElectionEntry candidate = candidateView.getSelectionModel().getSelectedItem();
        removalSelection = candidate;
        candidateVotes.setText(Integer.toString(candidate.getVotes()));
    }

    public void removeSelected(ActionEvent actionEvent) {
        System.out.println(election.remove(removalSelection.getPolitician()));
        updateCandidates();
    }

    public void updateCandidateVotes(ActionEvent actionEvent) {
        ElectionEntry candidate = election.find(candidateView.getSelectionModel().getSelectedItem());
        candidate.setVotes(Integer.parseInt(candidateVotes.getText()));
        System.out.println(candidateView.getSelectionModel().getSelectedItem().getPolitician());
        System.out.println(candidateVotes.getText());
        System.out.println(candidateView.getSelectionModel().getSelectedItem().getVotes());
        updateCandidates();
    }
}

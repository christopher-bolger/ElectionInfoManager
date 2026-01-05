package electionInfoManager.view;

import electionInfoManager.controller.PoliticianManager;
import electionInfoManager.model.election.Election;
import electionInfoManager.model.election.ElectionEntry;
import electionInfoManager.model.election.Politician;
import electionInfoManager.model.javafx.Insertable;
import electionInfoManager.model.linkedlist.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Arrays;

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
    public ComboBox<String> candidatesOptions;
    public String politicianFilter, candidateFilter;
    public ComboBox<String> politiciansOptions;

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

        ObservableList<String> result = FXCollections.observableArrayList();
        result.addAll(Politician.FIELDS);
        politiciansOptions.setItems(result);
        politiciansOptions.getSelectionModel().clearAndSelect(0);
        politicianFilter = politiciansOptions.getSelectionModel().getSelectedItem();

        ObservableList<String> result2 = FXCollections.observableArrayList();
        result2.addAll(Politician.FIELDS);
        result2.add("Votes");
        candidatesOptions.setItems(result2);
        candidatesOptions.getSelectionModel().clearAndSelect(0);
        candidateFilter = candidatesOptions.getSelectionModel().getSelectedItem();
    }

    public void searchPoliticians(){
        //public final static String[] FIELDS = {"Name", "Affiliation", "County", "PhotoURL", "DOB"};
        LinkedList<Politician> result = new LinkedList<>();
        String text = politicianSearchField.getText();
        for(ElectionEntry e : election.getCandidates()){
            Politician p = e.getPolitician();
            switch(politicianFilter){
                case "Name" -> {
                    if(p.getName().toLowerCase().contains(text.toLowerCase()))
                        result.add(p);
                }
                case "Affiliation" -> {
                    if(p.getAffiliation().toLowerCase().contains(text.toLowerCase()))
                        result.add(p);
                }
                case "County" -> {
                    if(p.getCounty().toLowerCase().contains(text.toLowerCase()))
                        result.add(p);
                }
                case "DOB" -> {
                    if(p.getDOB().toString().contains(text.toLowerCase()))
                        result.add(p);
                }
                default -> {
                    if(p.getPhotoURL().toLowerCase().contains(text.toLowerCase()))
                        result.add(p);
                }
            }
        }
        updatePoliticianList(result);
    }

    public void updatePoliticianList(LinkedList<Politician> list){
        politicianView.getItems().clear();
        if(list == null || list.isEmpty())
            return;
        ObservableList<Politician> result = FXCollections.observableArrayList();
        result.addAll(list);
        politicianView.setItems(result);
    }

    public void searchCandidates(){
        LinkedList<ElectionEntry> result = new LinkedList<>();
        String text = candidateSearchField.getText();
        for(ElectionEntry e : election.getCandidates()){
            switch(candidateFilter){
                case "Name" -> {
                    if(e.getPolitician().getName().toLowerCase().contains(text.toLowerCase()))
                        result.add(e);
                }
                case "Affiliation" -> {
                    if(e.getPolitician().getAffiliation().toLowerCase().contains(text.toLowerCase()))
                        result.add(e);
                }
                case "County" -> {
                    if(e.getPolitician().getCounty().toLowerCase().contains(text.toLowerCase()))
                        result.add(e);
                }
                case "DOB" -> {
                    if(e.getPolitician().getDOB().toString().contains(text.toLowerCase()))
                        result.add(e);
                }
                case "PhotoURL" -> {
                    if(e.getPolitician().getPhotoURL().toLowerCase().contains(text.toLowerCase()))
                        result.add(e);
                }
                default -> {
                    if(e.getVotes() == Integer.parseInt(text))
                        result.add(e);
                }
            }
        }
        updateCandidateList(result);
    }

    public void updateCandidateList(LinkedList<ElectionEntry> list){
        candidateView.getItems().clear();
        if(list == null || list.isEmpty())
            return;
        ObservableList<ElectionEntry> result = FXCollections.observableArrayList();
        result.addAll(list);
        candidateView.setItems(result);
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
            if(e != null && !candidates.contains(e))
                candidates.add(e);
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
        ElectionEntry candidate = candidateView.getSelectionModel().getSelectedItem();
        candidate.setVotes(Integer.parseInt(candidateVotes.getText()));
        election.updateVotes(candidate.getPolitician(), Integer.parseInt(candidateVotes.getText()));
        candidateView.refresh();
    }

    public void updateCandidateFilter(ActionEvent actionEvent) {
        candidateFilter = candidatesOptions.getSelectionModel().getSelectedItem();
    }

    public void updatePoliticianFilter(ActionEvent actionEvent) {
        politicianFilter = politiciansOptions.getSelectionModel().getSelectedItem();
    }
}

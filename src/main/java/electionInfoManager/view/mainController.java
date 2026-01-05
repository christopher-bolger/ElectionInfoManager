package electionInfoManager.view;

import electionInfoManager.controller.ElectionInfoManager;
import electionInfoManager.model.election.Election;
import electionInfoManager.model.election.Politician;
import electionInfoManager.model.javafx.Insertable;
import electionInfoManager.model.linkedlist.LinkedList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;

public class mainController{
    public HBox content;
    public MenuItem newManager;
    public MenuItem loadManager;
    public MenuItem saveManager;
    public MenuItem closeWindow;
    public MenuItem editButton;
    public MenuItem deleteButton;
    public TextField nameField;
    public Button saveNewManager;
    public Button viewButton;
    public TableView<Politician> politicianTableView;
    public TableColumn<Politician, Integer> politicianIndex;
    public TableColumn<Politician, String> politicianName;
    public TableColumn<Politician, String> politicianAffiliation;
    public TableColumn<Politician, String> politicianCounty;
    public TableColumn<Politician, String> politicianDOB;
    public TableView<Election> electionTableView;
    public TableColumn<Election, Integer> electionIndex;
    public TableColumn<Election, String> electionType;
    public TableColumn<Election, String> electionLocation;
    public TableColumn<Election, String> electionDate;
    public TableColumn<Election, Integer> electionWinners;
    public ComboBox<String> searchOptionsComboBox;
    public ComboBox<String> searchOptionsElectionsComboBox;
    public String searchFilter;
    public TextField searchBox;
    public TextField searchBox1;
    ElectionInfoManager manager;
    public MenuBar menuBar;
    Object selectedEntity;

    public void initialize(){

    }

    public void updateSelectedEntity(){
        if(electionTableView.isFocused())
            selectedEntity = electionTableView.getSelectionModel().getSelectedItem();
        else
            selectedEntity = politicianTableView.getSelectionModel().getSelectedItem();
        highlightSelectedEntity();
        viewButton.setDisable(false);
    }

    public void highlightSelectedEntity(){
        if(selectedEntity == null)
            return;
        if(selectedEntity instanceof Politician)
            politicianTableView.getSelectionModel().select((Politician)selectedEntity);
        if(selectedEntity instanceof Election)
            electionTableView.getSelectionModel().select((Election)selectedEntity);
    }

    public void updatePoliticianView(){
        LinkedList<Politician> politicians = manager.getPoliticians();
        politicianTableView.getItems().clear();
        if(politicians == null || politicians.isEmpty())
            return;
//        ObservableList<Politician> data = FXCollections.observableArrayList(politicians);
        ObservableList<Politician> data = FXCollections.observableArrayList();
        data.addAll(politicians);
        politicianTableView.setItems(data);
    }

    public void updateElectionView(){
        LinkedList<Election> elections = manager.getElections();
        electionTableView.getItems().clear();
        if(elections == null || elections.isEmpty())
            return;
        ObservableList<Election> data = FXCollections.observableArrayList();
        data.addAll(elections);
        electionTableView.setItems(data);
    }

    public void editPolitician() throws IOException {
        Politician oldPolitician = (Politician) selectedEntity, updatedPolitician;
        updatedPolitician = (Politician) insertPopupEdit("/newPolitician.fxml", oldPolitician);
        if(updatedPolitician == null)
            return;
        if(manager.updatePolitician(oldPolitician, updatedPolitician))
            updatePoliticianView();
    }

    public void editElection() throws IOException {
        Election oldElection = (Election) selectedEntity, updatedElection;
        updatedElection = (Election) insertPopupEdit("/newElection.fxml", oldElection);
        if(updatedElection == null)
            return;
        manager.updateElection(oldElection, updatedElection);
        updateElectionView();
    }

    public void viewSelectedItem() throws IOException {
        if(selectedEntity == null)
            return;
        String path;
        if(selectedEntity instanceof Politician)
            path = "/politicianView.fxml";
        else
            path = "/electionView.fxml";

        FXMLLoader insertLoader = new FXMLLoader(getClass().getResource(path));
        Node insertNode = insertLoader.load();
        Insertable insert =  insertLoader.getController();
        if(insert instanceof electionView)
            ((electionView) insert).setList(manager.getPoliticians());

        FXMLLoader skeletonLoader = new FXMLLoader(getClass().getResource("/popoutSkeletonViews.fxml"));
        Parent skeletonRoot = skeletonLoader.load();
        PopoutMenu skeletonController = skeletonLoader.getController();

        skeletonController.initialize(insert); // Pass controller here
        insert.edit(selectedEntity);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(skeletonRoot));
        stage.showAndWait();
    }

    public void setTables(){
        politicianIndex.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(politicianTableView.getItems().indexOf(cd.getValue()) + 1));
        politicianName.setCellValueFactory(new PropertyValueFactory<>("name"));
        politicianAffiliation.setCellValueFactory(new PropertyValueFactory<>("affiliation"));
        politicianCounty.setCellValueFactory(new PropertyValueFactory<>("county"));
        politicianDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        electionIndex.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(electionTableView.getItems().indexOf(cd.getValue()) + 1));
        electionType.setCellValueFactory(new PropertyValueFactory<>("electionType"));
        electionWinners.setCellValueFactory(new PropertyValueFactory<>("winners"));
        electionLocation.setCellValueFactory(new PropertyValueFactory<>("electionLocation"));
        electionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        politicianTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(Politician.FIELDS);
        searchOptionsComboBox.setItems(list);
        searchOptionsComboBox.getSelectionModel().clearAndSelect(0);

        ObservableList<String> list2 = FXCollections.observableArrayList();
        list2.addAll(Election.FIELDS);
        searchOptionsElectionsComboBox.setItems(list2);
        searchOptionsElectionsComboBox.getSelectionModel().clearAndSelect(0);
    }

    public void openExplorer(ActionEvent actionEvent) throws Exception {
        manager = new ElectionInfoManager("Name");
        FileChooser file = new FileChooser();
        file.setTitle("Open XML File");
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File selected = file.showOpenDialog(menuBar.getScene().getWindow());
        if (selected != null) {
            manager.load(selected);
            insertIntoHbox("/Lists.fxml");
            setTables();
            updatePoliticianView();
            updateElectionView();
        }
    }

    public FXMLLoader getLoader(String insertPath) throws IOException {
        return new FXMLLoader(getClass().getResource(insertPath));
    }

    public Object insertPopup(String insertPath) throws IOException {
        FXMLLoader insertLoader = new FXMLLoader(getClass().getResource(insertPath));
        Node insertNode = insertLoader.load();
        Insertable insert =  insertLoader.getController();

        FXMLLoader skeletonLoader = new FXMLLoader(getClass().getResource("/popoutSkeleton.fxml"));
        Parent skeletonRoot = skeletonLoader.load();
        PopoutMenu skeletonController = skeletonLoader.getController();

        skeletonController.initialize(insert); // Pass controller here

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(skeletonRoot));
        stage.showAndWait();
        return skeletonController.cancelled ? null : insert.getResult();
    }

    public Object insertPopupEdit(String insertPath, Object itemToEdit) throws IOException {
        FXMLLoader insertLoader = new FXMLLoader(getClass().getResource(insertPath));
        Node insertNode = insertLoader.load();
        Insertable insertController = insertLoader.getController();

        FXMLLoader skeletonLoader = new FXMLLoader(getClass().getResource("/popoutSkeleton.fxml"));
        Parent skeletonRoot = skeletonLoader.load();
        PopoutMenu skeletonController = skeletonLoader.getController();

        skeletonController.initialize(insertController);
        insertController.edit(itemToEdit);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(skeletonRoot));
        stage.showAndWait();
        return skeletonController.cancelled ? null : insertController.getResult();
    }

    public void insertIntoHbox(String insertPath) throws IOException {
        FXMLLoader insert = getLoader(insertPath);
        insert.setController(this);
        Node child = insert.load();
        content.getChildren().setAll(child);
    }

    public void newManager(ActionEvent actionEvent) throws IOException {
        insertIntoHbox("/newManager.fxml");
    }

    public void createNewManager(ActionEvent actionEvent) throws IOException {
        if(manager != null)
            manager.save();
        if(!nameField.getText().isEmpty())
            manager = new ElectionInfoManager(nameField.getText());
        insertIntoHbox("/Lists.fxml");
        setTables();
    }

    public void loadManager(ActionEvent actionEvent) throws Exception {
        openExplorer(actionEvent);
    }

    public void saveManager(ActionEvent actionEvent) throws IOException {
        manager.save();
    }

    public void closeWindow(ActionEvent actionEvent) throws IOException {
        if(manager != null)
            saveManager(actionEvent);
        Stage stage = (Stage) content.getScene().getWindow();
        stage.close();
    }

    public void startAddPolitician(ActionEvent actionEvent) throws IOException {
        Politician p = (Politician) insertPopup("/newPolitician.fxml");
        if(p != null)
            if(manager.addPolitician(p))
                updatePoliticianView();
    }

    public void startAddElection(ActionEvent actionEvent) throws IOException {
        Election e = (Election) insertPopup("/newElection.fxml");
        if(e != null)
            if(manager.addElection(e))
                updateElectionView();
    }

    public void deleteSelectedEntity(ActionEvent actionEvent) {
        if(selectedEntity == null)
            return;
        if(selectedEntity instanceof Politician)
            deleteSelectedPolitician();
        else
            deleteSelectedElection();
    }

    public void deleteSelectedPolitician(){
        Politician p = (Politician) selectedEntity;
        manager.removeEveryInstance(p);
        updatePoliticianView();
    }

    public void deleteSelectedElection(){
        Election e = (Election) selectedEntity;
        manager.removeElection(e);
        updateElectionView();
    }

    public void editSelectedEntity(ActionEvent actionEvent) throws IOException {
        if(selectedEntity == null)
            return;
        if(selectedEntity instanceof Politician)
            editPolitician();
        if(selectedEntity instanceof Election)
            editElection();
    }

    public void updateSearchFilter(ActionEvent actionEvent) {
        if(politicianTableView.isFocused())
            searchFilter = searchOptionsComboBox.getValue();
        else
            searchFilter = searchOptionsElectionsComboBox.getValue();
    }

    public void searchElections(ActionEvent actionEvent){
        updateSearchFilter(actionEvent);
        System.out.println(searchBox1.getText());
        showElectionResults(manager.searchElections(searchFilter, searchBox1.getText()));
    }

    public void showElectionResults(LinkedList<Election> list){
        electionTableView.getItems().clear();
        if(list == null || list.isEmpty())
            return;
        ObservableList<Election> result = FXCollections.observableArrayList();
        result.addAll(list);
        electionTableView.setItems(result);
    }

    public void searchPoliticians(ActionEvent actionEvent) {
        updateSearchFilter(actionEvent);
        System.out.println(searchBox.getText());
        showPoliticianResults(manager.searchPoliticians(searchFilter, searchBox.getText()));
    }

    public void showPoliticianResults(LinkedList<Politician> list){
        System.out.println("Going to highlight now");
        System.out.println(list);
        politicianTableView.getItems().clear();
        if(list == null || list.isEmpty())
            return;
        ObservableList<Politician> results = FXCollections.observableArrayList();
        results.addAll(list);
        politicianTableView.setItems(results);
    }

    public void sortByNameASC(ActionEvent actionEvent){
        manager.sortPoliticians(Comparator.comparing(Politician::getName));
        updatePoliticianView();
    }

    public void sortByNameDESC(ActionEvent actionEvent){
        manager.sortPoliticians(Comparator.comparing(Politician::getName).reversed());
    }
}

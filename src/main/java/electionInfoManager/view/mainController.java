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
        if(politicians == null || politicians.isEmpty())
            return;
//        ObservableList<Politician> data = FXCollections.observableArrayList(politicians);
        ObservableList<Politician> data = FXCollections.observableArrayList();
        data.addAll(politicians);
        politicianTableView.getItems().clear();
        politicianTableView.setItems(data);
    }

    public void updateElectionView(){
        LinkedList<Election> elections = manager.getElections();
        if(elections == null || elections.isEmpty())
            return;
        ObservableList<Election> data = FXCollections.observableArrayList();
        data.addAll(elections);
        electionTableView.getItems().clear();
        electionTableView.setItems(data);
    }

    public void editPolitician() throws IOException {
        Politician oldPolitician = (Politician) selectedEntity, updatedPolitician;
        updatedPolitician = (Politician) insertPopupEdit("/newPolitician.fxml", oldPolitician);
        if(updatedPolitician == null)
            return;
        if(manager.updatePolitician(oldPolitician, updatedPolitician))
            System.out.println("Updated politician");
        else
            System.out.println("Failed to update politician");
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

        FXMLLoader skeletonLoader = new FXMLLoader(getClass().getResource("/popoutSkeleton.fxml"));
        Parent skeletonRoot = skeletonLoader.load();
        PopoutMenu skeletonController = skeletonLoader.getController();

        skeletonController.initialize(insert); // Pass controller here
        insert.edit(selectedEntity);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(skeletonRoot));
        stage.showAndWait();
    }

    public void viewPolitician() throws IOException {

    }

    public void viewElection() throws IOException {
        FXMLLoader insertLoader = new FXMLLoader(getClass().getResource("/electionView.fxml"));
        Node insertNode = insertLoader.load();
        Insertable insert =  insertLoader.getController();

        FXMLLoader skeletonLoader = new FXMLLoader(getClass().getResource("/popoutSkeleton.fxml"));
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
    }

    public void editSelectedEntity(ActionEvent actionEvent) throws IOException {
        if(selectedEntity == null)
            return;
        if(selectedEntity instanceof Politician)
            editPolitician();
        if(selectedEntity instanceof Election)
            editElection();
    }
}

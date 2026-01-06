package electionInfoManager.controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import electionInfoManager.model.election.Election;
import electionInfoManager.model.election.ElectionEntry;
import electionInfoManager.model.election.Politician;
import electionInfoManager.model.hashmap.CustomHashMap;
import electionInfoManager.model.hashmap.HashMapNode;
import electionInfoManager.model.linkedlist.LinkedList;

import java.io.*;
import java.util.Comparator;

public class ElectionInfoManager{
    private final ElectionManager elections = new ElectionManager();
    private final PoliticianManager politicians = new PoliticianManager();
    private String name;
    private File file;

    public ElectionInfoManager(String name){
        if(name != null && !name.isEmpty())
            this.name = name;
        else this.name = "unnamed";
        file = new File(name + ".xml");
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        String oldName = this.name;
        if(name != null && !name.isEmpty())
            this.name = name;
        else this.name = "unnamed";
        if(!oldName.equals(this.name))
            file = new File(name);
    }

    public boolean addPolitician(Politician p){
        return politicians.add(p);
    }

    public boolean removePolitician(Politician p){
        return politicians.remove(p) == p;
    }

    public boolean removeEveryInstance(Politician p){
        if(politicians.find(p) != null){
            for(Election e : elections.getList()){
                if(e.getPolitician(new ElectionEntry(p)) != null)
                    e.remove(p);
            }
            politicians.remove(p);
            return true;
        }
        return false;
    }

    public boolean updatePolitician(Politician current, Politician updated){
        return politicians.updatePolitician(current, updated);
    }

    public boolean updateElection(Election current, Election updated){
        return elections.updateElection(current, updated);
    }

    public LinkedList<Politician> getPoliticians(){
        return politicians.getList();
    }

    public void setPoliticians(LinkedList<Politician> p){
        if(p != null && !p.isEmpty())
            politicians.set(p);
    }

    public LinkedList<Election> getElections(){
        return elections.getList();
    }

    public CustomHashMap<Integer, Election> getElectionsMap(){
        return elections.getMap();
    }

    public void setElectionsMap(CustomHashMap<Integer, Election> e){
        elections.setMap(e);
    }

    public void setElections(LinkedList<Election> e){
        if(e != null && !e.isEmpty())
            elections.set(e);
    }

    public CustomHashMap<Integer, Politician> getPoliticiansMap(){
        return politicians.getMap();
    }

    public void setPoliticiansMap(CustomHashMap<Integer, Politician> p){
        politicians.setMap(p);
    }

    public boolean addElection(Election e){
        return elections.add(e);
    }

    public boolean removeElection(Election e){
        if(elections.find(e) != null)
            return elections.remove(e) == e;
        return false;
    }

    public File getFile(){
        return file;
    }

    private void setFile(File file){
        if(file != null)
            this.file = file;
    }

    public void save() throws IOException {
        var xstream = new XStream(new DomDriver());
        ObjectOutputStream os = xstream.createObjectOutputStream(new FileWriter(file));
        os.writeObject(this);
        os.close();
    }

    public void load(File file) throws IOException, ClassNotFoundException {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{ElectionInfoManager.class, ElectionManager.class, PoliticianManager.class, Politician.class, Election.class, ElectionEntry.class, CustomHashMap.class, HashMapNode.class, LinkedList.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        //XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(file));
        ElectionInfoManager loaded = (ElectionInfoManager) in.readObject();
        in.close();
        setName(loaded.getName());
        setFile(loaded.getFile());
        setPoliticians(loaded.getPoliticians());
        setPoliticiansMap(loaded.getPoliticiansMap());
        setElections(loaded.getElections());
        setElectionsMap(loaded.getElectionsMap());
    }

    public LinkedList<Politician> searchPoliticians(String searchFilter, String text) {
        return politicians.search(searchFilter, text);
    }

    public LinkedList<Election> searchElections(String searchFilter, String text) {
        return elections.search(searchFilter, text);
    }

    public void sortPoliticians(Comparator<Politician> comparing) {
        politicians.sort(comparing);
    }

    public void sortElections(Comparator<Election> comparing){
        elections.sort(comparing);
    }

    public Election getElection(Election e){
        return getElectionsMap().get(e.hashCode());
    }

    public Politician getPolitician(Politician p){
        return getPoliticiansMap().get(p.hashCode());
    }
}

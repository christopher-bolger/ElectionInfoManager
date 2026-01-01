package controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import model.election.Election;
import model.election.ElectionEntry;
import model.election.Politician;
import java.io.*;

public class ElectionInfoManager{
    private ElectionManager elections = new ElectionManager();
    private PoliticianManager politicians = new PoliticianManager();
    private String name;
    private File file;

    public ElectionInfoManager(String name){
        if(name != null && !name.isEmpty())
            this.name = name;
        else this.name = "unnamed";
        file = new File(name);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        String oldname = this.name;
        if(name != null && !name.isEmpty())
            this.name = name;
        else this.name = "unnamed";
        if(!oldname.equals(this.name))
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
            politicians.remove(p);
            for(Election e : elections.getList()){
                if(e.getPolitician(new ElectionEntry(p)) != null)
                    e.remove(p);
            }
            return true;
        }
        return false;
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

    public void load() throws IOException, ClassNotFoundException {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{ElectionInfoManager.class, ElectionManager.class, PoliticianManager.class, Politician.class, Election.class, ElectionEntry.class};

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
    }
}

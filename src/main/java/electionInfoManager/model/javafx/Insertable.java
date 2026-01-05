package electionInfoManager.model.javafx;

import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public abstract class Insertable extends AnchorPane {
    public abstract AnchorPane getRoot();

    public abstract Object getResult();
    public abstract void edit(Object object) throws IOException;
}

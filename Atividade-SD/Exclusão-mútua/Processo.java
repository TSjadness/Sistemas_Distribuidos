import java.io.Serializable;

public class Processo implements Serializable {
    private int id;

    public Processo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

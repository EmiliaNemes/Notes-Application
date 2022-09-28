package app;

public class ListedNote {

    private int id;
    private String title;

    public ListedNote(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}

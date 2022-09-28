package app;

public class Note {

    static int COUNTER = 0;
    private int id;
    private String title;
    private String content;

    public Note(String title, String content) {
        this.id = COUNTER++;
        this.title = title;
        this.content = content;
    }

    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}

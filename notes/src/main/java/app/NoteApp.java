package app;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NoteApp {

    static Path DIRNAME;

    public void executeCommands(String[] args){
        if(args.length == 0){
            System.out.println("ERROR: No arguments!");
        } else {
            if (args[0].equals("-add")) {
                executeAddCommand(args);
            } else {
                if (args[0].equals("-list")) {
                    executeListCommand(args);
                } else {
                    System.out.println("ERROR: Invalid command!");
                }
            }
        }
    }

    public void createNotesDirectory(){
        String absolutePath = Paths.get("").toAbsolutePath().toString();
        Path dir = Paths.get(absolutePath + "/notes");

        createDirectory(dir);
        Note.COUNTER = Objects.requireNonNull(new File(String.valueOf(DIRNAME)).list()).length;
    }

    private void createDirectory(Path dir) {
        if(Files.notExists(dir)){
            try {
                DIRNAME = Files.createDirectory(dir);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else {
            DIRNAME = dir;
        }
    }

    private void executeAddCommand(String[] args) {
        if(args.length != 3){ // every note must have a title and content
            System.out.println("ERROR: Invalid number of parameters!");
        } else {
            Note note = new Note(args[1], args[2]);
            addNote(note);
        }
    }

    public void addNote(Note note){
        try {
            Path path = Paths.get(DIRNAME + "\\" + "note_" + note.getId() + ".txt");
            String allContent = note.getTitle() + "\n" + note.getContent();
            Path filePath = Files.createFile(path);
            Files.write(filePath, allContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeListCommand(String[] args) {
        if(args.length > 2){
            System.out.println("ERROR: Invalid number of parameters!");
        } else {
            if (args.length == 1) { // list all notes
                listAllNotes();
            } else { // list specific note
                listSpecificNote(args[1]);
            }
        }
    }

    private void listAllNotes() {
        List<String> titles = getAllNoteTitles();
        for (String noteTitle : titles) {
            System.out.println(noteTitle);
        }
    }

    public List<String> getAllNoteTitles(){
        List<String> titles = new ArrayList<>();
        try{
            List<String> files = listFilesUsingFilesList();
            titles = getTitlesFromFiles(files);
        } catch(IOException e){
            e.printStackTrace();
        }
        return titles;
    }

    public List<String> listFilesUsingFilesList() throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(String.valueOf(DIRNAME)))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }

    private List<String> getTitlesFromFiles(List<String> files) {
        List<String> titles = new ArrayList<>();
        for(String fileName: files){
            titles.add(getNoteTitleForFile(fileName));
        }
        return titles;
    }

    private String getNoteTitleForFile(String fileName) {
        try {
            String absolutePath = DIRNAME + "/" + fileName;
            List<String> allContent = Files.readAllLines(Paths.get(absolutePath));
            return allContent.get(0);
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private void listSpecificNote(String title) {
        CreateNote createNote = getNote(title);
        if (createNote == null) {
            System.out.println("No note was found with such title!");
        } else {
            System.out.println(createNote.getTitle());
            System.out.println(createNote.getContent());
        }
    }

    public CreateNote getNote(String noteTitle) {
        try{
            List<String> files = listFilesUsingFilesList();
            return getNoteByTitle(noteTitle, files);
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private CreateNote getNoteByTitle(String noteTitle, List<String> files) {
        for(String fileName: files){
            CreateNote createNote = readFile(fileName);
            if (createNote != null) {
                if (noteTitle.equals(createNote.getTitle())){
                    return createNote;
                }
            }
        }
        return null;
    }

    public Note getNoteById(int noteId) {
        CreateNote createNote = readFile("note_" + noteId + ".txt");
        return new Note(noteId, createNote.getTitle(), createNote.getContent());
    }

    private int getNoteId(String fileName) {
        String patternString = "note_|\\.txt";
        Pattern pattern = Pattern.compile(patternString);
        String[] split = pattern.split(fileName);
        return Integer.parseInt(split[1]);
    }

    private CreateNote readFile(String fileName) {
        try {
            String absolutePath = DIRNAME + "/" + fileName;
            List<String> lines = Files.readAllLines(Paths.get(absolutePath));
            return new CreateNote(lines.get(0), lines.get(1));
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<ListedNote> getNoteSpecifications(){
        List<ListedNote> notes = new ArrayList<>();
        try{
            List<String> files = listFilesUsingFilesList();
            notes = getAllListedNotes(files);
        } catch(IOException e){
            e.printStackTrace();
        }
        return notes;
    }

    private List<ListedNote> getAllListedNotes(List<String> files) {
        List<ListedNote> notes = new ArrayList<>();
        for(String fileName: files){
            CreateNote note = readFile(fileName);
            int id = getNoteId(fileName);
            notes.add(new ListedNote(id, note.getTitle()));
        }
        return notes;
    }
}


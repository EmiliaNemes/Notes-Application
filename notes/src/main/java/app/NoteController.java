package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("")
public class NoteController {

    @Autowired
    private NoteApp noteApp;

    @PostMapping("/add")
    public ResponseEntity<Note> addNote(@RequestBody CreateNote createNote){
        Note note = new Note(createNote.getTitle(), createNote.getContent());
        noteApp.addNote(note);
        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ListedNote>> getAllNotes(){
        List<ListedNote> notes = noteApp.getNoteSpecifications();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") int id){
        Note note = noteApp.getNoteById(id);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }
}

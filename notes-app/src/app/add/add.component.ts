import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { CreateNote } from '../model/CreateNote';
import { NoteService } from '../service/note-service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  noteForm = new FormGroup({
    title: new FormControl(''),
    content: new FormControl(''),
  });

  constructor(private noteService: NoteService, private router: Router) { }

  ngOnInit(): void {
  }

  public onAddNote(): void {
   
    const newNote: CreateNote = {
      title: this.noteForm.get('title')!.value,
      content: this.noteForm.get('content')!.value,
    };

    console.log(newNote.title)
    console.log(newNote.content)

    this.noteService.addNote(newNote).subscribe(
      () => {
        this.noteForm.reset();
        this.router.navigate(['/list-notes']).then(() => {
          window.location.reload();
        });
      }, 
      (error: HttpErrorResponse) => {
        alert(error.message);
        this.noteForm.reset();
      }
    );
    
  }
}

import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ListedNote } from '../model/ListedNote';
import { Note } from '../model/Note';
import { NoteDialogComponent } from '../note-dialog/note-dialog.component';
import { NoteService } from '../service/note-service';

@Component({
  selector: 'app-list-notes',
  templateUrl: './list-notes.component.html',
  styleUrls: ['./list-notes.component.css']
})
export class ListNotesComponent implements OnInit {
  public notes: ListedNote[] = [];
  public selectedNote: Note | null | undefined;

  constructor(private noteService: NoteService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.getNotes();
  }

  public getNotes(): void {
    this.noteService.getNotes().subscribe(
      (response: ListedNote[]) => {
        this.notes = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public openDialog(id: number): void {
    this.noteService.getNote(id).subscribe(
      (response: Note) => {
        this.selectedNote = response;

        const dialogRef = this.dialog.open(NoteDialogComponent, {
          data: { title: this.selectedNote!.title, content: this.selectedNote!.content },
        });
    
        dialogRef.afterClosed().subscribe(() => {
          console.log('The dialog was closed');
        });

      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

}

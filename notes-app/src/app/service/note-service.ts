import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Note } from '../model/Note';
import { CreateNote } from '../model/CreateNote';
import { ListedNote } from '../model/ListedNote';

@Injectable({
  providedIn: 'root'
})
export class NoteService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public addNote(note: CreateNote): Observable<Note> {
    return this.http.post<Note>(`${this.apiServerUrl}/add`, note);
  }

  public getNotes(): Observable<ListedNote[]> {
    return this.http.get<ListedNote[]>(`${this.apiServerUrl}/all`);
  }

  public getNote(noteId: number | null | undefined): Observable<Note> {
    return this.http.get<Note>(`${this.apiServerUrl}/find/${noteId}`);
  }
}

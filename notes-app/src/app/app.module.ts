import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule, routingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AddComponent } from './add/add.component';
import { ListNotesComponent } from './list-notes/list-notes.component';
import { MatDialogModule } from '@angular/material/dialog';
import { NoteDialogComponent } from './note-dialog/note-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    AddComponent,
    ListNotesComponent,
    NoteDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

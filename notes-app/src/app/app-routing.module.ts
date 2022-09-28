import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddComponent } from './add/add.component';
import { ListNotesComponent } from './list-notes/list-notes.component';

const routes: Routes = [
  {path: 'add', component: AddComponent},
  {path: 'list-notes', component: ListNotesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [AddComponent, ListNotesComponent]

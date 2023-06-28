import {Component, OnInit, ViewChild} from '@angular/core';
import {Dependency} from "./dependency";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {DependencyService} from "./dependency.service";
import {NgForm} from "@angular/forms";
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit{
  public dependencies: Dependency[] | undefined;
  public searchedDependencies: Dependency[] | undefined;
  title = 'FrontEnd';

  selectedFiles: FileList | undefined;
  currentFileUpload: File | any = null;
  @ViewChild('searchKeyword') searchKeyword: any;
  @ViewChild('addForm') addForm: NgForm | undefined;

  constructor(private dependencyService: DependencyService) { }

  ngOnInit() {
    this.getDependencies();
  }

  public getDependencies(): void{
    this.dependencyService.getDependencies().subscribe(
      (response: Dependency[]) => {
        this.dependencies = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public addDependency(addForm: NgForm): void {
    // addDependency is a service making a call to the backend, we have to subscribe to it so that we can be notified whenever there is something that comes back from the server
    this.upload(); //This will upload the selected file to the server only if the form values are valid
    this.dependencyService.addDependency(addForm.value).subscribe(
      //Waiting for a response
      (response: Dependency) => {
        this.getDependencies();
      },

      //If no response arrives then error condition
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    this.addForm = addForm;
    this.clearForm();
  }

  clearForm(){
    this.addForm?.reset();
  }

  selectFile({event}: { event: any }){
    this.selectedFiles = event.target.files;
  }

  upload(){
    this.currentFileUpload = this.selectedFiles?.item(0);

    this.dependencyService.pushFileToStorage(this.currentFileUpload).subscribe(
      event => {
        this.selectedFiles = undefined;
      }
    );
  }

  downloadOnButtonClick(value: any){
    this.dependencyService.download(value)
      .subscribe(
        blob => (saveAs(blob, value))
      );
  }

  search(keyWord: string){
    this.dependencyService.searchDependencyByKeyword(keyWord).subscribe(
      (response: Dependency[]) => {
        this.searchedDependencies = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    this.clearSearch();
  }

  clearSearch(){
    this.searchKeyword.nativeElement.value = '';
  }
}

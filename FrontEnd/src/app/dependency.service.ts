import { Observable } from "rxjs";
import {Injectable} from "@angular/core";
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Dependency} from "./dependency";
import {environment} from "../environments/environment";
import {Form} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})

export class DependencyService{
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }

  public getDependencies(): Observable<Dependency[]>{
    return this.http.get<Dependency[]>(`${this.apiServerUrl}/dependencies/all`);
  }

  public searchDependencyByKeyword(keyword: string): Observable<Dependency[]>{
    return this.http.get<Dependency[]>(`${this.apiServerUrl}/dependencies/find/${keyword}`);
  }

  public addDependency(dependency: FormData): Observable<Dependency>{
    return this.http.post<Dependency>(`${this.apiServerUrl}/dependencies/add`, dependency);
  }

  public updateDependency(dependency: Dependency): Observable<Dependency>{
    return this.http.put<Dependency>(`${this.apiServerUrl}/dependencies/update`, dependency);
  }

  public deleteDependency(dependencyId: number): Observable<void>{
    return this.http.delete<void>(`${this.apiServerUrl}/dependencies/delete/${dependencyId}`);
  }

  pushFileToStorage(file: File): Observable<void>{
    const data: FormData = new FormData();
    data.append('file', file);
    return this.http.post<void>(`${this.apiServerUrl}/dependencies/uploadFile`, data);
  }

  download(file: string | undefined): Observable<Blob> {
    return this.http.get(`${this.apiServerUrl}/dependencies/downloadFile/${file}`, {
      responseType: 'blob'
    });
  }
}

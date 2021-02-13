import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Course} from "../interfaces/course.interface";
import {Material} from "../interfaces/material.interface";

@Injectable({
  providedIn: 'root'
})
export class MaterialService {

  url = 'api/materials';

  constructor(private http: HttpClient) {
  }

  getMaterialsByCourseId(courseId: number): Observable<Material[]> {
    return this.http.get<Material[]>(`${this.url}/all/${courseId}`)
  }

}

import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Course} from "../interfaces/course.interface";

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  url = 'api/courses';

  constructor(private http: HttpClient) {
  }

  getAllCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.url}/all`);
  }

}

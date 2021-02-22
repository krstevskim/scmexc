import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Course} from "../interfaces/course.interface";
import {Material} from "../interfaces/material.interface";
import {PagedUser} from "../interfaces/user/PagedUser";
import {PagedMaterial} from "../interfaces/material/PagedMaterial";

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

  getAllMaterialsPaged(
    sortBy = 'id',
    sortOrder = 'asc',
    pageSize = 10,
    page = 0,
    query = '',
    course = null
  ): Observable<PagedMaterial> {
    return this.http.get<PagedMaterial>(`${this.url}/paged` , {
      params: new HttpParams()
        .set('sort', `${sortBy},${sortOrder}`)
        .set('pageSize', pageSize.toString())
        .set('page', page.toString())
        .set('q', query)
        .set('course', course)
    })
  }
}

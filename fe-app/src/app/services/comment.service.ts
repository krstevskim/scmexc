import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Comment} from "../interfaces/comment.interface";

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  url = 'api/comments';

  constructor(private http: HttpClient) {
  }

  addNewComment(request: any): Observable<Comment> {
    return this.http.post<Comment>(`${this.url}/create`, request);
  }

  getAllCommentsByMaterialId(materialId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.url}/all/${materialId}`);
  }

}

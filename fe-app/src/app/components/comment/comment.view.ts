import {Component, Input, OnInit} from '@angular/core';
import {Comment} from "../../interfaces/comment.interface";

@Component({
  selector: 'comment',
  templateUrl: './comment.view.html',
  styleUrls: ['./comment.view.scss']
})
export class CommentView implements OnInit {

  @Input() comment: Comment;
  constructor() { }

  ngOnInit(): void {
  }

}

import {Component, Input, OnInit} from '@angular/core';
import {Comment} from "../../interfaces/comment.interface";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/auth/authentication.service";

@Component({
  selector: 'comment-wrapper',
  templateUrl: './comment-wrapper.view.html',
  styleUrls: ['./comment-wrapper.view.scss']
})
export class CommentWrapperView implements OnInit {

  @Input() comments: Comment[];

  commentForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthenticationService) { }

  ngOnInit(): void {
    this.commentForm = this.formBuilder.group({
      comment: ['', [Validators.required]]
    });
  }

  get f() {
    return this.commentForm.controls;
  }

  submit() {
    //submit comment
  }
}

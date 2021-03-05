import {Component, Input, OnInit} from '@angular/core';
import {Item} from "../../../interfaces/material/item.interface";
import {ItemType} from "../../../interfaces/material/item-type.enum";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {NotifierService} from "angular-notifier";
import {BehaviorSubject} from "rxjs";
import {MaterialService} from "../../../services/material.service";
import {Material} from "../../../interfaces/material/material.interface";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {MaxSizeValidator} from "@angular-material-components/file-input";

@Component({
  selector: 'item-create-wrapper',
  templateUrl: './item-create-wrapper.component.html',
  styleUrls: ['./item-create-wrapper.component.scss']
})
export class ItemCreateWrapperComponent implements OnInit {


  @Input() items: Item[];
  @Input() material: Material;

  currentEditItem: Item;
  itemTypes = ItemType;
  itemForm: FormGroup;
  newItemType: ItemType;
  fileToUpload: File = null;
  fileControl: FormControl;
  progress:number;
  newItemEmitter: BehaviorSubject<Item>;

  constructor(private builder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute,
              private _notifierService: NotifierService,
              private service: MaterialService) { }

  ngOnInit(): void {
    this.currentEditItem = {type: null, name: null, url: null, question: null, itemID: null, timePosted: null, questionID: null};
    this.newItemType = null;
    this.initForm();
    this.fileControl = new FormControl(this.fileToUpload, [
      Validators.required,
      MaxSizeValidator(16 * 1024)
    ])
    this.fileControl.valueChanges.subscribe((files: any) => {
      this.fileToUpload = files;
    })
  }

  initForm() {
    this.itemForm = this.builder.group({
      description: ['', Validators.required]
    })
  }

  get f() {
    return this.itemForm.controls;
  }

  submit() {
    if(this.newItemType == ItemType.FILE || this.newItemType == ItemType.IMAGE) {
      this.uploadFileToActivity();
      location.reload();
    }
    if(this.newItemType == ItemType.QUESTION) {
      console.log('here')
      let request = this.itemForm.value;
      request.materialID = this.material.id;
      request.itemID = this.currentEditItem.itemID;
      this.service.addQuestion(request).subscribe(el => {
        this._notifierService.notify('success', 'Successfully saved question.');
        location.reload();
      }, error => {
        this._notifierService.notify('error', 'Error saving question.');
        location.reload();
      });
    }

  }
  downloadFile(item: Item) {
    window.open(item.url);
  }

  uploadFileToActivity() {
    if(this.fileToUpload != null) {
      this.service.upload(this.material.id, this.fileToUpload).subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
         this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          console.log(event.body.message);
        }
      }, error => {
        console.log(error);
      });
    }
  }

  changeCurrentEditItem(item: Item) {
    if(item.type == ItemType.QUESTION) {
      this.currentEditItem = item;
      this.newItemType = item.type;
      this.itemForm.patchValue({description:item.question});
    }
  }

  canEdit(type: ItemType) {
    return type == ItemType.QUESTION;
  }

  deleteItem(item: Item) {
    //DELETE
    this.service.deleteItem(item.itemID).subscribe(el => {
      this._notifierService.notify('success', 'Successfully deleted item.');
    }, error => {
      this._notifierService.notify('error', 'Error deleting item.');
    });
  }

}

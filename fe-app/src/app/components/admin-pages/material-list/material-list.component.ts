import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormControl} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Router} from "@angular/router";
import {debounceTime, distinctUntilChanged, tap} from "rxjs/operators";
import {merge} from "rxjs";
import {MaterialsDataSource} from "../../../interfaces/material/MaterialsDataSource";
import {MaterialService} from "../../../services/material.service";
import {CourseService} from "../../../services/course.service";
import {Material} from "../../../interfaces/material.interface";

@Component({
  selector: 'app-material-list',
  templateUrl: './material-list.component.html',
  styleUrls: ['./material-list.component.scss']
})
export class MaterialListComponent implements OnInit, AfterViewInit {

  search = new FormControl('');

  option = new FormControl(null);

  searchQuery = '';
  course = null;
  dataSource: MaterialsDataSource;
  displayedColumns = ['id','title', 'createdBy', 'dateCreated', 'published'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private materialService: MaterialService,
              private router: Router,
              private courseService: CourseService) {
  }

  ngOnInit(): void {
    this.dataSource = new MaterialsDataSource(this.materialService);
    this.search.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(query => {
      this.searchQuery = query;
      this.dataSource.loadMaterials('id', 'asc', 10, 0, query);
    })
    this.dataSource.loadMaterials();
  }

  ngAfterViewInit(): void {

    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(tap(() => this.loadMaterials()))
      .subscribe();
  }

  loadMaterials() {
    this.dataSource.loadMaterials(
      this.sort.active,
      this.sort.direction,
      this.paginator.pageSize,
      this.paginator.pageIndex,
      this.searchQuery
    );
  }

  onUserClick(row: any) {
    // this.router.navigate(['/', 'user', row.id]);
  }

  publish(material: Material) {
    console.log("published", material)
  }

  unPublish(material: Material) {
    console.log("unpublished", material)
  }
}

import { Component, OnInit } from '@angular/core';;
import { BooksService } from '../crud-example/books.service';
import { MessageService } from '../messages/message.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css'],
  providers:[MessageService, BooksService]
})
export class TestComponent implements OnInit {

 public config:string =  `{
         "apiUrl": "assets/books.json",
         "messages": {}
      }`;

  constructor() { }

  ngOnInit() {
  }
}




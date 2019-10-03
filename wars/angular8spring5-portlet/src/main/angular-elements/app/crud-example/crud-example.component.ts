import { Component, OnInit, Input } from '@angular/core';
import { BooksService } from './books.service';
import { Book } from './Book';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MessageService } from '../messages/message.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'crud-example',
  templateUrl: './crud-example.component.html',
  styleUrls: ['./crud-example.component.css'],
  providers: [
    MessageService, 
    BooksService
  ]
})
export class CrudExampleComponent implements OnInit {
  @Input() config: any;
  apiUrl: string;
  messages: any;

  books: Book[];
  book: Book;
  loadingSubject = new BehaviorSubject<boolean>(false);
  loading$:Observable<boolean> = this.loadingSubject.asObservable();
  public form: FormGroup;

  constructor(private fb: FormBuilder, private booksService:BooksService, private messageService:MessageService) { }

  ngOnInit() {
    this.config = JSON.parse(this.config);
    this.apiUrl = this.config["apiUrl"];
    this.messages = this.config["messages"];

    this.form = this.fb.group({
      isbn: ['', Validators.required],
      title: ['', Validators.required],
      author: ['', Validators.required]
    });

    this.loadBooks();
  }

  loadBooks() {
    this.loadingSubject.next(true);
    this.booksService.list(this.apiUrl).pipe(
      finalize(() => this.loadingSubject.next(false))
    ).subscribe(
      books => this.books = books
    );
  }

  save() {
    this.loadingSubject.next(true);
    let book:Book = <Book>this.form.value;
    this.booksService.put(this.apiUrl, book).pipe(
      finalize(() => this.loadingSubject.next(false))
    ).subscribe(
      success => {
        if (success) {
          this.messageService.success(success.message);
          this.loadBooks();
        }
      }
    )
  }

  delete(book: Book) {
    this.loadingSubject.next(true);
    this.booksService.delete(this.apiUrl, book.isbn).pipe(
      finalize(() => this.loadingSubject.next(false))
    ).subscribe(
      success => {
        if (success) {
          this.messageService.success(success.message);
          this.loadBooks();
        }
      }
    );
  }

  select(book:Book) {
    this.form.patchValue(book);
  }
}

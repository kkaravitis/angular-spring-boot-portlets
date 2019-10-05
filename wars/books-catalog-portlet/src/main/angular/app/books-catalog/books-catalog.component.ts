import { Component, OnInit, Input } from '@angular/core';
import { BooksService } from './books.service';
import { Book } from './Book';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MessageService } from '../messages/message.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { ConfigurationService } from '../configuration.service';

@Component({
  selector: 'books-catalog',
  templateUrl: './books-catalog.component.html',
  styleUrls: ['./books-catalog.component.css'],
  providers: [
    MessageService, 
    BooksService
  ]
})
export class BooksCatalogComponent implements OnInit {
  @Input() config: string;
  books: Book[];
  book: Book;
  loadingSubject = new BehaviorSubject<boolean>(false);
  loading$:Observable<boolean> = this.loadingSubject.asObservable();
  public form: FormGroup;

  constructor(private fb: FormBuilder, private booksService:BooksService, 
    private messageService:MessageService, private configurationService:ConfigurationService) { }

  ngOnInit() {
    this.configurationService.init(this.config);

    this.form = this.fb.group({
      isbn: ['', Validators.required],
      title: ['', Validators.required],
      author: ['', Validators.required]
    });

    this.loadBooks();
  }

  loadBooks() {
    this.loadingSubject.next(true);
    this.booksService.listBooks().pipe(
      finalize(() => this.loadingSubject.next(false))
    ).subscribe(
      books => this.books = books
    );
  }

  save() {
    this.loadingSubject.next(true);
    let book:Book = <Book>this.form.value;
    this.booksService.putBook(book).pipe(
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
    this.booksService.deleteBook(book.isbn).pipe(
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

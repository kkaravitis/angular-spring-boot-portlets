import { Injectable } from '@angular/core';
import { MessageService } from '../messages/message.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Book } from './Book';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { REST_PATH } from '../http-interceptors/PortletInterceptor';

export interface Success {
  message:string;
}

@Injectable()
export class BooksService {
  
  constructor(private http: HttpClient, private messageService:MessageService) { }
  
  public list(apiUrl: string): Observable<Book[]> {
    return this.http.get<Book[]>(apiUrl, {
      params: new HttpParams().set(REST_PATH, `/books`)
    }).pipe(
      catchError(error => {
        this.messageService.error(error.error["message"]);
        return of([]);
      })
    );
  }

  public put(apiUrl: string, book: Book): Observable<Success> {
    return this.http.put<Success>(apiUrl, book, { 
      params: new HttpParams().set(REST_PATH, `/book/${book.isbn}`)
    }).pipe(
      catchError(error => {
        this.messageService.error(error.error["message"]);
        return of(null);
      })
    );
  }

  public delete(apiUrl:string, isbn:string) : Observable<Success> {
    return this.http.delete<Success>(apiUrl, { 
      params: new HttpParams().set(REST_PATH, `/book/${isbn}`)
    }).pipe(
      catchError(error => {
        this.messageService.error(error.error["message"]);
        return of(null);
      })
    );
   }
}


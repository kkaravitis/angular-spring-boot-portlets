import { Injectable } from '@angular/core';

@Injectable()
export class MessageService {
  messages: string[] = [];
  errors: string[] = [];
  successMsg: string;

  add(message: string) {
    this.messages.push(message);
  }
  
  clear() {
    this.messages = [];
    this.successMsg = null;
    this.errors = [];
  }

  clearInfo() {
    this.messages = [];
  }

  success(message:string) {
    this.clear();
    this.successMsg = message;
  }

  error(message:string) {
    this.errors.push(message);
  }

  info(message:string) {
    this.messages.push(message);
  }

  getMessage(map:any, key:string) : string {
    return map ? (map[key] ? map[key] : key) : key;
  }
}

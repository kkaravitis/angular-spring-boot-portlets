import { Injectable } from '@angular/core';
import { Configuration } from './Configuration';

@Injectable()
export class ConfigurationService {
  private config:Configuration;

  init(json:string) {
    this.config = JSON.parse(json);
  }

  getConfig():Configuration {
    return this.config;
  }
}

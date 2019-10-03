import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injector } from '@angular/core';
import { DemoMaterialModule } from './demo-material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http'; 
import { TestComponent } from './test/test.component';
import { NativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CrudExampleComponent } from './crud-example/crud-example.component';
import { MessagesComponent } from './messages/messages.component';
import { httpInterceptorProviders } from './http-interceptors';
import {createCustomElement} from '@angular/elements';

@NgModule({
  declarations: [
    TestComponent,
    MessagesComponent,
    CrudExampleComponent
  ],
  imports: [
    BrowserModule, 
    DemoMaterialModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    NativeDateModule,
    FontAwesomeModule
  ],
  providers: [
    httpInterceptorProviders
  ],
  entryComponents: [
    CrudExampleComponent
  ],
   //Uncomment bootstrap to run the TestComponent as an independent app 
   // bootstrap: [TestComponent]
})

export class AppModule {
  // Comment out the constructor and ngDoBootstrap method when you want to bootstrap the TestComponent.
  constructor(private injector: Injector) {
      customElements.define('crud-example', createCustomElement(CrudExampleComponent, { injector }));
  }
  ngDoBootstrap() {}
}

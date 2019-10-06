# Angular 8 with Spring boot 2 portlets on Liferay 7.2
Examples for creating secure jsr286 or later restfull portlets on liferay 7.2 with angular 8 and Spring boot 2.
Every portlet is in wars folder and in modules folder there are the services apis produced by service builder.

## Books catalog example
A basic crud application where only authenticated users of portal can view the stored books on database and can add, update or delete a book of them.

This portlet leverages the power of angular custom elements and the powerful capabilities of Spring Boot with respect on liferay service builder as regards the persistence layer.

<img src=screenshots/authorized.png />

## How to build and deploy the modules
1) Install the npm dependencies to angular app by running <br/>
<code>npm install --prefix wars/books-catalog-portlet</code>

2) Run <code>npm run build:portlet --prefix wars/books-catalog-portlet</code>

3) Run <code>mvn install</code>

4) Run <code>mvn verify</code>

# liferay-7.2-angular8-spring5-portlet
Examples for creating secure jsr362 restfull portlets on liferay 7.2 with angular 8 and Spring 5.
Every portlet is in wars folder and in modules folder there are the services apis produced by service builder.

## Books catalog example
<img src=screenshots/authorized.png />

## How to build and deploy the modules
1) Install the npm dependencies to angular app by running <br/>
<code>npm install --prefix wars/books-catalog-portlet</code>

2) Run <code>npm run build:portlet --prefix wars/books-catalog-portlet</code>

3) Run <code>mvn install</code>

4) Run <code>mvn verify</code>

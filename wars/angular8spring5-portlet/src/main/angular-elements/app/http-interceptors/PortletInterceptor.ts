import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";

export const REST_PATH = "rest_path";

const REST_METHOD = "rest_method";

const METHOD_MAP = {
    "PUT" : "POST",
    "DELETE": "POST",
    "GET" : "GET",
    "POST": "POST"
};

@Injectable()
export class PortletInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler) : Observable<HttpEvent<any>> {
        // Resource request accept only the GET and POST http methods.
        const portletReq = req.clone({
            method: METHOD_MAP[req.method.toUpperCase()],
            params: req.params.set(REST_METHOD, req.method)
        });
        // send the cloned, "secure" request to the next handler.
        return next.handle(portletReq);
    }
}
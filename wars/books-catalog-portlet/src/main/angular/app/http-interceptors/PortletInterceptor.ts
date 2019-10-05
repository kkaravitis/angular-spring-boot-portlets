import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { ConfigurationService } from "../configuration.service";

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
    public constructor(private configurationService: ConfigurationService) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler) : Observable<HttpEvent<any>> {
        const portletReq = req.clone({
            url: this.configurationService.getConfig().apiUrl,
            method: METHOD_MAP[req.method.toUpperCase()],
            params: req.params.set(REST_PATH, req.url).set(REST_METHOD, req.method)
        });
        return next.handle(portletReq);
    }
}
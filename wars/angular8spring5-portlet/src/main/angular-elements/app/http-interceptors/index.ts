import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { PortletInterceptor } from "./PortletInterceptor";

export const httpInterceptorProviders = [
    { provide: HTTP_INTERCEPTORS, useClass: PortletInterceptor, multi: true },
];
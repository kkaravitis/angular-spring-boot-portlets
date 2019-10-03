import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { PortletInterceptor } from "./PortletInterceptor";

/** Http interceptor providers in outside-in order */
export const httpInterceptorProviders = [
    { provide: HTTP_INTERCEPTORS, useClass: PortletInterceptor, multi: true },
];
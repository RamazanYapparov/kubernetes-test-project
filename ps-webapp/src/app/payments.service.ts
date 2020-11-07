import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Payment} from "./payment.model";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PaymentsService {

  constructor(private readonly httpClient: HttpClient) { }

  readonly getPayments = () => this.httpClient.get<Payment[]>(environment.paymentsUrl)
  readonly addPayment = (payment: Payment) => this.httpClient.post<Payment>(environment.paymentsUrl, payment)
}

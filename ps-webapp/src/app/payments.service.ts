import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Payment} from "./payment.model";

const PAYMENTS_SERVICE_URL = 'http://localhost:8080/payments';

@Injectable({
  providedIn: 'root'
})
export class PaymentsService {

  constructor(private readonly httpClient: HttpClient) { }

  readonly getPayments = () => this.httpClient.get<Payment[]>(PAYMENTS_SERVICE_URL)
  readonly addPayment = (payment: Payment) => this.httpClient.post<Payment>(PAYMENTS_SERVICE_URL, payment)
}

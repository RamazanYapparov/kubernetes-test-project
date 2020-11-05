import {Component} from '@angular/core';
import {PaymentsService} from "./payments.service";
import {flatMap, map, mergeMap, tap} from "rxjs/operators";

@Component({
    selector: 'app-root',
    template: `
        <h2>Payments System</h2>
        <div>
            <input [(ngModel)]="from" type="text" placeholder="From">
            <input [(ngModel)]="to" type="text" placeholder="To">
            <input [(ngModel)]="amount" type="number" placeholder="Amount">
            <button (click)="handlePayment()">Pay!</button>
        </div>
        <table style="border: 1px solid black">
            <tr>
                <th>From:</th>
                <th>To:</th>
                <th>Amount:</th>
                <th>Date:</th>
                <th>Status:</th>
            </tr>
            <tr *ngFor="let payment of payments | async">
                <td>{{payment.from}}</td>
                <td>{{payment.to}}</td>
                <td>{{payment.amount}}</td>
                <td>{{payment.date | date}}</td>
                <td>{{payment.status}}</td>
            </tr>
        </table>
    `,
    styles: [`
        table {
            width: 80%;
            border-collapse: collapse;
        }

        td, th {
            padding: 3px;
            border: 1px solid black;
        }
        
        div {
            margin-bottom: 2px;
        }
        div input {
            margin-right: 2px;
        }
    `]
})
export class AppComponent {
    payments = this.paymentsService.getPayments();

    from: string;
    to: string;
    amount: number;
    constructor(private readonly paymentsService: PaymentsService) {
    }

    readonly handlePayment = () => this.paymentsService.addPayment({amount: this.amount, from: this.from, to: this.to})
        .pipe(
            tap(() => this.amount = this.from = this.to = null),
            map(() => this.paymentsService.getPayments()),
            tap(payments => this.payments = payments)
        ).subscribe();
}

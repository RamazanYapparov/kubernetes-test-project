export interface Payment {
    _id?: string;
    date?: Date;
    amount: number;
    from: string;
    to: string;
    status?: string;
}

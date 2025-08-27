export class Log {
    id!: number;
    sender!: string;
    receiver!: string;
    subject!: string;
    message!: string;
    timestamp!: string;

    constructor(id: number, sender: string, receiver: string, subject: string, message: string, timestamp: string) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.message = message;
        this.timestamp = timestamp;
    }
}

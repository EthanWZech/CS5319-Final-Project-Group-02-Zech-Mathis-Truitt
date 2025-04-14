import { AddCommentRequest } from "../dto/AddCommentRequest";
import { ThreadWithComments } from "../dto/ThreadWithComments";
import { WebSocketRequest } from "../dto/WebSocketRequest";

type ThreadWithCommentsListener = (data: ThreadWithComments) => void;

class WebSocketThreadService {
    private socket: WebSocket | null = null;
    private threadListener: ThreadWithCommentsListener | null = null;

    connect(threadId: string, listener: ThreadWithCommentsListener) {
        if(this.socket) {
            this.socket.close();
        }

        this.threadListener = listener;

        this.socket = new WebSocket(`ws://localhost:8080/thread/${threadId}`);

        this.socket.onmessage = (event) => {
            const data = JSON.parse(event.data);

            switch(data.type) {
                case "ThreadWithCommentsDto":
                    if(this.threadListener)    
                        this.threadListener(data as ThreadWithComments);
                    break;
                default:
                    console.warn("Unknown message type", data.type);
            }
        };

        this.socket.onopen = () => {
            console.log("Websocket connected to thread: ", threadId);
        };

        this.socket.onclose = () => {
            console.log("Websocket disconnected.");
            this.socket = null;
        };

        this.socket.onerror = (err) => {
            console.log("Websocket error: ", err);
        };
    }

    disconnect() {
        if(this.socket) {
            this.socket.close();
            this.socket = null;
            this.threadListener = null;
        }
    }

    addComment(data: AddCommentRequest) {
        if (this.socket?.readyState === WebSocket.OPEN) {
            let request: WebSocketRequest = { type: "addComment", payload: data };
            this.socket.send(JSON.stringify(request));
        }
    }
}

export default new WebSocketThreadService();
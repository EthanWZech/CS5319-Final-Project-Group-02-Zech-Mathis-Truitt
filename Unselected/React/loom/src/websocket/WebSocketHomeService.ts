import { AddThreadRequest } from "../dto/AddThreadRequest";
import { HomeThreads } from "../dto/HomeThreads";
import { WebSocketRequest } from "../dto/WebSocketRequest";

type HomeThreadsListener = (data: HomeThreads) => void;

class WebSocketHomeService {
private socket: WebSocket | null = null;
    private homeThreadsListener: HomeThreadsListener | null = null;

    connect(listener: HomeThreadsListener) {
        if(this.socket) {
            this.socket.close();
        }

        this.homeThreadsListener = listener;

        this.socket = new WebSocket(`ws://localhost:8080/home`);

        this.socket.onmessage = (event) => {
            const data = JSON.parse(event.data);

            switch(data.type) {
                case "HomeThreadsDto":
                    if(this.homeThreadsListener)    
                        this.homeThreadsListener(data as HomeThreads);
                    break;
                default:
                    console.warn("Unknown message type", data.type);
            }
        };

        this.socket.onopen = () => {
            console.log("Websocket connected to home");
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
            this.homeThreadsListener = null;
        }
    }

    addThread(data: AddThreadRequest) {
        const send = () => {
            const request: WebSocketRequest = { type: "addThread", payload: data };
            this.socket?.send(JSON.stringify(request));
        };
        
        if (this.socket?.readyState === WebSocket.OPEN) {
            send();
        } else if (this.socket?.readyState === WebSocket.CONNECTING) {
            this.socket.addEventListener("open", send, { once: true });
        } else {
            console.warn("WebSocket is not connected. Reconnecting...");
            this.connect(() => {}); // optional no-op listener
            this.socket?.addEventListener("open", send, { once: true });
        }
    }
}

export default new WebSocketHomeService
import { AddThreadRequest } from "../dto/AddThreadRequest";
import { HomeThreads } from "../dto/HomeThreads";

type HomeThreadsListener = (data: HomeThreads) => void;

class HomeService {
    pingHomeThreads(listener: HomeThreadsListener){
        console.log("Pinging Threads")
        const interval = setInterval(() => {
            fetch(`http://localhost:8080/threads/recent`)
                .then((res) => res.json())
                .then((data) => {
                let parsedData = JSON.parse(data.payload);
                console.log(parsedData);
                listener(parsedData as HomeThreads);
                });
            }, 3000); // Poll every 3 seconds
        
            return () => clearInterval(interval);
    }

    addThread(data: AddThreadRequest){
        console.log(JSON.stringify({
            data
        }));
        fetch(`http://localhost:8080/threads`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        });
    }
}

export default new HomeService;
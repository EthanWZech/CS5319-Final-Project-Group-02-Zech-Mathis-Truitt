import { AddCommentRequest } from "../dto/AddCommentRequest";
import { ThreadWithComments } from "../dto/ThreadWithComments";

type ThreadWithCommentsListener = (data: ThreadWithComments) => void;

class ThreadService {
    private threadListener: ThreadWithCommentsListener | null = null;

    pingThreads(listener: ThreadWithCommentsListener, threadId: number){
        console.log("Pinging Threads")
        const interval = setInterval(() => {
            fetch(`http://localhost:8080/threads/${threadId}`)
              .then((res) => res.json())
              .then((data) => {
                let parsedData = JSON.parse(data.payload);
                console.log(parsedData);
                listener(parsedData as ThreadWithComments);
              });
          }, 3000); // Poll every 3 seconds
        
          return () => clearInterval(interval);
    }

    addComment(data: AddCommentRequest, threadId: number) {
        console.log(JSON.stringify({
            data
        }));
        fetch(`http://localhost:8080/comments/thread/${threadId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        });
    }
}

export default new ThreadService
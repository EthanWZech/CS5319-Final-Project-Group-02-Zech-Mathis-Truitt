import { useEffect, useState } from 'react';
import './App.css';
import './Example.css'
import { AddCommentRequest } from './dto/AddCommentRequest';
import { ThreadWithComments } from './dto/ThreadWithComments';
import WebSocketThreadService from './websocket/WebSocketThreadService';
import WebSocketHomeService from './websocket/WebSocketHomeService';
import { HomeThreads } from './dto/HomeThreads';
import ThreadService from './services/ThreadService';
import HomeService from './services/HomeService';

export const Example = () => {
  const thingamajig = (num: number) => {
    // do stuff
    return num;
  }
//Testing Repo Rename
  const [thread, setThread] = useState<ThreadWithComments | null>(null);
  const [homeThreads, setHomeThreads] = useState<HomeThreads | null>(null);

  useEffect(() => {
    //WebSocketThreadService.connect("1", (data) => { setThread(data) });
    ThreadService.pingThreads(setThread, 1);
    HomeService.pingHomeThreads(setHomeThreads);
    //WebSocketHomeService.connect((data) => { setHomeThreads(data) })

    return () => {
      //WebSocketThreadService.disconnect();
      //WebSocketHomeService.disconnect();
    }
  }, ["1"]);

  const postComment = (msg: string) => {
    ThreadService.addComment( { threadId: 1, parentCommentId: null, username: "TestCommenter", text: msg, image: null }, 1 );
  }

  const postThread = (msg: string) => {
    HomeService.addThread( { topic: "Development", username: "TestPoster", title: msg, text: "Whatever", image: null } );
    //WebSocketHomeService.addThread( { topic: "Development", username: "TestPoster", title: msg, text: "Whatever", image: null } );
  }

  // const upvoteThread = () => {
  //   WebSocketThreadService.sendVote( { vote: true });
  // }

  // const downvoteThread = () => {
  //   WebSocketThreadService.sendVote( { vote: false });
  // }

  if(!thread || !homeThreads){
    return (
      <div>
          <h1 className='text-blue-500'>Hello</h1>
          <div>{thingamajig(3)}</div>
          Loading...
      </div>
    )
  }

  else{
    return (
      <div>
          <h1 className='text-blue-500'>Hello</h1>
          <div>{thingamajig(3)}</div>
          <div>This is Thread 1</div>
          {thread.text}
          {thread.comments.map((item, index) => (
            <div key={index}>{item.text}</div>
          ))}
          <button onClick={() => postComment("This is a test comment")}>Send Comment</button>
          <div>This is the most recent Threads</div>
          {homeThreads.threads.map((item, index) => (
            <div key={index}>{item.title} Upvotes: {item.upvotes} Downvotes: {item.downvotes}</div>
          ))}
          <button onClick={() => postThread("This is a test thread")}>Send Thread</button>
          {/* <button onClick={() => upvoteThread()}>Upvote Thread 1</button>
          <button onClick={() => downvoteThread()}>Downvote Thread 1</button> */}
      </div>
    )
  }
};

export default Example;

//*
//Creating States:
//const [error, setError] = useState("");
//
//Setting the state:
//setError(errorText);
//
//*
//Data Objects:
//export interface ApprovalInfo {
//  approve: boolean;
//  reason: string;
//}
//
//*
//Sharing Information From Other Components:
//type DocToolProps = {
//  loginInfo: LoginInfo;
//}
//
//const DocumentToolDrawer: React.FC<DocToolProps> = ({ loginInfo }) => { }
//
//Then When Calling the Component:
//<DocumentToolDrawer loginInfo={loginInfo} />
//
//*
//Navigation:
//  const navigate = useNavigate();
//  navigate('/home')
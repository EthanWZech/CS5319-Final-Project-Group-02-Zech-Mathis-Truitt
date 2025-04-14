import { useEffect, useState } from 'react';
import './App.css';
import './Example.css'
import { AddCommentRequest } from './dto/AddCommentRequest';
import { ThreadWithComments } from './dto/ThreadWithComments';
import WebSocketThreadService from './websocket/WebSocketThreadService';

export const Example = () => {
  const thingamajig = (num: number) => {
    // do stuff
    return num;
  }

  const [thread, setThread] = useState<ThreadWithComments | null>(null);

  useEffect(() => {
    WebSocketThreadService.connect("1", (data) => { setThread(data) });

    return () => {
      WebSocketThreadService.disconnect();
    }
  }, ["1"]);

  const postComment = (msg: string) => {
    WebSocketThreadService.addComment( { threadId: 1, parentCommentId: null, username: "TestCommenter", text: msg, image: null } )
  }

  if(!thread){
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
          {thread.text}
          {thread.comments.map((item, index) => (
            <div key={index}>{item.text}</div>
          ))}
          <button onClick={() => postComment("This is a test comment")}>Send Comment</button>
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
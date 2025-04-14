import { useEffect, useState } from 'react';
import WebSocketHomeService from './websocket/WebSocketHomeService';
import { HomeThreads } from './dto/HomeThreads';
import Post from './Post';  

const Home = () => {
  const [homeThreads, setHomeThreads] = useState<HomeThreads | null>(null);

  useEffect(() => {
    WebSocketHomeService.connect((data) => {
      setHomeThreads(data);
    });

    return () => {
      WebSocketHomeService.disconnect();
    };
  }, []);

  if (!homeThreads) return <div style={{color: '#000000'}}>Loading...</div>;

  return (
    <div className="postFeed">
      {homeThreads.threads.map((thread, index) => (
        <Post
          key={index}
          id={thread.id}
          score={thread.upvotes - thread.downvotes}
          title={thread.title}
          username={thread.username}
          content={thread.text}
          topic={thread.topic}
          imageUrl={thread.image}
        />
      ))}
    </div>
  );
};

export default Home;
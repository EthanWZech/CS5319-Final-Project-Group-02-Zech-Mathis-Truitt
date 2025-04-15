import { useEffect, useState } from 'react';
import { HomeThreads } from './dto/HomeThreads';
import Post from './Post';  
import WebSocketHomeService from './websocket/WebSocketHomeService';

const Home = () => {
  const [homeThreads, setHomeThreads] = useState<HomeThreads | null>(null);

  useEffect(() => {
    const listener = (data: HomeThreads) => {
      setHomeThreads((prev) => {
        if (!prev) return data;
  
        const newThreads = data.threads.filter(
          (newThread) => !prev.threads.some((t) => t.id === newThread.id)
        );
  
        return {
          ...prev,
          threads: [...newThreads, ...prev.threads],
        };
      });
    };
  
    WebSocketHomeService.connect(listener);
  
    return () => {
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
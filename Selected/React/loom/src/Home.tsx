import { useEffect, useState } from 'react';
import { HomeThreads } from './dto/HomeThreads';
import Post from './Post';  
import WebSocketHomeService from './websocket/WebSocketHomeService';

const Home = () => {
  const [homeThreads, setHomeThreads] = useState<HomeThreads | null>(null);

  useEffect(() => {
    const listener = (data: HomeThreads) => {
      setHomeThreads(data); // Trust backend to send the right list
    };

    WebSocketHomeService.connect(listener);

    return () => {
      WebSocketHomeService.disconnect();
    };
  }, []);

  if (!homeThreads) return <div style={{ color: '#000000' }}>Loading...</div>;

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
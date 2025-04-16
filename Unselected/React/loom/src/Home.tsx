import { useEffect, useState } from 'react';
import { HomeThreads } from './dto/HomeThreads';
import Post from './Post';  
import HomeService from './services/HomeService';

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

    const stopPolling = HomeService.pingHomeThreads(listener);

    return () => {
      stopPolling();
    };
  }, []);

  if (!homeThreads) return <div style={{ color: '#000000' }}>Loading...</div>;

  return (
    <div className="postFeed">
      {homeThreads.threads.map((thread, index) => (
        <Post
          key={thread.id ?? index}
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
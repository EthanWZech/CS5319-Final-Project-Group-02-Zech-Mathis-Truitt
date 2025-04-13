import { Link } from 'react-router-dom'
import './Home.css'
import Post from './Post.tsx';

const posts = [
    {
      id: 1000,
      username: 'haroldpenny',
      title: 'Check out this cool dog.',
      content: 'Check out this cool dog.',
      topic: 'Pets',
      timestamp: '2h ago',
      imageUrl: 'https://i.imgur.com/b6NffL5.png',
      score: -300,
      comments: [
        {
          id: 'c1000-1',
          username: 'doglover99',
          text: 'He’s perfect.',
          score: 21,
          timestamp: '1h ago',
          replies: [
            {
              id: 'c1000-1-1',
              username: 'haroldpenny',
              text: 'Thanks! He’s my little guy.',
              score: 6,
              timestamp: '45m ago',
            }
          ]
        },
        {
          id: 'c1000-2',
          username: 'catqueen',
          text: 'I mean he’s alright I guess...',
          score: -2,
          timestamp: '30m ago',
        }
      ]
    },
    {
      id: 1001,
      username: 'devgirl',
      title: 'Check out this cool dog.',
      content: 'Check out this cool article I wrote!',
      topic: 'News',
      timestamp: '4h ago',
      score: 12,
      comments: [
        {
          id: 'c1001-1',
          username: 'techdad',
          text: 'Actually really informative. Bookmarking.',
          score: 8,
          timestamp: '3h ago',
        }
      ]
    },
    {
      id: 1002,
      username: 'techguy',
      title: 'Interesting critter.',
      content: 'Found this weird animal.',
      topic: 'Exploring',
      timestamp: '1d ago',
      imageUrl: 'https://i.imgur.com/b6NffL5.png',
      score: -300,
      comments: [
        {
          id: 'c1002-1',
          username: 'naturenerd',
          text: 'That’s actually a long-nosed tree wombat.',
          score: 17,
          timestamp: '22h ago',
          replies: [
            {
              id: 'c1002-1-1',
              username: 'techguy',
              text: 'No way. Thought it was fake!',
              score: 3,
              timestamp: '21h ago',
            }
          ]
        }
      ]
    }
  ];
  

const Home = () => {
    return (
        <div className="postFeed">
            {posts.map((post, index) => (
            <Post
                key = {index}
                id = {post.id}
                score = {post.score}
                title = {post.title}
                username = {post.username}
                content = {post.content}
                topic = {post.topic}
                timestamp = {post.timestamp}
                imageUrl = {post.imageUrl}
                comments = {post.comments}
            />
            ))}
        </div>
    );
}

export default Home
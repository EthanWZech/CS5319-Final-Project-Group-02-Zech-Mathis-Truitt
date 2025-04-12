import { Link } from 'react-router-dom'
import './Home.css'
import Post from './Post.tsx';

const posts = [
    {
        username: 'haroldpenny',
        title: 'Check out this cool dog.',
        content: 'Check out this cool dog.',
        topic: 'Pets',
        timestamp: '2h ago',
        imageUrl: 'https://i.imgur.com/b6NffL5.png',
        score: -300
    },
    {
        username: 'devgirl',
        title: 'Check out this cool dog.',
        content: 'Check out this cool article I wrote!',
        topic: 'News',
        timestamp: '4h ago',
        score: 12
    },
    {
        username: 'techguy',
        title: 'Interesting critter.',
        content: 'Found this weird animal.',
        topic: 'Exploring',
        timestamp: '1d ago',
        imageUrl: 'https://i.imgur.com/b6NffL5.png',
        score: -300
    },
    {
        username: 'techguy',
        title: 'Interesting critter.',
        content: 'Found this weird animal.',
        topic: 'Exploring',
        timestamp: '1d ago',
        imageUrl: 'https://i.imgur.com/b6NffL5.png',
        score: -300
    },
    {
        username: 'techguy',
        title: 'Interesting critter.',
        content: 'Found this weird animal.',
        topic: 'Exploring',
        timestamp: '1d ago',
        imageUrl: 'https://i.imgur.com/b6NffL5.png',
        score: -300
    },
    {
        username: 'techguy',
        title: 'Interesting critter.',
        content: 'Found this weird animal.',
        topic: 'Exploring',
        timestamp: '1d ago',
        imageUrl: 'https://i.imgur.com/b6NffL5.png',
        score: -300
    },
    {
        username: 'techguy',
        title: 'Interesting critter.',
        content: 'Found this weird animal.',
        topic: 'Exploring',
        timestamp: '1d ago',
        imageUrl: 'https://i.imgur.com/b6NffL5.png',
        score: -300
    },
];

const Home = () => {
    return (
        <div className="postFeed">
            {posts.map((post, index) => (
            <Post
                key={index}
                score = {post.score}
                title={post.title}
                username={post.username}
                content={post.content}
                topic={post.topic}
                timestamp={post.timestamp}
                imageUrl={post.imageUrl}
            />
            ))}
        </div>
    );
}

export default Home
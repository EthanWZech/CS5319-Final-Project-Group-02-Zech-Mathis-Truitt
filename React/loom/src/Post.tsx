import { Link } from 'react-router-dom'
import './Post.css'

type PostVars = {
    score: number;
    username: string;
    title: string;
    content: string;
    topic: string;
    timestamp: string;
    imageUrl?: string;
};

const Post = ({ username, score, title, content, topic, timestamp, imageUrl }: PostVars) => {
    return (
        <div className="post">
            <div className="postHeader">
                <span>{username}</span> | <span>{topic}</span> | <span>{timestamp}</span>
            </div>
            <div className="importantInfo">
                <div className="title">{title}</div>
                <div className="scoreSystem">
                    <button style={{color:'#90D280'}}>↑</button>
                    <span className={`score ${score > 0 ? 'positive' : score < 0 ? 'negative' : 'neutral'}`}>{score}</span>
                    <button style={{color:'#C8797A'}}>↓</button>
                </div>
            </div>
            
            <div className="postContent">
                {imageUrl && (
                    <div className="postImage">
                    <img src={imageUrl} alt="Image" />
                    </div>
                )}
                <div className="text">{content}</div>
            </div>
        </div>
    );
};

export default Post;
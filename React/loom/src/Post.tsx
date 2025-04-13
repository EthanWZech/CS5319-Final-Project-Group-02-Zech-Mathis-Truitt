import { Link } from 'react-router-dom'
import './Post.css'

export type CommentType = {
    id: string;
    username: string;
    text: string;
    score: number;
    timestamp: string;
    replies?: CommentType[];
};

export type PostVars = {
    id: number,
    score: number;
    username: string;
    title: string;
    content: string;
    topic: string;
    timestamp: string;
    imageUrl?: string;
    comments?: CommentType[];
};

const Post = ({ id, username, score, title, content, topic, timestamp, imageUrl, comments }: PostVars) => {
    return (
        <Link to={`/postview/${id}`} state={{ id, username, score, title, content, topic, timestamp, imageUrl, comments }} style={{ textDecoration: 'none', color: 'inherit' }}>
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
        </Link>
    );
};

export default Post;
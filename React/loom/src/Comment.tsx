import { CommentType } from './Post'; // or wherever you define it
import './Comment.css'

type CommentProps = {
    comment: CommentType;
};
  
const Comment = ({ comment }: CommentProps) => {
    return (
        <div className="commentView">
            <div className="importantInfo">
                <div className="postHeader">
                    <span>{comment.username}</span>
                </div>
                <div className="scoreSystem">
                    <button style={{color:'#90D280'}}>↑</button>
                    <span className={`score ${comment.score > 0 ? 'positive' : comment.score < 0 ? 'negative' : 'neutral'}`}>{comment.score}</span>
                    <button style={{color:'#C8797A'}}>↓</button>
                </div>
            </div>
            <div className="postContent">
                <div className="text">{comment.text}</div>
                <button className="commentButton">💬</button>
            </div>
            {(comment.replies ?? []).length > 0 && (
            <div className="commentReplies">
                {comment.replies!.map((reply) => (
                <Comment key={reply.id} comment={reply} />
                ))}
            </div>
        )}
        </div>
    );
};

export default Comment;
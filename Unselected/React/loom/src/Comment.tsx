import { useState } from 'react';
import { CommentType } from './Post';
import { getGlobalUsername } from './Topbar';
import WebSocketThreadService from './websocket/WebSocketThreadService';
import './Comment.css';

type CommentProps = {
comment: CommentType;
};

const Comment = ({ comment }: CommentProps) => {
const [showReply, setShowReply] = useState(false);
const [replyText, setReplyText] = useState('');

const handleReplySubmit = () => {
    if (!replyText.trim() || !comment.id || !comment.threadId) return;

        WebSocketThreadService.addComment({
        threadId: comment.threadId,
        parentCommentId: comment.id,
        username: getGlobalUsername(),
        text: replyText,
        image: null
    });

    setReplyText('');
    setShowReply(false);
};

return (
    <div className="commentView">
    <div className="importantInfo">
        <div className="postHeader">
        <span>{comment.username}</span>
        </div>
        {/* <div className="scoreSystem">
        <button style={{ color: '#90D280' }}>↑</button>
        <span className={`score ${comment.score > 0 ? 'positive' : comment.score < 0 ? 'negative' : 'neutral'}`}>
            {comment.score}
        </span>
        <button style={{ color: '#C8797A' }}>↓</button>
        </div> */}
    </div>

    <div className="postContent">
        <div className="text">{comment.text}</div>
        <button className="commentButton" onClick={() => setShowReply(!showReply)}>💬</button>
    </div>

    {showReply && (
        <div className="replyInput">
        <input
            type="text"
            value={replyText}
            onChange={(e) => setReplyText(e.target.value)}
            placeholder="Write a reply..."
        />
        <button onClick={handleReplySubmit}>Reply</button>
        </div>
    )}

    {(comment.replies ?? []).length > 0 && (
        <div className="commentReplies">
        {(comment.replies ?? []).map((reply, index) => (
            <Comment key={reply.id ?? index} comment={reply} />
        ))}
        </div>
    )}
    </div>
);
};

export default Comment;
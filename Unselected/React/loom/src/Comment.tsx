import { useState } from 'react';
import { CommentType } from './Post';
import { getGlobalUsername } from './Topbar';
import ThreadService from './services/ThreadService';
import './Comment.css';

type CommentProps = {
  comment: CommentType;
};

const Comment = ({ comment }: CommentProps) => {
  const [showReply, setShowReply] = useState(false);
  const [replyText, setReplyText] = useState('');

  const handleReplySubmit = () => {
    if (!replyText.trim() || !comment.id || !comment.threadId) return;

    ThreadService.addComment(
        {
            threadId: comment.threadId,
            parentCommentId: comment.id,
            username: getGlobalUsername(),
            text: replyText,
            image: null,
        },
        comment.threadId
    );

    setReplyText('');
    setShowReply(false);
  };

  return (
    <div className="commentView">
      <div className="importantInfo">
        <div className="postHeader">
          <span>{comment.username}</span>
        </div>
      </div>

      <div className="postContent">
        <div className="text">{comment.text}</div>
        <button className="commentButton" onClick={() => setShowReply(!showReply)}>
          💬
        </button>
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
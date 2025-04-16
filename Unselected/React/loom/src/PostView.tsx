import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { PostVars } from './Post';
import './PostView.css';
import Comment from './Comment';
import WebSocketThreadService from './websocket/WebSocketThreadService';
import { ThreadWithComments } from './dto/ThreadWithComments';
import { CommentType } from './Post';
import ThreadService from './services/ThreadService';
import { getGlobalUsername } from './Topbar';

const normalizeCommentNode = (
    node: any,
    threadId: number
): CommentType => ({
    id: node.id ?? Math.floor(Math.random() * 100000),
    username: node.username ?? 'Anonymous',
    text: node.text ?? '',
    score: node.score ?? 0,
    timestamp: node.timestamp ?? 'now',
    threadId,
    replies: (node.replies ?? []).map((r: any) =>
        normalizeCommentNode(r, threadId)
    ),
});

const PostView = () => {
    const location = useLocation();
    const post = location.state as PostVars | null;

    const [thread, setThread] = useState<ThreadWithComments | null>(null);
    const [commentText, setCommentText] = useState('');

    useEffect(() => {
        if (!post) return;

        ThreadService.pingThreads(setThread, (post.id))

        return () => {

        };
    }, [post]);

    if (!post || !thread) {
        return <div style={{color: '#000000'}}>Post not found or loading...</div>;
    }

    const handleAddComment = () => {
        if (!commentText.trim()) return;
        ThreadService.addComment(
            {
                threadId: thread.id,
                parentCommentId: null,
                username: getGlobalUsername(),
                text: commentText,
                image: null
            },
            thread.id
        );
        setCommentText('');
    };

    const handleVote = (upvote: boolean) => {
        WebSocketThreadService.sendVote({ vote: upvote });
    };

    return (
        <div className="postView">
        <div className="postHeader">
            <span>{thread.username}</span> | <span>{thread.topic}</span>
        </div>
        <div className="importantInfo">
            <div className="title">{thread.title}</div>
            <div className="scoreSystem">
            <button style={{ color: '#90D280' }} onClick={() => handleVote(true)}>↑</button>
            <span
                className={`score ${
                thread.upvotes - thread.downvotes > 0
                    ? 'positive'
                    : thread.upvotes - thread.downvotes < 0
                    ? 'negative'
                    : 'neutral'
                }`}
            >
                {thread.upvotes - thread.downvotes}
            </span>
            <button style={{ color: '#C8797A' }} onClick={() => handleVote(false)}>↓</button>
            </div>
        </div>

        <div className="postContent">
            {thread.image && (
            <div className="postImage">
                <img src={thread.image} alt="Image" />
            </div>
            )}
            <div className="text">{thread.text}</div>
        </div>

        {thread.image && (
            <div className="postImageMirror">
            <img src={thread.image} alt="Image" />
            </div>
        )}

        <div className="commentBar">
            <div className="commentTitle">Comment</div>
            <div className="importantInfo">
            <input
                className="commentTyper"
                type="text"
                value={commentText}
                onChange={(e) => setCommentText(e.target.value)}
                placeholder=" Reply..."
            />
            <button className="commentButton" onClick={handleAddComment}>💬</button>
            </div>
        </div>

        {(thread.comments ?? []).map((comment, index) => (
            <Comment
            key={comment.id ?? index}
            comment={normalizeCommentNode(comment, thread.id)}
            />
        ))}
        </div>
    );
};

export default PostView;
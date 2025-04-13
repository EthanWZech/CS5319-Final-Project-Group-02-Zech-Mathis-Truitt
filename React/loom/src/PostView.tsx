import { useLocation, useParams } from 'react-router-dom';
import { PostVars } from './Post';
import './PostView.css'
import Comment from './Comment';
import { CommentType } from './Post'; 

const PostView = () => {
  const location = useLocation();
  const post = location.state as PostVars | null;

  if (!post) {
    return <div>Post not found or missing data.</div>;
  }

  console.log('Post in PostView:', post);
  console.log('Comments:', post.comments);

  return (
    <div className="postView">
        <div className="postHeader">
            <span>{post.username}</span> | <span>{post.topic}</span> | <span>{post.timestamp}</span>
        </div>
        <div className="importantInfo">
            <div className="title">{post.title}</div>
            <div className="scoreSystem">
                <button style={{color:'#90D280'}}>↑</button>
                <span className={`score ${post.score > 0 ? 'positive' : post.score < 0 ? 'negative' : 'neutral'}`}>{post.score}</span>
                <button style={{color:'#C8797A'}}>↓</button>
            </div>
        </div>

        <div className="postContent">
            {post.imageUrl && (
                <div className="postImage">
                <img src={post.imageUrl} alt="Image" />
                </div>
            )}
            <div className="text">{post.content}</div>
        </div>

        {post.imageUrl && (
            <div className="postImageMirror">
                <img src={post.imageUrl} alt="Image" />
            </div>
        )}
        <div className="commentBar">
            <div className="commentTitle">Comment</div>
            <div className="importantInfo">
                <input className="commentTyper" type="text" placeholder=" Reply..." />
                <button className="commentButton">💬</button>
            </div>
        </div>
        {post.comments?.map((comment: CommentType) => (
            <Comment key={comment.id} comment={comment} />
        ))}
    </div>
  );
};

export default PostView;
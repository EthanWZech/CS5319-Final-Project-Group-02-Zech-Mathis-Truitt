import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { getGlobalUsername } from './Topbar';
import HomeService from './services/HomeService'; 
import './New.css';

const New = () => {
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [topic, setTopic] = useState('');
  const [content, setContent] = useState('');
  const [imageLink, setImageLink] = useState('');

  const postThread = () => {
    HomeService.addThread({
      topic,
      username: getGlobalUsername(),
      title,
      text: content,
      image: imageLink || null,
    });

    setTimeout(() => {
      navigate('/', { replace: true });
    }, 500); 
  };

  return (
    <div className="newPost">
      <div className="importantInfo">
        <div className="addTitle">
          <input
            className="textTitle"
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            placeholder=" Enter Title..."
          />
        </div>
        <div className="addTopic">
          <input
            className="textTopic"
            type="text"
            value={topic}
            onChange={(e) => setTopic(e.target.value)}
            placeholder=" Enter Topic..."
          />
        </div>

        <input
          type="text"
          className="addPhoto"
          placeholder="Image URL"
          value={imageLink}
          onChange={(e) => setImageLink(e.target.value)}
        />

        <button className="sendPost" onClick={postThread}>Post</button>
      </div>

      <div className="newPostContent">
        <input
          className="textContents"
          type="text"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder=" Start writing..."
        />
      </div>
    </div>
  );
};

export default New;
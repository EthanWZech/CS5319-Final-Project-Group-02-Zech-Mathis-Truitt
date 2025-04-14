import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import WebSocketHomeService from './websocket/WebSocketHomeService';
import './New.css';

const New = () => {
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [topic, setTopic] = useState('');
    const [content, setContent] = useState('');
    const [imageData, setImageData] = useState<string | null>(null);

    const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (!file) return;

        const reader = new FileReader();
        reader.onloadend = () => {
            setImageData(reader.result as string); 
        };
        reader.readAsDataURL(file);
    };

    const postThread = () => {
        WebSocketHomeService.addThread({
            topic,
            username: 'TestPoster',
            title,
            text: content,
            image: imageData
        });
        navigate('/');
    };

    return (
        <div className="newPost">
            <div className="importantInfo">
                <div className="addTitle">
                    <input className="textTitle" type="text" value={title} onChange={(e) => setTitle(e.target.value)} placeholder=" Enter Title..."/>
                </div>
                <div className="addTopic">
                    <input className="textTopic" type="text" value={topic} onChange={(e) => setTopic(e.target.value)} placeholder=" Enter Topic..."/>
                </div>
                    <input type="file" accept="image/*" onChange={handleImageUpload} className="addPhoto" placeholder="Add Photo"/>
                    <button className="sendPost" onClick={postThread}>Post</button>
                </div>
                <div className="newPostContent">
                    <input className="textContents" type="text" value={content} onChange={(e) => setContent(e.target.value)} placeholder=" Start writing..."/>
                </div>
        </div>
    );
};

export default New;
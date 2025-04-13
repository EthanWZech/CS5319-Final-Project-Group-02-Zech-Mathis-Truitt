import { Link } from 'react-router-dom'
import './New.css'

const New = () => {
    return (
        <div className="newPost">
        <div className="importantInfo">
            <div className="addTitle"><input className="textTitle" type="text" placeholder=" Enter Title..." /></div>
            <div className="addTopic"><input className="textTopic" type="text" placeholder=" Enter Topic..." /></div>
            <button className="addPhoto">Attatch Photo</button>
            <button className="sendPost">Post</button>
        </div>

        <div className="newPostContent">
            <input className="textContents" type="text" placeholder=" Start writing..." />
        </div>
    </div>
    )
}

export default New
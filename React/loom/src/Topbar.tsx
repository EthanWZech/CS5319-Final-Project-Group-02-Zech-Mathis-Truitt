import { useState } from 'react';
import { Link } from 'react-router-dom';
import './Topbar.css';

const TopBar = () => {
  const [editingName, setEditingName] = useState(false);
  const [username, setUsername] = useState('');

  return (
    <div className="topbar">
      <nav>
        <div className="logoWrapper">
          <span className="loomLogoShadow">Loom</span>
          <Link className="loomLogo" to="/">Loom</Link>
        </div>

        <input className="searchBar" type="text" placeholder="🔎 Search previous threads..." />

        <div className="buttonSet">
          {!editingName ? (
            <Link
              className="username"
              to="#"
              onClick={(e) => {
                e.preventDefault();
                setEditingName(true);
              }}
            >
            Set Name
            </Link>
            ) : (
            <input
              className="usernameInput"
              type="text"
              placeholder="Enter name"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              onBlur={() => setEditingName(false)} 
              autoFocus
            />
          )}
          <Link className="makePost" to="/New">New Post</Link>
        </div>
      </nav>
    </div>
  );
};

export default TopBar;

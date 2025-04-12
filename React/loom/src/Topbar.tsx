import { Link } from 'react-router-dom'
import './Topbar.css'

const TopBar = () => {
  return (
    <div className="topbar">
      <nav>
          <div className="logoWrapper"><span className="loomLogoShadow">Loom</span><Link className="loomLogo" to="/">Loom</Link></div>
            <input className="searchBar" type="text" placeholder="🔎 Search previous threads..." />
          <div className="buttonSet">
          <Link className="username" to="/">Set Name</Link>
          <Link className="makePost" to="/">New Post</Link>
        </div>
      </nav>
    </div>
  )
}

export default TopBar
import { Routes, Route } from 'react-router-dom'
import './App.css'
import Example from './Example'
import Home from './Home'
import TopBar from './Topbar'; 

function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<Home />} />
      </Routes>
      <TopBar/>
    </div>
  )
}

export default App

import { Routes, Route } from 'react-router-dom'
import './App.css'
import Home from './Home'
import PostView from './PostView'
import New from './New'
import TopBar from './Topbar'; 
import { useEffect } from 'react';
import WebSocketHomeService from './websocket/WebSocketHomeService';
import { HomeThreads } from './dto/HomeThreads';

function App() {
  useEffect(() => {
    WebSocketHomeService.connect((data: HomeThreads) => {
      console.log("Received threads globally", data);
    });

    return () => {
      WebSocketHomeService.disconnect();
    };
  }, []);
  return (
    <div>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/New" element={<New />} />
        <Route path="/postview/:id" element={<PostView />} />
      </Routes>
      <TopBar/>
    </div>
  )
}

export default App

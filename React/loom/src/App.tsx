import { Routes, Route } from 'react-router-dom'
import './App.css'
import Example from './Example'

function App() {
  return (
    <Routes>
      <Route path="/" element={<Example />} />
    </Routes>
  )
}

export default App

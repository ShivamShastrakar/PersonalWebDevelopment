import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import UserCard from './components/UserCard'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className="container">
      <UserCard name="Rajesh" desc="Full Stack Developer" />
      <UserCard name="Alka" desc="Frontend Developer" />
      <UserCard name="Shivam" desc="Backend Developer" />
    </div>
  )
}

export default App

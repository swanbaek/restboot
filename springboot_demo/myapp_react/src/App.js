import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from 'react';
function App() {
  const [message, setMessage]=useState("");

  useEffect(()=>{
    fetch("/board/test")
    .then((res)=>res.json())
    .then((json)=>setMessage(json.msg))
  });
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h2>{message}</h2>
      </header>
    </div>
  );
}

export default App;

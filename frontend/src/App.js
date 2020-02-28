import React from 'react';
import logo from './logo.svg';
import './App.css';
import QrGenerator from './components/qrGenerator';

function App() {

  return (

    <div>

    <QrGenerator> </QrGenerator>

    <div className="App">
      <header className="App-header" >
        <p>
        Vesta.io
        </p>
      </header>
    </div>

    </div>
  );
}

export default App;

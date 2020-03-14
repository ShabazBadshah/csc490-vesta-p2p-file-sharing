import React from 'react';
import './App.css';
import background from './background.jpg';
import {
  Card,
  CardHeader,
  CardTitle,
  CardImg,
  CardBody,
  CardFooter,
  Button,
  Badge,
  ButtonGroup,
  ButtonToolbar,
  FormInput,
  InputGroup,
  Nav,
  NavItem,
  NavLink,
  Collapse
} from "shards-react";
import QrGenerator from './components/qrGenerator';
import Navigation from './components/navigation';
import Menu from './components/menu';

function App() {

  return (

    <div>

      <p style={{color: "#905EAF", position: "absolute", left: "90px", top: "10px", fontSize: "72px"}}> Vesta </p>
      <p style={{color: "#black", position: "absolute", left: "100px", top: "105px", fontSize: "18px"}}> Secure file sharing in your control  </p>

    <Navigation> </Navigation>


    </div>


  );
}

export default App;

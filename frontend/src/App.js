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
  Collapse
} from "shards-react";
import QrGenerator from './components/qrGenerator';
import Navigation from './components/navigation';

function App() {

  return (

    <div>

    <img src={background} style={{
        position: "fixed",
        top: "0",
        width: "100%",
        height: "100%",
    }}/>

    <Navigation> </Navigation>

    <Card style={{maxWidth: "300px", position: "absolute", top:"8px", right:"16px"}}>
    <CardBody>
    <ButtonToolbar>
      <Button outline squared size="sm" theme="dark"> Privacy </Button>
      <Button outline squared size="sm" theme="dark"> Help </Button>
      <Button outline squared size="sm" theme="dark"> Contact </Button>
    </ButtonToolbar>
    </CardBody>
    </Card>

    <div style={{position: "absolute", top: "8px", left: "16px", fontSize: "48px", color: "black"}}> Vesta < /div>

    </div>
  );
}

export default App;

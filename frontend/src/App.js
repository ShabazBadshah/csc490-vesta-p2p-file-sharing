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
    <Router>
      <Switch>
        <Route exact path="/" component={MainPage} />
        <Route exact path="/Host" component={HostPage} />
        <Route path="/Receive" component={ReceivePage} />
        <Route exact path="/404" component={ErrorPage} />
      <Redirect to="/404"/>
      </Switch>
    </Router>

    );

  }
}

export default App;
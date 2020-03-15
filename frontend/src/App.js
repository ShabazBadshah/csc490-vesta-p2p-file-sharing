import React, {Component} from 'react';
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
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Link,
  Redirect
} from "react-router-dom";

import MainPage from './pages/main';
import ErrorPage from './pages/404error';
import HostPage from './pages/host';
import ReceivePage from './pages/receive';


class App extends Component {
  render() {

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

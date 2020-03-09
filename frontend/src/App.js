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
import MainPage from './pages'


class App extends Component {
  render() {

  return (

    <Router>
      <Route path="/" component={MainPage} />
    </Router>

    );

  }

}

export default App;

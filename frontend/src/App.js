import React, { Component } from "react";
import "./App.css";
import background from "./background.jpg";
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
import QrGenerator from "./components/qrGenerator";
import Navigation from "./components/navigation";
import Menu from "./components/menu";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Link,
  Redirect
} from "react-router-dom";

import MainPage from "./pages/main";
import ErrorPage from "./pages/404error";
import HostPage from "./pages/host";
import ReceivePage from "./pages/receive";

class App extends Component {
  render() {
<<<<<<< HEAD

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

=======
    return (
      <Router>
        <Switch>
          <Route exact path="/" component={MainPage} />
          <Route exact path="/Host" component={HostPage} />
          <Route path="/Receive" component={ReceivePage} />
          <Route exact path="/404" component={ErrorPage} />
          <Redirect to="/404" />
        </Switch>
      </Router>
>>>>>>> 84f253bd4d09210d468d4f89f07efb0cb1c45bf0
    );
  }
}

export default App;

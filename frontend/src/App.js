import React, { Component } from "react";

import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect
} from "react-router-dom";

import MainPage from "./pages/main";
import ErrorPage from "./pages/404error";
import HostPage from "./pages/host";
import ReceivePage from "./pages/receive";

import "./App.css";

class App extends Component {
  render() {
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
    );
  }
}

export default App;

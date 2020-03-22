import React, { Component } from 'react';
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
  Collapse,
  Nav, NavItem, NavLink
} from "shards-react";
import "bootstrap/dist/css/bootstrap.min.css";
import "shards-ui/dist/css/shards.min.css";
/*

The following component does simple navigation within the home host / recieve ui

*/

class Menu extends Component {
  constructor(props) {
    super(props);
    this.homeAct = this.homeAct.bind(this);
    this.privacyAct = this.privacyAct.bind(this);
    this.helpAct = this.helpAct.bind(this);
    this.contactAct = this.contactAct.bind(this);
    this.state = {home: true, privacy: false, help: false ,contact: false}
  }

  homeAct() {
    this.setState({home: true, privacy: false, help: false ,contact: false});
  }

  privacyAct() {
    this.setState({home: false, privacy: true, help: false ,contact: false});
  }

  helpAct() {
    this.setState({home: false, privacy: false, help: true ,contact: false});
  }

  contactAct() {
    this.setState({home: false, privacy: false, help: false ,contact: true});
  }

  render(){

    const homeTab = this.state.home;
    const privacyTab = this.state.privacy;
    const helpTab = this.state.help;
    const contactTab = this.state.contact;
    let cardTitle
    let cardBody;

    if(homeTab){
      cardBody = <span>
      </span>
      cardTitle = <h5>  </h5>
    }

    else if (privacyTab){
      cardBody = <span>
      P Tab
      </span>
      cardTitle = <h5> Privacy </h5>
    }

    else if (helpTab){
      cardBody = <span>
        Help Tab
      </span>
        cardTitle = <h5> Help </h5>
    }

    else if (contactTab){
      cardBody = <span>
        C Tab
      </span>
      cardTitle = <h5> Contact </h5>
    }

    return (
      <div>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"350px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.homeAct}> Home </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"240px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.privacyAct}> Privacy </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"130px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.helpAct}> Help </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"20px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.contactAct}> Contact </Button>

      <div style={{position: "absolute", left:"650px", top:"120px"}}>
      {cardTitle}
      <span>
        {cardBody}
      </span>
    </div>

      </div>
    )

    }

}

export default Menu;

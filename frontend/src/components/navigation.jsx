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
import QrGenerator from "./qrGenerator";

/*

The following component does simple navigation within the home host / recieve ui

*/

class Navigation extends Component {
  constructor(props) {
    super(props);
    this.hostingClick = this.hostingClick.bind(this);
    this.receivingClick = this.receivingClick.bind(this);
    this.qrActivate = this.qrActivate.bind(this);
    this.state = {hostingTab: false, receivingTab: true, showQR: false}
    this.showQR = false
    this.hostingTab = false;
    this.receivingTab = false;
  }

  hostingClick() {
    this.setState({hostingTab: true, receivingTab: false});
  }

  receivingClick() {
    this.setState({hostingTab: false, receivingTab: true});
    this.setState({showQR: false});
  }

  qrActivate() {
    this.setState({showQR: true});
  }

  render(){

    const hostingTab = this.state.hostingTab;
    const receivingTab = this.state.receivingTab;
    const showQR = this.state.showQR;
    let cardBody;
    let hstyle;
    let rstyle;

    if (hostingTab){
      cardBody =
      <div>
        <Button theme="light" style={{color: 'white', borderColor: "#905EAF", backgroundColor: "#905EAF"}}> Ready to Host </Button>
      </div>
      hstyle = {color: "#905EAF", fontWeight: "bold", width: "210px", textDecoration: "underline"}
      rstyle= {color: "grey", fontWeight: "bold", width: "210px"}
    }

    else if (receivingTab){
      cardBody = <div> <FormInput placeholder="Enter Link" className="mb-2" style={{width: "360px", borderColor: "#905EAF"}}/>
      <Button theme="light" style={{color: 'white', borderColor: "#905EAF", backgroundColor: "#905EAF"}}> Go </Button>
      </div>
      hstyle = {color: "grey", fontWeight: "bold", width: "210px"}
      rstyle = {color: "#905EAF", fontWeight: "bold", width: "210px", textDecoration: "underline"}
    }

    else{
      cardBody =  <div> </div>
      hstyle = {color: "grey", fontWeight: "bold", width: "210px"}
      rstyle = {color: "grey", fontWeight: "bold", width: "210px"}
    }

    if (showQR){
      return (
        <div className="App">
          <header className="App-header">
          <Card style={{position: "absolute", left:"80px", top:"120px"}}>
          <Nav tabs>
             <NavItem>
               <NavLink disactive href="#" onClick={this.hostingClick} style={hstyle}>
                 Hosting
               </NavLink>
             </NavItem>
             <NavItem>
               <NavLink active href="#" onClick={this.receivingClick} style={rstyle}>
                 Recieve
               </NavLink>
             </NavItem>
           </Nav>
            <CardBody>
              &nbsp;
              <QrGenerator> </QrGenerator>
            </CardBody>
          </Card>
          </header>
        </div>
      )
    }

    else {

      return (
        <div className="App">
          <header className="App-header">
          <Card style={{position: "absolute", left:"80px", top:"150px"}}>
          <Nav tabs>
             <NavItem>
               <NavLink disactive href="#" onClick={this.hostingClick} style={hstyle}>
                 Hosting
               </NavLink>
             </NavItem>
             <NavItem>
               <NavLink active href="#" onClick={this.receivingClick}  style={rstyle}>
                 Recieve
               </NavLink>
             </NavItem>
           </Nav>
            <CardBody>
              &nbsp;
              {cardBody}
            </CardBody>
          </Card>
          </header>
        </div>
      )

    }

  }
}

export default Navigation;

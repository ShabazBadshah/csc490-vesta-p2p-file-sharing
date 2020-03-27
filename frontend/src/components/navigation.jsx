import React, { Component } from 'react';

import {
  Card,
  CardBody,
  Button,
  FormInput,
  Nav, NavItem, NavLink
} from "shards-react";

import "bootstrap/dist/css/bootstrap.min.css";
import "shards-ui/dist/css/shards.min.css";


/*
  The following component does simple navigation within the home host / recieve ui
*/

class Navigation extends Component {
  constructor(props) {
    super(props);
    this.hostingClick = this.hostingClick.bind(this);
    this.receivingClick = this.receivingClick.bind(this);
    this.state = {hostingTab: false, receivingTab: true, rinput: ""}
    this.hostingTab = false;
    this.receivingTab = false;
    this.rinput = "";
  }

  hostingClick() {
    this.setState({hostingTab: true, receivingTab: false});
  }

  receivingClick() {
    this.setState({hostingTab: false, receivingTab: true});
  }

  rinputRequest = (event) => {
      event.preventDefault()
      this.setState({[event.target.name]: event.target.value})
  }

  render() {

    const hostingTab = this.state.hostingTab;
    const receivingTab = this.state.receivingTab;
    const rInput = this.state.rinput;
    let cardBody;
    let hstyle;
    let rstyle;

    if (hostingTab){
      cardBody =
      <div>
        <a href="/host">
          <Button theme="light" style={{color: 'white', borderColor: "#905EAF", backgroundColor: "#905EAF"}}> Ready to Host </Button>
        </a>
      </div>
      hstyle = {color: "#905EAF", fontWeight: "bold", width: "210px", textDecoration: "underline"}
      rstyle= {color: "grey", fontWeight: "bold", width: "210px"}
    }

    else if (receivingTab){
      cardBody = <div> <FormInput placeholder="Enter Link" id='rname' name='rinput' onChange={this.rinputRequest} className="mb-2" style={{width: "360px", borderColor: "#905EAF"}}/>
      <a href={'/receive/'+ rInput}>
        <Button theme="light" style={{color: 'white', borderColor: "#905EAF", backgroundColor: "#905EAF"}}> Go </Button>
      </a>
      </div>
      hstyle = {color: "grey", fontWeight: "bold", width: "210px"}
      rstyle = {color: "#905EAF", fontWeight: "bold", width: "210px", textDecoration: "underline"}
    }

    else {
      cardBody =  <div> </div>
      hstyle = {color: "grey", fontWeight: "bold", width: "210px"}
      rstyle = {color: "grey", fontWeight: "bold", width: "210px"}
    }

    return(
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
    );
  }
}

export default Navigation;

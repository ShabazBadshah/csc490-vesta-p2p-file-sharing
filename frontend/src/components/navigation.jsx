import React, { Component } from 'react';
import styled from 'styled-components';
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
import "bootstrap/dist/css/bootstrap.min.css";
import "shards-ui/dist/css/shards.min.css";
import QrGenerator from "./qrGenerator";

class Navigation extends Component {
  constructor(props) {
    super(props);
    this.hostingClick = this.hostingClick.bind(this);
    this.receivingClick = this.receivingClick.bind(this);
    this.qrActivate = this.qrActivate.bind(this);
    this.state = {hostingTab: false, receivingTab: false, showQR: false}
    this.showQR = false
    this.hostingTab = false;
    this.receivingTab = false;
  }

  hostingClick() {
    this.setState({hostingTab: true, receivingTab: false});
  }

  receivingClick() {
    this.setState({hostingTab: false, receivingTab: true});
  }

  qrActivate() {
    this.setState({showQR: true});
  }

  render(){

    const hostingTab = this.state.hostingTab;
    const receivingTab = this.state.receivingTab;
    const showQR = this.state.showQR;
    let cardBody;

    if (hostingTab){
      cardBody =
      <div>
        <div>
        <Button outline squared size="sm" theme="dark" style={{backgroundColor: "#483D8B", color: "white"}}> Select File 📄 </Button>
        </div>
        <div>
        <Button outline squared size="sm" theme="dark" onClick={this.qrActivate} > Host </Button>
        </div>
      </div>
    }

    else if (receivingTab){
      cardBody = <div> <FormInput size="sm" placeholder="Enter Link" className="mb-2" style={{width: "165px", borderColor: "#483D8B"}}/>
      <Button outline squared size="sm" theme="dark"> Go </Button>

      </div>
    }

    else{
      cardBody =  <div> </div>
    }

    if (showQR){
      return (
        <div className="App">
          <header className="App-header">
          <Card style={{maxWidth: "300px", position: "absolute", left:"80px", top:"120px"}}>
            <CardBody>
            <ButtonToolbar>
              <Button outline squared size="sm" theme="dark"> Hosting </Button>
              <Button outline squared size="sm" theme="dark"> Receiving </Button>
            </ButtonToolbar>
              <text style={{color: "black", fontSize: "12px"}}> Share Link: </text> <p style={{color: "black", fontSize: "12px"}}> <a style={{color: "black", fontSize: "12px"}} href="www.google.com"/> www.google.com </p>
              <QrGenerator> </QrGenerator>
              <Button outline squared size="sm" theme="dark" style={{backgroundColor: "#483D8B", color: "white"}}> Quit </Button>
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
          <Card style={{maxWidth: "300px", position: "absolute", left:"80px", top:"120px"}}>
            <CardBody>
            <ButtonToolbar>
              <Button outline squared size="sm" theme="dark" onClick={this.hostingClick}> Hosting </Button>
              <Button outline squared size="sm" theme="dark" onClick={this.receivingClick}> Receiving </Button>
            </ButtonToolbar>
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

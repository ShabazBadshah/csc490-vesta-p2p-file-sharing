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
import QrGenerator from './qrGenerator';
import { FilePicker } from 'react-file-picker';

class HostNav extends Component {

  render(){

    return(

      <div>

      <p style={{color: "#905EAF", position: "absolute", left: "90px", top: "10px", fontSize: "72px"}}> Vesta - Host </p>
      <p style={{color: "#black", position: "absolute", left: "100px", top: "105px", fontSize: "18px"}}> Secure file sharing in your control  </p>

      <Card style={{position: "absolute", left:"80px", top:"150px", width: "500px"}}>
        <CardBody>
        <FilePicker
            extensions={['md']}


            >
            <Button theme="light" style={{color: 'white', borderColor: "#905EAF", backgroundColor: "#905EAF"}}> Select File </Button>
        </FilePicker>
            <br/>
            <br/>
            <Button theme="light" active="False"> Stream </Button>
          <br/>
          <br/>
          <div align="middle">
            <h5> <small> Share Link: </small> <b>ThisIsAnExample</b> </h5>
            <br/>
            <b> QR Code: </b>
            <QrGenerator> </QrGenerator>
            <br/>
          </div>
          <a href="/">
          <Button theme="light" style={{color: 'white', borderColor: "#8B0000", backgroundColor: "#8B0000", float:"right"}}> Quit </Button>
          </a>
        </CardBody>
      </Card>

      </div>

    )

  }


}

export default HostNav;

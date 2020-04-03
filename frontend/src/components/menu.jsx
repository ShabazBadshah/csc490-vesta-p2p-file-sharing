import React, { Component } from 'react';

import {
  Button
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

  render() {
    const homeTab = this.state.home;
    const privacyTab = this.state.privacy;
    const helpTab = this.state.help;
    const contactTab = this.state.contact;

    let cardTitle;
    let cardBody;

    let theButtons;

    if(homeTab){
      cardBody = <span style={{position:"absolute", left:"150px"}}>
      <br/>
      <a href="https://shabazbadshah.com/">
      {/* <Button theme="light" style={{color: 'white', borderColor: "#905EAF", backgroundColor: "#905EAF"}}> <b> Google Play </b> 📱 </Button> */}
      </a>
      </span>
      // cardTitle = <h5> Download the app to start sharing files securly </h5>
      theButtons = <div>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"350px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit", textDecoration: "underline"}} onClick={this.homeAct}> Home </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"240px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.privacyAct}> Privacy </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"130px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.helpAct}> Help </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"20px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.contactAct}> Contact </Button>
      </div>
    }

    else if (privacyTab){
      cardBody = <span> Privacy is never a concern with vesta, we use multiple reliable
      levels of security and encryption to make sure your files are secure.
      </span>
      cardTitle = <h5> Privacy </h5>
      theButtons =
      <div>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"350px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.homeAct}> Home </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"240px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit", textDecoration: "underline"}} onClick={this.privacyAct}> Privacy </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"130px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.helpAct}> Help </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"20px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.contactAct}> Contact </Button>
      </div>
    }

    else if (helpTab){
      cardBody = <span>
      Vesta is easy to use, before hosting or receiving a file
      make sure to download the vesta moblie app and after that <br/> follow these steps,
      <br/> <br/>
      <b> Hosting: </b> <br/>
      &bull; navigate to the host tab and click "Ready to host" <br/>
      &bull; select file and click stream <br/>
      &bull; scan the qr code with your vesta moblie app <br/>
      &bull; present the key and link to a person who you wish to share with
      given they also have the app <br/>
      <br/>
      <b> Receiving: </b> <br/>
      &bull; get key and link from the person who has hosted the file <br/>
      &bull; paste link in the receive tab <br/>
      &bull; validate key and download <br/>
      </span>
      cardTitle = <h5> Help </h5>
      theButtons =
      <div>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"350px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.homeAct}> Home </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"240px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.privacyAct}> Privacy </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"130px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit", textDecoration: "underline"}} onClick={this.helpAct}> Help </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"20px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.contactAct}> Contact </Button>
      </div>
    }

    else if (contactTab){
      cardBody = <span>
      If you have any questions about Vesta, whether it be about security
      or future updates <br/> navigate to our contact web page: <a href="https://shabazbadshah.com/"> link </a>
       </span>
      cardTitle = <h5> Contact </h5>
      theButtons =
      <div>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"350px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.homeAct}> Home </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"240px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.privacyAct}> Privacy </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"130px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit"}} onClick={this.helpAct}> Help </Button>
      <Button theme="light" style={{color: "#905EAF", position: "absolute", right:"20px", top:"20px", fontWeight: "bold", width: "100px", border: "none", backgroundColor: "inherit", textDecoration: "underline"}} onClick={this.contactAct}> Contact </Button>
      </div>
    }

    return (
      <div>
        {theButtons}
        <div style={{position: "absolute", left:"650px", top:"120px"}}>
          {cardTitle}
          <span> {cardBody} </span>
        </div>
      </div>
    );
  }
}

export default Menu;

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
  //Config
  /*configuration = {iceServers: [{ urls: ["stun:stun1.l.google.com:19302", "stun:stun2.l.google.com:19305" ] }]};

  constructor(props) {
    super(props);
    this.homeAct = this.homeAct.bind(this);
    this.privacyAct = this.privacyAct.bind(this);
    this.helpAct = this.helpAct.bind(this);
    this.contactAct = this.contactAct.bind(this);
    this.connectPeers = this.connectPeers.bind(this);
    this.receiveChannelCallback = this.receiveChannelCallback.bind(this)
    this.handleSendChannelStatusChange = this.handleSendChannelStatusChange.bind(this);
    this.state = {home: true, privacy: false, help: false ,contact: false}

    this.localConnection = null;   // RTCPeerConnection for our "local" connection
    this.remoteConnection = null;  // RTCPeerConnection for the "remote"
    
    this.sendChannel = null;       // RTCDataChannel for the local (sender)
    this.receiveChannel = null;    // RTCDataChannel for the remote (receiver)
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
  connectPeers() {
    this.localConnection = new RTCPeerConnection();

    this.sendChannel = this.localConnection.createDataChannel("sendChannel");
    this.sendChannel.onopen = this.handleSendChannelStatusChange;
    this.sendChannel.onclose = this.handleSendChannelStatusChange;
    
    this.remoteConnection = new RTCPeerConnection();
    this.remoteConnection.ondatachannel = this.receiveChannelCallback;

    this.localConnection.onicecandidate = e => !e.candidate
    || this.remoteConnection.addIceCandidate(e.candidate)
    .catch((error) => console.log("addICECandidate failed: " + error.toString()));

    this.remoteConnection.onicecandidate = e => !e.candidate
    || this.localConnection.addIceCandidate(e.candidate)
    .catch((error) => console.log("addICECandidate failed!: " + error.toString()));
    
    this.localConnection.createOffer()
    .then(offer => this.localConnection.setLocalDescription(offer))
    .then(() => this.remoteConnection.setRemoteDescription(this.localConnection.localDescription))
    .then(() => this.remoteConnection.createAnswer())
    .then(answer => this.remoteConnection.setLocalDescription(answer))
    .then(() => this.localConnection.setRemoteDescription(this.remoteConnection.localDescription))
    .catch((error) => console.log("Unable to create an offer: " + error.toString()));

    console.log("sup");
  }
  handleSendChannelStatusChange() { // Fix
    if (this.sendChannel) {
      var state = this.sendChannel.readyState;
    
      if (state === "open") {
        messageInputBox.disabled = false;
        messageInputBox.focus();
        sendButton.disabled = false;
        disconnectButton.disabled = false;
        connectButton.disabled = true;
      } else {
        messageInputBox.disabled = true;
        sendButton.disabled = true;
        connectButton.disabled = false;
        disconnectButton.disabled = true;
      }
    }
  }

  receiveChannelCallback() {
    this.receiveChannel = event.channel;
    this.receiveChannel.onmessage = handleReceiveMessage;//TODO
    this.receiveChannel.onopen = handleReceiveChannelStatusChange;//TODO
    this.receiveChannel.onclose = handleReceiveChannelStatusChange;//TODO
  }

  render(){

    const homeTab = this.state.home;
    const privacyTab = this.state.privacy;
    const helpTab = this.state.help;
    const contactTab = this.state.contact;
    let cardTitle
    let cardBody;

    if(homeTab){
      cardBody = <div> <FormInput placeholder="Enter Link" className="message" style={{width: "360px", borderColor: "#905EAF"}}/>
      <Button theme="light" style={{color: 'white', borderColor: "#905EAF", backgroundColor: "#905EAF"}} > Send message </Button>
      <Button theme="light" style={{color: 'white', borderColor: "#905EAF", backgroundColor: "#905EAF"}} onClick={this.connectPeers}> Connect </Button>
      </div>
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

    }*/

}

export default Menu;
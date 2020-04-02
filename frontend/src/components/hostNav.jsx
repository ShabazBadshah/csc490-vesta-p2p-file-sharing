import React, { Component } from 'react';

import {
  Card,
  CardBody,
  Button
} from "shards-react";

import "bootstrap/dist/css/bootstrap.min.css";
import "shards-ui/dist/css/shards.min.css";
import QrGenerator from './qrGenerator';

var P2P = require('socket.io-p2p');
var key = "key"
//var fs = require('fs');
var crypto = require('crypto')
var io = require('socket.io-client');
var socket = io("http://31640a7d.ngrok.io");
var opts = {autoUpgrade: false, numClients: 10};
var p2pSocket = new P2P(socket, opts);

p2pSocket.on('peer-msg', function (data) {
  var dec = crypto.createDecipher("aes-256-ctr",key).update(data.textVal,"hex","utf8");
  console.log('From a peer encrypted: %s', data.textVal);
  console.log('From a peer decrypted: %s', dec);
});

p2pSocket.on('go-private', function () {
  p2pSocket.upgrade();
  console.log("going private");
  // upgrade to peerConnection
  // privateClick();
  p2pSocket.useSockets = false;
});

p2pSocket.on('peer-file', function (data) {
  var dec = crypto.createDecipher("aes-256-ctr",key).update(data.textVal,"hex","utf8");
  console.log('From a peer encrypted: %s', data.textVal);
  console.log('From a peer decrypted: %s', dec);
  console.log(data)
  var blob = new Blob([dec]);
  var url = window.URL.createObjectURL(blob);
  var a = document.createElement('a');
  a.setAttribute('hidden', '');
  a.setAttribute('href', url);
  a.setAttribute('download', data.filename);
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
});

const privateClick = () => {
  p2pSocket.upgrade();
  socket.emit('go-private', true);
  p2pSocket.useSockets = false;
}

const submitClick = () => {
  var text = document.getElementById("submitText").value;
  var enc = crypto.createCipher("aes-256-ctr",key).update(text,"utf-8","hex");
  p2pSocket.emit('peer-msg', {textVal : enc});
  console.log("IT CLICKED")
}

class HostNav extends Component {

  constructor(props) {
    super(props)
    this.sendFileClick = this.sendFileClick.bind(this);
    this.fileInput = React.createRef();
    this.state = {streamButtonOn : true, qrOn : false}
  }

  sendFileClick() {
      var reader = new FileReader();
      console.log(this.fileInput)
      var file = this.fileInput.current.files[0]
      reader.readAsText(file);
      console.log(reader)
      reader.onload = function() {
          var text = reader.result;
          var enc = crypto.createCipher("aes-256-ctr",key).update(text,"utf-8","hex");
          p2pSocket.emit('peer-file', {textVal : enc, filename : file.name});
      }
      this.setState({qrOn : true})
  }

  isDisabled(e){
    if (this.fileInput.current != null && e.target.value != ""){
      this.setState({streamButtonOn : false})
    }
    else if (e.target.value == ""){
      this.setState({streamButtonOn : true})
    }
    else{
      this.setState({streamButtonOn : true})
    }

  }

  render() {
    const streamButtonOn = this.state.streamButtonOn;
    const qrOn = this.state.qrOn;

    let hostBody;

    if (qrOn){
      hostBody = <div align="middle">
        <h5> <small> Share Link: </small> <b>ThisIsAnExample</b> </h5>
        <br/>
        <b> QR Code: </b>
        <QrGenerator/>
        <br/>
      </div>
    }

    else{
      hostBody = <div align="middle"> </div>
    }

    return(
      <div>
        <p style={{color: "#905EAF", position: "absolute", left: "90px", top: "10px", fontSize: "72px"}}> Vesta - Host </p>
        <p style={{color: "#black", position: "absolute", left: "100px", top: "105px", fontSize: "18px"}}> Secure file sharing in your control  </p>

        <Card style={{position: "absolute", left:"80px", top:"150px", width: "500px"}}>
          <CardBody>
              <input id="file" type="file" ref={this.fileInput} onChange={(e) => this.isDisabled(e)} />
              <br/>
              <br/>
              <Button id="sendFileButton" theme="light" style={{color: 'white', borderColor: "#905EAF", backgroundColor: "#905EAF"}} disabled={streamButtonOn} onClick={(e) => this.sendFileClick(e)}> Stream </Button>
            <br/>
            <br/>
            {hostBody}
            <a href="/">
            <Button theme="light" style={{color: 'white', borderColor: "#8B0000", backgroundColor: "#8B0000", float:"right"}}> Quit </Button>
            </a>
          </CardBody>
        </Card>
      </div>
    );
  }
}

export default HostNav;

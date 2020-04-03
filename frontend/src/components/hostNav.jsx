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
var crypto = require('crypto')
var io = require('socket.io-client');
var socket = io(process.env.REACT_APP_NGROK_URL);
var opts = {autoUpgrade: false, numClients: 10};
var p2pSocket = new P2P(socket, opts);
window.$encSymKeyWithPubKey = "";
window.$symKeyBase64 = "";

p2pSocket.on('peer-msg', function (data) {
  
  if (data.fileTransferFlowState == "host") {
    console.log("WENT TO PEER MSG/HOST iF")
    window.$encSymKeyWithPubKey = data.textVal;
    window.$symKeyBase64 = data.symKeyBase64;
  }
  //localStorage.setItem("EncSymKeyWithPubKey", data.textVal)

  //coming from the recieve
  if (data.fileTransferFlowState == "recieve") {
    if (data.textVal == localStorage.getItem("EncSymKeyWithPubKey")) {
      //now we have to decrypt the data based on the symKey
      //need the encrypted data of the FILE!!
      //using localstorage for now
      console.log("in recieve")
      console.log(data)
    // var buf = Buffer.from(data.symKeyBase64, 'base64');
      //console.log(buf)
      //Buffer.from("SGVsbG8gV29ybGQ=", 'base64').toString('ascii')
      console.log("decrypting file data: " + localStorage.getItem("EncFileData") + "with key: " + data.symKeyBase64)
      var dec = crypto.createDecipher("aes-128-ctr",data.symKeyBase64)
      .update(localStorage.getItem("EncFileData"), "hex","utf-8");
      console.log("decrypting file data: " + localStorage.getItem("EncFileData") + "with key: " + data.symKeyBase64)
      console.log('From a peer recieve encrypted: %s', data.textVal);
      console.log('From a peer recieve decrypted: %s', dec);
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
      console.log("THIS IS DECRYPTED FILE CONTENT " + dec);
    }
  }


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

  //decrypting on recievers side
  //generate QR code from data.encSymKeyWithPubKey
  //send this to recieve.jsx
  localStorage.setItem("EncSymKeyWithPubKey", data.encSymKeyWithPubKey)
  localStorage.setItem("EncFileData", data.textVal)
  console.log("data.encSymKeyWithPubKey peer-file " + data.encSymKeyWithPubKey)

  //var dec = crypto.createDecipher("aes-128-ctr",localStorage.getItem("EncSymKeyWithPubKey")).update(data.textVal,"hex","utf8");
  
});

const privateClick = () => {
  p2pSocket.upgrade();
  socket.emit('go-private', true);
  p2pSocket.useSockets = false;
}

class HostNav extends Component {

  constructor(props) {
    super(props)
    this.sendFileClick = this.sendFileClick.bind(this);
    this.fileInput = React.createRef();
    this.state = {streamButtonOn : true, qrOn : true}
  }

  sendFileClick() {
    privateClick();
    var reader = new FileReader();
      console.log(this.fileInput)
      var file = this.fileInput.current.files[0]
      reader.readAsText(file);
      console.log(reader)
      reader.onload = function() {
          var text = reader.result;
          console.log("SEND FILE CLICK " + window.$encSymKeyWithPubKey)
          console.log("encrypting file data: " + text + "with key: " + Buffer.from(localStorage.getItem("browserSymKey")).toString('base64'))

          var enc = crypto.createCipher("aes-128-ctr",  Buffer.from(localStorage.getItem("browserSymKey")).toString('base64')).update(text,"utf-8","hex");
          p2pSocket.emit('peer-file', {textVal : enc, filename : file.name, encSymKeyWithPubKey : window.$encSymKeyWithPubKey});
      }
          // var reader = new FileReader();
      // console.log(this.fileInput)
      // var file = this.fileInput.current.files[0]
      // reader.readAsText(file);
      // console.log(reader)
      // reader.onload = function() {
      //     var text = reader.result;
      //     var enc = crypto.createCipher("aes-256-ctr",key).update(text,"utf-8","hex");
      //     p2pSocket.emit('peer-file', {textVal : enc, filename : file.name});
      // }
      //this.setState({qrOn : false})
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
              {/* <input id="file" type="file" ref={this.fileInput} onChange={(e) => this.isDisabled(e)} /> */}
              <input id="file" type="file" theme="light" style={{color: 'white', borderColor: "#905EAF", backgroundColor: "#905EAF", borderRadius: "5px", borderStyle: 'outset'}} ref={this.fileInput} onChange={(e) => this.isDisabled(e)} />
              <br/>
              <br/>
              <Button id="sendFileButton" theme="light" style={{textDecoration: 'none', color: 'white', borderColor: "#905EAF", backgroundColor: "#905EAF"}} disabled={streamButtonOn} onClick={(e) => this.sendFileClick(e)}> Send File </Button>
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

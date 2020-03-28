import React, { Component } from "react";
import HostNav from '../components/hostNav';
import Menu from '../components/menu';

var P2P = require('socket.io-p2p');
var key = "key"
//var fs = require('fs');
var crypto = require('crypto')
var io = require('socket.io-client');
var socket = io("http://811d589c.ngrok.io");
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

class Host extends Component {

  constructor(props) {
    super(props)
    this.sendFileClick = this.sendFileClick.bind(this);
    this.fileInput = React.createRef();
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
  }

//const HostPage = () => {

  render() {
    return (
      <div>
        <HostNav/>
        {/* <Menu> </Menu> */}
        <input id="submitText" type="text"/>
        <button type="submit" onClick={submitClick}> Submit </button>
        <button id="privateButton" type="submit" onClick={privateClick}>Private</button>
        <button id="sendFileButton" type="submit" onClick={(e) => this.sendFileClick(e)}>SendFile</button>
        <input id="file" type="file" ref={this.fileInput}/>
      </div>
    );
  }
}

export default Host;

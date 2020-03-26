import React, { Component } from "react";
import HostNav from '../components/hostNav';
import Menu from '../components/menu';

var P2P = require('socket.io-p2p');
var ioStream = require('socket.io-stream');
var key = "key"
var fs = require('fs');
var crypto = require('crypto')
var io = require('socket.io-client');
var socket = io("http://a3d26d6f.ngrok.io");
var opts = {autoUpgrade: false, numClients: 10};
var p2pSocket = new P2P(socket, opts)
p2pSocket.on('peer-msg', function (data) {
  var dec = crypto.createDecipher("aes-256-ctr",key).update(data.textVal,"hex","utf8");
  console.log('From a peer encrypted: %s', data.textVal);
  console.log('From a peer decrypted: %s', dec);
});
p2pSocket.on('go-private', function () {
  p2pSocket.upgrade(); // upgrade to peerConnection
  privateClick();
});  
ioStream(socket).on('file', (stream, data) => {
  stream.pipe(fs.createWriteStream(data.filename));
});
const privateClick = () => {
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
  sendFileClick(){
    //   var socket = io.connect("http://691afa1c.ngrok.io");
    ioStream.forceBase64 = true;
    console.log("Uploading file...")
    var file = this.fileInput.current.files[0];
    var stream = ioStream.createStream();
    ioStream(socket).emit('file', stream, { filename: "recieved-" + file.name });
    ioStream.createBlobReadStream(file).pipe(stream);
}

//const HostPage = () => {

  render() {
    return (
      <div>
      <HostNav> </HostNav>

      {/* <Menu> </Menu> */}

      <input id="submitText" type="text"/>
      <button type="submit" onClick={submitClick}>Submit</button>
      <button id="privateButton" type="submit" onClick={privateClick}>Private</button>
      <button id="sendFileButton" type="submit" onClick={(e) => this.sendFileClick(e)}>SendFile</button>
      <input id="file" type="file" ref={this.fileInput}/>



      </div>

      

    );
  }
}

export default Host;

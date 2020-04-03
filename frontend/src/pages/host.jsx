import React, { Component } from "react";
import HostNav from '../components/hostNav';
import qrGenerator from '../components/qrGenerator';
import BackImage from '../backimg.png';
import Menu from '../components/menu';
import { useState } from "react";

// var P2P = require('socket.io-p2p');
// var key = "key"
// //var fs = require('fs');
// var crypto = require('crypto')
// var io = require('socket.io-client');
// var socket = io(process.env.REACT_APP_NGROK_URL);
// var opts = {autoUpgrade: false, numClients: 10};
// var p2pSocket = new P2P(socket, opts);
// window.$encSymKeyWithPubKey = "";
// window.$symKeyBase64 = "";

// p2pSocket.on('peer-msg', function (data) {
  
//   if (data.fileTransferFlowState == "host") {
//     console.log("WENT TO PEER MSG/HOST iF")
//     window.$encSymKeyWithPubKey = data.textVal;
//     window.$symKeyBase64 = data.symKeyBase64;
//   }
//   //localStorage.setItem("EncSymKeyWithPubKey", data.textVal)

//   //coming from the recieve
//   if (data.fileTransferFlowState == "recieve") {
//     //now we have to decrypt the data based on the symKey
//     //need the encrypted data of the FILE!!
//     //using localstorage for now
//     console.log("in recieve")
//     console.log(data)
//    // var buf = Buffer.from(data.symKeyBase64, 'base64');
//     //console.log(buf)
//     //Buffer.from("SGVsbG8gV29ybGQ=", 'base64').toString('ascii')
//     console.log("decrypting file data: " + localStorage.getItem("EncFileData") + "with key: " + data.symKeyBase64)
//     var dec = crypto.createDecipher("aes-128-ctr",data.symKeyBase64)
//     .update(localStorage.getItem("EncFileData"), "hex","utf-8");
//     console.log("decrypting file data: " + localStorage.getItem("EncFileData") + "with key: " + data.symKeyBase64)
//     console.log('From a peer recieve encrypted: %s', data.textVal);
//     console.log('From a peer recieve decrypted: %s', dec);
//     console.log(data)
//     var blob = new Blob([dec]);
//     var url = window.URL.createObjectURL(blob);
//     var a = document.createElement('a');
//     a.setAttribute('hidden', '');
//     a.setAttribute('href', url);
//     a.setAttribute('download', data.filename);
//     document.body.appendChild(a);
//     a.click();
//     document.body.removeChild(a);
//     console.log("THIS IS DECRYPTED FILE CONTENT " + dec);

//   }


//   console.log('From a peer encrypted: %s', data.textVal);
//   console.log('From a peer decrypted: %s', dec);
// });

// p2pSocket.on('go-private', function () {
//   p2pSocket.upgrade();
//   console.log("going private");
//   // upgrade to peerConnection
//   // privateClick();
//   p2pSocket.useSockets = false;
// });  

// p2pSocket.on('peer-file', function (data) {

//   //decrypting on recievers side
//   //generate QR code from data.encSymKeyWithPubKey
//   //send this to recieve.jsx
//   localStorage.setItem("EncSymKeyWithPubKey", data.encSymKeyWithPubKey)
//   localStorage.setItem("EncFileData", data.textVal)
//   console.log("data.encSymKeyWithPubKey peer-file " + data.encSymKeyWithPubKey)

//   //var dec = crypto.createDecipher("aes-128-ctr",localStorage.getItem("EncSymKeyWithPubKey")).update(data.textVal,"hex","utf8");
  
// });

// const privateClick = () => {
//   p2pSocket.upgrade();
//   socket.emit('go-private', true);
//   p2pSocket.useSockets = false;
// }

// const submitClick = () => {
//   localStorage.setItem("encSymKeyWithPubKey", document.getElementById("submitText").value)
//   // var text = document.getElementById("submitText").value;
//   // var enc = crypto.createCipher("aes-256-ctr",key).update(text,"utf-8","hex");
//   // p2pSocket.emit('peer-msg', {textVal : enc});
//   console.log("IT CLICKED")
// }

// class Host extends Component {

//   constructor(props) {
//     super(props)
//     this.sendFileClick = this.sendFileClick.bind(this);
//     this.fileInput = React.createRef();
//   }

//   sendFileClick() {
//       var reader = new FileReader();
//       console.log(this.fileInput)
//       var file = this.fileInput.current.files[0]
//       reader.readAsText(file);
//       console.log(reader)
//       reader.onload = function() {
//           var text = reader.result;
//           console.log("SEND FILE CLICK " + window.$encSymKeyWithPubKey)

//           //enc is the encrypted file data with the encSymKeyWithPubKey
//           //passing in the encSymKeyWithPubKey to the recievers side
//           //will use encSymKeyWithPubKey to generate QR code
//           //Buffer.from(this.aesKey).toString('base64')
//           //Buffer.from(this.aesKey).toString('base64')
//           console.log("encrypting file data: " + text + "with key: " + Buffer.from(localStorage.getItem("browserSymKey")).toString('base64'))

//           var enc = crypto.createCipher("aes-128-ctr",  Buffer.from(localStorage.getItem("browserSymKey")).toString('base64')).update(text,"utf-8","hex");
//           p2pSocket.emit('peer-file', {textVal : enc, filename : file.name, encSymKeyWithPubKey : window.$encSymKeyWithPubKey});
//       }
//   }

const HostPage = () => {
    return (
      <div>
        <img src={BackImage} width="650" height="450" align="right" alight="bottom" style={{position: "absolute", bottom: 0, right: 0}}/>
        <HostNav/>
        <Menu> </Menu>
      </div>
    );
}

export default HostPage;

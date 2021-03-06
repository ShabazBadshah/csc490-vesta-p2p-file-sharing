import React, { Component } from 'react';
import QrCode from 'react.qrcode.generator';
import Enigma from '@cubbit/enigma';

var io = require('socket.io-client');
var socket = io("http://d26f0543.ngrok.io");

class QrGenerator extends Component {
  //static aesKey = Enigma.AES.create_key(32);

  constructor(props) {
    super(props)
    this.listenForEvents = this.listenForEvents.bind(this);
    this.aesKey = Enigma.AES.create_key(32);
    localStorage.setItem("browserSymKey", this.aesKey)
    //this.textDec = Buffer.from(this.aesKey).toString('base64');
  }

  //socket will be ready to listen automatically upon load
  componentDidMount() {
    window.addEventListener('load', this.listenForEvents);
  }

  //socket listening code 
  listenForEvents() {
    socket.on('peer-msg', function(msg){
      console.log(msg);
    });
  }

  render() {
    /* 
      The following generates a random qr code based on a key generated through
      WebAssembly import, the following import can be found below:

      References:
      - https://medium.com/cubbit/how-to-build-a-crypto-isomorphic-library-with-javascript-and-webassembly-6fc7aa708437
      - https://github.com/cubbit/enigma
    */

    var i = 0
    //const aesKey = Enigma.AES.create_key(32);
    console.log("This is the symkey buffer " + this.aesKey.buffer)


    for (i = 0; i < this.aesKey.length; i++){
      console.log(this.aesKey[i])
    }
    console.log("PRNOT PLX")
    /*
      Converting from a binary buffer to a string representation: 
      https://nodejs.org/api/buffer.html#buffer_class_method_buffer_from_string_encoding 
    */
    //const textDec = Buffer.from(aesKey).toString('base64')
    console.log("THE TEXTDEC " + Buffer.from(localStorage.getItem("browserSymKey")).toString('base64'));

    let state = {
      key: Buffer.from(localStorage.getItem("browserSymKey")).toString('base64'),
      fileTransferFlowState: "host",
      fromDesktop: true
    }
    console.log(state)

    return (
      <div>
        <QrCode value={JSON.stringify(state)} size='250'/>
      </div>
    );
  }
}

export default QrGenerator;

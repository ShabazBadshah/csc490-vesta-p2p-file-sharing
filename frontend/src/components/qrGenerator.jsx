import React, { Component } from 'react';
import QrCode from 'react.qrcode.generator'
import Enigma from '@cubbit/enigma';

class QrGenerator extends Component {

  render() {

    const key = Enigma.AES.create_key()
    // textEnd = TextEncoder("utf-8").encode(myString);
    const textDec = new TextDecoder("utf-8").decode(key).substring(0, 12);

    console.log(textDec)

    // const sharedSecret = crypto.randomBytes(16); // 128-bits === 16-bytes
    // const textSecret = sharedSecret.toString('base64');

    // random 24 character string
    // const qrValue = Math.random().toString(36).slice(-12).concat(
    // Math.random().toString(36).slice(-12))

    return (

      <div>

      <center> <h1 style={{fontSize: '72px'}}> Vesta.io </h1> </center>

      <div>
      <center> <QrCode value={textDec}/> </center>
      </div>

      </div>
    )

  }
}

export default QrGenerator;

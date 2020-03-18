import React, { Component } from 'react';
import QrCode from 'react.qrcode.generator';
import Enigma from '@cubbit/enigma';

class QrGenerator extends Component {

  render() {

    /*

    The following generates a random qr code based on a key generated through
    WebAssembly import, the following import can be found below:

    - References:
    https://medium.com/cubbit/how-to-build-a-crypto-isomorphic-library-with-javascript-and-webassembly-6fc7aa708437
    https://github.com/cubbit/enigma

    */



    const key = Enigma.AES.create_key()
    // textEnd = TextEncoder("utf-8").encode(myString);
    const textDec = new TextDecoder("utf-8").decode(key).substring(0, 12);

    // const sharedSecret = crypto.randomBytes(16); // 128-bits === 16-bytes
    // const textSecret = sharedSecret.toString('base64');

    // random 24 character string
    // const qrValue = Math.random().toString(36).slice(-12).concat(
    // Math.random().toString(36).slice(-12))

    return (
      <div>
      <QrCode value={textDec}/>
      </div>
    )

  }
}

export default QrGenerator;
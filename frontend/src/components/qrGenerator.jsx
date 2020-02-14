import React, { Component } from 'react';
import QrCode from 'react.qrcode.generator'

class QrGenerator extends Component {

  render() {

    // random 24 character string
    const qrValue = Math.random().toString(36).slice(-12).concat(
    Math.random().toString(36).slice(-12))

    return (

      <div>

      <center> <h1 style={{fontSize: '72px'}}> Vesta.io </h1> </center>

      <div>
      <center> <QrCode value={qrValue}/> </center>
      </div>

      </div>
    )

  }
}

export default QrGenerator;

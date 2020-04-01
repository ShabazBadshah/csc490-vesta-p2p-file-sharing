import React from "react";
import { render } from "react-dom";
import QrCode from 'react.qrcode.generator';

const ReceivePage = () => {

  let state = {
    key: localStorage.getItem("EncSymKeyWithPubKey"),
    fileTransferFlowState: "recieve",
    fromDesktop: true
  }

  return (
    <div>
      <h1> Receive </h1>
      <QrCode value={JSON.stringify(state)} size='350'/>
    </div>
  );
}





export default ReceivePage;

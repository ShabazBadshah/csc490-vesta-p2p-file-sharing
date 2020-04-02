import React from "react";
import { render } from "react-dom";
import QrCode from 'react.qrcode.generator';
import BackImage from '../backimg.png';
import Menu from '../components/menu';
import RecNav from '../components/recNav';

const ReceivePage = () => {

  console.log("ReceivePage EncSymKeyWithPubKey : " + localStorage.getItem("EncSymKeyWithPubKey"))
  let state = {
    key: localStorage.getItem("EncSymKeyWithPubKey"),
    fileTransferFlowState: "recieve",
    fromDesktop: true
  }

  return (
    <div>
      <h1> Receive </h1>
      <QrCode value={JSON.stringify(state)} size='350'/>
      <img src={BackImage} width="650" height="450" align="right" alight="bottom" style={{position: "absolute", bottom: 0, right: 0}}/>
      <RecNav></RecNav>
      <Menu> </Menu>
    </div>
  );
}





export default ReceivePage;

import React from "react";

import Navigation from '../components/navigation';
import Menu from '../components/menu';

const MainPage = () => {
  return (
    <div>
      <p style={{color: "#905EAF", position: "absolute", left: "90px", top: "10px", fontSize: "72px"}}> Vesta </p>
      <p style={{color: "#black", position: "absolute", left: "100px", top: "105px", fontSize: "18px"}}> Secure file sharing in your control  </p>
      <Navigation/>
      <Menu/>
    </div>
  );
}

export default MainPage;

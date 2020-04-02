import React from "react";
import Navigation from '../components/navigation';
import Menu from '../components/menu';
import BackImage from '../backimg.png';
import IconImage from '../iconclip.png';


const MainPage = () => {
  return (
    <div>
      <img src={BackImage} width="650" height="450" align="right" alight="bottom" style={{position: "absolute", bottom: 0, right: 0}}/>
      <p style={{color: "#905EAF", position: "absolute", left: "90px", top: "10px", fontSize: "72px"}}> Vesta <img src={IconImage}/> </p>
      <p style={{color: "#black", position: "absolute", left: "100px", top: "105px", fontSize: "18px"}}> Secure file sharing in your control </p>
      <Navigation/>
      <Menu/>
    </div>
  );
}

export default MainPage;

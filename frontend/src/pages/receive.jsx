import React from "react";
import BackImage from '../backimg.png';
import Menu from '../components/menu';
import RecNav from '../components/recNav';

const ReceivePage = () => {
  return (
    <div>
      <img src={BackImage} width="650" height="450" align="right" alight="bottom" style={{position: "absolute", bottom: 0, right: 0}}/>
      <RecNav></RecNav>
      <Menu> </Menu>
    </div>
  );
}

export default ReceivePage;

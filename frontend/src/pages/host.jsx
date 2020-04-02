import React, { Component } from "react";
import HostNav from '../components/hostNav';
import Menu from '../components/menu';
import BackImage from '../backimg.png';

const HostPage = () => {

    return (
      <div>
        <img src={BackImage} width="650" height="450" align="right" alight="bottom" style={{position: "absolute", bottom: 0, right: 0}}/>
        <HostNav/>
        <Menu> </Menu>
      </div>
    );
};

export default HostPage;

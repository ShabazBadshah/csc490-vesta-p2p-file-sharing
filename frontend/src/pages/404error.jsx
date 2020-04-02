import React from "react";
import BackImage from '../backimg.png';

const ErrorPage = () => {
  return (
    <div>
      <img src={BackImage} width="650" height="450" align="right" alight="bottom" style={{position: "absolute", bottom: 0, right: 0}}/>
      <h1> 404 Error </h1>
    </div>
  );
};

export default ErrorPage;

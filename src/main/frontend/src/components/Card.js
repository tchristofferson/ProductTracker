import React from "react";
import {Link} from "react-router-dom";

const Card = (props) => (
  <div className="col s12 m6 xl4">
    <div className="card blue-grey darken-1">
      <div className="card-content white-text">
        <span className="card-title truncate">{props.title}</span>
      </div>
      <div className="card-action">
        <Link className="btn blue darken-1" to={"#"}>{props.viewButtonText}</Link>
        <Link className="btn" to={"#"}>Edit</Link>
        <Link className="btn red" to={"#"}>Delete</Link>
      </div>
    </div>
  </div>
);

export default Card;

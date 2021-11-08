import React from "react";

const Card = (props) => (
  <div className="card blue-grey darken-1">
    <div className="card-content white-text">
      <span className="card-title truncate">{props.title}</span>
    </div>
    <div className="card-action">
      <a className="btn blue darken-1" href="#">
        {props.viewButtonText}
      </a>
      <a className="btn" href="#">
        Edit
      </a>
      <a className="btn red" href="#">
        Delete
      </a>
    </div>
  </div>
);

export default Card;

import React from "react";

const CardView = (props) => {
  return(
    <div className="row">
      {props.cards}
    </div>
  );
}

export default CardView;
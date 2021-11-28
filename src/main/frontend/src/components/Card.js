import React from "react";
import {Link} from "react-router-dom";
import axios from "axios";

class Card extends React.Component {
  deleteObj = () => {
    const self = this;
    const backend = this.props.settings.backend;
    const uri = this.props.deleteTo;

    axios
      .delete(backend + uri, {
        auth: self.props.settings.auth
      })
      .then(function (response) {
        //Delete this card from CardView cards
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  render() {
    return (
      <div className="col s12 m6 xl4">
        <div className="card blue-grey darken-1">
          <div className="card-content white-text">
            <span className="card-title truncate">{this.props.title}</span>
          </div>
          <div className="card-action">
            <Link className={"btn blue darken-1"} to={this.props.viewTo}>{this.props.viewButtonText}</Link>
            <Link className={"btn"} to={"#"}>Edit</Link>
            <button className={"btn red"} onClick={this.deleteObj}>Delete</button>
          </div>
        </div>
      </div>
    );
  }
}

export default Card;

import React from "react";
import {withRouter} from "react-router-dom";
import axios from "axios";

class Property extends React.Component {
  componentDidMount = () => {
    const propertyId = this.props.match.params.propertyId;

    axios
      .get("http://localhost:8080/propertyLocations?propertyId=" + propertyId, {
        auth: {
          username: 'admin',
          password: 'password'
        }
      })
      .then(function (response) {
        console.log(response.data);
      })
      .catch(function (error) {
        console.log(error);
      })
  }

  render() {
    return (
      <div>
        <h1>Product Tracking</h1>

      </div>
    );
  }
}

export default withRouter(Property);
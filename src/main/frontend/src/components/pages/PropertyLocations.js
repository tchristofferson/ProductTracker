import React from "react";
import {Link, withRouter} from "react-router-dom";
import axios from "axios";

class PropertyLocations extends React.Component {
  constructor() {
    super();
    this.state = {
      propertyName: null,
      propertyLocations: []
    }
  }

  componentDidMount = () => {
    const self = this;
    const credentials = this.props.settings.auth;
    const backend = this.props.settings.backend;
    const propertyId = this.props.match.params.propertyId;

    axios
      .get(backend + "/properties/" + propertyId, {
        auth: credentials
      })
      .then(function (response) {
        self.setState({
          propertyName: response.data.name
        })
      })
      .catch(function (error) {
        console.log(error);
      })

    axios
      .get(backend + "/propertyLocations?propertyId=" + propertyId, {
        auth: credentials
      })
      .then(function (response) {
        self.setState({
          propertyLocations: response.data
        })
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  render() {
    return (
      <div>
        <h1>Product Tracking</h1>
        <p>
          <Link to="/properties">{this.state.propertyName}</Link>
          <i className="material-icons tiny">chevron_right</i>
          <Link to="#">Locations</Link>
        </p>
        <h5>Locations</h5>
      </div>
    );
  }
}

export default withRouter(PropertyLocations);

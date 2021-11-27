import React from "react";
import { Link } from "react-router-dom";

class PropertyLocations extends React.Component {
  constructor() {
    super();
    this.state = {
      propertyLocations: []
    }
  }

  componentDidMount = () => {

  }

  render() {
    return (
      <div>
        <h1>Product Tracking</h1>
        <p>
          <Link to="/properties">[property name]</Link>
          <i className="material-icons tiny">chevron_right</i>
          <Link to="#">Locations</Link>
        </p>
        <h5>Locations</h5>
      </div>
    );
  }
}

export default PropertyLocations;

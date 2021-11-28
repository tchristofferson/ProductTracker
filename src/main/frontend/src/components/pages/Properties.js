import React from "react";
import Card from "../Card";
import CardView from "../CardView";
import axios from "axios";

class Properties extends React.Component {
  constructor() {
    super();
    this.state = {
      properties: []
    }
  }

  componentDidMount = () => {
    const self = this;

    axios
      .get("http://localhost:8080/properties", {
        //Adding this, automatically adds header
        auth: {
          username: 'admin',
          password: 'password'
        }
      })
      .then(function (response) {
        self.setState({
          properties: response.data
        })
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  render() {
    const cards = [];
    this.state.properties.forEach(property => {
      cards.push(
        <Card title={property.name}
              viewButtonText={"Locations"}
              viewTo={"/properties/" + property.id}
              deleteTo={"/properties/" + property.id}
              key={property.id}
              settings={this.props.settings} />
      );
    })

    return (
      <div>
        <h1>Product Tracking</h1>
        <h5>Properties</h5>
        <CardView cards={cards}/>
      </div>
    )
  }
}

export default Properties;

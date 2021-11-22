import React from "react";
import axios from "axios";
import Navigation from "./components/Navigation";
import Properties from "./components/Properties";
import PropertyLocations from "./components/PropertyLocations";
import { BrowserRouter, Route } from "react-router-dom";

class App extends React.Component {

  constructor() {
    super();
    this.state = {
      categories: [],
      properties: [],
      propertyLocations: [],
      products: []
    }
  }

  handleStateChange = (state) => {
    this.setState(state);
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
        console.log(response);
        self.setState({
          properties: response.data
        });
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  render = () => {
    return (
      <BrowserRouter>
        <div>
          <Navigation />
          <div className="container">
            <Route exact path="/" handleChanges={this.handleStateChange} component={() => <Properties data={this.state.properties} />} />
            <Route path="/properties" handleChanges={this.handleStateChange} component={() => <Properties data={this.state.properties} />}/>
            <Route path="/propertyLocations" handleChanges={this.handleStateChange} component={() => <PropertyLocations data={this.state.propertyLocations} />}/>
          </div>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;

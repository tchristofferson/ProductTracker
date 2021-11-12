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

  componentDidMount() {
    axios
      .get("http://localhost:8080/properties", {
        //Adding this automatically adds header
        auth: {
          username: 'admin',
          password: 'password'
        }
      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

    render() {
      return (
        <BrowserRouter>
          <div className="App">
            <Navigation/>
            <div className="container">
              <Route exact path="/" component={() => <Properties properties={this.state.properties} />} />
              <Route path="/properties" component={() => <Properties properties={this.state.properties} />}/>
              <Route path="propertyLocations" component={() => <PropertyLocations propertyLocations={this.state.propertyLocations} />}/>
            </div>
          </div>
        </BrowserRouter>
      );
  }
}

export default App;

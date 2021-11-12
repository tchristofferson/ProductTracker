import React, { useEffect } from "react";
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

  render() {
    useEffect(() => {
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
    });

    return (
        <BrowserRouter>
          <div className="App">
            <Navigation/>
            <div className="container">
              <Route exact path="/" component={Properties}/>
              <Route path="/properties" component={Properties}/>
              <Route path="propertyLocations" component={PropertyLocations}/>
            </div>
          </div>
        </BrowserRouter>
    );
  }
}

export default App;

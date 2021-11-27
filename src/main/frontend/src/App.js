import React from "react";
import axios from "axios";
import Navigation from "./components/Navigation";
import Properties from "./components/pages/Properties";
import PropertyLocations from "./components/pages/PropertyLocations";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Property from "./components/pages/Property";

class App extends React.Component {

  constructor() {
    super();
    this.state = {
      categories: new Map(),
      properties: new Map(),
      propertyLocations: new Map(),
      products: new Map()
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
        const propertiesMap = new Map();
        response.data.forEach(property => {
          propertiesMap.set(property.id, property);
        })

        self.setState({
          properties: propertiesMap
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
            <Switch>
              <Route exact path="/" component={() => <Properties handleStateChange={this.handleStateChange} data={this.state.properties} />} />
              <Route exact path="/properties" component={() => <Properties handleStateChange={this.handleStateChange} data={this.state.properties} />}/>
              <Route exact path={"/properties/:propertyId"} component={() => <Property />} />
              <Route exact path="/propertyLocations" component={() => <PropertyLocations handleStateChange={this.handleStateChange} data={this.state.propertyLocations} />}/>
            </Switch>
          </div>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;

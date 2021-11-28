import React from "react";
import Navigation from "./components/Navigation";
import Properties from "./components/pages/Properties";
import PropertyLocations from "./components/pages/PropertyLocations";
import {BrowserRouter, Route, Switch} from "react-router-dom";

class App extends React.Component {
  constructor() {
    super();
    this.state = {
      auth: {
        username: "admin",
        password: "password"
      },
      backend: "http://localhost:8080"
    }
  }

  render = () => {
    return (
      <BrowserRouter>
        <div>
          <Navigation />
          <div className="container">
            <Switch>
              <Route exact path="/" component={() => <Properties settings={this.state} />} />
              <Route exact path="/properties" component={() => <Properties settings={this.state} />}/>
              <Route exact path="/properties/:propertyId" component={() => <PropertyLocations settings={this.state} />} />
            </Switch>
          </div>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;

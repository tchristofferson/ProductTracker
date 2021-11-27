import React from "react";
import Navigation from "./components/Navigation";
import Properties from "./components/pages/Properties";
import PropertyLocations from "./components/pages/PropertyLocations";
import {BrowserRouter, Route, Switch} from "react-router-dom";

class App extends React.Component {
  render = () => {
    return (
      <BrowserRouter>
        <div>
          <Navigation />
          <div className="container">
            <Switch>
              <Route exact path="/" component={Properties} />
              <Route exact path="/properties" component={Properties}/>
              <Route exact path="/properties/:propertyId" component={PropertyLocations} />
            </Switch>
          </div>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;

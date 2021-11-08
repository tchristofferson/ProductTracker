import React, { useEffect } from "react";
import axios from "axios";
import Navigation from "./components/Navigation";
import Properties from "./components/Properties";
import PropertyLocations from "./components/PropertyLocations";
import { BorwserRouter, BrowserRouter, Route } from "react-router-dom";

const App = () => {
  useEffect(() => {
    axios
      .get("http://localhost:8080/properties")
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
        <Navigation />
        <div className="container">
          <Route exact path="/" component={Properties} />
          <Route path="/properties" component={Properties} />
          <Route path="propertyLocations" component={PropertyLocations} />
        </div>
      </div>
    </BrowserRouter>
  );
};

export default App;

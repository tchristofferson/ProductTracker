import React from "react";
import Card from "./Card";
import CardView from "./CardView";

const Properties = (props) => {
  const results = props.data;
  let properties = results.map(property => <Card title={property.name} viewButtonText={"Locations"} key={property.id} />);

  return(
    <div>
      <h1>Product Tracking</h1>
      <h5>Properties</h5>
      <CardView cards={properties} />
    </div>
  )
};

export default Properties;

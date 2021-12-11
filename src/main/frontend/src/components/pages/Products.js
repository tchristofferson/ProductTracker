import {useEffect, useState} from "react";
import axios from "axios";
import {Link, withRouter} from "react-router-dom";
import CardView from "../CardView";
import Card from "../Card";
import CollapsibleList from "../CollapsibleList";
import CollapsibleListView from "../CollapsibleListView";

const Products = (props) => {
  //key=categoryId, value=product array
  const [products, setProducts] = useState(new Map())

  useEffect(() => {
    const elems = document.querySelectorAll('.collapsible');
    window.M.Collapsible.init(elems);

    const credentials = props.settings.auth;
    const search = props.location.search;
    const propertyLocationId = new URLSearchParams(search).get("propertyLocationId");

    axios
      .get("http://localhost:8080/products?propertyLocationId=" + propertyLocationId, {
        auth: credentials
      })
      .then(function (response) {
        const products = new Map();

        response.data.forEach((product) => {
          const categoryId = product.category.id;
          let productArray = products.get(categoryId);

          if (productArray === undefined) {
            productArray = [];
            products.set(categoryId, productArray);
          }

          productArray.push(product);
        })

        setProducts(products);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [props])

  const collapsibleListArray = [];
  let productCardArray = [];
  let lastCategory = null;
  products.forEach((productArray, categoryId) => {
    if (lastCategory === null || lastCategory.id !== categoryId) {
      if (lastCategory !== null) {
        collapsibleListArray.push(
          <CollapsibleList title={lastCategory.name}
                           content={<CardView cards={productCardArray} />}
                           key={lastCategory.id} />
        );
      }

      //productArray guaranteed to have at least one element
      lastCategory = productArray[0].category;
      productCardArray = [];
    }

    productArray.forEach((product) => {
      productCardArray.push(
        <Card settings={props.settings}
              deleteTo={"/products/" + product.id}
              title={product.name}
              viewTo={"/products/" + product.id}
              viewButtonText={"View"}
              key={product.id} />
      );
    })
  })

  if (lastCategory !== null) {
    collapsibleListArray.push(
      <CollapsibleList title={lastCategory.name}
                       content={<CardView cards={productCardArray} />}
                       key={lastCategory.id} />
    );
  }

  return (
    <div>
      <h1>Product Tracking</h1>
      <h5>Products</h5>
      <CollapsibleListView content={collapsibleListArray} />
    </div>
  );
}

export default withRouter(Products);
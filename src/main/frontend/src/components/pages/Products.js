import {useEffect, useState} from "react";
import axios from "axios";
import {withRouter} from "react-router-dom";
import CardView from "../CardView";
import Card from "../Card";

const Products = (props) => {
  //key=categoryId, value=product array
  const [products, setProducts] = useState(new Map())

  useEffect(() => {
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
          let productList = products.get(categoryId);

          if (productList === undefined) {
            productList = [];
            products.set(categoryId, productList);
          }

          productList.push(product);
        })

        setProducts(products);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [props])

  const categoryList = [];
  products.forEach((productArray, categoryId) => {

  })

  return (
    <div>
      <h1>Test</h1>
    </div>
  );
}

export default withRouter(Products);
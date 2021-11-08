import React from "react";
import { NavLink } from "react-router-dom";

const Navigation = () => (
  <div>
    <nav className="blue darken-1">
      <div className="nav-wrapper">
        <ul className="right hide-on-med-and-down">
          <li>
            <NavLink to="#">Account</NavLink>
          </li>
          <li>
            <NavLink to="#">Logout</NavLink>
          </li>
        </ul>
        <NavLink to="#" data-target="mobile-demo" className="sidenav-trigger">
          <i className="material-icons">menu</i>
        </NavLink>
        <ul className="left hide-on-med-and-down">
          <li>
            <NavLink to="/properties">Products</NavLink>
          </li>
          <li>
            <NavLink to="/categories">Categories</NavLink>
          </li>
          <li>
            <NavLink to="#">Premium</NavLink>
          </li>
        </ul>
      </div>
    </nav>
    <ul className="sidenav" id="mobile-demo">
      <li>
        <NavLink to="/properties">Products</NavLink>
      </li>
      <li>
        <NavLink to="/categories">Categories</NavLink>
      </li>
      <li>
        <NavLink to="#">Premium</NavLink>
      </li>
    </ul>
  </div>
);

export default Navigation;

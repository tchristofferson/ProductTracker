import React from "react";
import { NavLink } from "react-router-dom";

class Navigation extends React.Component {
  constructor() {
    super();
    this.state = {
      sideNav: null
    }
  }

  componentDidMount = () => {
    const elems = window.document.querySelectorAll(".sidenav");
    const options = {};
    const instances = window.M.Sidenav.init(elems, options);
    this.setState({
      sideNav: instances[0]
    })
  }

  closeSideNav = () => {
    this.state.sideNav.close();
  }

  render() {
    return (
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
        <ul className="sidenav" id="mobile-demo" onClick={this.closeSideNav}>
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
  }
}

export default Navigation;

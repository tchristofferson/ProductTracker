import {Link} from "react-router-dom";

const FloatingAddButton = (props) => {
  return (
    <div className="fixed-action-btn">
      <Link className="btn-floating btn-large blue darken-1" to={props.to}>
        <i className="large material-icons">add</i>
      </Link>
    </div>
  );
}

export default FloatingAddButton;
const CollapsibleListView = (props) => {
  return (
    <ul className="collapsible">
      {props.content}
    </ul>
  );
}

export default CollapsibleListView;
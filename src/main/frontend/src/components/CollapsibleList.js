const CollapsibleList = (props) => {
  const title = props.title;
  const content = props.content;

  return(
    <li>
      <div className="collapsible-header black-text">
        {title}
      </div>
      <div className="collapsible-body">
        {content}
      </div>
    </li>
  );
}

export default CollapsibleList;
const CollapsibleList = (props) => {
  const title = props.title;
  const content = props.content;

  return(
    <div>
      <div className="collapsible-header">
        {title}
      </div>
      <div className="collapsible-body">
        {content}
      </div>
    </div>
  );
}

export default CollapsibleList;
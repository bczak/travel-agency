import React from "react";

export class TagContainer extends React.Component {
    render() {
      return (
        <div className="tagContainerWrapper">
          <div className="tagContainerHeader">Tags you chose</div>
          <div className="tagContainer">
            {this.props.tags.map(elem => (
              <div className="tagWrapper">
                <div className="tag">{elem}</div>
                <div
                  className="removeTagCross"
                  onClick={() => {
                    this.props.removeTag(elem);
                  }}
                >
                  ✗
                </div>
              </div>
            ))}
          </div>
        </div>
      );
  
      // console.log(this.props.tags);
    }
  }
  
  
import React from "react";
import TextField from "@material-ui/core/TextField";
import Autocomplete from "@material-ui/lab/Autocomplete";

class App extends React.Component {
  state = {
    tags: []
  };

  onAddTag = tag => {
    const newTags = this.state.tags.concat(tag);
    this.setState({ tags: newTags });
  };

  onDeleteTag = tag => {
    const newTags = this.state.tags.filter(elem => elem !== tag);
    this.setState({ tags: newTags });
  };

  render() {
    return (
      <div id="App">
        <TagFilter tags={this.state.tags} addTag={this.onAddTag} />
        <TagContainer tags={this.state.tags} removeTag={this.onDeleteTag} />
        <Result />
      </div>
    );
  }
}

class TagFilter extends React.Component {
  render() {
    return (
      <div className="tagFilterWraper">
        <div className="tagFilterHeader">Choose the tags, please</div>
        <Autocomplete
          options={filters}
          getOptionLabel={option => option.title}
          onChange={(event, value) => this.props.addTag(value.title)}
          style={{ width: 500 }}
          renderInput={params => (
            <TextField {...params} label="Filters" variant="outlined" />
          )}
        />
       
        
      </div>
    );
  }
}

class TagContainer extends React.Component {
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
                }}>
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

class Result extends React.Component {
  render() {
    return (
      <div className="resultWrapper">
        <div className="resultHeader">Results</div>
        <div className='sortElements'>
        <div className='sortByDate'>Sort by end date ⇅</div>
        <div className='sortByDate'>Sort by price ⇅</div>
        </div>
      </div>
    );
  }
}

const filters = [
  { title: "Summer" },
  { title: "Winter" },
  { title: "Family" },
  { title: "Beach" },
  { title: "Mountains" }
];

export default App;

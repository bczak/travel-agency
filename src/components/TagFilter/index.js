import React from "react";
import Autocomplete from "@material-ui/lab/Autocomplete";
import TextField from "@material-ui/core/TextField";

const filters = [
  { title: "Summer" },
  { title: "Winter" },
  { title: "Family" },
  { title: "Beach" },
  { title: "Mountains" },
];

export class TagFilter extends React.Component {
  render() {
    return (
      <div className="tagFilterWraper">
        <div className="tagFilterHeader">Choose tags, please</div>
        <Autocomplete
          options={filters}
          getOptionLabel={(option) => option.title}
          onChange={(event, value) => this.props.addTag(value.title)}
          style={{ width: 500 }}
          renderInput={(params) => (
            <TextField {...params} label="Filters" variant="outlined" />
          )}
        />
      </div>
    );
  }
}

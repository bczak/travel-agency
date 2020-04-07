import React from "react";
import TextField from "@material-ui/core/TextField";
import { Checkbox } from "@material-ui/core";

export class SearchButton extends React.Component {
    state = {
      isChecked: false
    };
    render() {
      return (
        <div className="searchButtonWrapper">
          <button
            className="searchButton"
            onClick={() => {
              this.props.handleOpenResult();
              this.props.getTrips();
            }}
          >
            Search
          </button>
          <div className="emailCheckboxWrapper">
            <Checkbox
              id="emailCheckbox"
              style={{ color: "#515151" }}
              onChange={event => this.setState({ isChecked: event.target.checked })}
            />
            <label htmlFor="emailCheckbox" className="emailCheckboxLabel">
              I want to receive new offers
            </label>
          </div>
          {this.state.isChecked ? (
            <TextField
              id="outlined-email-input"
              label="Email"
              type="email"
              name="email"
              autoComplete="email"
              margin="normal"
              variant="outlined"
              style={{ width: 350 }}
            />
          ) : null}
        </div>
      );
    }
  }
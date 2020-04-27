import React from "react";
import FormGroup from "@material-ui/core/FormGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import SwitchUI from "@material-ui/core/Switch";

export class Switch extends React.Component {
  state = {
    switch: false,
  };

  handleChange = (event) => {
    this.setState({ ...this.state, [event.target.name]: event.target.checked });
  };

  render() {
    return (
      <FormGroup row>
        <FormControlLabel
          control={
            <SwitchUI
              checked={this.state.switch}
              onChange={this.props.onChange}
              name="switch"
              color="primary"
              checked={this.props.value}
            />
          }
          label={this.props.label}
        />
      </FormGroup>
    );
  }
}

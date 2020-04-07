import React from "react";
import { extendMoment } from "moment-range";
import "date-fns";
import DateRangePicker from "react-daterange-picker";
import originalMoment from "moment";
const moment = extendMoment(originalMoment);

export class Calendar extends React.Component {
    constructor(props, context) {
      super(props, context);
  
      const today = moment();
  
      this.state = {
        isOpen: false,
        value: ""
      };
    }
  
    onSelect = (value, states) => {
      this.setState({ value, states });
    };
  
    onToggle = () => {
      this.setState({ isOpen: !this.state.isOpen });
    };
  
    renderSelectionValue = () => {
      return (
        <div className="calendarDateRangeText">
          <div>Choose a date range, please</div>
        </div>
      );
    };
  
    render() {
      return (
        <div className="calendarWrapper">
          <div>{this.renderSelectionValue()}</div>
  
          <div>
            <input
              className="calendarButton"
              type="button"
              value="Choose"
              onClick={this.onToggle}
            />
          </div>
          {this.state.value.length !== 0 ? (
            <div className='calendarDateRange'>
              {this.state.value.start.format("YYYY-MM-DD")}
              {" - "}
              {this.state.value.end.format("YYYY-MM-DD")}
            </div>
          ) : null}
          {this.state.isOpen && (
            <DateRangePicker
              value={this.state.value}
              onSelect={this.onSelect}
              singleDateRange={true}
            />
          )}
        </div>
      );
    }
  }
  
import React from "react";
import TextField from "@material-ui/core/TextField";
import Autocomplete from "@material-ui/lab/Autocomplete";
import "date-fns";
import { Checkbox } from "@material-ui/core";
import tour1 from "./assets/tour1.jpg";
import tour2 from "./assets/tour2.jpg";
import tour3 from "./assets/tour3.jpg";
import DateRangePicker from "react-daterange-picker";
import "react-daterange-picker/dist/css/react-calendar.css";
import originalMoment from "moment";
import { extendMoment } from "moment-range";
const moment = extendMoment(originalMoment);

class App extends React.Component {
  state = {
    tags: [],
    isSearchClicked: false
  };

  onAddTag = tag => {
    const newTags = this.state.tags.concat(tag);
    this.setState({ tags: newTags });
  };

  onDeleteTag = tag => {
    const newTags = this.state.tags.filter(elem => elem !== tag);
    this.setState({ tags: newTags });
  };

  handleOpenResult = () => {
    this.setState({ isSearchClicked: true });
  };

  render() {
    return (
      <div id="App">
        <Calendar />
        <TagFilter tags={this.state.tags} addTag={this.onAddTag} />
        <TagContainer tags={this.state.tags} removeTag={this.onDeleteTag} />
        <SearchButton handleOpenResult={this.handleOpenResult} />
        {this.state.isSearchClicked ? <Result /> : null}
      </div>
    );
  }
}

class Calendar extends React.Component {
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

class TagFilter extends React.Component {
  render() {
    return (
      <div className="tagFilterWraper">
        <div className="tagFilterHeader">Choose tags, please</div>
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

class SearchButton extends React.Component {
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
          }}
        >
          Search
        </button>
        <div className="emailCheckboxWrapper">
          <Checkbox
            id="emailCheckbox"
            style={{ color: "#515151" }}
            onChange={event => {
              this.setState({ isChecked: event.target.checked });
            }}
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

class Result extends React.Component {
  render() {
    return (
      <div className="resultWrapper">
        <div className="resultHeader">Results</div>
        <div className="sortElements">
          <div className="sortByDate">Sort by end date ⇅</div>
          <div className="sortByDate">Sort by price ⇅</div>
        </div>
        <div className="resultElementsWrapper">
          <img src={tour1} />
          <img src={tour2} />
          <img src={tour3} />
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

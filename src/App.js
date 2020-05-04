import React from "react";
import API from "./api";
import "react-date-range/dist/styles.css"; // main style file
import "react-date-range/dist/theme/default.css"; // theme css file
import { DateRangePicker } from "react-date-range";
import Icon from "@material-ui/core/Icon";

import {
  AppBar,
  Paper,
  Typography,
  Button,
  Toolbar,
  Card,
  CardActions,
  CardMedia,
  CardContent,
  Slider,
  TextField,
} from "@material-ui/core";
import Autocomplete from "@material-ui/lab/Autocomplete";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import { Switch } from "./components/Switch";
import Chip from "@material-ui/core/Chip";
import { PageCount } from "./components/PageCount";

function getDate(date) {
  const ye = new Intl.DateTimeFormat("en", { year: "numeric" }).format(date);
  const mo = new Intl.DateTimeFormat("en", { month: "2-digit" }).format(date);
  const da = new Intl.DateTimeFormat("en", { day: "2-digit" }).format(date);
  return `${da}.${mo}.${ye}`;
}

class App extends React.Component {
  state = {
    tags: [],
    isSearchClicked: false,
    trips: [],
  };

  getTrips = async (by = "PRICE", order = "ASCENDING") => {
    try {
      const trips = await API.getTripsApi(by, order);
      if (Array.isArray(trips)) {
        this.setState({ trips });
      }
    } catch (error) {
      console.log({ error });
    }
  };
  allTags = [];

  async componentDidMount() {
    let tag = await API.getAllTags();
    let cnt = await API.getAllCountries();
    this.setState({ ...this.state, tags: tag, countries: cnt });
  }

  constructor() {
    super();
    this.state = {
      tags: [],
      selectedTags: [],
      selectedCountries: [],
      isAllTags: true,
      isAllCountries: true,
      countries: [],
      value: [1000, 2000],
      cards: [],
      sort: 0,
      open: false,
      expanded: false,
      limit: 10,
      selectionRange: {
        startDate: new Date(),
        endDate: new Date(),
        key: "selection",
      },
    };
  }

  handleLimit = (value) => {
    this.setState({ limit: value });
    this.search(value);
  };

  sortBy = (param) => {
    this.search(this.state.limit, param);
  };

  handleChange = (event, newValue) => {
    this.setState({ ...this.state, value: newValue });
  };
  handleSelect = (ranges) => {
    this.setState({
      ...this.state,
      selectionRange: {
        ...ranges.selection,
        key: this.state.selectionRange.key,
      },
    });
  };

  search = async (limit = 10, param) => {
    this.setState({ ...this.state, open: true });
    if (param === undefined) param = 0;
    let res = await API.getTrips(
      this.state.selectedTags.map((e) => e.title),
      this.state.isAllTags,
      this.state.selectedCountries.map((e) => e.title),
      this.state.isAllCountries,
      limit
    );
    console.log(res);
    let cards = [];

    for (let i = 0; i < res.length; i++) {
      // if (
      //   res[i].price < this.state.value[0] ||
      //   res[i].price > this.state.value[1]
      // )
      //   continue;

      let start = new Date(res[i].startDate);
      let end = new Date(res[i].endDate);
      let needS = this.state.selectionRange.startDate;
      let needE = this.state.selectionRange.endDate;
      if (getDate(needE) !== getDate(needS)) {
        if ((end > needS && end < needE) || (start > needS && start < needE)) {
        } else continue;
      }

      cards.push(
        <Card
          className={"card"}
          variant={"outlined"}
          key={res[i].id}
          json={res[i]}
        >
          <CardMedia
            className={"image"}
            image={res[i].imageLink}
            title={res[i].name}
          />
          <CardContent>
            <Typography variant={"h4"}>{res[i].name}</Typography>
            {/*<Typography variant={'body2'}>text</Typography>*/}
            <List component="nav" aria-label="contacts">
              <ListItem>
                <ListItemIcon>
                  <Icon>attach_money</Icon>
                </ListItemIcon>
                <ListItemText primary={res[i].price} />
              </ListItem>
              <ListItem>
                <ListItemIcon>
                  <Icon>history</Icon>
                </ListItemIcon>
                <ListItemText
                  primary={
                    Math.floor(
                      Math.abs(
                        new Date(res[i].startDate) - new Date(res[i].endDate)
                      ) /
                        1000 /
                        60 /
                        60 /
                        24
                    ) + " days"
                  }
                />
              </ListItem>
              <ListItem>
                <ListItemIcon>
                  <Icon>calendar_today</Icon>
                </ListItemIcon>
                <ListItemText
                  primary={
                    getDate(new Date(res[i].startDate)) +
                    " - " +
                    getDate(new Date(res[i].endDate))
                  }
                />
              </ListItem>
              <ListItem>
                <ListItemIcon>
                  <Icon>explore</Icon>
                </ListItemIcon>
                <ListItemText
                  primary={res[i].countries.map((e) => e.name).join(", ")}
                />
              </ListItem>
            </List>
            <div className="chips">
              {res[i].tags.map((e) => (
                <Chip label={e.name} />
              ))}
            </div>
          </CardContent>
          <CardActions disableSpacing className={"actions"}>
            <Button
              variant={"outlined"}
              className={"more-button"}
              href={res[i].link}
              color={"primary"}
            >
              More
            </Button>
          </CardActions>
        </Card>
      );
    }
    if (cards.length === 0) {
      alert("No results");
    }

    if (param === 0) {
      cards.sort((a, b) => {
        let nameA = a.props.json.name.toLowerCase(),
          nameB = b.props.json.name.toLowerCase();
        if (nameA < nameB)
          //sort string ascending
          return -1;
        if (nameA > nameB) return 1;
        return 0; //default return value (no sorting)
      });
    } else if (param === 3) {
      cards.sort((a, b) => {
        return b.props.json.price - a.props.json.price;
      });
    } else if (param === 2) {
      cards.sort((a, b) => {
        return a.props.json.price - b.props.json.price;
      });
    } else if (param === 1) {
      cards.sort((a, b) => {
        let durationA = Math.abs(
          new Date(a.props.json.startDate) - new Date(a.props.json.endDate)
        );
        let durationB = Math.abs(
          new Date(b.props.json.startDate) - new Date(b.props.json.endDate)
        );
        return durationA - durationB;
      });
    } else if (param === 4) {
      cards.sort((a, b) => {
        let A = new Date(a.props.json.startDate);
        let B = new Date(b.props.json.startDate);
        return A - B;
      });
    } else if (param === 5) {
      cards.sort((a, b) => {
        let A = new Date(a.props.json.startDate);
        let B = new Date(b.props.json.startDate);
        return B - A;
      });
    }

    console.log(cards);
    this.setState({ ...this.state, cards: cards, sort: param, open: false });
  };

  render() {
    function valuetext(value) {
      return `${value}$`;
    }

    return (
      <div id="App">
        <AppBar className={"root"}>
          <Toolbar>
            <Typography variant="h6" className={"title"}>
              Travel Agency
            </Typography>
          </Toolbar>
        </AppBar>
        <Paper elevation={3} variant={"outlined"} className={"main"}>
          <div className="filter">
            <span id={"filter"}>Filter</span>
            <span className={"select-data"}>Select data range:</span>
            <div className={"date"}>
              <DateRangePicker
                editableDateInputs={true}
                ranges={[this.state.selectionRange]}
                onChange={this.handleSelect}
                moveRangeOnFirstSelection={false}
              />
              <div className="params">
                <Autocomplete
                  multiple
                  id="tags"
                  options={this.state.tags}
                  onChange={(k, v) => {
                    this.setState({ ...this.state, selectedTags: v });
                  }}
                  getOptionLabel={(option) => option.title}
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      variant="outlined"
                      label="Select tags"
                      placeholder="Tags"
                    />
                  )}
                />
                <Switch
                  label={this.state.isAllTags ? "Any match" : "All match"}
                  onChange={(value) => {
                    this.setState({ isAllTags: !this.state.isAllTags });
                  }}
                  value={this.state.isAllTags}
                />
                <span className={"spacer"} />
                <Autocomplete
                  multiple
                  id="cntrs-standard"
                  options={this.state.countries}
                  onChange={(k, v) => {
                    this.setState({ ...this.state, selectedCountries: v });
                  }}
                  getOptionLabel={(option) => option.title}
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      variant="outlined"
                      label="Select countries"
                      placeholder="Countries"
                    />
                  )}
                />
                <Switch
                  label={this.state.isAllCountries ? "Any match" : "All match"}
                  onChange={(value) => {
                    this.setState({
                      isAllCountries: !this.state.isAllCountries,
                    });
                  }}
                  value={this.state.isAllCountries}
                />
                <span className="spacer" />
                <div>
                  <Typography id="range-slider" gutterBottom>
                    Price range
                  </Typography>
                  <Slider
                    className={"slider"}
                    // marks={true}
                    value={this.state.value}
                    min={0}
                    max={5000}
                    onChange={this.handleChange}
                    aria-labelledby="range-slider"
                    getAriaValueText={valuetext}
                    valueLabelDisplay="on"
                  />
                  <Typography>
                    Price from {this.state.value[0]}$ to {this.state.value[1]}$
                  </Typography>
                </div>

                {/*<ChipInput*/}
                {/*	value={tags}*/}
                {/*	onAdd={(chip) => handleAddChip(chip)}*/}
                {/*	onDelete={(chip, index) => handleDeleteChip(chip, index)}*/}
                {/*	dataSource={allTags}*/}
                {/*	helperText={'Select Tags'}*/}

                {/*/>*/}
              </div>
            </div>
            <Button
              className={"button-search"}
              variant={"outlined"}
              color={"primary"}
              onClick={() => this.search()}
            >
              Search
            </Button>
          </div>
        </Paper>
        {this.state.cards.length > 0 ? (
          <Paper className={"reminder"} variant={"outlined"} elevation={2}>
            <Typography variant={"h4"}>
              Do you want to subscribe to this filter?
            </Typography>
            <TextField
              variant={"outlined"}
              className={"subs-input"}
              placeholder={"Enter your email"}
              type={"mail"}
            />
            <Button variant={"outlined"} color={"primary"} className={"subs"}>
              Subscribe
            </Button>
          </Paper>
        ) : (
          ""
        )}
        <Paper className={"result"} variant={"outlined"} elevation={2}>
          <Paper className={"panel"} variant={"outlined"} elevation={3}>
            <div className="button-group">
              <Button>Sort By:</Button>
              <Button
                onClick={() => {
                  this.sortBy(0);
                }}
                disableElevation
                variant={this.state.sort === 0 ? "contained" : "outlined"}
                color={"primary"}
              >
                Title
              </Button>
              <Button
                onClick={() => {
                  this.sortBy(1);
                }}
                disableElevation
                variant={this.state.sort === 1 ? "contained" : "outlined"}
                color={"primary"}
              >
                Duration
              </Button>
              <Button
                onClick={() => {
                  this.sortBy(2);
                }}
                disableElevation
                variant={this.state.sort === 2 ? "contained" : "outlined"}
                color={"primary"}
              >
                Low Price
              </Button>
              <Button
                onClick={() => {
                  this.sortBy(3);
                }}
                disableElevation
                variant={this.state.sort === 3 ? "contained" : "outlined"}
                color={"primary"}
              >
                High Price
              </Button>
              <Button
                onClick={() => {
                  this.sortBy(4);
                }}
                disableElevation
                variant={this.state.sort === 4 ? "contained" : "outlined"}
                color={"primary"}
              >
                Newest
              </Button>
              <Button
                onClick={() => {
                  this.sortBy(5);
                }}
                disableElevation
                variant={this.state.sort === 5 ? "contained" : "outlined"}
                color={"primary"}
              >
                Latest
              </Button>
            </div>
          </Paper>
          <Paper className={"res"} elevation={3} variant={"outlined"}>
            <div className="resultText">{this.state.cards.length} results</div>
            <PageCount
              limit={this.state.limit}
              handleLimit={this.handleLimit}
            />
          </Paper>
          <Backdrop open={this.state.open} className={"loader"}>
            <CircularProgress color="inherit" />
          </Backdrop>
          {this.state.cards}
        </Paper>
      </div>
    );
  }
}

export default App;

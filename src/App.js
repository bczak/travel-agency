import React from "react";
import "react-daterange-picker/dist/css/react-calendar.css";
import { Calendar } from "./components/Calendar";
import { Result } from "./components/Result";
import { TagContainer } from "./components/TagContainer";
import { TagFilter } from "./components/TagFilter";
import { SearchButton } from "./components/SearchButton";
import { getTripsApi } from "./api";

class App extends React.Component {
  state = {
    tags: [],
    isSearchClicked: false,
    trips: [],
    dateRange: null
  };

  onAddTag = (tag) => {
    const newTags = this.state.tags.concat(tag);
    this.setState({ tags: newTags });
  };

  onDeleteTag = (tag) => {
    const newTags = this.state.tags.filter((elem) => elem !== tag);
    this.setState({ tags: newTags });
  };

  handleOpenResult = () => {
    this.setState({ isSearchClicked: true });
  };

  toObjectTags = (tags) =>
    tags.reduce((prev, tag) => {
      if (!prev[tag]) prev[tag] = tag;
      return prev;
    }, {});

  getTaggedTrips = (trips) => {
    let result = [];
    const tags = this.toObjectTags(this.state.tags);

    trips.forEach((trip) => {
      const isAll = [];
      trip.tags.forEach((tag) => {
        isAll.push(Boolean(tags[tag.name]));
      });
      const isTaggedTrip =
        isAll.filter((item) => item).length === Object.keys(tags).length;
      if (isTaggedTrip) {
        result.push(trip);
      }
    });
    return result;
  };

  getTrips = async (by = "PRICE", order = "ASCENDING") => {
    try {
      const resTrips = await getTripsApi(by, order);
      if (Array.isArray(resTrips)) {
        const trips = this.getTaggedTrips(resTrips);
        this.setState({ trips });
        console.log(trips);
      }
    } catch (error) {
      // console.log({ error });
    }
  };

  getDateRange = (value) => {
    this.setState({dateRange: value})
    console.log(value.start.format("YYYY-MM-DD"));
    console.log(value.end.format("YYYY-MM-DD"));
  }

  render() {
    return (
      <div id="App">
        <Calendar  getDateRange={this.getDateRange}/>
        <TagFilter tags={this.state.tags} addTag={this.onAddTag} />
        <TagContainer tags={this.state.tags} removeTag={this.onDeleteTag} />
        <SearchButton
          handleOpenResult={this.handleOpenResult}
          getTrips={this.getTrips}
        />
        {this.state.isSearchClicked ? (
          <Result
            tags={this.state.tags}
            trips={this.state.trips}
            getTrips={this.getTrips}
          />
        ) : null}
      </div>
    );
  }
}

export default App;

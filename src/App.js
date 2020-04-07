import React from "react";
import "react-daterange-picker/dist/css/react-calendar.css";
import { Calendar } from "./components/Calendar";
import { Result } from "./components/Result";
import { TagContainer } from "./components/TagContainer";
import { TagFilter } from "./components/TagFilter";
import { SearchButton } from "./components/SearchButton";
import { getTripsApi } from "./api"

class App extends React.Component {
  state = {
    tags: [],
    isSearchClicked: false,
    trips: []
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

  getTrips = async (by='PRICE', order='ASCENDING') => {
    const trips = await getTripsApi(by, order);
    this.setState({ trips })
  }

  render() {
    return (
      <div id="App">
        <Calendar />
        <TagFilter tags={this.state.tags} addTag={this.onAddTag} />
        <TagContainer tags={this.state.tags} removeTag={this.onDeleteTag} />
        <SearchButton handleOpenResult={this.handleOpenResult} getTrips={this.getTrips}/>
        {this.state.isSearchClicked ? <Result trips={this.state.trips} getTrips={this.getTrips} /> : null}
      </div>
    );
  }
}


export default App;

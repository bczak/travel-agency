import React from "react";
import { getTripDetailApi } from "../../api";
import moment from "moment";


export class TripDetailModal extends React.Component {
  state = {
    trip: {},
  };

  async componentDidMount() {
    const trip = await getTripDetailApi(this.props.tripId);
    this.setState({ trip: trip });
  }

  render() {
    console.log(this.state.trip);
    return (
      <div className="tripDetailModalWrapper">
        <div className="tripDetailModalView">
          <div className="tripDetailModalTextWrapper">
            <div>{`Name: ${this.state.trip.name}`}</div>
            <div>{`Start date: ${moment(this.state.trip.startDate).format(
              "DD-MM-YY"
            )}`}</div>
            <div>{`End date: ${moment(this.state.trip.endDate).format("DD-MM-YY")}`}</div>
            <div>{`Price: ${this.state.trip.price}`}</div>
            <div>{`Location: ${this.state.trip.location}`}</div>
          </div>
          <button
            className="tripDetailModalCloseButton"
            onClick={() => {
              this.props.closeTripDetailsModal();
            }}
          >
            Close
          </button>
        </div>
      </div>
    );
  }
}

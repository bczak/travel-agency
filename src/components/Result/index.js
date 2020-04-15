import React from "react";
import moment from "moment";
import { TripDetailModal } from "../TripDetailModal";

export class Result extends React.Component {
  state = {
    order: "DESCENDING",
    tripDetailRequested: false,
    tripId: null
  };

  closeTripDetailsModal = () => {
    this.setState({ tripDetailRequested: false });
  };

  render() {
    return (
      <>
        {this.state.tripDetailRequested ? (
          <TripDetailModal closeTripDetailsModal={this.closeTripDetailsModal} tripId={this.state.tripId} />
        ) : null}
        <div className="resultWrapper">
          <div className="resultHeader">Results</div>
          <div className="sortElements">
            <div
              className="sortByDate"
              onClick={() => {
                this.props.getTrips("START", this.state.order);
                this.setState({
                  order:
                    this.state.order === "ASCENDING"
                      ? "DESCENDING"
                      : "ASCENDING",
                });
              }}
            >
              Sort by end date ⇅
            </div>
            <div
              className="sortByPrice"
              onClick={() => {
                this.props.getTrips("PRICE", this.state.order);
                this.setState({
                  order:
                    this.state.order === "ASCENDING"
                      ? "DESCENDING"
                      : "ASCENDING",
                });
              }}
            >
              Sort by price ⇅
            </div>
          </div>
          <div className="resultElementsWrapper">
            {this.props.trips.map((trip, idx) => {
              return (
                <div
                  key={trip.id}
                  className="result_trip"
                  onClick={() => {
                    this.setState({ tripId: trip.id})
                    this.setState({ tripDetailRequested: true });
                  }}
                >
                  <div>{`Name: ${trip.name}`}</div>
                  <div>{`Start date: ${moment(trip.startDate).format(
                    "DD-MM-YY"
                  )}`}</div>
                  <div>{`End date: ${moment(trip.endDate).format(
                    "DD-MM-YY"
                  )}`}</div>
                  <div>{`Price: ${trip.price}`}</div>
                  <br />
                </div>
              );
            })}
          </div>
        </div>
      </>
    );
  }
}

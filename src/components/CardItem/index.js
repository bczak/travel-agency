import React from "react";
import Icon from "@material-ui/core/Icon";

import {
  Typography,
  Button,
  Card,
  CardActions,
  CardMedia,
  CardContent,
} from "@material-ui/core";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import { getDate } from "../../App";
import Chip from "@material-ui/core/Chip";

export class CardItem extends React.PureComponent {
  render() {
    const { card } = this.props;

    return (
      <Card className={"card"} variant={"outlined"} key={card.id} json={card}>
        <CardMedia
          className={"image"}
          image={card.imageLink}
          title={card.name}
        />
        <CardContent>
          <Typography variant={"h4"}>{card.name}</Typography>
          {/*<Typography variant={'body2'}>text</Typography>*/}
          <List component="nav" aria-label="contacts">
            <ListItem>
              <ListItemIcon>
                <Icon>attach_money</Icon>
              </ListItemIcon>
              <ListItemText primary={card.price} />
            </ListItem>
            <ListItem>
              <ListItemIcon>
                <Icon>history</Icon>
              </ListItemIcon>
              <ListItemText
                primary={
                  Math.floor(
                    Math.abs(
                      new Date(card.startDate) - new Date(card.endDate)
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
                  getDate(new Date(card.startDate)) +
                  " - " +
                  getDate(new Date(card.endDate))
                }
              />
            </ListItem>
            <ListItem>
              <ListItemIcon>
                <Icon>explore</Icon>
              </ListItemIcon>
              <ListItemText
                primary={card.countries.map((e) => e.name).join(", ")}
              />
            </ListItem>
          </List>
          <div className="chips">
            {card.tags.map((e) => (
              <Chip key={e.name} label={e.name} />
            ))}
          </div>
        </CardContent>
        <CardActions disableSpacing className={"actions"}>
          <Button
            variant={"outlined"}
            className={"more-button"}
            href={card.link}
            color={"primary"}
          >
            More
          </Button>
        </CardActions>
      </Card>
    );
  }
}

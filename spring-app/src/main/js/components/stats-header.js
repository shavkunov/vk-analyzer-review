import React from 'react';
import Avatar from 'material-ui/Avatar';
import {List, ListItem} from 'material-ui/List';
import Subheader from 'material-ui/Subheader';
import Reposts from 'material-ui/svg-icons/social/share';
import Favorite from 'material-ui/svg-icons/action/favorite';
import Visibility from 'material-ui/svg-icons/action/visibility';

const center = {
    display: "flex",
    flexDirection: "column",
    flexWrap: "wrap",
    justifyContent: "center",
    alignItems: "center",
};

const header = {
    display: "flex",
    flexDirection: "column",
    flexWrap: "wrap",
    justifyContent: "center",
    alignItems: "center",
    marginTop: "0px",
    marginBottom: "0px"
};

const rowAlignment = {
    display: "flex",
    flexDirection: "row",
    alignItems: "center",
    flexWrap: "wrap",
};

/**
 * Component responsible for information displayed in stats header.
 */
class StatsHeader extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        let statsHeader = "Statistics of " + this.props.posts + " last posts";
        return (
            <div style={center}>
                <List style={header}>
                    <ListItem disabled={true} 
                              leftAvatar={<Avatar src={this.props.owner.photo} />} 
                              primaryText={<a href={this.props.owner.link}> {this.props.owner.name} </a>} /> 
                </List>

                <p style={header}> {statsHeader} </p>

                <Subheader style={header}> Average values </Subheader>

                <div style={rowAlignment}>
                    <Favorite style={{ marginLeft: "15px", marginRight: "10px" }} /> {this.props.averageLikes} 
                    <Reposts style={{ marginLeft: "15px", marginRight: "10px" }} /> {this.props.averageReposts}
                    <Visibility style={{ marginLeft: "15px", marginRight: "10px" }} /> {this.props.averageViews}
                </div>
            </div>
        );
    }
}

export default StatsHeader;
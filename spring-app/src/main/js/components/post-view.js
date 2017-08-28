import React from 'react';
import {GridList, GridTile} from 'material-ui/GridList';
import Reposts from 'material-ui/svg-icons/social/share';
import Favorite from 'material-ui/svg-icons/action/favorite';
import Visibility from 'material-ui/svg-icons/action/visibility';

const center = {
    display: "flex",
    flexDirection: "column",
    flexWrap: "wrap",
    justifyContent: "start",
    alignItems: "center",
    width: "45%",
};

const rowAlignment = {
    display: "flex",
    flexDirection: "row",
    alignItems: "center",
    flexWrap: "wrap",
    marginTop: "10px",
};

/**
 * Post representing component.
 */
class PostView extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div style={center}>
                <h4  style={{marginTop: "5px", marginBottom: "0px"}}> {this.props.data.description} </h4>
                <p style={{marginTop: "10px", marginBottom: "0px"}}> <a href={this.props.data.postLink}> Post link </a> </p>

                <p style={{ marginRight: "5px", marginLeft: "5px" }}> {this.props.data.text} </p>

                {this.renderImage()}

                <div style={rowAlignment}>
                    <Favorite style={{ marginLeft: "10px", marginRight: "5px" }} /> {this.props.data.likes} 
                    <Reposts style={{ marginLeft: "10px", marginRight: "5px" }} /> {this.props.data.reposts}
                    <Visibility style={{ marginLeft: "10px", marginRight: "5px" }} /> {this.props.data.views}
                </div>
            </div>
        );
    }

    renderImage = () => {
        if (this.props.data.images.length == 1) {
            let imageUrl = this.props.data.images[0];
            return (
                // TODO : relative to screen.
                <img src={imageUrl} style={{ maxHeight: "500px", maxWidth: "400px", }}/>
            );
        }

        // TODO : style this grid list.
        return (
            <GridList cols={2} cellHeight={200}>
                {this.props.data.images.map((image) => (
                    <GridTile>
                        <img src={image} />
                    </GridTile>
                ))}
            </GridList>
        );
    };
}

export default PostView;
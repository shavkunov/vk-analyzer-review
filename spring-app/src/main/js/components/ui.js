import React from 'react';
import StartForm from './start-form'
import StatsView from './stats-view'
import Paper from 'material-ui/Paper';
import Snackbar from 'material-ui/Snackbar';
import RaisedButton from 'material-ui/RaisedButton';
import Loading from 'react-loading-animation';
import { fadeInUp } from 'react-animations';
import { StyleSheet, css } from 'aphrodite';

/**
 * Animation for stats appearing.
 * @type {*}
 */
const fadeAnimation = StyleSheet.create({
    fadeInUp: {
        animationName: fadeInUp,
        animationDuration: '1s'
    }
});

/**
 * Statistics styling.
 */
const paper = {
    display: "flex",
    flexWrap: "wrap",
    justifyContent: "center",
    alignItems: "center",
    marginTop: "15px",
    width: "90%",
};


const center = {
    display: "flex",
    flexDirection: "column",
    flexWrap: "wrap",
    justifyContent: "center",
    alignItems: "center",
};

/**
 * Main compoments rendering front
 */
class UI extends React.Component {
    constructor() {
        super();

        /**
         * if open is true when snackbar appears otherwise it's hidden.
         * if beginLoad is true then animation circle is shown otherwise not.
         * if isTableLoaded is true then client accepted table from server and it's stored to
         * table value.
         */
        this.state = {
            open: false,
            beginLoad: false,
            isTableLoaded: false,
            table: null
        }
    }

    /**
     * Render loading animation circle when it's needed.
     */
    renderLoading = () => {
        if (this.state.beginLoad) {
            return (
                <Loading style={{ marginTop: "40px" }} />
            );
        }
    };

    /**
     * Rendering statistics when it's loaded.
     */
    renderTable = () => {
        if (this.state.isTableLoaded) {
            return (
                <Paper zDepth={4} style={paper} className={css(fadeAnimation.fadeInUp)}>
                    <RaisedButton onClick={this.saveStats}
                                  label="Add to database"/>

                    <StatsView table={this.state.table}/>

                    <Snackbar contentStyle={center}
                              open={this.state.open}
                              message="Stats saved"
                              autoHideDuration={4000}
                              onRequestClose={this.handleRequestClose}
                    />
                </Paper>
            );
        }
    };

    /**
     * Request to server for saving results in database.
     */
    saveStats = () => {
        this.setState({
            open: true,
        });

        let requestUrl = "http://localhost:8080/saveStats";
        let requestData = this.state.table;
        $.ajax({
            type: "POST",
            url: requestUrl,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(requestData),
            dataType: "json",
            success: function(response) {
                console.log(response);
            }
        });
    };

    /**
     * Hiding the snackbar.
     */
    handleRequestClose = () => {
        this.setState({
            open: false,
        });
    };

    /**
     * Rendering all together.
     */
    render() {
        return (
            <div style={center}>
                <StartForm handleSubmit={this.handleSubmit.bind(this)}
                           beginLoad={this.beginLoad.bind(this)}
                           stopLoad={this.stopLoad.bind(this)}/>
                {this.renderLoading()}
                {this.renderTable()}
            </div>
        );
    }

    beginLoad = () => {
        this.setState({
            beginLoad: true,
        });
    };

    stopLoad = () => {
        this.setState({
            beginLoad: false,
        });
    };


    handleSubmit = (table) => {
        this.stopLoad();
        this.setState({
            isTableLoaded: true,
            table: table,
        });
    };
}

export default UI;
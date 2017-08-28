import React from 'react';
import StatsHeader from './stats-header';
import StatsPostsView from './stats-posts-view';

/**
 * Component is collecting all subcomponents together.
 */
class StatsView extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <StatsHeader posts={this.props.table.amount}
                             averageLikes={this.props.table.averageLikes}
                             averageReposts={this.props.table.averageReposts}
                             averageViews={this.props.table.averageViews}
                             owner={this.props.table.owner}
                             />

                <StatsPostsView table={this.props.table}/>

            </div>
        );
    }
}

export default StatsView;
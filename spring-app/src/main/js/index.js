import React from 'react';
import ReactDOM from 'react-dom';
import injectTapEventPlugin from 'react-tap-event-plugin';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import UI from './components/ui.js';

injectTapEventPlugin();
const App = () => (
    <MuiThemeProvider>
        <UI />
    </MuiThemeProvider>
);

ReactDOM.render(
    <App />,
    document.getElementById('react')
);
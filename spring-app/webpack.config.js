var webpack = require('webpack');
var path = require('path');

module.exports = {
    entry: './src/main/js/index.js',
    devtool: 'sourcemaps',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },

    resolve: {
        modules: [__dirname, 'node_modules', 'src/main/js'],
        extensions: ['.js', '.jsx', '.css', '.json']
    },

    module: {
        rules: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loaders: ['babel-loader?presets[]=react,presets[]=es2015,presets[]=stage-0']
            },

            {
                test: /\.css$/,
                loader: 'css-loader',
                query: {
                    modules: true,
                    localIdentName: '[name]__[local]___[hash:base64:5]'
                }
            }
        ]
    },
};
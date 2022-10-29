var path = require('path');

module.exports = {
    context: path.resolve(__dirname, './src/main/resources/app/'),
    entry: {
        application: './app.ts'
    },
    output: {
        filename: './[name].js',
        path: __dirname + 'dest'
    },
    resolve: {
        modulesDirectories: ['node_modules'],
        root: path.resolve(__dirname),
        extensions: ['', '.ts', '.js']
    },
    devtool: "source-map",
    module: {
        loaders: [
            {
                test: /\.ts$/,
                loader: 'ts-loader'
            }
        ]
    }
}

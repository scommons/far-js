const nodeExternals = require('webpack-node-externals');
const webpack = require('webpack');

// Setup webpack Hot Module Replacement (HMR)
// see: https://github.com/webpack/docs/issues/45
//
module.exports = {

  mode: 'development',
  
  entry: [
    'webpack/hot/poll?1000',
    './reload.index.js'
  ],
  output: {
    filename: './bundle.js'
  },
  
  target: 'node', // important in order not to bundle built-in modules like path, fs, etc.  
  
  externals: [nodeExternals({ // in order to ignore modules in node_modules folder from bundling
    whitelist: ['webpack/hot/poll?1000']
  })],
  
  plugins: [
      new webpack.HotModuleReplacementPlugin()
  ]
};

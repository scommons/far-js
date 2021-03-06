## Developing

### How to Build

To build and run all the tests use the following command:
```bash
sbt test
```

### How to Run

To run the app use the following commands:
```bash
sbt "project farjs-app" fastOptJS::webpack

node ./app/target/scala-2.12/scalajs-bundler/main/reload.index.js
```

### How to Run with Reload Workflow

```bash
#console 1:
sbt
>project farjs-app
>~fastOptJS

#console 2:
cd ./app/target/scala-2.12/scalajs-bundler/main/
./node_modules/webpack/bin/webpack.js --watch --config ./reload.webpack.config.js

#console 3:
node ./app/target/scala-2.12/scalajs-bundler/main/dist/bundle.js
```

## Resources

You can find more info about the common modules [here](https://scommons.org/)

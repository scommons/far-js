
[![Build Status](https://travis-ci.com/scommons/far-js.svg?branch=master)](https://travis-ci.com/scommons/far-js)
[![Coverage Status](https://coveralls.io/repos/github/scommons/far-js/badge.svg?branch=master)](https://coveralls.io/github/scommons/far-js?branch=master)
[![npm version](https://img.shields.io/npm/v/farjs-app)](https://www.npmjs.com/package/farjs-app)
[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.17.svg)](https://www.scala-js.org)

## FAR.js

Good old Windows File and Archive Manager
([FAR](https://farmanager.com/index.php?l=en)) app built with:
  [Scala.js](https://www.scala-js.org/),
  [React.js](https://reactjs.org/),
  [react-blessed](https://github.com/Yomguithereal/react-blessed),
  [blessed](https://github.com/chjj/blessed)

Cross-platform: `Mac OS`, `Linux`, `Windows`.

Runs everywhere, where [Node.js](https://nodejs.org/) can run.

## Install

To install (or upgrade) it on your machine use the following command:

``` bash
$ npm i -g farjs-app
```

then you can run the application from your favorite terminal:

``` bash
$ farjs
```

![Screenshots](https://raw.githubusercontent.com/scommons/far-js/master/docs/images/screenshots.png)

To exit the application press `F10` on the keyboard.

## Documentation

### Modules

- [File Browser](#file-browser)
- [Dev Tools](#dev-tools)
  - [Logs](#logs)
  - [Colors](#colors)

### Other

- Developing
  - See [develop.md](https://github.com/scommons/far-js/blob/master/develop.md)
- [FAQ](#faq)
  - [Key Bindings](#key-bindings)

## File Browser

Main application window that consists of two similar panels.
Each panel displays list of files and directories. You can perform
different operations:

* Navigation within panels:
  * Items selection - `Shift + Up/Down/Left/Right/PageUp/PageDown/Home/End`
  * Back to the parent folder - `Ctrl + PageUp`
  * Go into a folder - `Ctrl + PageDown` / `Return`

* Open item in **default application** - `Alt + PageDown`
  (see [Key Bindings](#key-bindings) for how to re-map it to `Shift + Return`)
* Copy current item's path into **Clipboard** - `Ctrl + C`
  (in iTerm2 only)
* Swap the panels - `Ctrl + U`
* View Item(s) - `F3`
  * Scans selected folder(s)/file(s) and calculates size(s)
* Create Folder (with intermediate sub-folders) - `F7`
* Delete Item(s) - `F8`

## Dev Tools

Use `F12` to show/hide DEV tools components on the right side.
Press `F12` again to switch between the components.

### Logs

Shows all the intercepted `console.log` and `console.error` messages,
since the app itself is rendered to the console.

### Colors

Shows possible colors with their `hex` codes for current terminal.

## FAQ

### Key Bindings

* Why supported key combination doesn't work or trigger another
action in my terminal?
  - You may re-map the keys to send supported **escape sequences**.
  For example you can re-map:
    - | Key | Supported Key | Escape Sequence ^[ ... |
      | --- | --- | --- |
      | `Shift + Return` | `Alt + PageDown` | `[6;3~` |
      | `CMD + PageDown` | `Ctrl + PageDown` | `[6^` |
      | `CMD + PageUp` | `Ctrl + PageUp` | `[5^` |
  - In [iTerm2](https://iterm2.com/) it looks like this:
    - ![Keys Re Mapping](https://raw.githubusercontent.com/scommons/far-js/master/docs/images/keys_re_mapping.png)
  

# snailfinder-app



## Setup

Install all the [dependencies](https://github.com/drapanjanas/re-natal#dependencies) of re-natal and then re-natal itself

```
$ npm install -g re-natal
```

## Usage

To run in iOS:
```
$ react-native run-ios
```
To run in Android, connect your device and:
```
$ adb reverse tcp:8081 tcp:8081
$ react-native run-android
```

## Development with Figwheel

Initially the ClojureScript is compiled in "prod" profile, meaning `index.*.js` files
are compiled with `optimizations :simple`.
Development in such mode is not fun because of slow compilation and long reload time.

Luckily, this can be improved by compiling with `optimizations :none` and using
Figwheel.

Start your app from Xcode and pick a simulator target, or just run `react-native run-ios`

Then, to start development mode execute commands:
```
$ re-natal use-figwheel
$ lein figwheel ios
```
This will generate index.ios.js and index.android.js which works with compiler mode`optimizations :none`.

NOTE: you might need to restart react native packager and reload your app in simulator.

If all went well you should see REPL prompt and changes in source files
should be hot-loaded by Figwheel.

To disable Figwheel run `lein prod-build`


## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

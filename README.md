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

#### Using real iOS device

Switch to using your iOS device: `re-natal use-ios-device real`.
If this doesn't correctly detect your computer's IP you can pass your IP address explicitly: `re-natal use-ios-device IP`

Then follow the remaining directions above for the iOS simulator except pick your connected device in Xcode

#### Using real Android device
To run figwheel with real Android device please read [Running on Device](https://facebook.github.io/react-native/docs/running-on-device-android.html#content).
To make it work on USB connected device I had also to do the following:
```
$ adb reverse tcp:8081 tcp:8081
$ adb reverse tcp:3449 tcp:3449
```
Then:
```
$ re-natal use-figwheel
$ lein figwheel android
```
And deploy your app:
```
$ react-native run-android
```
#### Using Genymotion simulator
With genymotion Android simulator you have to use IP "10.0.3.2" in urls to refer to your local machine.
To specify this use:
```
$ re-natal use-android-device genymotion
$ re-natal use-figwheel
$ lein figwheel android
```
Start your simulator and deploy your app:
```
$ react-native run-android
```

#### Using stock Android emulator (AVD)
With stock Android emulator you have to use IP "10.0.2.2" in urls to refer to your local machine.
To specify this use:
```
$ re-natal use-android-device avd
$ re-natal use-figwheel
$ lein figwheel android
```
Start your simulator and deploy your app:
```
$ react-native run-android
```
#### Switching between Android devices
Run `use-android-device` to configure device type you want to use in development:
```
$ re-natal use-android-device <real|genymotion|avd>
$ re-natal use-figwheel
$ lein figwheel android
```

#### Developing iOS and Android apps simultaneously
```
$ re-natal use-figwheel
$ lein figwheel ios android
```
Then start iOS app from xcode, and Android by executing `react-native run-android`

#### Faster Figwheel hot reloading
Since version 0.22 React Native supports Hot Module Reload. But at least for now, this feature is useless
as Figwheel is doing that as good.

It looks like Figwheel reloads are faster if Hot Moduler Reload is OFF.
Also, no need packager to watch for changed files - Figwheel does that.

Two things you can do:

1. Turn off HMR from the development menu.
2. Start packager with option `--nonPersistent`. You can use `npm start` for that.

#### Starting Figwheel REPL from nREPL
To start Figwheel within nREPL session:
```
$ lein repl
```
Then in the nREPL prompt type:
```
user=> (start-figwheel "ios")
```
Or, for Android build type:
```
user=> (start-figwheel "android")
```
Or, for both type:
```
user=> (start-figwheel "ios" "android")
```

## REPL
You have to reload your app, and should see the REPL coming up with the prompt.

At the REPL prompt, try loading your app's namespace:

```clojure
(in-ns 'future-app.ios.core)
```

Changes you make via the REPL or by changing your `.cljs` files should appear live
in the simulator.

Try this command as an example:

```clojure
(dispatch [:set-greeting "Hello Native World!"])
```
## Running on Linux
In addition to the instructions above on Linux you might need to
start React Native packager manually with command `react-native start`.
This was reported in [#3](https://github.com/drapanjanas/re-natal/issues/3)

See also [Linux and Windows support](https://facebook.github.io/react-native/docs/linux-windows-support.html)
in React Native docs.

## "Prod" build
Do this with command:
```
$ lein prod-build
```
It will clean and rebuild index.ios.js and index.android.js with `optimizations :simple`

Having index.ios.js and index.android.js build this way, you should be able to
follow the RN docs to proceed with the release.

## Static Images
Since version 0.14 React Native supports a [unified way of referencing static images](https://facebook.github.io/react-native/docs/images.html)

In Re-Natal skeleton images are stored in "images" directory. Place your images there and reference them from cljs code:
```clojure
(def my-img (js/require "./images/my-img.png"))
```
#### Adding an image during development
When you have dropped a new image to "images" dir, you need to restart RN packager and re-run command:
```
$ re-natal use-figwheel
```
This is needed to regenerate index.\*.js files which includes `require` calls to all local images.
After this you can use a new image in your cljs code.


## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

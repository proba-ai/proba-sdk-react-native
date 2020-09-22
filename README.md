# appbooster-sdk-react-native

React Native wrapper for Appbooster SDK ([ios](https://github.com/appbooster/appbooster-sdk-ios), [android](https://github.com/appbooster/appbooster-sdk-android))

## Setup

using either Yarn:

```sh
yarn add appbooster-sdk-react-native
```

or npm:

```sh
npm install --save appbooster-sdk-react-native
```

## Installation

<details>
    <summary><b>For RN >= 0.60</b></summary>

### iOS

1. Integrate Swift in your porject if your porject doesn't support Swift

2. Install Pods:

```bash
cd ios && pod install
```

### Android

No need to do anything.

</details>

<details><summary><b>For RN < 0.60</b></summary>

### iOS

see [docs](https://reactnative.dev/docs/linking-libraries-ios)

### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`

- Add `import com.appboostersdkreactnative.AppboosterSdkReactNativePackage;` to the imports at the top of the file
- Add `new AppboosterSdkReactNativePackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:

   ```android
   include ':appbooster-sdk-react-native'
   project(':appbooster-sdk-react-native').projectDir = new File(rootProject.projectDir, '../node_modules/appbooster-sdk-react-native/android')
   ```

3. Insert the following lines inside the dependencies block in `android/app/build.gradle`: `implementation project(':appbooster-sdk-react-native')`

</details>

## Usage

```js
import AppboosterSdkReactNative from 'appbooster-sdk-react-native';

const connected = await AppboosterSdk.connect({
  appId: 'YOUR_APP_ID',
  sdkToken: 'YOUR_SDK_TOKEN',
  deviceId: 'YOUR_DEVICE_ID',
  usingShake: false,
  defaults: {
    ['TEST_1_KEY']: 'TEST_1_DEFAULT_VALUE',
    ['TEST_2_KEY']: 'TEST_2_DEFAULT_VALUE',
  },
  isInDevMode: false,
});
console.log('connected to AppboosterSdk: ', connected); // boolean

const experiments = await AppboosterSdk.fetch();
console.log('experiments: ', experiments); // list of experiments

const duration = await AppboosterSdk.getLastOperationDurationMillis();
console.log('duration: ', duration); // number

const isDebugModeLaunched = await AppboosterSdk.launchDebugMode();
console.log('isDebugModeLaunched: ', isDebugModeLaunched); // boolean
```

For more information see [example](example) and AppboosterSDK docs ([ios](https://github.com/appbooster/appbooster-sdk-ios), [android](https://github.com/appbooster/appbooster-sdk-android))

### iOS specific

For calling debug menu through `UIViewController`'s inheritance you need to use custom `rootViewController` in `AppDelegate.m` file of your React Native project (more info about debug menu [here](https://github.com/appbooster/appbooster-sdk-ios#how-to-debug))

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

```

```

# appbooster-sdk-react-native

React Native wrapper for Appbooster SDK ([ios](https://github.com/appbooster/appbooster-sdk-ios), [android](https://github.com/appbooster/appbooster-sdk-android))

## Installation

Note that RN SDK currently doesn't support projects with **RN < 0.62**.  
If you want to use RN SDK in your project, please, upgrade your project to **RN >= 0.62**

### Common steps

#### install library

Yarn:

```sh
yarn add appbooster-sdk-react-native
```

npm:

```sh
npm install --save appbooster-sdk-react-native
```

### Platform-specific steps

#### iOS

##### 1. Swift support step

<details>
<summary><b>If you use <code>use_frameworks!</code></b></summary>
<br>
Add next lines of code in <code>Podfile</code>:

```rb
use_frameworks! # You already use it in your project!

# add next lines of code:

pre_install do |installer|
  installer.pod_targets.each do |pod|
    if pod.name.eql?('appbooster-sdk-react-native')
      def pod.build_type; # Uncomment one line depending on your CocoaPods version
        Pod::BuildType.static_library # Pods version >= 1.9 (uncommented by default)
        # Pod::Target::BuildType.static_library # Pods version < 1.9
      end
    end
  end
end
```

</details>

<details>
<summary><b>If you don't use <code>use_frameworks!</code></b> (default for RN projects)</summary>
<br>
If you <b>have no Swift integration</b> in your project follow next steps:
<br><br>

1. In XCode, in the project navigator, right click your `[your project's name]` folder, choose ➜ `Add Files to [your project's name]`

![Create Swift File](https://i.imgur.com/00K5UZ1.png)

2. Select `Swift File` ➜ `Next`

![Create Swift File](https://i.imgur.com/Mdc9MLk.png)

3. Specify name for example `Dummy.swift` ➜ `Create`

![Create Swift File](https://i.imgur.com/2HSk7Jp.png)

4. Now a pop up is shown select `Create Bridging Header`

![Create Swift File](https://i.imgur.com/f2zA0n9.png)

</details>

##### 2. Pods installation step

```bash
cd ios && pod install && cd ..
```

#### Android

You no need to do anything!

## Usage

```js
import AppboosterSdk from 'appbooster-sdk-react-native';
```

### Initialization:

```js
const connected = await AppboosterSdk.connect({
  appId: 'YOUR_APP_ID',
  sdkToken: 'YOUR_SDK_TOKEN',
  deviceId: 'YOUR_DEVICE_ID', // optional, UUID generated by default
  appsFlyerId: 'YOUR_APPSFLYER_ID', // optional, null by default, use appsFlyer.getAppsFlyerUID if AppsFlyer integration is needed
  amplitudeUserId: 'YOUR_AMPLITUDE_ID', // optional, null by default, use id that you set with `amplitudeInstance.setUserId` method if Amplitude integration is needed
  usingShake: false, // false by default, cause React Native already uses shake motion in debug mode for own purposes (show React Native debug window after shaking your device)
  defaults: {
    ['TEST_1_KEY']: 'TEST_1_DEFAULT_VALUE',
    ['TEST_2_KEY']: 'TEST_2_DEFAULT_VALUE',
  },
}); // boolean
```

### How to fetch known test values that associated with your device?

```js
const fetched = await AppboosterSdk.fetch(); // boolean
```

### How to get the value for a specific test?

```js
const experiments = await AppboosterSdk.getExperiments(); // object with experiments

// or if you need additional details for experiments
// const experiments = await AppboosterSdk.getExperimentsWithDetails(); // object with experiments

const value = experiments['TEST_1_KEY']; // string
```

In case of problems with no internet connection or another, the values obtained in the previous session will be used, or if they are missing, the default values specified during initialization will be used.

### How to get user tests for analytics?

```js
const experiments = await AppboosterSdk.getExperiments(); // object with experiments

// or if you need additional details for experiments
// const experiments = await AppboosterSdk.getExperimentsWithDetails(); // object with experiments

// example: send to amplitude and appsflyer

// https://github.com/amplitude/Amplitude-ReactNative
const amplitudeInstance = Amplitude.getInstance();
amplitudeInstance.init('Your Amplitude key');
amplitudeInstance.setUserProperties(experiments);

// https://github.com/AppsFlyerSDK/appsflyer-react-native-plugin
try {
  await appsFlyer.initSdk(/*object with options*/);
  appsFlyer.setAdditionalData(experiments, (res) => {
    /*...*/
  });
} catch (err) {}
```

### How to debug?

Before debug make sure that debug-mode for your App is turned-on on [settings page](https://platform.appbooster.com/ab/settings)

![](https://imgproxy.appbooster.com/9ACImnEbmsO822dynjTjcC_B8aXzbbpPQsOgop2PlBs//aHR0cHM6Ly9hcHBib29zdGVyLWNsb3VkLnMzLmV1LWNlbnRyYWwtMS5hbWF6b25hd3MuY29tLzk0N2M5NzdmLTAwY2EtNDA1Yi04OGQ4LTAzOTM4ZjY4OTAzYi5wbmc.png)
<img src="https://imgproxy.appbooster.com/DTJe8gCCUt-FBdGoEvwIp7TFYQ1JfwCZZPiFrR4tkic//aHR0cHM6Ly9hcHBib29zdGVyLWNsb3VkLnMzLmV1LWNlbnRyYWwtMS5hbWF6b25hd3MuY29tLzNkM2IyMDkyLWNiOGYtNDhhNi05MjgwLTMxMWRhNDZmZmJiMy5wbmc.png" width="229" height="494" />
<img src="https://imgproxy.appbooster.com/vK0c6Ia7iBueCiczg27J7AVhnEIW0dDULdgHlWPg0Po//aHR0cHM6Ly9hcHBib29zdGVyLWNsb3VkLnMzLmV1LWNlbnRyYWwtMS5hbWF6b25hd3MuY29tL2Y3NTZkODdmLTc4YTAtNGE1ZS04YjhjLTAwNTBhMWVjNThkZC5wbmc.png" width="241" height="494" />

```js
const connected = await AppboosterSdk.connect({
  //...
  showLogs: true, // false by default, to print all debugging info in the console (note that currently you can see logs in XCode or Android Studio but not in JS console)
  //...
});

const duration = await AppboosterSdk.getLastOperationDurationMillis(); // number (the duration of the last operation in milliseconds)
```

In debug mode you can see all actual tests and check how the user will see each option of the test. To show the debug activity you just need to turn it on in your personal cabinet and call

```js
const isDebugModeLaunched = await AppboosterSdk.launchDebugMode(); // boolean
```

#### Platform specific (optional info for React Native project)

You can find more info about debug menu usage in iOS and Android here: [ios](https://github.com/appbooster/appbooster-sdk-ios#how-to-debug), [android](https://github.com/appbooster/appbooster-sdk-android#how-to-debug))

==================================================

You can see the example of usage [here](example)  
For additional info you also can see docs of native Appbooster SDK ([ios](https://github.com/appbooster/appbooster-sdk-ios), [android](https://github.com/appbooster/appbooster-sdk-android))

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

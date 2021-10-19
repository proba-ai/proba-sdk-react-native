import * as React from 'react';
import { StyleSheet, View, Text, Button } from 'react-native';
import ProbaSdk from 'proba-sdk-react-native';

const connectToProbaSDK = async () => {
  try {
    const connected = await ProbaSdk.connect({
      appId: 'YOUR_APP_ID',
      sdkToken: 'YOUR_SDK_TOKEN',
      deviceId: 'YOUR_DEVICE_ID',
      appsFlyerId: 'YOUR_APPSFLYER_ID',
      amplitudeUserId: 'YOUR_AMPLITUDE_ID',
      deviceProperties: {
        installedAt: '2021-05-20T09:55:05.000+03:00',
      },
      usingShake: false,
      defaults: {
        ['TEST_1_KEY']: 'TEST_1_DEFAULT_VALUE',
        ['TEST_2_KEY']: 'TEST_2_DEFAULT_VALUE',
      },
      showLogs: false,
    });
    console.log('connected to ProbaSdk: ', connected);

    const fetched = await ProbaSdk.fetch();
    console.log('fetch experiments: ', fetched);

    const experiments = await ProbaSdk.getExperiments();
    console.log('experiments : ', experiments);

    const experimentsWithDetails = await ProbaSdk.getExperimentsWithDetails();
    console.log('experimentsWithDetails : ', experimentsWithDetails);

    const experiment = experiments['TEST_1_KEY'];
    console.log('experiment: ', experiment);

    const duration = await ProbaSdk.getLastOperationDurationMillis();
    console.log('duration: ', duration);
  } catch (error) {
    console.log('ProbaSdkReactNative error: ', error);
  }
};

const activateDebug = async () => {
  const isDebugModeLaunched = await ProbaSdk.launchDebugMode();
  console.log('isDebugModeLaunched: ', isDebugModeLaunched);
};

export default function App() {
  React.useEffect(() => {
    connectToProbaSDK();
  }, []);

  return (
    <View style={styles.container}>
      <Text>ProbaSdk test</Text>
      <Button title="activate Debug Mode" onPress={activateDebug} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

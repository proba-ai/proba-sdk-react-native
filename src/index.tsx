import { NativeModules } from 'react-native';

type AppboosterSdkReactNativeType = {
  multiply(a: number, b: number): Promise<number>;
};

const { AppboosterSdkReactNative } = NativeModules;

export default AppboosterSdkReactNative as AppboosterSdkReactNativeType;

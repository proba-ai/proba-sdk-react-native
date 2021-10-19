package ai.probasdkreactnative

import ai.proba.probasdk.ProbaSdk
import com.facebook.react.bridge.*

class ProbaSdkReactNativeModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private var sdk: ProbaSdk? = null

    override fun getName(): String {
        return "ProbaSdkReactNative"
    }

    @ReactMethod
    fun connect(settings: ReadableMap, promise: Promise) {
        val preparedSettings = Utils.prepareSetting(settings)

        val appId = preparedSettings.getString("appId") ?: ""
        val sdkToken = preparedSettings.getString("sdkToken") ?: ""
        val deviceId = preparedSettings.getString("deviceId") ?: ""
        val usingShake = preparedSettings.getBoolean("usingShake")
        val defaults = Utils.getDefaultExperiments(preparedSettings.getJSONObject("defaults"))
        val showLogs = preparedSettings.getBoolean("showLogs")
        val appsFlyerId = if (preparedSettings.has("appsFlyerId")) preparedSettings.getString("appsFlyerId") else null
        val amplitudeUserId = if (preparedSettings.has("amplitudeUserId")) preparedSettings.getString("amplitudeUserId") else null
        val deviceProperties = Utils.getDeviceProperties(preparedSettings.getJSONObject("deviceProperties"))

        if (currentActivity != null) {
            currentActivity!!.runOnUiThread(Runnable {
                val sdkBuilder = ProbaSdk.Builder(currentActivity!!.applicationContext)
                  .appId(appId)
                  .sdkToken(sdkToken)
                  .deviceId(deviceId)
                  .usingShake(usingShake)
                  .defaults(defaults)
                  .showLogs(showLogs)
                  .deviceProperties(deviceProperties)

                appsFlyerId?.let {
                  sdkBuilder.appsFlyerId(it)
                }

                amplitudeUserId?.let {
                  sdkBuilder.amplitudeUserId(it)
                }

                sdk = sdkBuilder.build()

                promise.resolve(true)
            })
        } else { promise.resolve(false) }
    }

    @ReactMethod
    fun fetch(promise: Promise) {
        sdk?.fetch(
                onSuccessListener = object: ProbaSdk.OnSuccessListener{
                    override fun onSuccess() {
                        promise.resolve(true)
                    }
                },
                onErrorListener = object: ProbaSdk.OnErrorListener{
                    override fun onError(th: Throwable) {
                        promise.resolve(false)
                    }
                }
        )
    }

    @ReactMethod
    fun getExperiments(promise: Promise) {
        promise.resolve(Utils.prepareExperimentsForJS(sdk!!.getExperiments()))
    }

    @ReactMethod
    fun getExperimentsWithDetails(promise: Promise) {
        promise.resolve(Utils.prepareExperimentsForJS(sdk!!.getExperimentsWithDetails()))
    }

    @ReactMethod
    fun getLastOperationDurationMillis(promise: Promise) {
        promise.resolve(sdk!!.lastOperationDurationMillis.toDouble())
    }

    @ReactMethod
    fun launchDebugMode(promise: Promise) {
        promise.resolve(sdk!!.launchDebugMode(currentActivity!!.applicationContext))
    }

}

package hr.prijavituriste.in_app_comments;

import androidx.annotation.NonNull;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** InAppCommentsPlugin */
public class InAppCommentsPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    private MethodChannel channel;
    private Result result;
    private Activity activity;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "in_app_comments");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        this.result = result;
        if (call.method.equals("requestComments")) {
            if (activity == null) {
                result.error("NO_ACTIVITY", "Activity is null", "Ensure the plugin is correctly initialized.");
                return;
            }
            Intent intent = new Intent("com.huawei.appmarket.intent.action.guidecomment");
            intent.setPackage("com.huawei.appmarket");

            // Start the activity using the registered launcher
            activityResultLauncher.launch(intent);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
        activity = activityPluginBinding.getActivity();

        // Ensure the Activity is a LifecycleOwner
        if (activity instanceof LifecycleOwner) {
            LifecycleOwner lifecycleOwner = (LifecycleOwner) activity;
            lifecycleOwner.getLifecycle().addObserver(new LifecycleObserver() {
                @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
                public void onCreate() {
                    activityResultLauncher = activityPluginBinding.getActivity().registerForActivityResult(
                            new ActivityResultContracts.StartActivityForResult(),
                            new ActivityResultCallback<ActivityResult>() {
                                @Override
                                public void onActivityResult(ActivityResult resultData) {
                                    if (result == null) return;
                                    int resultCode = resultData.getResultCode();
                                    if (resultCode == 102 || resultCode == 103) {
                                        result.success(resultCode);
                                    } else {
                                        String errorMsg;
                                        switch (resultCode) {
                                            case 0: errorMsg = "Unknown error"; break;
                                            case 101: errorMsg = "The app has not been released on AppGallery"; break;
                                            case 104: errorMsg = "The HUAWEI ID sign-in status is invalid"; break;
                                            case 105: errorMsg = "User does not meet conditions for commenting"; break;
                                            case 106: errorMsg = "Commenting function is disabled"; break;
                                            case 107: errorMsg = "In-app commenting service is not supported"; break;
                                            case 108: errorMsg = "User canceled the comment"; break;
                                            default: errorMsg = "Unexpected error: " + resultCode;
                                        }
                                        result.error(String.valueOf(resultCode), "error", errorMsg);
                                    }
                                }
                            }
                    );
                }
            });
        } else {
            Log.e("InAppCommentsPlugin", "Activity does not implement LifecycleOwner, unable to register for activity results.");
        }
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        activity = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }

    @Override
    public void onDetachedFromActivity() {
        activity = null;
    }
}

package hr.prijavituriste.in_app_comments;

import androidx.annotation.NonNull;

import android.content.Intent;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** InAppCommentsPlugin */
public class InAppCommentsPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native
  /// Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine
  /// and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Result result;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "in_app_comments");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    this.result = result;
    if (call.method.equals("requestComments")) {
      Intent intent = new Intent("com.huawei.appmarket.intent.action.guidecomment");
      intent.setPackage("com.huawei.appmarket");
      MainActivity.this.startActivityForResult(intent, 1001);
    } else {
      result.notImplemented();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 1001) {
      if (resultCode == 102 || resultCode == 103) {
        result.success(resultCode);
      } else {
        result.error(resultCode);
      }
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}

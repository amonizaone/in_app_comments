import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'in_app_comments_platform_interface.dart';

/// An implementation of [InAppCommentsPlatform] that uses method channels.
class MethodChannelInAppComments extends InAppCommentsPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('in_app_comments');

  @override
  Future<int?> requestComments() async {
    final version = await methodChannel.invokeMethod<int>('requestComments');
    return version;
  }
}

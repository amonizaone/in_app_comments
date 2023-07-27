import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'in_app_comments_method_channel.dart';

abstract class InAppCommentsPlatform extends PlatformInterface {
  /// Constructs a InAppCommentsPlatform.
  InAppCommentsPlatform() : super(token: _token);

  static final Object _token = Object();

  static InAppCommentsPlatform _instance = MethodChannelInAppComments();

  /// The default instance of [InAppCommentsPlatform] to use.
  ///
  /// Defaults to [MethodChannelInAppComments].
  static InAppCommentsPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [InAppCommentsPlatform] when
  /// they register themselves.
  static set instance(InAppCommentsPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<int?> requestComments() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}

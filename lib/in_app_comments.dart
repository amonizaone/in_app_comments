import 'in_app_comments_platform_interface.dart';

class InAppComments {
  Future<int?> requestComments() {
    return InAppCommentsPlatform.instance.requestComments();
  }
}

A Flutter plugin that lets you display the AppGallery comment pop-up in your app. After a user rates and comments on your app, you can process the commenting result.

It only works on Huawei phones and uses the [In-App Comments](https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-Guides/agc-comments-introduction-0000001063018306) API

# Usage

## `requestComments()`

The following code triggers the In-App Comments prompt

```dart
import 'package:in_app_comments/in_app_comments.dart';

  final InAppComments _inAppCommentsPlugin = InAppComments();

try {
    await _inAppCommentsPlugin.requestComments();
} on PlatformException catch (e) {
    print(e.details);
}
```

### Before You Start

- You have released your app officially on AppGallery.
- Users have installed AppGallery 11.3.2.302 or later and signed in using HUAWEI IDs.

### Precautions

You are advised to prompt your users to rate and comment on your app at a proper moment, by following these rules:
- The prompt should not interrupt a user's activities in your app.
- You can prompt your users to rate and comment on your app when they finish an operation, for example, getting leveled up or completing a task, when they are most likely to be satisfied with your app.
- The prompt should not be too frequent. You are advised to display the prompt only once for each version.

### Testing the Function

- If your app has been released on AppGallery, you need to release an open testing version for it and then perform the testing.
- If your app has not been released on AppGallery, you need to release an open testing version for it and then perform the testing. Otherwise, app authentication will fail.
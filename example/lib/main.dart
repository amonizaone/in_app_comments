import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'package:in_app_comments/in_app_comments.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _inAppCommentsPlugin = InAppComments();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Comments Example'),
        ),
        body: Center(
          child: ElevatedButton(
              onPressed: () async {
                try {
                  await _inAppCommentsPlugin.requestComments();
                } on PlatformException catch (e) {
                  print(e.details);
                }
              },
              child: const Text('Request comment')),
        ),
      ),
    );
  }
}

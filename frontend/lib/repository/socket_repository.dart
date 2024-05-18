import 'package:docs_clone_flutter/clients/socket_client.dart';
import 'package:flutter_quill/flutter_quill.dart';
import 'package:socket_io_client/socket_io_client.dart';

class SocketRepository {
  final SocketClient _socketClient = SocketClient();

  void connect() {
    _socketClient.connect();
  }

  void onChanges(Function(dynamic) callback) {
    _socketClient.onEvent('changes', callback);
  }

  void joinRoom(String documentId) {
    _socketClient.sendEvent('join', documentId);
  }

  void typing(Map<String, dynamic> data) {
    _socketClient.sendEvent('typing', data);
  }

  void autoSave(Map<String, dynamic> data) {
    _socketClient.sendEvent('save', data);
  }

  void changeListener(Function(Map<String, dynamic>) func) {
    _socketClient.onEvent('changes', (data) => func(data));
  }
  void disconnect() {
    _socketClient.disconnect();
  }

  SocketClient get socketClient => _socketClient;
}

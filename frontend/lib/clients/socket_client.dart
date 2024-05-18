import 'package:socket_io_client/socket_io_client.dart' as IO;

class SocketClient {
  late IO.Socket _socket;

  // Singleton instance
  static final SocketClient _instance = SocketClient._internal();

  factory SocketClient() {
    return _instance;
  }

  SocketClient._internal();

  IO.Socket get socket => _socket;

  void connect() {
    _socket = IO.io('http://localhost:9092', <String, dynamic>{
      'transports': ['websocket'],
    });

    _socket.on('connect', (_) {
      print('connected');
    });

    _socket.on('disconnect', (_) => print('disconnected'));
  }

  void sendMessage(String message) {
    _socket.emit('message', message);
  }

  void sendEvent(String event, dynamic data) {
    _socket.emit(event, data);
  }

  void onEvent(String event, Function(dynamic) callback) {
    _socket.on(event, callback);
  }

  void disconnect() {
    _socket.disconnect();
  }
}

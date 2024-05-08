import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;

@Component
public class SocketIOClient {
    private Socket socket;

    @Value("${socket.server.url}")
    private String socketServerUrl; 

    @PostConstruct
    public void connectToSocketServer() {
        try {
            IO.Options options = new IO.Options();
            options.reconnection = true;

            socket = IO.socket(socketServerUrl, options);
            socket.connect();

            socket.on(Socket.EVENT_CONNECT, args -> {
                System.out.println("Connected to Socket.IO server");
            });

            socket.on("join", args -> {
                String documentId = (String) args[0];
                socket.emit("join", documentId);
                System.out.println("Joined document: " + documentId);
            });

            socket.on("typing", args -> {
                // Handle typing event
                System.out.println("Received typing event");
            });

            socket.on("save", args -> {
                // Handle save event
                System.out.println("Received save event");
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void disconnectFromSocketServer() {
        if (socket != null && socket.connected()) {
            socket.disconnect();
            System.out.println("Disconnected from Socket.IO server");
        }
    }
}


package src.zad1;

import src.zad1.GUI.ClientGUI;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private SocketChannel socketChannel = null;
    private static final Charset charset  = Charset.forName("ISO-8859-2");
    private static final int BSIZE = 1024;

    public Client() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));

        while (!socketChannel.finishConnect()) {
        }
    }

    public void receiveMessages(ClientGUI gui) throws IOException {
        Thread thread = new Thread(() -> {
            ByteBuffer inBuf = ByteBuffer.allocateDirect(BSIZE);
            while (true){
                inBuf.clear();
                int bytesRead = 0;
                try {
                    bytesRead = socketChannel.read(inBuf);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (bytesRead == 0) {
                    continue;
                }
                else if (bytesRead == -1) {
                    break;
                }
                else {
                    inBuf.flip();
                    byte[] bytes = new byte[inBuf.remaining()];
                    inBuf.get(bytes);
                    String message = new String(bytes);
                    gui.displayMessage(message);
                }
            }
        });
        thread.start();
    }

    public void subscribe(String topic) throws IOException {
        sendMessage("subscribe:" + topic);
    }

    public void unsubscribe(String topic) throws IOException {
        sendMessage("unsubscribe:" + topic);
    }

    private void sendMessage(String message) throws IOException {
        socketChannel.write(charset.encode(message));
    }
}

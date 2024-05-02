package src.zad1;

import src.zad1.GUI.AdminGUI;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Admin {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private SocketChannel socketChannel = null;
    private static final Charset charset  = Charset.forName("ISO-8859-2");
    private static final int BSIZE = 1024;

    public Admin() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));

        while (!socketChannel.finishConnect()) {
        }
    }

    public void receiveMessages(AdminGUI gui) throws IOException {
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

    public void addTopic(String topic) throws IOException {
        sendMessage("add:" + topic);
    }

    public void removeTopic(String topic) throws IOException {
        sendMessage("remove:" + topic);
    }

    public void updateTopic(String topic, String news) throws IOException {
        sendMessage("update:" + topic + ":" + news);
    }

    private void sendMessage(String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        socketChannel.write(buffer);
    }
}

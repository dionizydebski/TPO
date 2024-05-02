package src.zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        new Server();
    }
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;
    private static Map<SocketChannel, ArrayList<String>> subscribers = new HashMap<SocketChannel, ArrayList<String>>();
    private static ArrayList<String> topics = new ArrayList<>();
    private static Selector selector;
    private static final Charset charset  = Charset.forName("ISO-8859-2");
    private static final int BSIZE = 1024;

    Server () throws IOException {
        selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(SERVER_ADDRESS,PORT));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iter = keys.iterator();

            while(iter.hasNext()){
                SelectionKey key = iter.next();
                iter.remove();

                if(key.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                    socketChannel.write(charset.encode("Polaczono z serwerem"));
                }

                if(key.isReadable()){
                    processMessage(key);
                }

            }
        }

    }
    private static void processMessage(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        int bytesRead = socketChannel.read(buffer);

        if (bytesRead == -1) {
            socketChannel.close();
            key.cancel();
            return;
        }

        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        String message = new String(bytes);

        String[] parts = message.split(":");
        String action = parts[0];
        String topic = parts[1];

        switch (action) {
            case "subscribe":
                if(topics.contains(topic)){
                    if(subscribers.containsKey(socketChannel)) {
                        if(subscribers.get(socketChannel).contains(topic)){
                            socketChannel.write(charset.encode("Juz subskrybujesz: " + topic));
                        }
                        else{
                            subscribers.get(socketChannel).add(topic);
                            socketChannel.write(charset.encode("Zasubskrybowano: " + topic));
                        }
                    }
                    else {
                        subscribers.put(socketChannel, new ArrayList<>());
                        subscribers.get(socketChannel).add(topic);
                        socketChannel.write(charset.encode("Zasubskrybowano: " + topic));
                    }
                }
                else {
                    socketChannel.write(charset.encode("Nie ma tematu: " + topic));
                }
                break;

            case "unsubscribe":
                if(subscribers.get(socketChannel) != null && subscribers.get(socketChannel).contains(topic)){
                    subscribers.get(socketChannel).remove(topic);
                    socketChannel.write(charset.encode("Zrezygnowano: " + topic));
                }
                else{
                    socketChannel.write(charset.encode("Nie subskrybujesz takiego tematu: " + topic));
                }
                break;

            case "add":
                if(!topics.contains(topic)){
                    topics.add(topic);
                    socketChannel.write(charset.encode("Dodano: " + topic));
                }
                else{
                    socketChannel.write(charset.encode("Tamat juz zostal dodany: " + topic));
                }

                break;
            case "remove":
                if(topics.contains(topic)){
                    topics.remove(topic);
                    socketChannel.write(charset.encode("Usuniento: " + topic));
                }
                else{
                    socketChannel.write(charset.encode("Nie ma takiego tematu: " + topic));
                }

                break;
            case "update":
                if(topics.contains(topic)){
                    String news = parts[2];
                    for(Map.Entry<SocketChannel, ArrayList<String>> entry : subscribers.entrySet()){
                        for(String tmp : entry.getValue()){
                            if (tmp.equals(topic))
                                entry.getKey().write(charset.encode(CharBuffer.wrap(topic + "->" + news)));
                        }
                    }
                    socketChannel.write(charset.encode("Przeslano: " + topic + "->" + news));
                }
                else{
                    socketChannel.write(charset.encode("Nie ma takiego tematu: " + topic));
                }
                break;
            default:
                break;
        }
    }
}

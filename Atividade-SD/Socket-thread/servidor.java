import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;

public class WebServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/files", new FileHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String fileName = t.getRequestURI().getPath().substring(1);
            File file = new File("arquivos/" + fileName);
            if (!file.exists()) {
                sendResponse(t, 404, "Not Found");
                return;
            }
            InputStream in = new FileInputStream(file);
            t.sendResponseHeaders(200, file.length());
            OutputStream out = t.getResponseBody();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            in.close();
        }

        private void sendResponse(HttpExchange exchange, int responseCode, String responseMessage) throws IOException {
            exchange.sendResponseHeaders(responseCode, responseMessage.length());
            OutputStream os = exchange.getResponseBody();
            os.write(responseMessage.getBytes());
            os.close();
        }
    }
}

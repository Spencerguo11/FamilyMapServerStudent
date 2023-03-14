package handler;

import com.sun.net.httpserver.*;
import result.FillResult;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.*;

public class FileHandler implements HttpHandler {

    public FileHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if(exchange.getRequestMethod().toLowerCase().equals("get")) {
                String requestedURL = exchange.getRequestURI().toString();

                if(requestedURL == null || requestedURL.equals("/")) {
                    requestedURL = "/index.html";
                }
                String path = "web" + requestedURL;
                File filePath = new File(path);
                if(filePath.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Files.copy(filePath.toPath(), exchange.getResponseBody());
                    exchange.getResponseBody().close();
                } else {
                    path = "web/HTML/404.html";
                    Path fPath = FileSystems.getDefault().getPath(path);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    Files.copy(fPath, exchange.getResponseBody());
                    exchange.getResponseBody().close();
                }

            }
            if(!success){
             exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
             exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR,0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
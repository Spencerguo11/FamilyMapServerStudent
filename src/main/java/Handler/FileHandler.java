package Handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // check if the file exists
        // if it exists, then read the file and  write it to the httpExchange output stream
        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                String filePath = exchange.getRequestURI().toString();
                if(filePath.length() == 1){
                    // index.html
                    String newPath = "web/index.html";
                    Path newFilePath = FileSystems.getDefault().getPath(newPath);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Files.copy(newFilePath, exchange.getResponseBody());
                }
                else{
                    // /404.html /Users/guozhupeng/Downloads/CS 240/FamilyMapServerStudent/web/HTML/404.html
                    String newPath = "web" + filePath;
                    Path newFilePath = FileSystems.getDefault().getPath(newPath);
                    File file = new File(newPath);
                    if (!file.exists()) {
                        newFilePath = FileSystems.getDefault().getPath("web/HTML/404.html");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    Files.copy(newFilePath, exchange.getResponseBody());
                }

                exchange.getResponseBody().close();

                success = true;

            }

            if (!success) {
                // The HTTP request was invalid somehow, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                // We are not sending a response body, so close the response body
                // output stream, indicating that the response is complete.
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }

    }
}

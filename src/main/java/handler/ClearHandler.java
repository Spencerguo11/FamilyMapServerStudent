package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import result.ClearResult;
import service.ClearService;

import java.io.*;
import java.net.HttpURLConnection;

public class ClearHandler extends RootHandler {

    public ClearHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ClearService myClearService = new ClearService();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                ClearResult myClearResult = myClearService.clear();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Gson gson = new Gson();
                String jsonStr = gson.toJson(myClearResult);
                OutputStream respBody = exchange.getResponseBody();
                writeString(jsonStr, respBody);
                respBody.close();
            }
        }
        catch (IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String jsonStr = new String("{\"message\": \"Internal server error\"");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();

        }
    }

}

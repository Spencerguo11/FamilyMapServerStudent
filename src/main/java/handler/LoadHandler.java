package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.LoadRequest;
import result.LoadResult;
import service.LoadService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {

    public LoadHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoadResult myLoadResult = new LoadResult();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                LoadService myLoadService = new LoadService();
                Reader reader = new InputStreamReader(exchange.getRequestBody());

                Gson gson = new Gson();
                LoadRequest myLoadRequest = gson.fromJson(reader, LoadRequest.class);

                myLoadResult = myLoadService.load(myLoadRequest);

                if (myLoadResult.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonStr = "{\"message\" :\"Successfully added " + myLoadResult.getNumUsers() + " users, "
                            + myLoadResult.getNumPersons() + " persons, and " +myLoadResult.getNumEvents() + " events to the database.\"}";
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    respBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String jsonStr = new String("{\"message\" : \"" + myLoadResult.getMessage() + "\"}");
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    respBody.close();
                }
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

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
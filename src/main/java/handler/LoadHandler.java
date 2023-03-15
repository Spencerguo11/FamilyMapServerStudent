package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataaccess.DataAccessException;
import request.LoadRequest;
import result.LoadResult;
import service.LoadService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler extends RootHandler  {

    public LoadHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoadResult loadResult = new LoadResult();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                LoadService myLoadService = new LoadService();
                Reader reader = new InputStreamReader(exchange.getRequestBody());

                Gson gson = new Gson();
                LoadRequest myLoadRequest = gson.fromJson(reader, LoadRequest.class);

                loadResult = myLoadService.load(myLoadRequest);

                checkSuccess(exchange, loadResult);

            }
        }
        catch (IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String jsonStr = new String("{\"message\": \"Internal server error\"");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkSuccess(HttpExchange exchange, LoadResult loadResult) throws IOException{
        if (loadResult.isSuccess()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            String jsonStr = "{\"message\" :\"Successfully added " + loadResult.getNumUsers() + " users, "
                    + loadResult.getNumPersons() + " persons, and " +loadResult.getNumEvents() + " events to the database.\"}";
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String jsonStr = new String("{\"message\" : \"" + loadResult.getMessage() + "\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();
        }
    }

}
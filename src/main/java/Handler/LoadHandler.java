package Handler;

import Request.LoadRequest;
import Request.LoginRequest;
import Result.ClearResult;
import Result.LoadResult;
import Service.ClearService;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import dao.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {


                InputStream reqBody = exchange.getRequestBody();

                String reqData = readString(reqBody);

                System.out.println(reqData);

                // TODO: Claim a route based on the request data
                Gson gson = new Gson();
                LoadRequest request = (LoadRequest) gson.fromJson(reqData, LoadRequest.class);
                LoadService service = new LoadService();
                LoadResult result = service.load(request);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream resBody = exchange.getResponseBody();
                writeString(gson.toJson(result), resBody);
                resBody.close();

                success = true;

            }


            if (!success) {

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                exchange.getResponseBody().close();
            }
        }
        catch (IOException | DataAccessException e) {
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


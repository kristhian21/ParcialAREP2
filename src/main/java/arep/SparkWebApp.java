package arep;

import spark.Filter;
import static spark.Spark.*;
import com.google.gson.Gson;

public class SparkWebApp {

    public static void main(String[] args) {
        RoundRobin roundRobin  = new RoundRobin();
        // Set up the port
        port(getPort());
        // GET index
        staticFiles.location("/public");
        init();
        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });
        // GET Operations
        get("/cos", ((request, response) -> {
            response.type("application/json");
            String result = roundRobin.sendRequest(request.queryParams("value"), "cos");
            return new Gson().toJson(new MathResponse("Coseno(x)", request.queryParams("value"), result));
        }));
        get("/log", ((request, response) -> {
            response.type("application/json");
            String result = roundRobin.sendRequest(request.queryParams("value"), "log");
            return new Gson().toJson(new MathResponse("Log(x)", request.queryParams("value"), result));
        }));
    }

    /**
     * Set up the port
     * @return the port number
     */
    private static int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }

}

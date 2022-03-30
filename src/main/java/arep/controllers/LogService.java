package arep.controllers;

import static spark.Spark.*;

public class LogService {

    public static void main(String[] args) {
        // Set up the port
        port(getPort());
        System.out.println("Escuchando peticiones...");
        // GET cos
        get("/cos", (request, response) -> {
            String number = request.queryParams("value");
            System.out.println("Number: " + number);
            System.out.println("Operation: Cos(x)");
            Double result = Math.cos(Double.valueOf(number));
            System.out.println("Result: " + result);
            return result;
        });
        // GET log
        get("/log", (request, response) -> {
            String number = request.queryParams("value");
            System.out.println("Number: " + number);
            System.out.println("Operation: Log(x)");
            Double result = Math.log(Double.valueOf(number));
            System.out.println("Result: " + result);
            return result;
        });
    }

    /**
     * Set up the port
     * @return the port number
     */
    private static int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35001;
    }
}

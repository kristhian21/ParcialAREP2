package arep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.PriorityQueue;

public class RoundRobin {
    private final PriorityQueue<String> queue;

    public RoundRobin() {
        queue =  new PriorityQueue<>();
        int[] ports = getPortsArray();
        for (int i = 0; i < ports.length; i++) {
            queue.add("http://localhost:"+ports[i]);
        }
    }

    public String sendRequest(String num, String operation){
        try {
            URL url = new URL(selectHost()+"/"+operation+"?value="+num);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            StringBuilder result = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                result.append(output);
            }
            conn.disconnect();
            return result.toString();

        } catch (Exception e) {
            System.out.println("---------ERROR---------");
            System.out.println("Exception in NetClientGet:- " + e);
            return "";
        }
    }

    private String selectHost() {
        String returnHost = queue.poll();
        queue.add(returnHost);
        return returnHost;
    }

    private int[] getPortsArray(){
        int[] result = new int[3];
        if (System.getenv("PORT_S1") != null) {
            result[0] = Integer.parseInt(System.getenv("PORT_S1"));
        }
        else{
            result[0] = 35001;
        }
        if (System.getenv("PORT_S2") != null) {
            result[1] = Integer.parseInt(System.getenv("PORT_S2"));
        }
        else{
            result[1] = 35002;
        }
        if (System.getenv("PORT_S3") != null) {
            result[2] = Integer.parseInt(System.getenv("PORT_S3"));
        }
        else{
            result[2] = 35003;
        }
        return result;
    }
}

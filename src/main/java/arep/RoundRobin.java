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
        String[] ports = getPortsArray();
        for (int i = 0; i < ports.length; i++) {
            queue.add(ports[i]);
        }
    }

    public String sendRequest(String num, String operation){
        try {
            String hostPort = selectHost();
            System.out.println("Maquina: "+hostPort);
            URL url = new URL(hostPort+"/"+operation+"?value="+num);
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

    private String[] getPortsArray(){
        String[] result = new String[2];
        if (System.getenv("PORT_S1") != null) {
            result[0] = "ec2-18-233-166-108.compute-1.amazonaws.com:"+Integer.parseInt(System.getenv("PORT_S1"));
        }
        else{
            result[0] = "ec2-18-233-166-108.compute-1.amazonaws.com:"+35001;
        }
        if (System.getenv("PORT_S2") != null) {
            result[1] = "ec2-54-87-128-178.compute-1.amazonaws.com"+Integer.parseInt(System.getenv("PORT_S2"));
        }
        else{
            result[1] = "ec2-54-87-128-178.compute-1.amazonaws.com"+35002;
        }
        return result;
    }
}

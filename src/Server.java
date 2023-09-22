import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.logging.Level;

public class Server {
    //  Server xử lý theo yêu cầu của client

    // Khai báo Socket
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;

    // Biến đọc dữ liệu từ commandLine để test dữ liệu truyền và nhận từ Server-Client
    private static BufferedWriter out = null;

    // Biến ds lưu data đọc được trả về dạng json từ API CoinMarketCap
   // private static LinkedHashMap<String, String> data;

    // Bên thân server đã là localhost nên chỉ cần port thoi
    // Server cần được chạy trước

    public Server(int port){
        try {
            server = new ServerSocket(port);
            while (true) {
                long startTime = System.currentTimeMillis();
                    System.out.println("Waiting for a client...");
                    // Accept yêu cầu từ client
                    socket = server.accept();
                    System.out.println("Client connected");

                    // Đọc dữ liệu từ Client
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // Ghi dữ liệu cho Client
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    String serverLine = "";
                    while (true) {
                       serverLine = in.readLine();
                        if (serverLine.equalsIgnoreCase("stop"))
                            break;
                    // Khi server nhận được từ khóa "stop" thì đóng kết nối socket
                    // Connect to website coinmarketcap
                    // Hứng đường dẫn https://coinmarketcap.com từ Client nhập vào màn hình

                        String url = serverLine;
                        // Trả về 1 bảng
                        Document doc = Jsoup.connect(url).get();

                    // chọn bảng đang chứa data
                        Element table = doc.select("table").first();

                    // xác định vị trí record trong bảng cần lấy data
                       Elements rows = table.select("tr");


                  //      Elements rows = table.select("tr:not(:first-child)");
                        //  for (Element row : rows){

                        // Không lấy header của bảng
                        for (int i = 1; i < rows.size(); i++) {
                            Element row = rows.get(i);
                            String result = row.text();

                            System.out.println(result);

                            // Ghi xuống đường ống
                            out.write(result + "\t\t\t");
                            out.newLine();
                            out.flush();


                    /*    String rank = row.select("td:nth-child(2)").text();
                        String name = row.select("td:nth-child(3)").text();

                        String price = row.select("td:nth-child(4)").text();
                        String oneHourPercent = row.select("td:nth-child(5)").text();
                        String oneDayPercent = row.select("td:nth-child(6)").text();
                        String sevenDaysPercent = row.select("td:nth-child(7)").text();

                        String marketCap = row.select("td:nth-child(8)").text();
                        String volume = row.select("td:nth-child(9)").text();
                        String circulatingSupply = row.select("td:nth-child(10)").text();
                        String urlLast7Days = row.select("td:nth-child(11) a img ").text();*/


                /*        System.out.println("Rank: " + rank);
                        System.out.println("Name: " + name);
                        System.out.println("Price: " + price);
                        System.out.println("1h% : " + oneHourPercent);
                        System.out.println("24h%: " + oneDayPercent);
                        System.out.println("7d%: " + sevenDaysPercent);
                        System.out.println("Market Cap: " + marketCap);
                        System.out.println("Volum(24h): " + volume);
                        System.out.println("Circulating Supply: " + circulatingSupply);
                        System.out.println("Last 7 days: " + urlLast7Days);*/
                    }

                    if (rows.size() == 0) {
                        System.out.println("Không tìm thấy thông tin");
                        return;
                    }


                    //listRow Chứa thông tin
                        // Chỉnh lại cho chạy index size
                    //String result = "";

                    // Write luôn header
                  /*  for (Element row : rows) {
                        result = row.text();

                        // xuất list coin
                        System.out.println(result);

                        // Ghi xuống đường ống
                        out.write(result + "\t\t\t");
                        out.newLine();
                        out.flush();
                    }*/

                } //  Đóng kết nối

                // Server closed connection
                System.out.println("Server closed connection!");
                in.close();
                out.close();
                socket.close();
                server.close();
                System.out.println("Total excuted time: " + (System.currentTimeMillis() - startTime));

            } // Đóng kết nối socket

            } catch(IOException e){
                e.printStackTrace();
            }

    }

    public static void main(String[] args) {
        Server server = new Server( 52020);

        try {
            Log serverLog =  new Log("ServerLog.txt");

            if (serverLog.logger.isLoggable(Level.INFO)) {
                serverLog.logger.info("Client info message");
            }

            if (serverLog.logger.isLoggable(Level.WARNING)){
                serverLog.logger.warning("Client warning message");
            }

            if (serverLog.logger.isLoggable(Level.SEVERE)) {
                serverLog.logger.severe("Client severe message");
            }

            if (serverLog.logger.isLoggable(Level.CONFIG)){
                serverLog.logger.config("Client cofig message");
            }

            if (serverLog.logger.isLoggable(Level.ALL)){
                serverLog.logger.notifyAll();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

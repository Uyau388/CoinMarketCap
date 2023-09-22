import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Client {
    // Gửi yêu cầu cho server xử lý

    private static Socket socket = null ;
    private static BufferedReader in = null;
    private static BufferedReader stdIn = null;
    private static BufferedWriter out = null;

    public Client (String hostname, int port){
        int listCount = 0;
        List<String> listInfo = new ArrayList<String>();

        try {
            socket = new Socket("localhost", port);
            socket.setSoTimeout(60*1000);
            // Biến đọc dữ liệu response từ Server trả về
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Đọc dữ liệu từ bàn phím
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            String line = "";
            while (true){
                System.out.print("Client input: "); // https://coinmarketcap.com
                line = stdIn.readLine();
                // Ghi dữ liệu nhận xuống stream
                out.write(line);
                // Xuống dòng và tiếp tục ghi
                out.newLine();

                // Ghi hết dữ liệu vào đường ống
                out.flush();
                // Client nhận response từ Server

                if (line.equalsIgnoreCase("Stop")){
                    break;
                }

                // Nhận dữ liệu từ Server response
                System.out.println("Client received: ");
                //line = in.readLine();

           /*     do {

                    System.out.println(line);
                }while ((line = in.readLine())!= null);
*/
                while ( (line = in.readLine()) != null ) {
                    System.out.println(line);
                     if (line.isEmpty() || line.equalsIgnoreCase("") || line == null) {
                         break;
                     }

                }

            }

            System.out.println("Client closed connection");
            in.close();
            out.close();
            socket.close();
            stdIn.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client client = new Client ("127.0.0.1", 52020);
        try {
            Log clientLog = new Log("ClientLog.txt");
        //    clientLog.logger.setLevel(Level.WARNING);

            if (clientLog.logger.isLoggable(Level.INFO)) {
                clientLog.logger.info("Client info message");
            }

            if (clientLog.logger.isLoggable(Level.WARNING)){
                clientLog.logger.warning("Client warning message");
            }

            if (clientLog.logger.isLoggable(Level.SEVERE)) {
                clientLog.logger.severe("Client severe message");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

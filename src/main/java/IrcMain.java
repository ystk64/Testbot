import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class IrcMain {
    private static String nick;
    private static String username;
    private static String realName;
    private static PrintWriter out;
    private static Scanner in;
    private static String join;

    public static void main(String[] args) throws IOException {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter a nickname ");
        nick = console.nextLine();
        System.out.print("Enter a username ");
        username = console.nextLine();
        System.out.print("Enter your real name ");
        realName = console.nextLine();
        System.out.print("Specify channel to join ");
        join = console.nextLine();

        Socket socket = new Socket("chat.freenode.net", 6667);

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new Scanner(socket.getInputStream());

          write("NICK ", nick);
          write("USER", username + " 0 * :" + realName);
          write("JOIN", join);

          while (in.hasNext()) {
              String serverMessage = in.nextLine();
              System.out.println("<<< " + serverMessage );
              if (serverMessage.startsWith("PING")) {
                  String pingContents = serverMessage.split(" ", 2)[1];
                  write("PONG", pingContents);
              }
          }

          in.close();
          out.close();
          socket.close();

          System.out.println("Done");

    }

    private static void write(String command, String message) {
        String fullMessage = command + " " + message;
        out.print(fullMessage + "\r\n");
        System.out.println(">>>" + fullMessage);
        out.flush();
    }
}

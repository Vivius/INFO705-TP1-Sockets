import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;

public class Serveur {
    public static void main(String[] arg) {
        try {
            // Démarrage du serveur
            IMoteurCalcul calculator = new MoteurCalculSimple();
            ServerSocket socketServer = new ServerSocket(2017);
            System.out.println("Le serveur est à l'écoute du port " + socketServer.getLocalPort());

            while (true) {
                // Acceptation d'un client
                Socket socket = socketServer.accept();
                System.out.println("Un client est connecté");

                // Input. Attention : socket.getInputStream est bloquant.
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String expression = in.readLine();

                String resultat;
                try {
                    resultat = calculator.calculer(expression).toString();
                } catch (ExpressionInvalide e) {
                    resultat = "Erreur";
                }

                // Envoie du résultat. Attention : socket.getOutputStream est bloquant.
                System.out.println("Resultat serveur = " + resultat);
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(resultat);
                out.flush();
            }

            //socket.close();
            //socketServer.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

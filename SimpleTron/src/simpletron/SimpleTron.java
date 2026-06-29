/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simpletron;
import java.util.Scanner;
import classes.Computador;
import classes.IterativeMode;

/**
 *
 * @author eferreir
 */
public class SimpleTron {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Computador computador = new Computador();
        computador.setScanner(scanner);
        int opcao;
        int[] programa = {1099, 1098, 2099, 3098, 2197, 1197};
        
        //      MENU ITERATIVO
        System.out.println("\n╔═══════════════════════════════════╗");
        System.out.println("║         S I M P L E T R O N        ║");
        System.out.println("╚═══════════════════════════════════╝");
        do{
            System.out.println("  ════════════ MENU ════════════");
            System.out.println("  0  Sair");
            System.out.println("  1  Modo Iterativo (REPL)");
            System.out.println("  2  Modo de Execucao");
            System.out.print("  > ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }
            switch (opcao) {
                case 0:
                    return ;
                case 1:
                    {
                        IterativeMode iterativo = new IterativeMode(scanner);
                        iterativo.iniciar();
                    }
                    break ;
                case 2:
                    System.out.println("Digite o nome do ficheiro '.sml' que deseja executar");
                    System.out.print(": ");
                    String ficheiro = scanner.nextLine();
                    computador.executarPrograma(programa, ficheiro);
                    break ;
                default:
                    System.out.println("Opcao invalida!");
            }
        }while(opcao != 0);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simpletron;
import java.util.Scanner;
import classes.Computador;

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
        int opcao;
        int[] programa = {1099, 1098, 2099, 3098, 2197, 1197};
        
        //      MENU ITERATIVO
        System.out.println("SIMPLETRON");
        do{
            System.out.println("Selecione uma opção:");
            System.out.println("0: Sair");
            System.out.println("1: Modo compilador");
            System.out.println("2: Modo executor");
            System.out.print(": ");
            opcao = scanner.nextInt();
            switch (opcao) {
                case 0:
                    return ;
                case 1:
                    System.out.println("Modo Compilador");
                    break ;
                case 2:
                    System.out.println("Modo Executor");
                    computador.executarPrograma(programa);
                    break ;
                default:
                    System.out.println("Opcao inválida!");
            }
        }while(opcao != 0);
    }
}

package classes;

import java.util.Scanner;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author eferreir
 */

public class Computador {
    private Memoria memoria;
    private Registadores regs;
    private Loader loader;
    private final Scanner scanner = new Scanner(System.in);
    
    public Computador(){
        memoria = new Memoria();
        regs = new Registadores();
        loader = new Loader();
    }

    //  processamento
    
    private void buscaDescodificacao(){
        regs.operacao = memoria.ler(regs.ic) / 100;
        regs.operando = memoria.ler(regs.ic) % 100;
    }
    
    private boolean executarInstrucao(){
        SimpleTronInstrucoes instr = SimpleTronInstrucoes.paraOpCode(regs.operacao);
        if (instr == null) {
            return false;
        }
        
        switch (instr){
            case READ:
                System.out.println("Pedindo input do usuário");
                memoria.escrever(regs.operando, scanner.nextInt());
				System.out.println("");
                regs.ic++;
                return true;
            case WRITE:
                 System.out.printf("Imprimindo valor guardado na posição %d\n", regs.operando);
                System.out.println(memoria.ler(regs.operando));
				System.out.println("");
                regs.ic++;
                return true;
            case LOAD:
                System.out.printf("Carregando valor da posição %d no registador acumulador\n", regs.operando);
				System.out.println("");
                regs.acumulador = memoria.ler(regs.operando);
                regs.ic++;
                return true;
            case STORE:
                System.out.printf("guardando valor %d na posição %d \n", regs.acumulador, regs.operando);
				System.out.println("");
                memoria.escrever(regs.operando, regs.acumulador);
                regs.ic++;
                return true;
            case ADD:
                System.out.printf("somando valor na posição %d com acumulador \n", regs.operando);
				System.out.println("");
                regs.acumulador += memoria.ler(regs.operando);
                regs.ic++;
                return true;
            case SUBTRACT:
                System.out.printf("subtraindo valor na posição %d com acumulador \n", regs.operando);
				System.out.println("");
                regs.acumulador -= memoria.ler(regs.operando);
                regs.ic++;
                return true;
            case MULTIPLY:
                System.out.printf("multiplicando valor na posição %d com acumulador \n", regs.operando);
				System.out.println("");
                regs.acumulador *= memoria.ler(regs.operando);
                regs.ic++;
                return true;
            case DIVIDE:
                System.out.printf("multiplicando valor na posição %d com acumulador \n", regs.operando);
				System.out.println("");
                int divisor = memoria.ler(regs.operando);
                if (divisor == 0)
                    return false;
                regs.acumulador /= divisor;
                regs.ic++;
                return true;
            case BRANCH:
                System.out.printf("indo para %d \n", regs.operando);
				System.out.println("");
                regs.ic = regs.operando;
                return true;
            case BRANCHNEG:
                System.out.printf("indo para %d se %d for negativo\n", regs.operando, regs.acumulador);
				System.out.println("");
                if (regs.acumulador < 0)
                    regs.ic = regs.operando;
                else 
                    regs.ic++;
                return true;
            case BRANCHZERO:
                System.out.printf("indo para %d se %d for igual a 0 \n", regs.operando, regs.acumulador);
				System.out.println("");
                if (regs.acumulador == 0)
                    regs.ic = regs.operando;
                else 
                    regs.ic++;
                return true;
            case HALT:
				System.out.println("Terminando o programa");
				System.out.println("");
                return false;
            default:
                return false;
        }
    }

    public void executarPrograma(int[] programa){
        regs.acumulador = 0;
        regs.ic = 0;
        regs.operacao = 0;
        regs.operando = 0;
        regs.emExecucao =  true;
        loader.carregarPrograma(programa, memoria);

        while((regs.ic < 100) && (regs.emExecucao == true)) {
            buscaDescodificacao();
            regs.emExecucao = executarInstrucao();
        }
    }
    
    public void executarImediato(int instrucao)
    {
      regs.operacao = instrucao / 100;
      regs.operando = instrucao % 100;
      executarInstrucao();
    }
}

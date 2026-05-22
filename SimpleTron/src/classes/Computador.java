package classes;

import static classes.SimpleTronInstrucoes.BRANCH;
import static classes.SimpleTronInstrucoes.LOAD;
import static classes.SimpleTronInstrucoes.READ;
import static classes.SimpleTronInstrucoes.STORE;
import static classes.SimpleTronInstrucoes.WRITE;

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
                Scanner scanner = new Scanner(System.in);
                memoria.escrever(regs.operando, scanner.nextInt());
                regs.ic++;
                return true;
            case WRITE:
                System.out.println(memoria.ler(regs.operando));
                regs.ic++;
                return true;
            case LOAD:
                regs.acumulador = memoria.ler(regs.operando);
                regs.ic++;
                return true;
            case STORE:
                memoria.escrever(regs.operando, regs.acumulador);
                regs.ic++;
                return true;
            case ADD:
                regs.acumulador += memoria.ler(regs.operando);
                regs.ic++;
                return true;
            case SUBTRACT:
                regs.acumulador -= memoria.ler(regs.operando);
                regs.ic++;
                return true;
            case MULTIPLY:
                regs.acumulador *= memoria.ler(regs.operando);
                regs.ic++;
                return true;
            case DIVIDE:
                int divisor = memoria.ler(regs.operando);
                if (divisor == 0)
                    return false;
                regs.acumulador /= divisor;
                regs.ic++;
                return true;
            case BRANCH:
                regs.ic = regs.operando;
                return true;
            case BRANCHNEG:
                if (regs.acumulador < 0)
                    regs.ic = regs.operando;
                else 
                    regs.ic++;
                return true;
            case BRANCHZERO:
                if (regs.acumulador == 0)
                    regs.ic = regs.operando;
                else 
                    regs.ic++;
                return true;
            case HALT:
                regs.ic = 0;
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
}

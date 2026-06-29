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
    private Scanner scanner = new Scanner(System.in);

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
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
    
    private void cabecalhoInstr(String mnem) {
        int espacos = 37 - mnem.length();
        System.out.printf("┌─ %s %s┐\n", mnem, "─".repeat(espacos));
    }

    private boolean executarInstrucao(){
        SimpleTronInstrucoes instr = SimpleTronInstrucoes.paraOpCode(regs.operacao);
        if (instr == null) {
            return false;
        }

        int instrucaoCompleta = memoria.ler(regs.ic);
        int accAntes = regs.acumulador;
        int valorMem = memoria.ler(regs.operando);
        String M = instr.toString();

        cabecalhoInstr(M);
        System.out.printf("│ IC: %02d    %04d              →  mem[%02d] │\n",
            regs.ic, instrucaoCompleta, regs.operando);
        System.out.printf("│ %s│\n", "─".repeat(40));

        switch (instr){
            case READ:
                System.out.printf("│  Input do utilizador                  │\n");
                System.out.printf("└%s┘\n", "─".repeat(40));
                System.out.print("  > ");
                int valor = scanner.nextInt();
                scanner.nextLine();
                memoria.escrever(regs.operando, valor);
                System.out.printf("  ✓ mem[%02d] = %d\n\n", regs.operando, valor);
                regs.ic++;
                return true;
            case WRITE:
                System.out.printf("│  mem[%02d] = %-27d│\n", regs.operando, valorMem);
                System.out.printf("└%s┘\n", "─".repeat(40));
                System.out.printf("  → %d\n\n", valorMem);
                regs.ic++;
                return true;
            case LOAD:
                regs.acumulador = valorMem;
                System.out.printf("│  mem[%02d] = %-27d│\n", regs.operando, valorMem);
                System.out.printf("│  ACC: %d → %-27d│\n", accAntes, regs.acumulador);
                System.out.printf("└%s┘\n\n", "─".repeat(40));
                regs.ic++;
                return true;
            case STORE:
                System.out.printf("│  ACC = %-33d│\n", accAntes);
                memoria.escrever(regs.operando, regs.acumulador);
                System.out.printf("│  mem[%02d] ← %-30d│\n", regs.operando, accAntes);
                System.out.printf("└%s┘\n\n", "─".repeat(40));
                regs.ic++;
                return true;
            case ADD:
                regs.acumulador += valorMem;
                System.out.printf("│  mem[%02d] = %-27d│\n", regs.operando, valorMem);
                System.out.printf("│  ACC: %d + %d = %-20d│\n", accAntes, valorMem, regs.acumulador);
                System.out.printf("└%s┘\n\n", "─".repeat(40));
                regs.ic++;
                return true;
            case SUBTRACT:
                regs.acumulador -= valorMem;
                System.out.printf("│  mem[%02d] = %-27d│\n", regs.operando, valorMem);
                System.out.printf("│  ACC: %d - %d = %-20d│\n", accAntes, valorMem, regs.acumulador);
                System.out.printf("└%s┘\n\n", "─".repeat(40));
                regs.ic++;
                return true;
            case MULTIPLY:
                regs.acumulador *= valorMem;
                System.out.printf("│  mem[%02d] = %-27d│\n", regs.operando, valorMem);
                System.out.printf("│  ACC: %d × %d = %-20d│\n", accAntes, valorMem, regs.acumulador);
                System.out.printf("└%s┘\n\n", "─".repeat(40));
                regs.ic++;
                return true;
            case DIVIDE:
                System.out.printf("│  mem[%02d] = %-27d│\n", regs.operando, valorMem);
                if (valorMem == 0) {
                    System.out.printf("│  ERRO: divisao por zero!             │\n");
                    System.out.printf("└%s┘\n\n", "─".repeat(40));
                    return false;
                }
                regs.acumulador /= valorMem;
                System.out.printf("│  ACC: %d / %d = %-20d│\n", accAntes, valorMem, regs.acumulador);
                System.out.printf("└%s┘\n\n", "─".repeat(40));
                regs.ic++;
                return true;
            case BRANCH:
                System.out.printf("│  Saltar para IC = %-23d│\n", regs.operando);
                System.out.printf("└%s┘\n\n", "─".repeat(40));
                regs.ic = regs.operando;
                return true;
            case BRANCHNEG:
                System.out.printf("│  ACC = %-33d│\n", regs.acumulador);
                if (regs.acumulador < 0) {
                    System.out.printf("│  ✓ ACC < 0 → saltar IC = %-18d│\n", regs.operando);
                    regs.ic = regs.operando;
                } else {
                    System.out.printf("│  ✗ ACC >= 0 → continuar               │\n");
                    regs.ic++;
                }
                System.out.printf("└%s┘\n\n", "─".repeat(40));
                return true;
            case BRANCHZERO:
                System.out.printf("│  ACC = %-33d│\n", regs.acumulador);
                if (regs.acumulador == 0) {
                    System.out.printf("│  ✓ ACC == 0 → saltar IC = %-18d│\n", regs.operando);
                    regs.ic = regs.operando;
                } else {
                    System.out.printf("│  ✗ ACC != 0 → continuar               │\n");
                    regs.ic++;
                }
                System.out.printf("└%s┘\n\n", "─".repeat(40));
                return true;
            case HALT:
                System.out.printf("│  Programa terminado!                   │\n");
                System.out.printf("└%s┘\n\n", "─".repeat(40));
                return false;
            default:
                return false;
        }
    }

    public void executarPrograma(int[] programa, String ficheiro){
        regs.acumulador = 0;
        regs.ic = 0;
        regs.operacao = 0;
        regs.operando = 0;
        regs.emExecucao =  true;
        loader.carregarFicheiro(ficheiro, memoria);

        System.out.printf("\n═══════ A EXECUTAR PROGRAMA ═══════\n\n");

        while((regs.ic < 100) && (regs.emExecucao == true)) {
            buscaDescodificacao();
            regs.emExecucao = executarInstrucao();
        }

        System.out.printf("═══════ PROGRAMA TERMINADO ═══════\n\n");
    }
    
    public Memoria getMemoria() { return memoria; }
    public Registadores getRegs() { return regs; }
    public Loader getLoader() { return loader; }

    public boolean passo() {
        if (regs.ic >= 100 || !regs.emExecucao) return false;
        buscaDescodificacao();
        regs.emExecucao = executarInstrucao();
        return regs.emExecucao;
    }

    public boolean executarOpcode(int opcode, int operando) {
        regs.operacao = opcode;
        regs.operando = operando;
        regs.emExecucao = true;
        SimpleTronInstrucoes instr = SimpleTronInstrucoes.paraOpCode(opcode);
        if (instr == null) return false;
        System.out.printf("\n┌─ %s IMEDIATO ─────────────────────────┐\n", instr);
        return executarInstrucao();
    }

    public void executarImediato(int instrucao, int p)
    {
        memoria.escrever(instrucao, p);
    }
}

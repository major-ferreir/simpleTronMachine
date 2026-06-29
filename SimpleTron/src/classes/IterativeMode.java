package classes;

import java.util.Scanner;

public class IterativeMode {
    private Computador computador;
    private Scanner scanner;
    private int proximaPosicao;

    public IterativeMode(Scanner scanner) {
        this.scanner = scanner;
        computador = new Computador();
        computador.setScanner(scanner);
        proximaPosicao = 0;
    }

    public void iniciar() {
        System.out.println("\n╔═══════════════════════════════════╗");
        System.out.println("║    MODO ITERATIVO - SIMPLETRON    ║");
        System.out.println("╚═══════════════════════════════════╝");
        System.out.println("  Digite 'help' para ver os comandos disponiveis.\n");

        while (true) {
            System.out.print("> ");
            String linha = scanner.nextLine().trim();
            if (linha.isEmpty()) continue;

            String[] partes = linha.split("\\s+");
            String comando = partes[0].toLowerCase();

            switch (comando) {
                case "help":
                case "ajuda":
                    mostrarAjuda();
                    break;
                case "sair":
                case "exit":
                case "quit":
                    System.out.println("A sair do modo iterativo...");
                    return;
                case "memoria":
                case "mem":
                    mostrarMemoria();
                    break;
                case "registadores":
                case "regs":
                    mostrarRegistadores();
                    break;
                case "dump":
                    mostrarDump();
                    break;
                case "carregar":
                case "escrever":
                    if (partes.length >= 3) {
                        escreverMemoria(partes);
                    } else {
                        System.out.println("Uso: carregar <posicao> <valor>");
                    }
                    break;
                case "step":
                case "passo":
                    passo();
                    break;
                case "continuar":
                case "run":
                    continuar();
                    break;
                case "asm":
                case "montar":
                    if (partes.length >= 3) {
                        montar(partes);
                    } else {
                        System.out.println("Uso: asm <mnemonico> <operando>");
                        System.out.println("Exemplo: asm READ 99");
                    }
                    break;
                case "ficheiro":
                case "file":
                    if (partes.length >= 2) {
                        carregarFicheiro(partes[1]);
                    } else {
                        System.out.println("Uso: ficheiro <nome_do_ficheiro>");
                    }
                    break;
                case "reset":
                    reset();
                    break;
                case "ler":
                case "read":
                    if (partes.length >= 2) {
                        lerTeclado(partes);
                    } else {
                        System.out.println("Uso: ler <posicao>");
                    }
                    break;
                default:
                    try {
                        int valor = Integer.parseInt(comando);
                        if (valor < 100 && SimpleTronInstrucoes.paraOpCode(valor) != null) {
                            int endereco;
                            if (valor == 10) {
                                endereco = proximaPosicao;
                                proximaPosicao++;
                            } else {
                                System.out.print("Digite o endereco: ");
                                endereco = Integer.parseInt(scanner.nextLine());
                            }
                            computador.executarOpcode(valor, endereco);
                        } else {
                            computador.getMemoria().escrever(proximaPosicao, valor);
                            System.out.printf("Instrucao %d carregada na posicao %d\n", valor, proximaPosicao);
                            proximaPosicao++;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Comando desconhecido. Digite 'help' para ajuda.");
                    }
                    break;
            }
        }
    }

    private void mostrarAjuda() {
        System.out.println("\n╔═══════════════════════════════════╗");
        System.out.println("║        C O M A N D O S            ║");
        System.out.println("╚═══════════════════════════════════╝");
        System.out.printf("  %-22s  %s\n", "help, ajuda", "Mostra esta mensagem");
        System.out.printf("  %-22s  %s\n", "sair, exit, quit", "Volta ao menu principal");
        System.out.printf("  %-22s  %s\n", "memoria, mem", "Mostra o conteudo da memoria");
        System.out.printf("  %-22s  %s\n", "registadores, regs", "Mostra o estado dos registadores");
        System.out.printf("  %-22s  %s\n", "dump", "Mostra memoria e registadores");
        System.out.printf("  %-22s  %s\n", "carregar <pos> <val>", "Escreve um valor na memoria");
        System.out.printf("  %-22s  %s\n", "ler, read <pos>", "Le um valor do teclado");
        System.out.printf("  %-22s  %s\n", "asm <mnem> <oper>", "Monta instrucao (ex: asm READ 99)");
        System.out.printf("  %-22s  %s\n", "step, passo", "Executa uma instrucao passo a passo");
        System.out.printf("  %-22s  %s\n", "continuar, run", "Executa o programa ate ao fim");
        System.out.printf("  %-22s  %s\n", "ficheiro, file <nome>", "Carrega um ficheiro .sml");
        System.out.printf("  %-22s  %s\n", "reset", "Reinicia o computador");
        System.out.printf("  %-22s  %s\n", "<numero>", "Insere instrucao crua na memoria");
        System.out.println();

        System.out.println("  ── Instrucoes (para 'asm' ou exec. imediata) ──");
        System.out.printf("  %-15s  %s\n", "READ (10)", "Le um inteiro do teclado");
        System.out.printf("  %-15s  %s\n", "WRITE (11)", "Escreve um inteiro no ecra");
        System.out.printf("  %-15s  %s\n", "LOAD (20)", "Carrega valor para o acumulador");
        System.out.printf("  %-15s  %s\n", "STORE (21)", "Guarda acumulador na memoria");
        System.out.printf("  %-15s  %s\n", "ADD (30)", "Soma ao acumulador");
        System.out.printf("  %-15s  %s\n", "SUBTRACT (31)", "Subtrai do acumulador");
        System.out.printf("  %-15s  %s\n", "DIVIDE (32)", "Divide o acumulador");
        System.out.printf("  %-15s  %s\n", "MULTIPLY (33)", "Multiplica o acumulador");
        System.out.printf("  %-15s  %s\n", "BRANCH (40)", "Salto incondicional");
        System.out.printf("  %-15s  %s\n", "BRANCHNEG (41)", "Salto se ACC < 0");
        System.out.printf("  %-15s  %s\n", "BRANCHZERO (42)", "Salto se ACC == 0");
        System.out.printf("  %-15s  %s\n", "HALT (43)", "Termina o programa");
        System.out.println();
    }

    private void mostrarMemoria() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║           M E M O R I A        ║");
        System.out.println("╚════════════════════════════════╝");
        for (int i = 0; i < 100; i++) {
            int valor = computador.getMemoria().ler(i);
            if (valor != 0) {
                String mnem = "";
                if (valor >= 1000) {
                    SimpleTronInstrucoes instr = SimpleTronInstrucoes.paraOpCode(valor / 100);
                    if (instr != null) mnem = "  (" + instr + " " + (valor % 100) + ")";
                }
                System.out.printf("  mem[%02d] = %-6d%s\n", i, valor, mnem);
            }
        }
        System.out.println();
    }

    private void mostrarRegistadores() {
        Registadores r = computador.getRegs();
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║        R E G I S T A D O R E S  ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.printf("  Acumulador (ACC) . . . . : %d\n", r.acumulador);
        System.out.printf("  Instruction Counter (IC). : %d\n", r.ic);
        System.out.printf("  Operacao  . . . . . . . . : %d\n", r.operacao);
        System.out.printf("  Operando  . . . . . . . . : %d\n", r.operando);
        System.out.printf("  Em execucao  . . . . . .  : %s\n", r.emExecucao ? "Sim" : "Nao");
        System.out.println();
    }

    private void mostrarDump() {
        mostrarRegistadores();
        mostrarMemoria();
    }

    private void escreverMemoria(String[] partes) {
        try {
            int posicao = Integer.parseInt(partes[1]);
            int valor = Integer.parseInt(partes[2]);
            computador.getMemoria().escrever(posicao, valor);
            System.out.printf("Valor %d escrito na posicao %d\n", valor, posicao);
        } catch (NumberFormatException e) {
            System.out.println("Erro: posicao e valor devem ser numeros inteiros.");
        }
    }

    private void passo() {
        Registadores r = computador.getRegs();
        if (r.ic >= 100 || !r.emExecucao) {
            System.out.println("Programa ja terminou. Use 'reset' para reiniciar.");
            return;
        }
        System.out.printf("\n═══════ PASSO: IC=%02d ═══════\n", r.ic);
        boolean continua = computador.passo();
        if (!continua) {
            System.out.println("Programa terminado.\n");
        }
    }

    private void continuar() {
        Registadores r = computador.getRegs();
        if (r.ic >= 100 || !r.emExecucao) {
            System.out.println("Programa ja terminou. Use 'reset' para reiniciar.");
            return;
        }
        System.out.printf("\n═══════ A EXECUTAR PROGRAMA ═══════\n\n");
        while (r.ic < 100 && r.emExecucao) {
            computador.passo();
        }
        System.out.printf("═══════ PROGRAMA TERMINADO ═══════\n\n");
    }

    private void montar(String[] partes) {
        String mnemonico = partes[1].toUpperCase();
        try {
            int operando = Integer.parseInt(partes[2]);
            SimpleTronInstrucoes instr = SimpleTronInstrucoes.valueOf(mnemonico);
            int instrucao = instr.getOpCode() * 100 + operando;
            computador.getMemoria().escrever(proximaPosicao, instrucao);
            System.out.printf("%s %d -> %d carregado na posicao %d\n", mnemonico, operando, instrucao, proximaPosicao);
            proximaPosicao++;
        } catch (IllegalArgumentException e) {
            System.out.printf("Erro: mnemonico '%s' invalido. Use 'help' para ver os mnemonicos disponiveis.\n", mnemonico);
        }
    }

    private void carregarFicheiro(String nome) {
        computador.getLoader().carregarFicheiro(nome, computador.getMemoria());
        System.out.println();
    }

    private void reset() {
        computador = new Computador();
        computador.setScanner(scanner);
        proximaPosicao = 0;
        System.out.println("Computador reiniciado.\n");
    }

    private void lerTeclado(String[] partes) {
        try {
            int posicao = Integer.parseInt(partes[1]);
            System.out.printf("Digite o valor para a posicao %d: ", posicao);
            int valor = scanner.nextInt();
            scanner.nextLine();
            computador.getMemoria().escrever(posicao, valor);
            System.out.printf("Valor %d guardado na posicao %d\n", valor, posicao);
        } catch (NumberFormatException e) {
            System.out.println("Erro: posicao deve ser um numero inteiro.");
        }
    }
}

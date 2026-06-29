package classes;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Loader {

    public void carregarPrograma(int[] programa, Memoria mem) {

        for (int i = 0; i < programa.length; i++) {
            mem.escrever(i, programa[i]);
            String mnem = "";
            int opCode = programa[i] / 100;
            SimpleTronInstrucoes instr = SimpleTronInstrucoes.paraOpCode(opCode);
            mnem = (instr != null) ? "  │  " + instr.toString() : "";
            System.out.printf("  [%02d]  %04d%s%n", i, programa[i], mnem);
        }

        System.out.printf("\n  ✓ Programa carregado com sucesso!%n");
    }

    public void carregarFicheiro(String caminho, Memoria mem) {
        ArrayList<Integer> instrucoes = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int numeroLinha = 0;

            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;
                linha = linha.trim();

                // ignorar linhas vazias
                if (linha.isEmpty()) continue;

                // validar que a linha é um número inteiro
                try {
                    int valor = Integer.parseInt(linha);
                    instrucoes.add(valor);
                } catch (NumberFormatException e) {
                    System.out.printf(
                        "Erro: linha %d contém valor inválido (\"%s\"). Apenas números inteiros são aceites.%n",
                        numeroLinha, linha
                    );
                    return;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.printf("Erro: ficheiro \"%s\" não encontrado.%n", caminho);
            return;
        } catch (IOException e) {
            System.out.printf("Erro ao ler o ficheiro \"%s\": %s%n", caminho, e.getMessage());
            return;
        }

        if (instrucoes.isEmpty()) {
            System.out.println("Erro: o ficheiro está vazio, nenhum programa carregado.");
            return;
        }

        if (instrucoes.size() > 100) {
            System.out.printf(
                "Erro: o ficheiro contém %d instruções, mas a memória só suporta 100.%n",
                instrucoes.size()
            );
            return;
        }

        // converter List<Integer> para int[]
        int[] programa = instrucoes.stream().mapToInt(Integer::intValue).toArray();

        System.out.printf("  ✓ Ficheiro \"%s\" lido com sucesso (%d instrucoes)%n", caminho, programa.length);
        System.out.printf("  ─ A carregar programa para a memoria...%n%n");
        carregarPrograma(programa, mem);
    }
}

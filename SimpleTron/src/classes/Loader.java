package classes;

public class Loader {

    public void carregarPrograma(int[] programa, Memoria mem) {

        for (int i = 0; i < programa.length; i++) {

            mem.escrever(i, programa[i]);

            System.out.println(
                "Instrução carregada na posição "
                + i +
                ": " + programa[i]
            );
        }

        System.out.println("\nPrograma carregado com sucesso!");
    }

    public void carregarFicheiro(String caminho, Memoria mem) {
        List<Integer> instrucoes = new ArrayList<>();

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

        System.out.printf("Ficheiro \"%s\" lido com sucesso. A carregar %d instrução...%n%n", caminho, programa.length);
        carregarPrograma(programa, mem);
    }
}

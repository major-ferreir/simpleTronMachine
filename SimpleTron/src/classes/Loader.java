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
}

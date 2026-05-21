package classes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author eferreir
 */
    public class Memoria {
        private int[] mem;

        public Memoria(){
            //Inicializar memória
           mem= new int [100];
        }
        public int ler (int posicao){
            return mem[posicao];
        }
        
        public void escrever (int posicao, int valor){
            //verificações
            if (posicao<0 || posicao>99){
             System.out.println("Opção Inválida! \n");
             return;
            }
            boolean memoria_cheia= true;
            for(int i=0; i< mem.length; i++){
              if (mem[i]==0){
               memoria_cheia= false;
               break;
              }
            }
            
            if(memoria_cheia){
             System.out.println("Memória Cheia!");
             return;
            }
            //guarda o valor
         mem[posicao]= valor;
        }
        
    }

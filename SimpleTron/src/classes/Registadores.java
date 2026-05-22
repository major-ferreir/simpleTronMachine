package classes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author eferreir
 */
public class Registadores {
    public int acumulador;
    public int ic;
    public int operacao;
    public int operando;
    public boolean emExecucao;
    Registadores(){
        acumulador = ic = operacao = operando = 0;
        emExecucao = true;
    }
}

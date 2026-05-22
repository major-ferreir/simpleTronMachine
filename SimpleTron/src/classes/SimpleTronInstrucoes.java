/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package classes;

/**
 *
 * @author eferreir
 */
public enum SimpleTronInstrucoes {
    READ(10),
    WRITE(11),
    LOAD(20),
    STORE(21),
    ADD(30),
    SUBTRACT(31),
    DIVIDE(32),
    MULTIPLY(33),
    BRANCH(40),
    BRANCHNEG(41),
    BRANCHZERO(42),
    HALT(43);
    
    private final int opCode;
    
    SimpleTronInstrucoes(int opCode){
        this.opCode = opCode;
    }
    
    public int getOpCode(){
        return opCode;
    }
    
    public static SimpleTronInstrucoes paraOpCode(int opCode){
        for(SimpleTronInstrucoes inst: values()) {
            if (inst.opCode == opCode) return inst;
        }
        return null;
    }
}

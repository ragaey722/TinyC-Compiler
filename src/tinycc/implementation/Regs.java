package tinycc.implementation;

import java.util.ArrayList;
import java.util.List;

import tinycc.mipsasmgen.GPRegister;

public class Regs {

List<GPRegister> table;

public Regs(){
this.table = new ArrayList<GPRegister>();
this.table.add(GPRegister.T0);
this.table.add(GPRegister.T1);
this.table.add(GPRegister.T2);
this.table.add(GPRegister.T3);
this.table.add(GPRegister.T4);
this.table.add(GPRegister.T5);
this.table.add(GPRegister.T6);
this.table.add(GPRegister.T7);
this.table.add(GPRegister.T8);
this.table.add(GPRegister.T9);
} 

public Regs(Regs s){
this.table = new ArrayList<GPRegister>();
for(GPRegister r: s.table){
  this.table.add(r);  
}
this.table.remove(0);    
}
public Regs getnext(){
 return new Regs(this);   
}
public GPRegister getfront(){
    return this.table.get(0);
}


}

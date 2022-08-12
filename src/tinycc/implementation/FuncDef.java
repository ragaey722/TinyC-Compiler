package tinycc.implementation;

import java.util.List;


import tinycc.diagnostic.Diagnostic;

import tinycc.implementation.statement.Statement;
import tinycc.implementation.type.Func;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.*;
import tinycc.parser.Token;

public class FuncDef implements External {


    Func type; Token name;
    List<Token> par; Statement body;
    External next;
    public FuncDef(Type type, Token name,List<Token> par, Statement body ){
        this.type=(Func)type;
        this.name=name;
        this.par=par;
        this.body=body;
        this.next=null;
    }

    public External getnext(){
        return this.next;
    }

    public void setnext(External ex){
        this.next= ex;
    }


    public void checktype(Diagnostic d , Scope s, List<String> defined ){
        if(defined.contains(name.getText()))
        d.printError(name , "Function already defined");
        else
        defined.add(name.getText());
        
        Scope funScope = s.newNestedScope();

        for(int q=0;q<par.size();q++)
        { funScope.add(par.get(q).getText(), type.par.get(q));
            if(type.par.get(q).isVoid())
            d.printError(name , "Function paramter cannot be void");

        }
        body.checktype(d, funScope, type.getRet(), false, true );

    }

    public void generateCode( MipsAsmGen out, List<String> generated ){
    TextLabel Flabel = out.makeTextLabel(name.getText());        
     out.emitLabel(Flabel);
     CompilationScope funCompilationScope = new CompilationScope();
     funCompilationScope.add("ra");
     out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
    out.emitInstruction(MemoryInstruction.SW, GPRegister.RA, null,0 , GPRegister.SP);
    int q =0 ;

    for(Token t : par){
        funCompilationScope.add(t.getText());
        out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
        GPRegister dest=null; 
        switch(q) {
            case 0:
              dest= GPRegister.A0; 
            break;
            case 1:
            dest= GPRegister.A1; 
            break;
            case 2:
            dest= GPRegister.A2; 
            break;
            case 3:
            dest= GPRegister.A3; 
            break;
        }
        out.emitInstruction(type.par.get(q).getStore(), dest, null,0 , GPRegister.SP);
        q++;
    }    

    body.generateCode(out, funCompilationScope , true,null,null, par.size()+1);

	}
    
}

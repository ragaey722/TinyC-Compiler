package tinycc.implementation.expression;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.DataLabel;
import tinycc.mipsasmgen.GPRegister;
import tinycc.mipsasmgen.ImmediateInstruction;
import tinycc.mipsasmgen.MemoryInstruction;
import tinycc.mipsasmgen.MipsAsmGen;
import tinycc.parser.Token;

public class Variable extends Expression {

	Token token;
	public Variable  (Token token){
	this.token= token;
	}

	@Override
	public String toString() {
		return "Var_"+ token.getText();
	}

	@Override
	public Type checktype(Diagnostic d, Scope s) {
	Type t = s.lookup(token.getText());	
		if(t==null)
			d.printError(token," Didn't find variable in Enviroment_");
			return this.finalType= t;
	}

	public boolean isLassignable(){
		return true ;
	}
	public String getVar(){
		return token.getText();
	}

	public  void generateR(MipsAsmGen out, CompilationScope scope, Regs r ){
		this.generateL(out, scope, r);
		out.emitInstruction(MemoryInstruction.LW, r.getfront() , null, 0 , r.getfront());
	}
	public void generateL(MipsAsmGen out, CompilationScope scope, Regs r ){
		int x = scope.getoffs(token.getText());	
		if(x>=0)		
		out.emitInstruction(ImmediateInstruction.ADDIU, r.getfront() ,GPRegister.SP , x);
		else
		{  DataLabel tmp = out.makeDataLabel(token.getText());
			out.emitInstruction(MemoryInstruction.LA, r.getfront() , tmp , 0, null);}
	}
	public int getSize(){
		return finalType.getSize();
	}



}

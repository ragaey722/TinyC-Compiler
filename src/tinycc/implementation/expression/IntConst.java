package tinycc.implementation.expression;

import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.typeInt;
import tinycc.mipsasmgen.GPRegister;
import tinycc.mipsasmgen.ImmediateInstruction;
import tinycc.mipsasmgen.MipsAsmGen;
import tinycc.parser.Token;

public class IntConst extends Expression {

	Type t = typeInt.getInstance();
	Token token;
	public IntConst (Token token){
	this.token= token;
	}

	@Override
	public String toString() {
		
		return "Const_"+ token.getText();
	}

	@Override
	public Type checktype(Diagnostic d, Scope s) {
		return this.finalType= this.t;
	}
	public boolean isLassignable(){
		return false ;
	}

	@Override
	public void generateR(MipsAsmGen out, CompilationScope scope, Regs r) {
		out.emitInstruction(ImmediateInstruction.ORI, r.getfront() , GPRegister.ZERO , Integer.parseInt(token.getText()));

	}

	@Override
	public void generateL(MipsAsmGen out, CompilationScope scope, Regs r) {

	}

	public int getSize(){
		return finalType.getSize();
	}


}

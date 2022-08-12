package tinycc.implementation.expression;


import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.typeChar;
import tinycc.mipsasmgen.ImmediateInstruction;
import tinycc.mipsasmgen.*;
import tinycc.parser.Token;

public class CharConst extends Expression {
	Token token;
	Type t= typeChar.getInstance();
	public CharConst  (Token token){
	this.token= token;
	}
	@Override
	public String toString() {
		return "Const_"+"\'" +token.getText() + "\'";
	}

	public Type checktype( Diagnostic d , Scope s ){
		return this.finalType=this.t;
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

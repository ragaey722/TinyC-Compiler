package tinycc.implementation.expression;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Pointer;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.typeChar;
import tinycc.mipsasmgen.*;
import tinycc.parser.Token;

public class StringConst extends Expression {
	
	Token token;
	Type t= new Pointer(typeChar.getInstance());
	public  StringConst (Token token){
	this.token= token;
	}

	@Override
	public String toString() {
		return "Const_"+ "\"" + token.getText()+"\"";
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
		DataLabel str = out.makeUniqueDataLabel();
		out.emitASCIIZ(str, token.getText());
		out.emitInstruction(MemoryInstruction.LA, r.getfront() ,str , 0, null);
	}

	@Override
	public void generateL(MipsAsmGen out, CompilationScope scope, Regs r) {

		
	}
	public int getSize(){
		return token.getText().length()+1;
	}

}

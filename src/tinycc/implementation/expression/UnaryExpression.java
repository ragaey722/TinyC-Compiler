package tinycc.implementation.expression;

import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Pointer;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.typeInt;
import tinycc.mipsasmgen.MipsAsmGen;
import tinycc.parser.Token;
import tinycc.parser.TokenKind;

public class UnaryExpression extends Expression {
	Token operator; Expression operand;
	boolean postfix;
	public UnaryExpression (Token operator, boolean postfix, Expression operand){
		this.operator=operator;
		this.operand=operand;
		this.postfix=postfix;
	}

	@Override
	public String toString() {
	
		return  "Unary_"+ operator.getText() +"["+operand.toString()+"]" ;
	}

	@Override
	public Type checktype(Diagnostic d, Scope s) {
			Type t= null;
		switch(operator.getKind())
		{ 	case ASTERISK :
			t= operand.checktype(d, s);
			if(!(t.isPointer()))
			d.printError(operator,"not pointer");
			Pointer pt = (Pointer)t;
			if(!(pt.getPointed().isCompelete()))
			d.printError(operator,"not complete pointed");
			return this.finalType=  pt.getPointed();

			case AND :
			if(!(operand instanceof Variable))
			d.printError(operator,"not L valuable");
				t= operand.checktype(d, s);
			if(!(t.isCompelete()))
			d.printError(operator," not complete addressable");
			return this.finalType= new Pointer(t);

			case SIZEOF:
			t=operand.checktype(d, s);
			if(!(t.isCompelete()))
			d.printError(operator,"not complete sizeof operand");
			return this.finalType= typeInt.getInstance();

			default :
			d.printError(operator,"Not proper Unary exp");
			return this.finalType= t;

		}
	}

	public boolean isLassignable(){
		if(operator.getKind()!= TokenKind.ASTERISK)
		return false;
		else
		return operand.isLassignable();

	}

	@Override
	public void generateR(MipsAsmGen out, CompilationScope scope, Regs r) {
		switch(operator.getKind())
		{ 	case ASTERISK :
				operand.generateR(out, scope, r);
			case AND :
			operand.generateL(out, scope, r);

			case SIZEOF:
			operand.getSize();

			default:
		}
		
	}

	@Override
	public void generateL(MipsAsmGen out, CompilationScope scope, Regs r) {
		operand.generateR(out, scope, r);
		
	}

	public int getSize(){
		return finalType.getSize();
	}


}

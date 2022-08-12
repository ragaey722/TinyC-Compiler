package tinycc.implementation.expression;

import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.type.*;
import tinycc.mipsasmgen.GPRegister;
import tinycc.mipsasmgen.ImmediateInstruction;
import tinycc.mipsasmgen.MipsAsmGen;
import tinycc.mipsasmgen.RegisterInstruction;
import tinycc.mipsasmgen.SpecialInstruction;
import tinycc.mipsasmgen.SpecialRegisterInstruction;
import tinycc.parser.Token;

public class BinaryExpression extends Expression {
	Token operator;
	Expression left;
	Expression right;
	Type l, r;

	public BinaryExpression(Token operator, Expression left, Expression right) {
		this.operator = operator;
		this.left = left;
		this.right = right;

	}

	@Override
	public String toString() {
		return "Binary_" + operator.getText() + "[" + left.toString() + "," + right.toString() + "]";
	}

	@Override
	public Type checktype(Diagnostic d, Scope s) {
		l = left.checktype(d, s);
		r = right.checktype(d, s);

		switch (operator.getKind()) {
			case PLUS:
				if (l.isIntegar() && r.isIntegar())
					return this.finalType= typeInt.getInstance();

				else if (l.isPointer() && r.isIntegar()) {
					Pointer pt = (Pointer) l;
					if (pt.getPointed().isCompelete())
						return this.finalType= l;
					else
						d.printError(operator, "not valid addition of Pointer to complete type");
				} else if (l.isIntegar() && r.isPointer()) {
					Pointer pt = (Pointer) r;
					if (pt.getPointed().isCompelete())
						return this.finalType= r;
					else
						d.printError(operator, "not valid addition of Pointer to complete type");
				}

				else
					d.printError(operator, "not valid addition operands");

			case MINUS:
				if (l.isIntegar() && r.isIntegar())
					return this.finalType= typeInt.getInstance();

				else if (l.isPointer() && r.isIntegar()) {
					Pointer pt = (Pointer) l;
					if (pt.getPointed().isCompelete())
						return this.finalType= l;
					else
						d.printError(operator, "not valid subtraction of Pointer to complete type");
				}

				else if (l.isPointer() && r.isPointer()) {
					if (!(l.equals(r)))
						d.printError(operator, "not valid subtraction of two Pointers with same type");
					Pointer pt = (Pointer) l;
					if (pt.getPointed().isCompelete())
						return this.finalType= l;
					else
						d.printError(operator, "not valid subtraction of two Pointers to complete type");

				} else
					d.printError(operator, "not valid Subtraction operands");

			case ASTERISK:
			case SLASH:
				if (l.isIntegar() && r.isIntegar())
					return this.finalType= typeInt.getInstance();
				else
					d.printError(operator, "not valid mul or div operands");

			case EQUAL:
				if (!(left.isLassignable()))
					d.printError(operator, "not valid L Valuable");
				if (!l.isScalar() || !r.isScalar())
					d.printError(operator, "not valid Scalar Assignment");
				if (l.equals(r))
					return this.finalType= l;
				else if (l.isIntegar() && r.isIntegar())
					return this.finalType= typeInt.getInstance();
				else if (l.isPointer() && r.isPointer()) {
					Pointer lpt = (Pointer) l;
					Pointer rpt = (Pointer) r;
					if (lpt.isVoidPtr() || rpt.isVoidPtr())
						return this.finalType= l;
					else
						d.printError(operator, "not valid Pointers Assignment");
				} else if (l.isPointer() && r.isIntegar()) {
					if (right.toString() == "Const_0" )
						return this.finalType= l;
					else
						d.printError(operator, "not valid NUll ptr Assignment");
				} else
					d.printError(operator, "not valid Assignment");

			case EQUAL_EQUAL:
			case BANG_EQUAL:
				if (l.isIntegar() && r.isIntegar())
					return this.finalType= typeInt.getInstance();
				else if (l.isPointer() && r.isPointer()) {
					Pointer lpt = (Pointer) l;
					Pointer rpt = (Pointer) r;
					if (l.equals(r))
						return this.finalType= typeInt.getInstance();
					else if (lpt.isVoidPtr() || rpt.isVoidPtr())
						return this.finalType= typeInt.getInstance();
					else
						d.printError(operator, "not valid Pointers Comparison");
				} else if (l.isPointer() && r.isIntegar()) {
					if (right.toString() == "Const_0" )
						return this.finalType= typeInt.getInstance();
					else
						d.printError(operator, "not valid null Pointers Comparison");

				} else if (l.isIntegar() && r.isPointer()) {
					if (left.toString() == "Const_0" )
						return this.finalType= typeInt.getInstance();
					else
						d.printError(operator, "not valid null Pointer Comparison");

				} else
					d.printError(operator, "not valid Comparison Args");

			case LESS:
			case GREATER:
			case LESS_EQUAL:
			case GREATER_EQUAL:
				if (l.isIntegar() && r.isIntegar())
					return this.finalType= typeInt.getInstance();
				else if (l.isPointer() && r.isPointer() && l.equals(r))
					return this.finalType= typeInt.getInstance();
				else
					d.printError(operator, "not valid Comparison Args");

			default:
				d.printError(operator, "not valid Binary Operator");

		}

		return this.finalType= null;
	}

	public boolean isLassignable() {
		return false;
	}

	@Override
	public void generateR(MipsAsmGen out, CompilationScope scope, Regs r) {

	
	switch(operator.getKind()){
		case PLUS:
		left.generateR(out, scope, r);
		right.generateR(out, scope, r.getnext());
		out.emitInstruction(RegisterInstruction.ADD,r.getfront(), r.getfront() , r.getnext().getfront());
		break;
		case MINUS:
		left.generateR(out, scope, r);
		right.generateR(out, scope, r.getnext());
		out.emitInstruction(RegisterInstruction.SUB,r.getfront(), r.getfront() , r.getnext().getfront());

		break;
		case ASTERISK:
		left.generateR(out, scope, r);
		right.generateR(out, scope, r.getnext());
		out.emitInstruction(RegisterInstruction.MUL,r.getfront(), r.getfront() , r.getnext().getfront());

		break;
		case SLASH:
		left.generateR(out, scope, r);
		right.generateR(out, scope, r.getnext());
		out.emitInstruction(SpecialInstruction.DIV, r.getfront() , r.getnext().getfront());
		out.emitInstruction(SpecialRegisterInstruction.MFLO, r.getfront());

		break;
		case EQUAL_EQUAL:
		left.generateR(out, scope, r);
		right.generateR(out, scope, r.getnext());
		out.emitInstruction(RegisterInstruction.XOR, r.getfront() , r.getfront(), r.getnext().getfront());
		out.emitInstruction(ImmediateInstruction.SLTIU, r.getfront() , 1);

		break;
		case BANG_EQUAL:
		left.generateR(out, scope, r);
		right.generateR(out, scope, r.getnext());
		out.emitInstruction(RegisterInstruction.XOR, r.getfront() , r.getfront(), r.getnext().getfront());
		out.emitInstruction(RegisterInstruction.SLTU, r.getfront(), GPRegister.ZERO);

		break;
		case LESS:
		left.generateR(out, scope, r);
		right.generateR(out, scope, r.getnext());
		out.emitInstruction(RegisterInstruction.SLT,r.getfront(), r.getfront() , r.getnext().getfront());

		break;
		case LESS_EQUAL:
		left.generateR(out, scope, r);
		right.generateR(out, scope, r.getnext());
		out.emitInstruction(RegisterInstruction.SLT,r.getfront(), r.getfront() , r.getnext().getfront());
		break;

		case GREATER:
		left.generateR(out, scope, r);
		right.generateR(out, scope, r.getnext());
		out.emitInstruction(RegisterInstruction.SLT,r.getfront(), r.getnext().getfront() , r.getfront());
		break; 

		case GREATER_EQUAL:
		left.generateR(out, scope, r);
		right.generateR(out, scope, r.getnext());
		out.emitInstruction(RegisterInstruction.SLT,r.getfront(), r.getnext().getfront() , r.getfront());

		break;

		case EQUAL:
		left.generateL(out, scope, r);
		right.generateR(out, scope, r.getnext());
		out.emitInstruction(l.getStore(),r.getnext().getfront() , null , 0, r.getfront() );

		break;
		default:
	}	
		
	}

	@Override
	public void generateL(MipsAsmGen out, CompilationScope scope, Regs r) {

		
	}

	public int getSize(){
		return finalType.getSize();
	}

}

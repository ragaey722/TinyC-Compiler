package tinycc.implementation.statement;

import tinycc.diagnostic.Diagnostic;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.expression.Expression;
import tinycc.implementation.type.Pointer;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.*;

public class ReturnStatement extends Statement {
	Expression expression ;
	Locatable loc;
	Type rType=null;
	public ReturnStatement( Locatable loc, Expression expression ){
		this.expression=expression;
		this.loc = loc;
	}

	@Override
	public String toString() {
		String s="Return[";
		if(this.expression!=null)
		s+= expression.toString();
		s+="]";
		return s;
	}

	public void checktype( Diagnostic d , Scope s, Type rt, boolean isloop, boolean def ){
		rType=rt;
		if(rt==null)
		d.printError(loc,"return outside function");
		if(rt.isVoid()&& expression!=null)
		d.printError(loc,"Return Experssion for Void func");
		Type r = expression.checktype(d, s);
		if(rt.equals(r)) return;
		else if(rt.isIntegar() && r.isIntegar()) return;
		else if(rt.isPointer() && r.isPointer() ){
			Pointer lpt = (Pointer)rt;
			Pointer rpt = (Pointer)r;
			if(lpt.isVoidPtr()|| rpt.isVoidPtr())
			return;
			else 
			d.printError(loc, "not valid Pointers for return");			
		}
		else if(rt.isPointer()&& r.isIntegar())
		{ if(expression.toString()=="Const_0")
				return;
				else 
				d.printError(loc, "not valid NUll ptr for Return");
		}
		else
		d.printError(loc,"not Return Type ");

	}
	
	public void generateCode(MipsAsmGen out, CompilationScope Scope,boolean isfunc, TextLabel endL, TextLabel jcon,int decs ){
		if (expression!=null)
		{expression.generateR(out, Scope, new Regs());
			if(rType.isChar())
			{ out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -1 );
			  out.emitInstruction(MemoryInstruction.SB, GPRegister.T0, null,0 , GPRegister.SP);
			  out.emitInstruction(MemoryInstruction.LB, GPRegister.V0, null, 0 , GPRegister.SP);
			  out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , 1 );
			}
			else
			out.emitInstruction(RegisterInstruction.ADDU, GPRegister.V0 , GPRegister.ZERO, GPRegister.T0);
	
		}

		int x = Scope.getoffs("ra");
		out.emitInstruction(MemoryInstruction.LW, GPRegister.RA, null , x , GPRegister.SP);
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP, x+4);
		out.emitInstruction(JumpRegisterInstruction.JR, GPRegister.RA );
	
	}
	


}

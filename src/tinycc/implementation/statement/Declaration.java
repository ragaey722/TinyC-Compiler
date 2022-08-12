package tinycc.implementation.statement;

import tinycc.parser.*;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Regs;
import tinycc.implementation.Scope;
import tinycc.implementation.expression.*;
import tinycc.implementation.type.*;
import tinycc.mipsasmgen.ImmediateInstruction;
import tinycc.mipsasmgen.*;

public class Declaration extends Statement {

	Type t;
	Token id;
	Expression init;
	
	public Declaration(Type t , Token id , Expression init)
	{
		this.t=t;
		this.id=id;
		this.init=init;

	}
	@Override
	public String toString() {
		String s="Declaration_" +  id.getText() + "[" + t.toString();
		if(init!=null){
			s+=","+init.toString();
		}
		s+="]";
		return s;
	}
	public Type getType(){
		return this.t;
	}
	public void checktype( Diagnostic d , Scope s, Type rt, boolean isloop, boolean def ){
		if(t.isVoid())
		d.printError(id, " Declaration with type void");

		if( init!=null )
		{  Type r = init.checktype(d, s);
			if (!t.isScalar() || !r.isScalar())
					d.printError(id, "not valid Scalar declaration");
			if (t.equals(r)){}
			else if ( (t.isIntegar() && r.isIntegar()) ){}
			else if( (t.isPointer() && r.isPointer()) ){
				Pointer lpt = (Pointer) t;
				Pointer rpt = (Pointer) r;
				if ( !lpt.isVoidPtr() && !rpt.isVoidPtr())
				d.printError(id, "not valid Pointer declaration initialization");
			}
			else if( (t.isPointer() && r.isIntegar()) ){
				if (!(init.toString() == "Const_0") )
					d.printError(id, "not valid NUll ptr Declaration");
			} 
			else
			d.printError(id, "not valid Pointer declaration initialization");

			
		}
		if(s.exists(id.getText()))
		d.printError(id, " Declaration with already declared identifier");

		s.add(id.getText(), t);

		
	}


	public void generateCode(MipsAsmGen out, CompilationScope Scope,boolean isfunc, TextLabel endL, TextLabel jcon,int decs ){
		Scope.add(id.getText());
		out.emitInstruction(ImmediateInstruction.ADDIU, GPRegister.SP , -4 );
		if(init!=null)
		{ init.generateR(out , Scope , new Regs());
		out.emitInstruction(t.getStore(), GPRegister.T0, null,0 , GPRegister.SP);
		}
		
	}



}


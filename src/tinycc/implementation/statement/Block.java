package tinycc.implementation.statement;

import java.util.List;

import tinycc.diagnostic.Diagnostic;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.CompilationScope;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.*;

public class Block extends Statement {

	List<Statement> stmts;
	Locatable loc;
	public int decnum =0;
	public Block( Locatable loc, List<Statement> stmts)
	{	this.loc=loc;
		this.stmts=stmts;
	}

	@Override
	public String toString() {
		String s ="Block[";
		boolean q= false;
		for(Statement st:stmts)
		{ if(q)
			s+=",";

			s+=st.toString();
			q=true;
			
		}

		s+="]";
		return s;
	}

	public void checktype( Diagnostic d , Scope s, Type rt, boolean isloop, boolean def ){
		Scope child = s;
		if(!def)
		child = s.newNestedScope();
		for(Statement sts :stmts )
		{ 
			sts.checktype(d, child,rt,isloop,false);}
	}


	public void generateCode(MipsAsmGen out, CompilationScope Scope,boolean isfunc, TextLabel endL, TextLabel jcon,int decs ){
	if (isfunc)
	{ for(Statement sts: stmts)
		{if(sts instanceof Declaration)
			decnum++;
			sts.generateCode(out, Scope, false , endL,jcon,decs+decnum);}
	}
	
	else 
	{
		CompilationScope child = Scope.newNestedScope();
		for(Statement sts: stmts)
		{ if(sts instanceof Declaration)
			decnum++;
			sts.generateCode(out, child, isfunc,endL,jcon,decs+decnum);	
		}
	}
		
	}


}

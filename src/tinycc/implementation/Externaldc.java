package tinycc.implementation;

import java.util.List;

import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.type.Func;
import tinycc.implementation.type.Type;
import tinycc.mipsasmgen.*;
import tinycc.parser.Token;

public class Externaldc implements External {
    Type type; Token name;
    External next;
    public Externaldc(Type type, Token name ){
        this.type=type;
        this.name=name;
        this.next=null;
    }

    public External getnext(){
        return this.next;
    }

    public void setnext(External ex){
        this.next= ex;
    }


    public void checktype(Diagnostic d , Scope s, List<String> defined ){

        Type t=	s.lookup(name.getText());
			if(t==null)
			{ if(!type.isFunc())
				{ if (type.isVoid())	
					d.printError(name, "External Variable declared with Void");
					s.add(name.getText(),type);

				}
				else
				{ 	Func f = (Func)type;
					for(Type ty:f.par){
					if(ty.isVoid())
					d.printError(name, "Function paramater declared with void");

					}
					s.add(name.getText(),type);
				}

			}
		else
		{ if(!t.equals(type))
			d.printError(name, "External declaration with diffrent Type" );
		}	



    }

	public void generateCode( MipsAsmGen out, List<String> generated ){
	if (generated.indexOf(name.getText())==-1)
	{	generated.add(name.getText());
		if(!this.type.isFunc())
		{DataLabel data = out.makeDataLabel(name.getText());
		if(this.type.isChar())
		out.emitByte(data, (byte)0);
		if(this.type.isInt())
		out.emitWord(data, 0);}
	}

	}


    
}

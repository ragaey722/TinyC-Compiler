package tinycc.implementation.type;

import java.util.List;

import tinycc.mipsasmgen.MemoryInstruction;

public class Func extends Type {
public List<Type> par ;
Type ret;
public Func(Type ret , List<Type> par)
{ this. ret = ret;
	this.par=par;
}

 public Type getRet(){
	return ret;
 }

	@Override
	public String toString() {
		String s="FunctionType[" + ret.toString();
		for(Type t:par)
		s+=","+t.toString();
		s+="]";
		return s;
	}

	public boolean isScalar(){
		return false;
	}

	
	public boolean isVoid(){
		return false;
	}

	
	public boolean isIntegar(){
		return false;
	}

	
	public boolean isPointer(){
		return false;
	}

	
	public boolean isChar(){
		return false;
	}
	
	public boolean isInt(){

		return false;
	}
	public boolean isFunc(){
        return true;
    }

	public boolean isCompelete(){
        return false;
    }

	@Override
	public boolean equals(Object o ){
		if(this == o)
            return true;
		if(! (o instanceof Func))
		return false;
		Func f = (Func)o;
		boolean flag = f.ret.equals(this.ret);
		flag = flag && (f.par.size() == this.par.size());
		if(!flag)
		return false;
		for(int q = 0 ; q<par.size();q++)
		{ flag = flag && par.get(q).equals(f.par.get(q));
		}
		return flag;
	}

	@Override
    public int hashCode()
    {
          
        return 42;
    }

	@Override
	public int getSize() {
	
		return ret.getSize();
	}
	@Override
	public MemoryInstruction getStore() {
	
		return ret.getStore() ;
	}
	@Override
	public MemoryInstruction getLoad() {
		return ret.getLoad() ;
	}





}

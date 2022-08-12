package tinycc.implementation;

import java.util.List;

import tinycc.diagnostic.Diagnostic;
import tinycc.mipsasmgen.MipsAsmGen;

public interface External {

public External getnext();
public void setnext(External ex);

public void checktype(Diagnostic d , Scope s , List<String> defined );

public void generateCode( MipsAsmGen out, List<String> generated );

}

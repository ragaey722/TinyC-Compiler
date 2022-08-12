package tinycc.implementation;

import java.util.*;

public class CompilationScope {

private final List<String> table;
private final CompilationScope parent;

public CompilationScope() {
    this(null);
  }

  private CompilationScope(CompilationScope parent) {
    this.parent = parent;
    this.table  = new ArrayList<String>();
  }

  public CompilationScope newNestedScope() {
    return new CompilationScope(this);
  }

  public void add(String id){
    table.add(id);
  }

  public int getoffs(String s){
    int x=0;
    if (table.contains(s))
    {x = table.indexOf(s);
     return (table.size()- x -1)*4 ;
    }

    else if (parent!=null) {
      x = parent.getoffs(s);
      if(x!=-1)
      x+= (table.size())*4;
      
    return x;
    }
    else
    return -1;
    
  }


    
}

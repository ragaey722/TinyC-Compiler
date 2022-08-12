package tinycc.implementation;

import java.util.*;

import tinycc.implementation.type.Type;

public class Scope {

private final Map<String, Type> table;
  private final Scope parent;

  public Scope() {
    this(null);
  }

  private Scope(Scope parent) {
    this.parent = parent;
    this.table  = new HashMap<String,Type>();
  }

  public Scope newNestedScope() {
    return new Scope(this);
  }

  public void add(String id, Type d){
    table.put(id, d);
  }

  public Type lookup(String id) {
    if(table.containsKey(id))
    return table.get(id);
    else if(parent!=null)
    return parent.lookup(id);
    else 
    return null;
  }
  public boolean exists(String id){
    if (table.containsKey(id))
    return true;
    else 
    return false;
  }
    
}

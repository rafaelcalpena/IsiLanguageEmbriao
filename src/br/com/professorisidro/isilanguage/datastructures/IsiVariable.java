package br.com.professorisidro.isilanguage.datastructures;

public class IsiVariable extends IsiSymbol {
	
	public static final int NUMBER=0;
	public static final int TEXT  =1;
	public static final int BOOL  =2;
	
	private int type;
	private String value;
	private int usedCount;
	private boolean attributed;
	
	public IsiVariable(String name, int type, String value) {
		super(name);
		this.type = type;
		this.value = value;
		this.usedCount = 0;
		this.attributed = false;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean getAttributed() {
		return this.attributed;
	}
	
	public void setAttributed(boolean v) {
		this.attributed = v;
	}

	@Override
	public String toString() {
		return "IsiVariable [name=" + name + ", type=" + type + ", value=" + value + "]";
	}
	
	public void increaseUsedCount() {
		this.usedCount++;
	}
	
	public int getUsedCount() {
		return this.usedCount;
	}
	
	public String generateJavaCode() {
       String str;
       if (type == NUMBER) {
    	   str = "double ";
       }
       else if (type == TEXT) {
    	   str = "String ";
       }
       else {
    	   str = "boolean ";
       }
       return str + " "+super.name+";";
	}
	
	

}

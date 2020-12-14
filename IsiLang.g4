grammar IsiLang;

@header{
	import br.com.professorisidro.isilanguage.datastructures.IsiSymbol;
	import br.com.professorisidro.isilanguage.datastructures.IsiVariable;
	import br.com.professorisidro.isilanguage.datastructures.IsiSymbolTable;
	import br.com.professorisidro.isilanguage.exceptions.IsiSemanticException;
	import br.com.professorisidro.isilanguage.ast.IsiProgram;
	import br.com.professorisidro.isilanguage.ast.AbstractCommand;
	import br.com.professorisidro.isilanguage.ast.CommandLeitura;
	import br.com.professorisidro.isilanguage.ast.CommandEscrita;
	import br.com.professorisidro.isilanguage.ast.CommandAtribuicao;
	import br.com.professorisidro.isilanguage.ast.CommandDecisao;
	import br.com.professorisidro.isilanguage.ast.CommandEnquanto;
	import br.com.professorisidro.isilanguage.ast.CommandComentario;
	import java.util.ArrayList;
	import java.util.Stack;
}

@members{
	private int _tipo;
	private String _varName;
	private String _varValue;
	private IsiSymbolTable symbolTable = new IsiSymbolTable();
	private IsiSymbol symbol;
	private IsiProgram program = new IsiProgram();
	private ArrayList<AbstractCommand> curThread;
	private Stack<ArrayList<AbstractCommand>> stack = new Stack<ArrayList<AbstractCommand>>();
	private String _readID;
	private String _writeID;
	private String _exprID;
	private String _exprContent;
	private String _exprDecision;
	private ArrayList<AbstractCommand> listaTrue;
	private ArrayList<AbstractCommand> listaFalse;
	private ArrayList<AbstractCommand> listaEnquanto;
	private Stack<String> exprDecisions = new Stack<String>();	
	private Stack<ArrayList<AbstractCommand>> listasTrue = new Stack<ArrayList<AbstractCommand>>();
	
	public void verificaID(String id, boolean atribuir){
		if (!symbolTable.exists(id)){
			throw new IsiSemanticException("Symbol "+id+" not declared");
		} else {
			IsiVariable var = (IsiVariable) symbolTable.get(id);
			if (!var.getAttributed() && !atribuir) {
				throw new IsiSemanticException("Symbol "+id+" not attributed");			
			} else {
				var.setAttributed(true);
			}
		}
		marcarUsado(id);		
	}

	public void verificaTipo(String id, int tipoExpr){
		IsiVariable var = (IsiVariable) symbolTable.get(id);
		if (tipoExpr != var.getType()) {
	       	throw new IsiSemanticException("Can't attribute to symbol "+id+". Expected type "+var.getType()+" but got type "+tipoExpr); 
		}
	}
	
	public void verificaUsado(String id) {
		IsiVariable var = (IsiVariable) symbolTable.get(id);
		if (var.getUsedCount() == 0) {
	       	System.out.println("WARNING: Symbol "+id+" was declared but not used"); 
		}	
	}
	
	public void marcarUsado(String id) {
		IsiVariable var = (IsiVariable) symbolTable.get(id);
		var.increaseUsedCount();
	}
	
	public void exibeComandos(){
		for (AbstractCommand c: program.getComandos()){
			System.out.println(c);
		}
	}
	
	public void generateCode(){
		program.generateTarget();
	}
}



prog	: 'programa' decl bloco  'fimprog.'
           {  program.setVarTable(symbolTable);
           	  program.setComandos(stack.pop());
           	  for (IsiSymbol sym: symbolTable.getAll()) {
				IsiVariable var = (IsiVariable) sym; 
           	  	verificaUsado(var.getName());
           	  }
           } 
		;
		
decl    :  (declaravar)+
        ;
        
        
declaravar :  tipo ID  {
	                  _varName = _input.LT(-1).getText();
	                  _varValue = null;
	                  symbol = new IsiVariable(_varName, _tipo, _varValue);
	                  if (!symbolTable.exists(_varName)){
	                     symbolTable.add(symbol);	
	                  }
	                  else{
	                  	 throw new IsiSemanticException("Symbol "+_varName+" already declared");
	                  }
                    } 
              (  VIR 
              	 ID {
	                  _varName = _input.LT(-1).getText();
	                  _varValue = null;
	                  symbol = new IsiVariable(_varName, _tipo, _varValue);
	                  if (!symbolTable.exists(_varName)){
	                     symbolTable.add(symbol);	
	                  }
	                  else{
	                  	 throw new IsiSemanticException("Symbol "+_varName+" already declared");
	                  }
                    }
              )* 
               PF
           ;
           
tipo       : 'numero' { _tipo = IsiVariable.NUMBER; }
           | 'texto'  { _tipo = IsiVariable.TEXT;   }
           | 'bool'   { _tipo = IsiVariable.BOOL;   }
           ;
        
bloco	: { curThread = new ArrayList<AbstractCommand>(); 
	        stack.push(curThread);  
          }
          (cmd)+
		;
		

cmd		:  cmdleitura  
 		|  cmdescrita 
 		|  cmdattrib
 		|  cmdselecao  
 		|  cmdenquanto
 		|  cmdcomentario
		;
		
		
cmdleitura	: 'leia' AP
                     ( ID { verificaID(_input.LT(-1).getText(), true); }
                     | TEXT) { _readID = _input.LT(-1).getText(); }
                     FP 
                     PF 
                     
              {
              	IsiVariable var = (IsiVariable)symbolTable.get(_readID);
              	CommandLeitura cmd = new CommandLeitura(_readID, var);
              	stack.peek().add(cmd);
              }   
			;
			
cmdescrita	: 'escreva' 
                 AP 
                 (ID { verificaID(_input.LT(-1).getText(), false);}
                 | TEXT ) { _writeID = _input.LT(-1).getText(); }
                 FP 
                 PF
               {
               	  CommandEscrita cmd = new CommandEscrita(_writeID);
               	  stack.peek().add(cmd);
               }
			;
			
cmdattrib	:  ID { verificaID(_input.LT(-1).getText(), true);
                    _exprID = _input.LT(-1).getText();
                   } 
               ATTR { _exprContent = ""; } 
               expr 
               PF
               {
               	 CommandAtribuicao cmd = new CommandAtribuicao(_exprID, _exprContent);
               	 stack.peek().add(cmd);
               }
			;
			
			
cmdselecao  :  'se' AP
                    ID    { 
                    		verificaID(_input.LT(-1).getText(), false);
                    		_exprDecision = _input.LT(-1).getText(); 
                    		exprDecisions.push(_exprDecision);
                    	  }
                    OPREL { 
                    		_exprDecision = exprDecisions.pop();
                    		_exprDecision += _input.LT(-1).getText(); 
                    		exprDecisions.push(_exprDecision);
                    	  }
                    (ID   { verificaID(_input.LT(-1).getText(), false); }
                    | NUMBER) {
                    		_exprDecision = exprDecisions.pop();                    
                    		_exprDecision += _input.LT(-1).getText(); 
                    		exprDecisions.push(_exprDecision);
                    		}
                    FP 
                    ACH 
                    { curThread = new ArrayList<AbstractCommand>(); 
                      stack.push(curThread);
                    }
                    (cmd)+ 
                    
                    FCH 
                    {
                       listaTrue = stack.pop();
                       listasTrue.push(listaTrue);	
                    } 
                   ('senao' 
                   	 ACH
                   	 {
                   	 	curThread = new ArrayList<AbstractCommand>();
                   	 	stack.push(curThread);
                   	 } 
                   	(cmd+) 
                   	FCH
                   	{
                        _exprDecision = exprDecisions.pop();      
                        listaTrue = listasTrue.pop();              	
                   		listaFalse = stack.pop();
                   		CommandDecisao cmd = new CommandDecisao(_exprDecision, listaTrue, listaFalse);
                   		stack.peek().add(cmd);
                   	}
                   )?
            ;
            
cmdenquanto    : 'enquanto' AP
                    		ID    { 
                    			verificaID(_input.LT(-1).getText(), false);
                    			_exprDecision = _input.LT(-1).getText(); 
                    			exprDecisions.push(_exprDecision); 
                    			}
                    		OPREL { 
                    			_exprDecision = exprDecisions.pop();
                    			_exprDecision += _input.LT(-1).getText(); 
								exprDecisions.push(_exprDecision);
                    		}
                    		(ID   { verificaID(_input.LT(-1).getText(), false); }
                    		| NUMBER) {
                    			_exprDecision = exprDecisions.pop();
                    			_exprDecision += _input.LT(-1).getText(); 
                    			exprDecisions.push(_exprDecision);
                    		}  
							FP 
							ACH 
		                    { curThread = new ArrayList<AbstractCommand>(); 
		                      stack.push(curThread);
		                    }							
							(cmd)+
							FCH 
							{
							    listaEnquanto = stack.pop();
							    _exprDecision = exprDecisions.pop();
							    CommandEnquanto cmd = new CommandEnquanto(_exprDecision, listaEnquanto);
							    stack.peek().add(cmd);
							}
			;

cmdcomentario : COMMENTA 
				TEXT { _exprContent = _input.LT(-1).getText(); }
				COMMENTF
				{
               	 CommandComentario cmd = new CommandComentario( _exprContent);
               	 stack.peek().add(cmd);
                }
              ;

expr		:  termo 
               ( OP  { _exprContent += _input.LT(-1).getText();}
	             termo
	           )*
			;
			
termo		:
			(AP { _exprContent += "("; } 
			expr 
            FP { _exprContent += ")"; })
			| ID   { verificaID(_input.LT(-1).getText(), false);
				   IsiVariable var = (IsiVariable) symbolTable.get(_input.LT(-1).getText());
			       verificaTipo(_exprID, var.getType());
	               _exprContent += _input.LT(-1).getText();
                 } 
            | NUMBER { verificaTipo(_exprID, IsiVariable.NUMBER);
				       _exprContent += _input.LT(-1).getText();
                     }
			| TEXT   { verificaTipo(_exprID, IsiVariable.TEXT);
              		   _exprContent += _input.LT(-1).getText();
            	     }
            | BOOL   { verificaTipo(_exprID, IsiVariable.TEXT);
              		   _exprContent += _input.LT(-1).getText();
            	     }
			;
			
ASP	: '"'
	;		
	
AP	: '('
	;
	
FP	: ')'
	;
	
SC	: ';'
	;
	
PF  : '.'
	;
	
OP	: '+' | '-' | '*' | '/'
	;
	
ATTR : ':='
	 ;
	 
VIR  : ','
     ;
     
ACH  : '{'
     ;
     
FCH  : '}'
     ;
     
COMMENTA : '/*'
         ; 
        
COMMENTF : '*/'
         ;
	 
OPREL : '>' | '<' | '>=' | '<=' | '==' | '!='
      ;
	
ID	: LET (LET | NUMBER)*
	;
	
NUMBER	: [0-9]+ ('.' [0-9]+)?
		;		

TEXT : ASP (~'"')* ASP 
	;

LET	 : ([A-Z] | [a-z])+
	 ;
	 
BOOL : 'true' | 'false'
     ;
	  
WS	 : (' ' | '\t' | '\n' | '\r') -> skip
     ;
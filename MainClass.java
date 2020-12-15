import java.util.Scanner;
public class MainClass{ 
  public static void main(String args[]){
       Scanner _key = new Scanner(System.in);
double  a;
double  b;
double  c;
double  d;
double  e;
double  f;
String  t1;
String  t2;
System.out.println("Qual é o seu nome?");
t1= _key.nextLine();
System.out.println("Digite o valor de a:");
a= _key.nextDouble();
// "Esse é um comentário para mostra a possibilidade de inserir um."

System.out.println("Digite o valor de b:");
b= _key.nextDouble();
if (a>b) {
System.out.println("Seu a é maior que seu b. O valor dele é:");System.out.println(a);
} else {
System.out.println("Seu b é maior que seu a. O valor dele é:");System.out.println(b);
}

System.out.println("O compilador também aceita números decimais.");
System.out.println("Digite um número decimal:");
a= _key.nextDouble();
System.out.println("Seu número é ");
System.out.println(a);
c = (1+2)*3;
d = 1+2*3;
e = (3/3)-2*2;
f = ((5+2+3)*2)-10/2;
System.out.println("Vamos mostrar a utilização de parênteses em expressões numéricas.");
System.out.println("(1 + 2) * 3 = ");
System.out.println(c);
System.out.println("1 + 2 * 3 = ");
System.out.println(d);
System.out.println("(3 / 3) - 2 * 2 = ");
System.out.println(e);
System.out.println("((5+2+3) * 2) - 10 / 2 = ");
System.out.println(f);
System.out.println("Vamos mostrar a execução da estrutura de repetição.");
System.out.println("Para isso, vou contar de 0 a 3 sozinho.");
a = 0;
while (a<=3) {
System.out.println(a);a = a+1;
}
System.out.println("Também é possível colocar decisões aninhadas:");
System.out.println("Digite um número de 1 a 100: ");
a= _key.nextDouble();
if (a>25) {
if (a<75) {
System.out.println("Seu número está entre 25 e 75");
} else {
System.out.println("Seu número é maior que 75");
}

} else {
System.out.println("Seu número é menor que 25");
}

System.out.println("E repetições aninhadas:");
a = 0;
b = 1;
while (a<3) {
b = 1;while (b<=2) {
System.out.println(b);b = b+1;
}a = a+1;
}
// "a := t1"

// "g := 32"

      _key.close();
  }
}
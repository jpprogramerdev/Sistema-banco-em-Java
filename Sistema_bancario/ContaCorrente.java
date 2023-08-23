package Sistema_bancario;

import java.util.ArrayList;
import java.util.Objects;

public class ContaCorrente {
    private int numeroConta;
    private double saldo;
    private String chavePix;
    private String password;

    public ContaCorrente(int numero_conta, double saldo, String chave, String password){
        setNumeroConta(numero_conta);
        setSaldo(saldo);
        setChavePix(chave);
        setPassword(password);
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getChavePix() {
        return this.chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
         Objects.requireNonNull(password, "Campo de senha não pode ser vazio");
         this.password = password.trim();
    }

    public void SubtrairSaldo(double valor){
        saldo -= valor;
    }

    public void AdicionarSaldo(double valor) {
    	saldo += valor;
    }
    
    public boolean SaldoDisponivel(double valor){
        if(saldo < valor){
            return false;
        }
        return true;
    }

    public StatusAção ObterResultado(double valor){
        if(SaldoDisponivel(valor)){
            return StatusAção.SUCESSO;
        }else if(!SaldoDisponivel(valor)){
            return StatusAção.SALDOINDISPONIVEL;
        }else{
            return StatusAção.SENHAINCORRETA;
        }
    }
    
    public static Pessoa ProcurarConta(int numeroConta, ArrayList<Pessoa> listPessoa) {
    	for (Pessoa pessoa : listPessoa) {
            if(pessoa.VerificarConta(numeroConta)) return pessoa;
        }
    	return null;
    }
    
    public static Pessoa ProcurarPix(String pixConta, ArrayList<Pessoa> listPessoa) {
    	for (Pessoa pessoa : listPessoa) {
            if(pessoa.VerificarPix(pixConta)) return pessoa;
        }
    	return null;
    }
    
}
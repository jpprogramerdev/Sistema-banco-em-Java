package Sistema_bancario;

import java.util.Objects;

public class Pessoa {
    private String Nome;
    private ContaCorrente Conta;

    public Pessoa(String nome, ContaCorrente conta){
        setNome(nome);
        setConta(conta);
    }

    public String getNome() {
        return Nome;
    }

    private void setNome(String nome) {
        Objects.requireNonNull(nome, "Campo de nome não pode estar vario");
        Nome = nome.trim();
    }

    public ContaCorrente getConta(){
        return Conta;
    }

    public void setConta(ContaCorrente conta){
        this.Conta = conta;
    }

    public boolean ValidarConta(int numero_conta, String password){
        if(numero_conta == Conta.getNumeroConta() && password.equals(Conta.getPassword())) return true;
        return false;
    }
    
    public boolean VerificarConta(int numero_conta) {
    	if(numero_conta == Conta.getNumeroConta() ) return true;
    	return false;
    }

    public boolean VerificarSenha(String password) {
    	if(password.equals(Conta.getPassword()) ) return true;
    	return false;
    }
    
    public boolean VerificarPix(String pix) {
    	if(pix.equals(Conta.getChavePix()) ) return true;
    	return false;
    } 
}
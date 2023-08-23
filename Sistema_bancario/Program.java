package Sistema_bancario;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Program{
    public static void main(String[] args) {
        ArrayList<Pessoa> listPessoa = new ArrayList<>();
        ArrayList<Boleto> listBoleto = new ArrayList<>();

        Pessoa p1 = new Pessoa("João Pedro Gerotto Fernandes", new ContaCorrente(1204, 500, "jp1204gerotto@gmail.com", "123"));
        Pessoa p2 = new Pessoa("Rayssa Rafaeli de Oliveira Inocencio", new ContaCorrente(1401, 500, "11912345678", "jp120404"));
        Pessoa p3 = new Pessoa("Renato Silva Mafra", new ContaCorrente(1234, 500, "12345678900", "123456"));
        Boleto b1 = new Boleto(123000989, 100.50);
        
        listPessoa.add(p1);
        listPessoa.add(p2);
        listPessoa.add(p3);
        
        listBoleto.add(b1);
        

        Pessoa usuarioLogado = AutenticarConta(listPessoa, Integer.parseInt(JOptionPane.showInputDialog("Digite o numero da conta")),JOptionPane.showInputDialog("Digite a senha da conta"));

        if(usuarioLogado == null){
            msgContaNaoEncontrada();                                                                                        
        }else{
        	int option = 0;
        	do {
	        	try {
	        		option =  SelectOption(Integer.parseInt(JOptionPane.showInputDialog(Menu())), usuarioLogado, listPessoa, listBoleto);
	        	}catch(Exception ex) {
	        		JOptionPane.showMessageDialog(null,"[ERRO]\nAlgo deu errado");
	        		option = 0;
	        	}
        	}while(option < 6);
        	JOptionPane.showMessageDialog(null,"Saindo...");
        }
    }

    public static Pessoa AutenticarConta(ArrayList<Pessoa> listPessoa, int conta, String password){
        for (Pessoa pessoa : listPessoa) {
            if(pessoa.ValidarConta(conta, password)) return pessoa;
        }

        return null;
    }
    
    public static void msgContaNaoEncontrada() {
    	JOptionPane.showMessageDialog(null,"[ERRO]\nConta não encontrada");
    }

    public static String Menu(){
        return "Digite a opção\n\n" + 
                "1 - Sacar\n" + 
                "2 - Depositar\n" + 
                "3 - Pagar com pix\n" + 
                "4 - Pagar com boleto\n" + 
                "5 - Transferir dinheiro para uma conta\n" + 
                "6 - Sair";
    }

    public static int SelectOption(int option, Pessoa usuario, ArrayList<Pessoa> listPessoa, ArrayList<Boleto> listBoleto){
    	switch(option){    
	            case 1:SacarValor(usuario);break;
	            case 2:DepositarValor(usuario, listPessoa);break;
	            case 3:PagarPix(usuario, listPessoa);break;
	            case 4:PagarBoleto(usuario, listBoleto);;break;
	            case 5:TransferirConta(usuario, listPessoa);break;
	            case 6: return option;
	            default:JOptionPane.showMessageDialog(null, "[ERRO] Opção invalida");break;
	     }
    	return option;
    }

    public static void SacarValor(Pessoa usuario){
        double valorSaque;
        valorSaque = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor de saque:"));
        MsgResultado(usuario, valorSaque);
    }

    public static void DepositarValor(Pessoa usuario, ArrayList<Pessoa> listPessoa){
    	try {
	        double valorDeposito;
	
	        valorDeposito = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor de deposito:"));
	
	        Pessoa contaDeposito = ContaCorrente.ProcurarConta(Integer.parseInt(JOptionPane.showInputDialog("Digite a conta que deseja depositar:")), listPessoa);
	        
	        if(contaDeposito == null) {
	        	msgContaNaoEncontrada();
	        }else {
	        	if(usuario.ValidarConta(usuario.getConta().getNumeroConta(),JOptionPane.showInputDialog("Digite a senha para confirmar a operação"))) {
	        		contaDeposito.getConta().AdicionarSaldo(valorDeposito);
	        		JOptionPane.showMessageDialog(null, "Comprovante depósito\n\nNumero da conta: " + contaDeposito.getConta().getNumeroConta() + "\nValor: " + valorDeposito + "\nData: " + FormatarData() + "\nHorário: " + FormatarHora());
	        	}
	        }
    	}catch(Exception ex){
    		JOptionPane.showMessageDialog(null,"[ERRO]\nAlgo deu errado");
    	}
    }
    
    public static void PagarPix(Pessoa usuario, ArrayList<Pessoa> listPessoa) {
    	try {
	    	double valorPix;
	    	
	    	valorPix = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor do pix:"));
	    	
	    	Pessoa contaPix = ContaCorrente.ProcurarPix(JOptionPane.showInputDialog("Digite o pix que deseja transferir:"), listPessoa);
	    	
	    	if(contaPix == null) {
	        	msgContaNaoEncontrada();
	        }else {
	        	if(MsgResultado(usuario, valorPix)) {
	        		contaPix.getConta().AdicionarSaldo(valorPix);
	        		JOptionPane.showMessageDialog(null, "Comprovante pix\n\nNome: " + contaPix.getNome() + "\nValor: " + valorPix + "\nData: " + FormatarData() + "\nHorário: " + FormatarHora());
	        	}
	        }
    	}catch(Exception ex){
    		JOptionPane.showMessageDialog(null,"[ERRO]\nAlgo deu errado");
    	}
    }
    
    public static void PagarBoleto(Pessoa usuario, ArrayList<Boleto> listBoleto) {
    	try {
			Boleto boleto = Boleto.procurarBoleto(Integer.parseInt(JOptionPane.showInputDialog("Digite o código do boleto:")), listBoleto);
			    	
			if(boleto == null) {
				JOptionPane.showMessageDialog(null,"[ERRO]\nCódigo inválido");
			 }else {
				 if(MsgResultado(usuario,  boleto.getValor())) {
			        usuario.getConta().SubtrairSaldo(boleto.getValor());
			        listBoleto.remove(boleto);
			        JOptionPane.showMessageDialog(null, "Comprovante boleto\n\nCódigo de barras: " + boleto.getCodigo() + "\nValor: " + boleto.getValor() + "\nData: " + FormatarData() + "\nHorário: " + FormatarHora());
			        boleto = null;
				 }
			 }
    	}catch(Exception ex){
    		JOptionPane.showMessageDialog(null,"[ERRO]\nAlgo deu errado");
    	}
    }
    
    public static void TransferirConta(Pessoa usuario, ArrayList<Pessoa> listPessoa){
    	try {
	        double valorTransferencia;
	
	        valorTransferencia = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor  transferir:"));
	
	        Pessoa contaTransferencia = ContaCorrente.ProcurarConta(Integer.parseInt(JOptionPane.showInputDialog("Digite a conta que deseja transferir:")), listPessoa);
	        
	        if(contaTransferencia == null) {
	        	msgContaNaoEncontrada();
	        }else if(contaTransferencia.getConta().getNumeroConta() == usuario.getConta().getNumeroConta()) {
	        	JOptionPane.showMessageDialog(null, "[ERROR] Você não pode transferir para você mesmo, use a opção");
	        }else {
	        	if(usuario.ValidarConta(usuario.getConta().getNumeroConta(),JOptionPane.showInputDialog("Digite a senha para confirmar a operação"))) {
	        		contaTransferencia.getConta().AdicionarSaldo(valorTransferencia);
	        		usuario.getConta().SubtrairSaldo(valorTransferencia);
	        		JOptionPane.showMessageDialog(null, "Comprovante de transferencia\n\nNumero da conta: " + contaTransferencia.getConta().getNumeroConta() + "\nValor: " + valorTransferencia + "\nData: " + FormatarData() + "\nHorário: " + FormatarHora());
	        	}
	        }
    	}catch(Exception ex){
    		JOptionPane.showMessageDialog(null,"[ERRO]\nAlgo deu errado");
    	}
    }
    
    
    public static boolean MsgResultado(Pessoa usuario, double valor) {
    	if(usuario.ValidarConta(usuario.getConta().getNumeroConta(),JOptionPane.showInputDialog("Digite a senha para confirmar a operação:")) && usuario.getConta().ObterResultado(valor) == StatusAção.SUCESSO){
    		usuario.getConta().SubtrairSaldo(valor);
    		JOptionPane.showMessageDialog(null, "Operação feita com sucesso\n\nSaldo atual: " + usuario.getConta().getSaldo());
            
            return true;
    	}else if(usuario.getConta().ObterResultado(valor) == StatusAção.SALDOINDISPONIVEL){
            JOptionPane.showMessageDialog(null, "[ERRO] Operação negada\n\nSaldo indisponivel");
            return false;
    	}else{
            JOptionPane.showMessageDialog(null, "[ERRO] Operação negada\n\nSenha Incorreta");
            return false;
    	}
    }
     
    public static String FormatarData() {
		LocalDate data = LocalDate.now();
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatoData);
        
    }
    
    public static String FormatarHora() {
    	LocalTime hora = LocalTime.now();
		DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        return hora.format(formatoHora);
    }
}
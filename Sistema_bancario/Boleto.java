package Sistema_bancario;

import java.util.ArrayList;

public class Boleto {
	private int codigo;
	private double valor;
	
	public Boleto(int codigo, double valor) {
		 setCodigo(codigo);
		 setValor(valor);
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public static Boleto procurarBoleto(int codigoBoleto, ArrayList <Boleto> listBoleto) {
		for (Boleto boleto : listBoleto) {
			if(boleto.getCodigo() == codigoBoleto) {
				return boleto;
			}
		}
		return null;
	}

}

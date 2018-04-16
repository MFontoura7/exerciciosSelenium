package controleDeOperacoes;

public class MateraContabil {
	private String numeroGuia;
	private char tipo;
	private String cpfCnpj;
	private String contaContabil;
	private String data;
	private String historico;
	private double valor;
	
	public MateraContabil(String numeroGuia, char tipo, String cpfCnpj, String contaContabil, String data, String historico,
			double valor) {
		super();
		this.numeroGuia = numeroGuia;
		this.tipo = tipo;
		this.cpfCnpj = cpfCnpj;
		this.contaContabil = contaContabil;
		this.data = data;
		this.historico = historico;
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "MateraContabil [numeroGuia=" + numeroGuia + ", tipo=" + tipo + ", cpfCnpj=" + cpfCnpj
				+ ", contaContabil=" + contaContabil + ", data=" + data + ", historico=" + historico + ", valor="
				+ valor + "]";
	}

	public String getNumeroGuia() {
		return numeroGuia;
	}

	public char getTipo() {
		return tipo;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public String getContaContabil() {
		return contaContabil;
	}

	public String getData() {
		return data;
	}

	public String getHistorico() {
		return historico;
	}

	public double getValor() {
		return valor;
	}
	
	
	
	
	
	
	
}

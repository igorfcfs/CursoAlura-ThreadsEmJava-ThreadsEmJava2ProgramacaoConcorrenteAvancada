package br.com.alura.threads;

import java.io.IOException;

public class ServidorDeTeste {
	
	private volatile boolean estaRodando = false;
	
	public static void main(String[] args) throws IOException, InterruptedException {		
		ServidorDeTeste servidor = new ServidorDeTeste();
		servidor.rodar();
		servidor.alterandoAtributo();
	}

	// cada thread tem seu cache, ou seja, nao da pra alterar o valor aqui e a outra thread mudar ele, para isso temos que alterar esse atributo em memoria principal, fazer com que os threads acessem o atributo atraves da memoria principal - usaremos volatile
	private void alterandoAtributo() throws InterruptedException {
		Thread.sleep(5000);
		System.out.println("Main alterando estaRodando=true");
		estaRodando = true;
		
		Thread.sleep(5000);
		System.out.println("Main alterando estaRodando=false");
		estaRodando = false;
	}

	private void rodar() throws IOException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Servidor começando, estaRodando=" + estaRodando);
				
				while(!estaRodando) {}
				
				System.out.println("Servidor rodando, estaRodando=" + estaRodando);
				
				while(estaRodando) {}
				
				System.out.println("Servidor terminando, estaRodando=" + estaRodando);
			}
		}).start();
	}
}

package br.com.alura.servidor;

import java.util.concurrent.BlockingQueue;

public class TarefaConsumir implements Runnable {

	private BlockingQueue<String> filaComandos;
	
	public TarefaConsumir(BlockingQueue<String> filaComandos) {
		super();
		this.filaComandos = filaComandos;
	}

	@Override
	public void run() {
		try {
			String comando = null;
			
			while((filaComandos.take()) != null) {
				System.out.println("Consumindo comando " + comando + ", " + Thread.currentThread().getName());
				Thread.sleep(10000);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException();
		}
	}
}

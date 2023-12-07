package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
	private ExecutorService threadPool;
	private ServerSocket servidor;
//	private volatile boolean estaRodando;
	private AtomicBoolean estaRodando; //alternativa para volatile, abstrai essa parte de volatile, pq as vezes o desenvolvedor nao entende corretamente
	
	public ServidorTarefas() throws IOException {
		System.out.println("-----Inicializando Servidor-----");
		this.servidor = new ServerSocket(12345); //ponto de comunicacao
		this.threadPool = Executors.newCachedThreadPool();//cresce/diminiu dinamicamente suas threads
//		this.estaRodando = true;
		this.estaRodando = new AtomicBoolean(true);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {		
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
		servidor.parar();
	}


	public void rodar() throws IOException {
		try {
			while(/*this.estaRodando*/this.estaRodando.get()) {
				Socket socket = servidor.accept();
				System.out.println("Aceitando novo cliente " + socket.getPort());
				
				DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, this/*passar como parametro este servidor que esta rodando*/);
				threadPool.execute(distribuirTarefas);
			}
		} catch (SocketException e) {
			System.out.println("SocketException, Está Rodando? " + this.estaRodando);
		}
	}
	
	public void parar() throws IOException {
//		estaRodando = false;
		this.estaRodando.set(false);
		servidor.close();
		threadPool.shutdown();
//		System.exit(0); // termina sem esperar os clientes irem embora/terminarem sua tarefa
	}
}

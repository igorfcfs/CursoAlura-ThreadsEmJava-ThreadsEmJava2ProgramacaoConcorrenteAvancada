package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
	private ExecutorService threadPool;
	private ServerSocket servidor;
//	private volatile boolean estaRodando;
	private AtomicBoolean estaRodando; //alternativa para volatile, abstrai essa parte de volatile, pq as vezes o desenvolvedor nao entende corretamente
	private BlockingQueue<String> filaComandos;
	
	public ServidorTarefas() throws IOException {
		System.out.println("-----Inicializando Servidor-----");
		this.servidor = new ServerSocket(12345);
		this.threadPool = Executors.newCachedThreadPool(new FabricaDeThreads());
		this.estaRodando = new AtomicBoolean(true);
		this.filaComandos = new ArrayBlockingQueue<>(2);
		iniciarConsumidores();
	}
	
	private void iniciarConsumidores() {
		
		int qtdeConsumidores = 2;
		for(int i = 0; i < qtdeConsumidores; i++) {
			TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
			this.threadPool.execute(tarefa);
		}
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
				
				DistribuirTarefas distribuirTarefas = new DistribuirTarefas(this.threadPool, this.filaComandos, socket, this/*passar como parametro este servidor que esta rodando*/);
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

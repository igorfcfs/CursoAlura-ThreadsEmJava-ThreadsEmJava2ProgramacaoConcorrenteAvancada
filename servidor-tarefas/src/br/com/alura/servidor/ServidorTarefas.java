package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("-----Inicializando Servidor-----");
		ServerSocket servidor = new ServerSocket(12345); //ponto de comunicacao
		
		ExecutorService threadPool = Executors.newCachedThreadPool();//cresce/diminiu dinamicamente suas threads
		
		while(true) {
			Socket socket = servidor.accept();
			System.out.println("Aceitando novo cliente " + socket.getPort());
			
			DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
			threadPool.execute(distribuirTarefas);
		}
	}
}

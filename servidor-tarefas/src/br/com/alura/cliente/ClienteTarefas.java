package br.com.alura.cliente;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 12345); //ponto de comunicacao
		System.out.println("Conexão Estabelecida");
		
		Thread threadEnviaComando = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Pode enviar comandos!");
					
					PrintStream saida = new PrintStream(socket.getOutputStream());
					
					Scanner teclado = new Scanner(System.in);
					while(teclado.hasNextLine()) {
						String linha = teclado.nextLine();
						
						if(linha.trim().equals(""))break;
						
						saida.println(linha);
					}
					saida.close();
					teclado.close();
				} catch (IOException e) {
					throw new RuntimeException();
				}
			}
		});
		
		Thread threadRecebeResposta = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Recebendo dados do servidor");
					
					Scanner respostaServidor = new Scanner(socket.getInputStream());
					while(respostaServidor.hasNextLine()) {
						String linha = respostaServidor.nextLine();
						System.out.println(linha);
					}
					
					socket.close();
				} catch (IOException e) {
					throw new RuntimeException();
				}
			}
		});
		
		threadEnviaComando.start();
		threadRecebeResposta.start();
		
		threadEnviaComando.join(); // o thread main vai aguardar o threadEnviaComando terminar
		
		System.out.println("Fechando socket do cliente");
		socket.close();
	}
}

package br.com.alura.threads;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class TesteFila {

	public static void main(String[] args) throws InterruptedException {
		Queue<String> fila = new LinkedList<>(); // linked list é uma fila e uma lista, pois essa classe implementa ambas interfaces
		
		fila.offer("c1");	
		fila.offer("c2");	
		fila.offer("c3");
		
		System.out.println(fila.peek());
		System.out.println(fila.poll());
		System.out.println(fila.poll());
		System.out.println(fila.poll());
		
		System.out.println(fila.size());
		
		System.out.println("--------------------------------------");
		
		BlockingQueue<String> filaThreadSafe = new ArrayBlockingQueue<String>(3);
		
		filaThreadSafe.put("c1");	
		filaThreadSafe.put("c2");	
		filaThreadSafe.put("c3");
		filaThreadSafe.put("c4");
		
		System.out.println(filaThreadSafe.peek());
		System.out.println(filaThreadSafe.take());
		System.out.println(filaThreadSafe.take());
		System.out.println(filaThreadSafe.take());
		System.out.println(filaThreadSafe.take());
		
		System.out.println(filaThreadSafe.size());
	}
}

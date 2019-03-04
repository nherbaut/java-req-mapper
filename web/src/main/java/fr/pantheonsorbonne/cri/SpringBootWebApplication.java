package fr.pantheonsorbonne.cri;

import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


import io.grpc.Server;
import io.grpc.ServerBuilder;

@SpringBootApplication
public class SpringBootWebApplication extends SpringBootServletInitializer {

	public static Deque<String> req=new ConcurrentLinkedDeque<>();
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootWebApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootWebApplication.class, args);

		Server server = ServerBuilder.forPort(8081).addService(new CollectorImpl() ).build();

		server.start();
		server.awaitTermination();
	}

}
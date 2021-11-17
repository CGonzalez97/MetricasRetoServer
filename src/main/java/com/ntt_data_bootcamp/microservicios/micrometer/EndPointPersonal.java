package com.ntt_data_bootcamp.microservicios.micrometer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
@Endpoint(id="metricas")
@RestController
public class EndPointPersonal {
	
	List<String> historicoEstados = new ArrayList<String>();
	
	private Counter counter1;
	private Counter counter2;

	public EndPointPersonal(MeterRegistry registry) {
		this.counter1 = Counter.builder("invocaciones.getStatus").description("Contador de invocaciones").register(registry);
		this.counter2 = Counter.builder("invocaciones.setStatus").description("Contador de invocaciones").register(registry);
	}
	
	@ReadOperation
	public String getEstado() {
		counter1.increment();
		if(historicoEstados.isEmpty()) {
			return "no-data";
		}else {
			return historicoEstados.get(historicoEstados.size());
		}		
	}
	
	@WriteOperation
	public void setEstado(@Selector String nuevoEstado) {
		counter2.increment();
		historicoEstados.add(nuevoEstado);
	}

}

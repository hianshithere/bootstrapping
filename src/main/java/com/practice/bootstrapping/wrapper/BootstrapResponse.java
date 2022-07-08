package com.practice.bootstrapping.wrapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonPropertyOrder({ "timestamp", "metadata", "result" })
public class BootstrapResponse {

	private String timestamp;
	private Object result;

	private Object metadata;

	public BootstrapResponse(Object result, Object metadata) {
		this.result = result;
		this.metadata = metadata;
		this.timestamp = timestampToString();
	}

	public BootstrapResponse(Object result) {
		this.result = result;
		this.timestamp = timestampToString();
	}

	public BootstrapResponse(){
		this.timestamp = timestampToString ();
	}

	public String timestampToString() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy hh:mm:ss");
		return date.format(formatter);
	}

}

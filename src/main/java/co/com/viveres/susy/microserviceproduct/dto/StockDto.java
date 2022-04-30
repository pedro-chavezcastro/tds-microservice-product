package co.com.viveres.susy.microserviceproduct.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class StockDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;
	private Integer numberItems;

}
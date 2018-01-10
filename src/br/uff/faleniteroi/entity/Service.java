package br.uff.faleniteroi.entity;

import java.io.Serializable;


public class Service implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int icon;
	private String name;
	private String description;

	public Service() {
		super();
	}

	public Service(int id, int icon, String name, String description) {
		super();
		this.id = id;
		this.icon = icon;
		this.name = name;
		this.description = description;
	}

	/*
	public ArrayList<Service> getServiceList() {
		
		ArrayList<Service> services = new ArrayList<Service>();
		services.add(new Service(1, R.drawable.ic_iluminacao, "Iluminação", "Relatar problemas com a iluminação pública, como luzes apagadas"));
		services.add(new Service(2, R.drawable.ic_sinalizacao, "Sinalização de trânsito", "Relatar problemas em semáforos, placas e faixas"));
		services.add(new Service(3, R.drawable.ic_buracos, "Buracos na via", "Relatar buracos e irregularidades em via pública"));
		services.add(new Service(3, R.drawable.ic_calcadas, "Calçada irregular", "Relatar irregularidades e obstáculos em calçadas"));
		services.add(new Service(3, R.drawable.ic_arborizacao, "Arborização", "Comunicar necessidade de poda de árvores em locais públicos"));
		services.add(new Service(3, R.drawable.ic_limpeza, "Limpeza Urbana", "Comunicar necessidade de limpeza em locais públicos"));
		services.add(new Service(3, R.drawable.ic_estacionamento, "Estacionamento irregular", "Relatar veículos estacionados em lugares proíbidos"));
		services.add(new Service(3, R.drawable.ic_mobiliario, "Mobiliário Urbano", "Reladar danos em mobiliário público, como pontos de ônibus danificados"));
		
		return services;
	}
	*/
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

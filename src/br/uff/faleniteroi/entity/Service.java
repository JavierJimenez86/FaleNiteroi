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
		services.add(new Service(1, R.drawable.ic_iluminacao, "Ilumina��o", "Relatar problemas com a ilumina��o p�blica, como luzes apagadas"));
		services.add(new Service(2, R.drawable.ic_sinalizacao, "Sinaliza��o de tr�nsito", "Relatar problemas em sem�foros, placas e faixas"));
		services.add(new Service(3, R.drawable.ic_buracos, "Buracos na via", "Relatar buracos e irregularidades em via p�blica"));
		services.add(new Service(3, R.drawable.ic_calcadas, "Cal�ada irregular", "Relatar irregularidades e obst�culos em cal�adas"));
		services.add(new Service(3, R.drawable.ic_arborizacao, "Arboriza��o", "Comunicar necessidade de poda de �rvores em locais p�blicos"));
		services.add(new Service(3, R.drawable.ic_limpeza, "Limpeza Urbana", "Comunicar necessidade de limpeza em locais p�blicos"));
		services.add(new Service(3, R.drawable.ic_estacionamento, "Estacionamento irregular", "Relatar ve�culos estacionados em lugares pro�bidos"));
		services.add(new Service(3, R.drawable.ic_mobiliario, "Mobili�rio Urbano", "Reladar danos em mobili�rio p�blico, como pontos de �nibus danificados"));
		
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

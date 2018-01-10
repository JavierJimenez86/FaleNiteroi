package br.uff.faleniteroi.entity;


public class MainNav {
	
	private int id;
	private int icon;
	private String name;
	private String description;

	public MainNav() {
		super();
	}

	public MainNav(int id, int icon, String name, String description) {
		super();
		this.id = id;
		this.icon = icon;
		this.name = name;
		this.description = description;
	}

	/*	
	public ArrayList<Nav> getNavList() {
		
		ArrayList<Nav> nav = new ArrayList<Nav>();
		nav.add(new Nav(1, R.drawable.ic_nav_nova_solicitacao, "Nova solicitação", "Relate problemas, irregularidades ou necessidades de serviços públicos"));
		nav.add(new Nav(2, R.drawable.ic_nav_minhas_solicitacoes, "Minhas solicitações", "Acompanhe solicitações que você realizou"));
		nav.add(new Nav(3, R.drawable.ic_nav_mapa_solicitacoes, "Mapa de solicitações", "Visualize solicitações recentes no mapa"));
		nav.add(new Nav(3, R.drawable.ic_nav_intervencoes, "Intervenções", "Notificações sobre interdições causadas por obras e eventos"));
		nav.add(new Nav(3, R.drawable.ic_nav_comentarios, "Críticas e sugestões", "Ajude-nos a melhorar nossos serviços"));
		
		return nav;
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

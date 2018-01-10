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
		nav.add(new Nav(1, R.drawable.ic_nav_nova_solicitacao, "Nova solicita��o", "Relate problemas, irregularidades ou necessidades de servi�os p�blicos"));
		nav.add(new Nav(2, R.drawable.ic_nav_minhas_solicitacoes, "Minhas solicita��es", "Acompanhe solicita��es que voc� realizou"));
		nav.add(new Nav(3, R.drawable.ic_nav_mapa_solicitacoes, "Mapa de solicita��es", "Visualize solicita��es recentes no mapa"));
		nav.add(new Nav(3, R.drawable.ic_nav_intervencoes, "Interven��es", "Notifica��es sobre interdi��es causadas por obras e eventos"));
		nav.add(new Nav(3, R.drawable.ic_nav_comentarios, "Cr�ticas e sugest�es", "Ajude-nos a melhorar nossos servi�os"));
		
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

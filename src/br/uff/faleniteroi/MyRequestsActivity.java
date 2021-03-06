package br.uff.faleniteroi;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import br.uff.faleniteroi.adapter.RequestAdapter;
import br.uff.faleniteroi.adapter.ViewPagerAdapter;
import br.uff.faleniteroi.entity.Request;
import br.uff.faleniteroi.sqlite.RequestDAO;
import br.uff.faleniteroi.tabsfragments.TabRequestFinishedFragment;
import br.uff.faleniteroi.tabsfragments.TabRequestLatestFragment;
import br.uff.faleniteroi.tabsfragments.TabRequestsUnderAnalysisFragment;

public class MyRequestsActivity extends FragmentActivity implements 
	FragmentTabHost.OnTabChangeListener, ViewPager.OnPageChangeListener, SearchView.OnQueryTextListener, OnItemClickListener  {

	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private FragmentTabHost tabHost;
	
	//search
	private SearchView searchView;
	private LinearLayout llSearch;
    private ListView listView;
    private RequestAdapter requestAdapter;
    private ArrayList<Request> itens;
    private TextView tvSearchInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_requests);
		
		listView = (ListView) findViewById(R.id.lvSearch);
		llSearch = (LinearLayout) findViewById(R.id.llSearch);
		tvSearchInfo = (TextView) findViewById(R.id.tvSearchInfo);
		
		listView.setOnItemClickListener(this);
		
		
		this.initialiseTabHost();
		this.intialiseViewPager();
	
	}
	
	private void initialiseTabHost() {
		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("�ltimas"), TabRequestLatestFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Em an�lise"), TabRequestsUnderAnalysisFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Conclu�das"), TabRequestFinishedFragment.class, null);
		
		tabHost.setOnTabChangedListener(this);
	}
	
	private void intialiseViewPager() {
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, TabRequestLatestFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, TabRequestsUnderAnalysisFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, TabRequestFinishedFragment.class.getName()));
		
		pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager = (ViewPager) super.findViewById(R.id.pager);
		
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(this);
	}
	
    private void setupSearchView(MenuItem searchItem) {
    		
        if (isAlwaysExpanded()) {
            searchView.setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }
        
        searchView.setOnQueryTextListener(this);
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_my_request, menu);
        
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
		OptionsMenu menu = new OptionsMenu(item);
		boolean result = menu.onOptionsItemSelected(this); 

		if (!result) {
	    	if (llSearch.getVisibility() == View.VISIBLE)  {
	    		llSearch.setVisibility(View.GONE);
	    		return true;
	    	}
		}

		return super.onOptionsItemSelected(item);
    }

	@Override
	public void onTabChanged(String arg0) {
		int pos = tabHost.getCurrentTab();
		viewPager.setCurrentItem(pos);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		int pos = viewPager.getCurrentItem();
		tabHost.setCurrentTab(pos);
	}

	@Override
	public void onPageSelected(int arg0) {

	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		Request request = (Request) requestAdapter.getItem(pos);
		
		Intent intent = new Intent(view.getContext(), RequestDetailActivity.class);
		intent.putExtra("Request", request);  
		startActivity(intent);
	}
	
    public boolean onClose() {
    	return false;
	}
	
	protected boolean isAlwaysExpanded() {
	    return false;
	}

	@Override
	public boolean onQueryTextChange(String query) {
		
		if (query.trim().equals("")) {
			llSearch.setVisibility(View.GONE);
		} else {
		
			llSearch.setVisibility(View.VISIBLE);

	    	RequestDAO requestDAO = new RequestDAO(this);
	    	itens = requestDAO.listSearched(query);
	    	
	    	tvSearchInfo.setText(itens.size() == 0 ? "Nenhuma solicita��o encontrada" : "Resultados da busca");

		    requestAdapter = new RequestAdapter(this, itens);
		    listView.setAdapter(requestAdapter);
			
		}

		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		
		llSearch.setVisibility(View.VISIBLE);

    	RequestDAO requestDAO = new RequestDAO(this);
    	itens = requestDAO.listSearched(query);
    	
    	tvSearchInfo.setText(itens.size() == 0 ? "Nenhuma solicita��o encontrada" : "Resultados da busca");

	    requestAdapter = new RequestAdapter(this, itens);
	    listView.setAdapter(requestAdapter);
	    
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(tvSearchInfo.getWindowToken(), 0);
		
		return false;
	}
}

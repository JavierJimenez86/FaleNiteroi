package br.uff.faleniteroi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import br.uff.faleniteroi.adapter.CommentAdapter;
import br.uff.faleniteroi.entity.Comment;
import br.uff.faleniteroi.entity.Request;
import br.uff.faleniteroi.sqlite.CommentDAO;
import br.uff.faleniteroi.sqlite.RequestDAO;
import br.uff.faleniteroi.util.BitmapUtil;
import br.uff.faleniteroi.util.Preferences;

public class RequestDetailActivity extends Activity {

	private static int IDENTIFICATION = 2;
	
	private boolean isDetailVisible = false;
	private Request request;
	private RequestDAO requestDAO; 
	private CommentDAO commentDAO;
	private Preferences preferences;
	
	//comments
	private AlertDialog.Builder builder;
	private View commentView;
    private ListView listView;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> itens;
    private AlertDialog commentDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_detail);
		
		preferences = new Preferences(this);
		
		commentDAO = new CommentDAO(this);
		requestDAO = new RequestDAO(this);
		request = (Request) getIntent().getSerializableExtra("Request");
		
		if(getIntent().hasExtra("HiddenProtocol"))
		{
			((LinearLayout) findViewById(R.id.llProtocol)).setVisibility(View.GONE);
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy hh:ss");
		
        TextView tvRequestStatus = (TextView) findViewById(R.id.tvRequestStatus);
        tvRequestStatus.setText(request.isFinished() ? "Concluída" : "Em análise");
        tvRequestStatus.setTextColor(request.isFinished() ? Color.parseColor("#289F00") : Color.parseColor("#E17600"));
		
		((TextView) findViewById(R.id.tvRequestDate)).setText(dateFormat.format(request.getDate()));
        ((TextView) findViewById(R.id.tvRequestServiceName)).setText(request.getService());
        ((TextView) findViewById(R.id.tvRequestAddress)).setText(request.getAddress());
       // ((TextView) findViewById(R.id.tvRequestStatus)).setText(request.isFinished() ? "Concluída" : "Em análise");
        ((TextView) findViewById(R.id.tvRequestProtocol)).setText(request.getProtocol());
        ((TextView) findViewById(R.id.tvRequestResponse)).setText(request.getResponse());
        ((TextView) findViewById(R.id.tvRequestDescription)).setText(request.getDescription());
        
        //request detail
        final ImageView ivRequestPhoto = (ImageView) findViewById(R.id.ivRequestPhoto);
        final TextView tvRequestDescription = (TextView) findViewById(R.id.tvRequestDescription);
        final ImageView btShowDetail = (ImageView) findViewById(R.id.btShowDetail);
        
		btShowDetail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				if (!isDetailVisible) {
					if (request.getPhoto() != null) {
						ivRequestPhoto.setVisibility(View.VISIBLE);
					}
					tvRequestDescription.setVisibility(View.VISIBLE);
					btShowDetail.setImageResource(R.drawable.ic_arrow_up);
					isDetailVisible = true;
				} else {
					tvRequestDescription.setVisibility(View.GONE);
					ivRequestPhoto.setVisibility(View.GONE);
					btShowDetail.setImageResource(R.drawable.ic_arrow_down);
					isDetailVisible = false;
				}
				
			}
		});
		
		final LinearLayout llRated = (LinearLayout) findViewById(R.id.llRated);
		final LinearLayout llRate = (LinearLayout) findViewById(R.id.llRate);
		final LinearLayout llResponse = (LinearLayout) findViewById(R.id.llResponse);
	
        //like
		final ImageView ivLike = (ImageView) findViewById(R.id.ivLike);
		final TextView tvLikeCount = (TextView) findViewById(R.id.tvLikeCount);
		
		ivLike.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				request.setLike(request.getLike() + 1);
				requestDAO.updateRequest(request);
				
				tvLikeCount.setText("(" + request.getLike() + ")");
				
				llRated.setVisibility(View.VISIBLE);
				llRate.setVisibility(View.GONE);
			}
		});
		
        //unlike
		final ImageView ivUnlike = (ImageView) findViewById(R.id.ivUnlike);
		final TextView tvUnikeCount = (TextView) findViewById(R.id.tvUnikeCount);
		
		ivUnlike.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				request.setUnlike(request.getUnlike() + 1);
				requestDAO.updateRequest(request);
				
				tvUnikeCount.setText("(" + request.getUnlike() + ")");
				
				llRated.setVisibility(View.VISIBLE);
				llRate.setVisibility(View.GONE);
			}
		});
		
		if (request.isFinished()) {
			llResponse.setVisibility(View.VISIBLE);
			llRate.setVisibility(View.VISIBLE);
		}
		
		if (request.getLike() != 0 || request.getUnlike() != 0) {
			llRated.setVisibility(View.VISIBLE);
			llRate.setVisibility(View.GONE);
		}
		
		tvLikeCount.setText("(" + request.getLike() + ")");
		tvUnikeCount.setText("(" + request.getUnlike() + ")");
        
        loadBitmapFromPath();

        //show comments
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
        
        commentView = getLayoutInflater().inflate(R.layout.view_comments, null);
        commentView.setMinimumWidth((int) (size.x * 0.9f));
        commentView.setMinimumHeight((int) (size.y * 0.8f));
        
		builder = new AlertDialog.Builder(this);
		builder.setView(commentView);
		commentDialog = builder.create();
		
		final TextView tvShowComments = (TextView) findViewById(R.id.tvShowComments);
		
		tvShowComments.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				initComments();
				commentDialog.show();
			}
		});
		
		//close comments
		final ImageView ivCloseComment = (ImageView) commentView.findViewById(R.id.ivCloseComment);
		
		ivCloseComment.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				commentDialog.dismiss();
			}
		});
		
		//send comments
		final Button btSend = (Button) commentView.findViewById(R.id.btSend);
		final EditText etComment = (EditText) commentView.findViewById(R.id.etComment);
		
		btSend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				String comment = etComment.getText().toString();
				String userName = preferences.getPreferences(Preferences.PREFERENCES_NAME);
				
				if (!comment.trim().equals("")) {
					
					Date currentDate = new Date();
					
					commentDAO.insertCommnet(new Comment(comment, 
							userName,
							request.getRequestId(), 
							currentDate));
					
					etComment.setText(null);
					
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(etComment.getWindowToken(), 0);

					loadComments(commentView.getContext());
				}
			}
		});
		
        //register
        initComments();
        
        //bt register
		final Button btResgiter = (Button) commentView.findViewById(R.id.btRegister);

		btResgiter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), IdentificationActivity.class);
				startActivityForResult(intent, IDENTIFICATION);
			}
		});
		
		//load comments
		listView = (ListView) commentView.findViewById(R.id.lvComments);
		loadComments(commentView.getContext());
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == IDENTIFICATION && resultCode == RESULT_OK) {
			
			if (preferences.isRegistered()) {
				initComments();
				commentDialog.show();
			}
		}
	}
	
	private void initComments() {
		final LinearLayout llRegister = (LinearLayout) commentView.findViewById(R.id.llRegister);
		final LinearLayout llRegistered = (LinearLayout) commentView.findViewById(R.id.llRegistered);
		
		if (preferences.isRegistered()) {
			llRegistered.setVisibility(View.VISIBLE);
			llRegister.setVisibility(View.GONE);
		} else {
			llRegister.setVisibility(View.VISIBLE);
			llRegistered.setVisibility(View.GONE);
		}	
	}
	
	private void loadComments(Context context) {
		
		itens = commentDAO.listByRequestId(request.getRequestId());
		
		commentAdapter = new CommentAdapter(context, itens);
        listView.setAdapter(commentAdapter);
        
        ((TextView) findViewById(R.id.tvShowComments)).setText("Comentários (" + itens.size() + ")");
        
	}
	
	private void loadBitmapFromPath() {
		
		if (request.getPhoto() != null) {
			
			ImageView ivRequestPhoto = (ImageView) findViewById(R.id.ivRequestPhoto);
			
			Bitmap thumbnail = (BitmapUtil.decodeSampledBitmapFromFile(request.getPhoto(), 100, 100));
			ivRequestPhoto.setImageBitmap(thumbnail);
		
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		OptionsMenu menu = new OptionsMenu(item);
		boolean result = menu.onOptionsItemSelected(this); 
		
		if (!result) {
			finish();
			return true;
		}
		
		return false;
    }

}

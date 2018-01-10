package br.uff.faleniteroi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import br.uff.faleniteroi.adapter.ServiceSpinnerAdapter;
import br.uff.faleniteroi.entity.Opinion;
import br.uff.faleniteroi.entity.Service;
import br.uff.faleniteroi.sqlite.OpinionDAO;
import br.uff.faleniteroi.util.Preferences;

public class OpinionFormActivity extends Activity implements OnItemSelectedListener {

	private static int IDENTIFICATION = 2;
	
	private AlertDialog.Builder builder;
	private AlertDialog.Builder successBuilder;
	private View successView;
	
	public static EditText etComment;
	public static RadioGroup rgOpinionType;
	private Spinner sServices;
	private ServiceSpinnerAdapter spinnerAdapter;
	private ArrayList<Service> itens;
	private Service selectedService;
	
	private Preferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opinion_form);
		
		preferences = new Preferences(this);

		builder = new AlertDialog.Builder(this);
		
		successView = getLayoutInflater().inflate(R.layout.view_opinion_create, null);
		successBuilder = new AlertDialog.Builder(this);
		successBuilder.setTitle("Opinião enviada com sucesso");
		successBuilder.setIcon(R.drawable.ic_success);
		successBuilder.setView(successView);
		successBuilder.setCancelable(false);
		successBuilder.create();

    	itens = new ArrayList<Service>();
    	
    	itens.add(new Service(0, 0, "Selecione um serviço", ""));
    	itens.add(new Service(1, R.drawable.ic_iluminacao, "Iluminação", "Relatar problemas com a iluminação pública, como luzes apagadas"));
    	itens.add(new Service(2, R.drawable.ic_sinalizacao, "Sinalização de trânsito", "Relatar problemas em semáforos, placas e faixas"));
    	itens.add(new Service(3, R.drawable.ic_buracos, "Buracos na via", "Relatar buracos e irregularidades em via pública"));
    	itens.add(new Service(4, R.drawable.ic_calcadas, "Calçada irregular", "Relatar irregularidades e obstáculos em calçadas"));
		itens.add(new Service(5, R.drawable.ic_arborizacao, "Arborização", "Comunicar necessidade de poda de árvores em locais públicos"));
		itens.add(new Service(6, R.drawable.ic_limpeza, "Limpeza Urbana", "Comunicar necessidade de limpeza em locais públicos"));
		itens.add(new Service(7, R.drawable.ic_estacionamento, "Estacionamento irregular", "Relatar veículos estacionados em lugares proíbidos"));
		itens.add(new Service(8, R.drawable.ic_mobiliario, "Mobiliário Urbano", "Reladar danos em mobiliário público, como pontos de ônibus danificados"));
		itens.add(new Service(9, R.drawable.ic_outros, "Outros serviços", ""));
		
		etComment = (EditText) findViewById(R.id.etComment);
		rgOpinionType = (RadioGroup) findViewById(R.id.rgOpinionType);
		sServices = (Spinner) findViewById(R.id.sServices);

		spinnerAdapter = new ServiceSpinnerAdapter(this, itens);
		sServices.setAdapter(spinnerAdapter);
		sServices.setOnItemSelectedListener(this);
		
		// Cancel Button
		final Button btCancel = (Button) findViewById(R.id.btCancel);
		btCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Intent intent = new Intent(OpinionFormActivity.this, MainActivity.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//startActivity(intent);
				finish();
			}
		});
		
		// Sent Button
		final Button btSent = (Button) findViewById(R.id.btSend);
		
		btSent.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				String opinionComment = etComment.getText().toString();
				
				if (opinionComment.trim().equals("")) {
					builder.setMessage("O campo comentário deve ser informado");
					builder.setPositiveButton(R.string.ok, null);
					builder.create();
					builder.show();

				} else if (selectedService == null || selectedService.getId() == 0) {
					builder.setMessage("Selecione um serviço");
					builder.setPositiveButton(R.string.ok, null);
					builder.create();
					builder.show();

				} else {
						
					if (!preferences.isRegistered()) {
						
						Intent intent = new Intent(view.getContext(), IdentificationActivity.class);
						startActivityForResult(intent, IDENTIFICATION);
						
					} else {
						
						saveOpinion(view.getContext());
					}
				}
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		super.onActivityResult(requestCode, resultCode, intent);
		
		if (requestCode == IDENTIFICATION && resultCode == RESULT_OK) {
			
			if (preferences.isRegistered()) {
				saveOpinion(this);
			}
		}
	}
	
	private void saveOpinion(Context context)
	{
		OpinionDAO opinionDAO = new OpinionDAO(context);
		
		String opinionComment = etComment.getText().toString();
		int radioButtonID = rgOpinionType.getCheckedRadioButtonId();
		
		View radioButton = rgOpinionType.findViewById(radioButtonID);
		int radioIndex = rgOpinionType.indexOfChild(radioButton);
		
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyddhhmmss");
		String opinionProtocol = dateFormat.format(currentDate);

		Opinion opinion = null;
		
		if (opinionDAO.getNumOpinions() % 2 == 0)
		{
			opinion = new Opinion(opinionComment, 
					radioIndex == 0 ? 0 : 1, 
					selectedService.getName(), 
					currentDate, 
					opinionProtocol, 
					"Seu comentário foi encaminhado ao setor responsável. Obrigado pelas considerações.", 
					true
			);
		} else {
			opinion = new Opinion(opinionComment, 
					radioIndex == 0 ? 0 : 1, 
					selectedService.getName(), 
					currentDate, 
					opinionProtocol, 
					null, 
					false
			);	
		}
		
		opinionDAO.insertOpinion(opinion);
		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etComment.getWindowToken(), 0);
		
		((TextView) successView.findViewById(R.id.tvRequestProtocol)).setText(opinionProtocol);

        successBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
            	selectedService = null;
            	rgOpinionType.setSelected(false);
        		etComment.setText(null);
            	
            	Intent intent = new Intent(OpinionFormActivity.this, MyOpinionsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   
                startActivity(intent);
            }
         });

        successBuilder.show();
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		selectedService = (Service) spinnerAdapter.getItem(pos);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		selectedService = null;
	}	
}

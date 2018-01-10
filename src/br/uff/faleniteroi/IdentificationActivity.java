package br.uff.faleniteroi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import br.uff.faleniteroi.util.Preferences;

public class IdentificationActivity extends Activity {

	public static EditText etName;
	public static EditText etEmail;
	private AlertDialog.Builder builder;
	private Preferences preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_identification);

		preferences = new Preferences(this);
		
		etName = (EditText) findViewById(R.id.etName);
		etEmail = (EditText) findViewById(R.id.etEmail);

		builder = new AlertDialog.Builder(this);

		// Back Button
		final Button btBack = (Button) findViewById(R.id.btBack);
		btBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});

		// Sent Button
		final Button btSent = (Button) findViewById(R.id.btSend);
		btSent.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				final String name = etName.getText().toString();
				final String email = etEmail.getText().toString();
				
				if (name.trim().equals("")) {
					builder.setMessage("O campo nome deve ser informado");
					builder.setPositiveButton(R.string.ok, null);
					builder.create();
					builder.show();
					
				} else if (!email.equals("") && !email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
					
					builder.setMessage("E-mail inválido");
					builder.setPositiveButton(R.string.ok, null);
					builder.create();
					builder.show();
					
				} else {
					
					preferences.editPreferences(name, email);
					
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
					
                	setResult(RESULT_OK);
                	finish();
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}
}

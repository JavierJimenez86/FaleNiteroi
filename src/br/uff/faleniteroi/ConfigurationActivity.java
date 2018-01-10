package br.uff.faleniteroi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import br.uff.faleniteroi.sqlite.DBHelper;
import br.uff.faleniteroi.util.Preferences;

public class ConfigurationActivity extends Activity {
	
	public static EditText etName;
	public static EditText etEmail;
	private AlertDialog.Builder builder;
	private Preferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuration);
		
		preferences = new Preferences(this);

		builder = new AlertDialog.Builder(this);

		etName = (EditText) findViewById(R.id.etName);
		etEmail = (EditText) findViewById(R.id.etEmail);
		
		etName.setText(preferences.getPreferences(Preferences.PREFERENCES_NAME));
		etEmail.setText(preferences.getPreferences(Preferences.PREFERENCES_EMAIL));
		
		// Salve Button
		final Button btSave = (Button) findViewById(R.id.btSave);
		
		btSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String name = etName.getText().toString();
				String email = etEmail.getText().toString();
				
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
					
					
					CheckBox cbClearDB = (CheckBox) findViewById(R.id.cbClearDB);
					if (cbClearDB.isChecked()) {
						DBHelper dbHelper = new DBHelper(v.getContext());
						dbHelper.deleteDatabase();
					}
					

	                builder.setMessage("Configurações salvas com sucesso!");
	                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                    	onBackPressed();
	                    }
	                 });
	                builder.create();
	                builder.show();
				}
			}
		});
		
		
		// Cancel Button
		final Button btCancel = (Button) findViewById(R.id.btCancel);
		
		btCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		OptionsMenu menu = new OptionsMenu(item);
		return menu.onOptionsItemSelected(this);
    }

}

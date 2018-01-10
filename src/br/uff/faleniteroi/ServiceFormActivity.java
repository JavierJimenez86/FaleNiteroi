package br.uff.faleniteroi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import br.uff.faleniteroi.entity.Request;
import br.uff.faleniteroi.entity.Service;
import br.uff.faleniteroi.sqlite.RequestDAO;
import br.uff.faleniteroi.util.BitmapUtil;
import br.uff.faleniteroi.util.Preferences;

public class ServiceFormActivity extends Activity {

	public static EditText etAddress;
	public static EditText etDescription;
	public static double latitude;
	public static double longitude;

	private static int LOAD_IMAGE_FROM_ALBUM = 1;
	private static int LOAD_IMAGE_FROM_CAMERA = 3;
	private static int IDENTIFICATION = 2;
	
	private Bitmap thumbnail = null;
	private String picturePath;
	private Service service;
	private AlertDialog.Builder builder;
	private AlertDialog.Builder successBuilder;
	private View successView;
	private Preferences preferences;
	private Uri fileUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_form);
		
		preferences = new Preferences(this);
		
		etAddress = (EditText) findViewById(R.id.etAddress);
		etDescription = (EditText) findViewById(R.id.etDescription);

		builder = new AlertDialog.Builder(this);
		
		successView = getLayoutInflater().inflate(R.layout.view_request_create, null);
		successBuilder = new AlertDialog.Builder(this);
		successBuilder.setTitle("Solicitação enviada com sucesso");
		successBuilder.setIcon(R.drawable.ic_success);
		successBuilder.setView(successView);
		successBuilder.setCancelable(false);
		successBuilder.create();

		service = (Service) getIntent().getSerializableExtra("Service");

		((TextView) findViewById(R.id.tvServiceName)).setText(service.getName());
		((TextView) findViewById(R.id.tvServiceDescription)).setText(service.getDescription());
		((ImageView) findViewById(R.id.ivServiceIcon)).setImageResource(service.getIcon());

		// Cancel Button
		final Button btCancel = (Button) findViewById(R.id.btCancel);
		btCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ServiceFormActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		// Sent Button
		final Button btSent = (Button) findViewById(R.id.btSend);
		btSent.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				String requestAddress = etAddress.getText().toString();
				String requestDescription = etDescription.getText().toString();

				if (requestAddress.trim().equals("")) {
					builder.setMessage("O campo endereço da solicitação deve ser informado");
					builder.setPositiveButton(R.string.ok, null);
					builder.create();
					builder.show();

				} else if (requestDescription.trim().equals("")) {
					builder.setMessage("O campo descrição da situação deve ser informado");
					builder.setPositiveButton(R.string.ok, null);
					builder.create();
					builder.show();

				} else {
						
					if (!preferences.isRegistered()) {
						
						Intent intent = new Intent(view.getContext(), IdentificationActivity.class);
						startActivityForResult(intent, IDENTIFICATION);
						
					} else {
						
						saveRequest(view.getContext());
					}
				}
			}
		});

		// Select Address
		final Button btSelectAddress = (Button) findViewById(R.id.btSelectAddress);
		btSelectAddress.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SelectAddressActivity.class);
				startActivity(intent);
			}
		});

		// Select Album Button
		final Button btAlbum = (Button) findViewById(R.id.btAlbum);
		btAlbum.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, LOAD_IMAGE_FROM_ALBUM);
			}
		});

		// Select Camera Button
		final Button btCamera = (Button) findViewById(R.id.btCamera);
		btCamera.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

				path.mkdirs();
				
				File photo = new File(path, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpeg");
				fileUri = Uri.fromFile(photo);
				
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				startActivityForResult(intent, LOAD_IMAGE_FROM_CAMERA);
			}
		});

		// Selected image
		final ImageView ivSelectedImage = (ImageView) findViewById(R.id.ivSelectedImage);
		ivSelectedImage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, LOAD_IMAGE_FROM_ALBUM);
			}
		});

		// Remove image
		final Button btRemoveImage = (Button) findViewById(R.id.btRemoveImage);
		btRemoveImage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				removePhoto();
			}
		});
	}
	
	private void removePhoto()
	{
		((ImageView) findViewById(R.id.ivSelectedImage)).setVisibility(View.GONE);
		((Button) findViewById(R.id.btRemoveImage)).setVisibility(View.GONE);
		((Button) findViewById(R.id.btAlbum)).setVisibility(View.VISIBLE);
		((Button) findViewById(R.id.btCamera)).setVisibility(View.VISIBLE);
		picturePath = null;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		super.onActivityResult(requestCode, resultCode, intent);

		if (requestCode == LOAD_IMAGE_FROM_ALBUM && resultCode == RESULT_OK && intent != null) {

			Uri selectedImage = intent.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex);
			cursor.close();

			loadBitmapFromPath();
		}
		
		if (requestCode == LOAD_IMAGE_FROM_CAMERA && resultCode == RESULT_OK) {
			
			File file = new File(fileUri.getPath());
			
			if (file.exists()) {
				picturePath = fileUri.getPath();
				loadBitmapFromPath();
			}
		}
		
		if (requestCode == IDENTIFICATION && resultCode == RESULT_OK) {
			
			if (preferences.isRegistered()) {
				saveRequest(this);
			}
		}
	}

	private void loadBitmapFromPath() {

		if (picturePath != null)
		{
			thumbnail = (BitmapUtil.decodeSampledBitmapFromFile(picturePath, 100, 100));
	
			final ImageView ivSelectedImage = (ImageView) findViewById(R.id.ivSelectedImage);
			ivSelectedImage.setVisibility(View.VISIBLE);
			ivSelectedImage.setImageBitmap(thumbnail);

			((Button) findViewById(R.id.btRemoveImage)).setVisibility(View.VISIBLE);
			((Button) findViewById(R.id.btAlbum)).setVisibility(View.GONE);
			((Button) findViewById(R.id.btCamera)).setVisibility(View.GONE);
		}
	}
	
	private void saveRequest(Context context)
	{
		RequestDAO requestDAO = new RequestDAO(context);
		
		String requestAddress = etAddress.getText().toString();
		String requestDescription = etDescription.getText().toString();
		
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyddhhmmss");
		String requestProtocol = dateFormat.format(currentDate);
		
		Request request = null;
		
		if (requestDAO.getNumRequests() % 2 == 0) {
			request = new Request(service.getName(), 
					requestAddress, 
					picturePath, 
					requestDescription,
					currentDate,
					latitude, 
					longitude,
					true, 
					requestProtocol,
					"O setor responsável foi notificado e irá providenciar a solução em até 7 dias úteis.",
					0,
					0,
					0
				);
		} else {
			request = new Request(service.getName(), 
					requestAddress, 
					picturePath, 
					requestDescription,
					currentDate,
					latitude, 
					longitude,
					false, 
					requestProtocol,
					null,
					0,
					0,
					0
				);	
		}

		requestDAO.createRequest(request);
		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etDescription.getWindowToken(), 0);
		
		((TextView) successView.findViewById(R.id.tvRequestProtocol)).setText(requestProtocol);

        successBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
        		etAddress.setText(null);
        		etDescription.setText(null);
        		latitude = 0;
        		longitude = 0;
        		
        		removePhoto();
        		
            	Intent intent = new Intent(ServiceFormActivity.this, MyRequestsActivity.class);
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
		return menu.onOptionsItemSelected(this);
    }
}

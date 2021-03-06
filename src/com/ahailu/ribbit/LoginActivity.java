package com.ahailu.ribbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {
	protected EditText mUsername;
	protected EditText mPassword;
	protected Button mLoginButton;
	protected TextView mSignUpTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); // Actionbar progress indicator
		setContentView(R.layout.activity_login);
		
		mSignUpTextView = (TextView) findViewById(R.id.signupText);
		mSignUpTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		
		mUsername = (EditText) findViewById(R.id.usernameField);
		mPassword = (EditText) findViewById(R.id.passwordField);
		mLoginButton = (Button) findViewById(R.id.login_button);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// getText() method returns an "Editable", need to convert to String
				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();
				
				// Trim any whitespace user may have entered
				username = username.trim();
				password = password.trim();
				
				if(username.isEmpty() || password.isEmpty()){
					AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
					// LOOK AT THIS STYLE
					builder.setMessage(R.string.login_error_message) // First this method is called
						.setTitle(R.string.login_error_title) // Then this method
						.setPositiveButton(android.R.string.ok, null); // And finally this, ended with a semi-colon
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				else{
					// Login
					setProgressBarIndeterminateVisibility(true); // Begin loading progress indicator
					ParseUser.logInInBackground(username, password, new LogInCallback() {
						
						@Override
						public void done(ParseUser user, ParseException e) {
							setProgressBarIndeterminateVisibility(false); // End loading progress indicator
							if(e == null){
								// Success!
								Intent intent = new Intent(LoginActivity.this, MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
							else{
								// Something wrong happened	
								AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
								// LOOK AT THIS STYLE
								builder.setMessage(e.getMessage()) // First this method is called
									.setTitle(R.string.login_error_title) // Then this method
									.setPositiveButton(android.R.string.ok, null); // And finally this, ended with a semi-colon
								AlertDialog dialog = builder.create();
								dialog.show(); 
							}
							
						}
					});
					
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}

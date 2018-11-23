package alexandre.thauvin.gym3000x;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;



public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, SignUpRequest.sendDataResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        findViewById(R.id.signup).setOnClickListener(this);
    }

    @Override
    public void sendData(final String str){

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!str.contains("Invalid")) {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(SignUpActivity.this, "SignUp successful", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(SignUpActivity.this, "Invalid SignUp", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        EditText email;
        EditText password;
        EditText confirmPassword;
        CheckBox agreement;

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmpassword);
        agreement = findViewById(R.id.agreement);
        if (agreement.isChecked()) {
            if (Tools.isValidEmail(email.getText().toString())) {
                if (Tools.isValidPassword(password.getText().toString())) {
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                        new SignUpRequest.MakeRequestTask(this, SignUpActivity.this).execute(email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "password are not equals", Toast.LENGTH_SHORT).show();
                        //password not equals
                    }
                }
                else {
                    Toast.makeText(SignUpActivity.this, "The password need to be longer than 6 characters and have 1 number", Toast.LENGTH_SHORT).show();
                    //passwordnotvalid
                }
            }
            else {
                Toast.makeText(SignUpActivity.this, "Please enter an valid email", Toast.LENGTH_SHORT).show();
                //emailnotvalid
            }
        }
        else {
            Toast.makeText(SignUpActivity.this, "Please Accept the agreement", Toast.LENGTH_SHORT).show();
            //box not checked
        }

    }

}

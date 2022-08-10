package com.company.newsinternapp.views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.company.newsinternapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPage extends AppCompatActivity {


// defining all the properties
    TextView username_var,password_var,signupbutton,forgotPass;
    Button loginbutton,signupTop;
    SignInButton googlesignin;


    GoogleSignInClient googleSignInClient ;

    ActivityResultLauncher<Intent> activityResultLauncher;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        username_var = findViewById(R.id.usernames);
        password_var = findViewById(R.id.passwords);
        forgotPass = findViewById(R.id.forgotpass);

        registerActivityForGoogle();




        loginbutton = findViewById(R.id.logingbtnsignup);
        googlesignin = findViewById(R.id.googlesignin);
        signupbutton =  findViewById(R.id.signupbtn);
        signupTop= findViewById(R.id.signupbtntop);
        firebaseAuth = FirebaseAuth.getInstance();


        //setting listener to login button

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_ = username_var.getText().toString();
                String password_ = password_var.getText().toString();

                if(!username_.isEmpty()){
                    username_var.setError(null);
                    if(!password_.isEmpty()){
                        password_var.setError(null);

                        final String username_data =username_var.getText().toString();
                        final String password_data = password_var.getText().toString();

//                        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
//                        DatabaseReference databaseReference = firebaseDatabase.getReference("datauser");

                        firebaseAuth.signInWithEmailAndPassword(username_data,password_data).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),"You are not registered Or Please check the details",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }else{
                        password_var.setError("enter password");
                    }

                }else{
                    username_var.setError("please enter username");
                }
            }
        });

        googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signingoogle();
            }
        });


        // button for signup

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);


            }
        });
        signupTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }
        });


        // textview for forgot password
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, ForgotPassword.class);
                startActivity(intent);
            }
        });


    }


    public  void  signingoogle(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("756094655835-p39nesqqcsg3o37toihmiroaeib9doqb.apps.googleusercontent.com").requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(LoginPage.this, gso);

        signinusingGoogle();
    }

    public  void signinusingGoogle(){

        Intent signinintent = googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(signinintent);
    }

    public void registerActivityForGoogle(){


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        int resultcode = result.getResultCode();
                        Intent data = result.getData();

                        if(resultcode== RESULT_OK && data!=null){
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            firebaseSignInWithGoogle(task);
                        }

                    }
                });
    }

    public void firebaseSignInWithGoogle(Task<GoogleSignInAccount> task)
    {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            Toast.makeText(this, "SuccessFull", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginPage.this,MainActivity.class);
            startActivity(intent);
            finish();
            firebaseGoogleAccount(account);
        } catch (ApiException e) { Toast.makeText(this, "erroor" +e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void firebaseGoogleAccount(GoogleSignInAccount account){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    //firebaseUser.
                }else{

                }
            }
        });

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
//
//        if(firebaseUser!=null){
//
//            Intent intent = new Intent(LoginPage.this,MainActivity.class);
//            startActivity(intent);
//        }
//    }


}


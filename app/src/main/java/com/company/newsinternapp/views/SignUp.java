package com.company.newsinternapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.newsinternapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    // defining all the properties

    EditText email,password;
    Button signup,signinTop;
    EditText username, mobile;
    TextView signin;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //getting the properties and matching with the containers ids


        email = findViewById(R.id.emailsignup);
        password =findViewById(R.id.passwordsignup);
        username = findViewById(R.id.username);
        signinTop = findViewById(R.id.loginbtntop);
        signin = findViewById(R.id.signinbtn);
        signup = findViewById(R.id.registerbtn);
        mobile = findViewById(R.id.contactNum);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = firebaseDatabase.getReference();


        // adding listener to move to login page
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, LoginPage.class);
                startActivity(intent);
            }
        });


        signinTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, LoginPage.class);
                startActivity(intent);
            }
        });


        // for new user sign up
        signup.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {


                                          String username_= username.getText().toString();
                                          String email_=email.getText().toString();
                                          String phone_=mobile.getText().toString();
                                          String password_=password.getText().toString();

                                          //Validations validations = new Validations();
                                          // instead of username.isEmty use validations.(specified string like username , phone number , emaik)

                                          if(!username_.isEmpty()){

                                              username.setError(null);
                                              if ( !email_.isEmpty()){
                                                  email.setError(null);

                                                  if( !phone_.isEmpty()){
                                                      mobile.setError(null);
                                                      if(!password_.isEmpty()){
                                                          password.setError(null);

                                                          signupwithemail(email_, password_,username_,phone_);// method build for signup


                                                      }else{
                                                          password.setError("enter password");
                                                      }
                                                  }else{
                                                      mobile.setError("enter number");
                                                  }

                                              }else{
                                                  email.setError("enter email which is valid");
                                              }
                                          }else{
                                              username.setError("enter username");
                                          }
                                      }



                                  }
        );


    }

    public void signupwithemail(String email,String password,String user,String contact){ // signup using email and pass and storing info into database



        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    databaseReference.child("Users").child(firebaseAuth.getUid()).child("username").setValue(user);
                    databaseReference.child("Users").child(firebaseAuth.getUid()).child("contact").setValue(contact);


                    signup.setClickable(false);
                    Toast.makeText(SignUp.this, "success ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    intent.putExtra("username",user);
                    startActivity(intent);

                }else{
                    Toast.makeText(SignUp.this,"failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }






}

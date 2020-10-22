package com.uc.myfirebaseapss;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.myfirebaseapss.model.Student;

public class FragmentAccount extends Fragment {

    TextView lbl_name, lbl_gender, lbl_address ;
    Button btn_logout;

    private FirebaseAuth mAuth;//
    FirebaseDatabase dbStudent = FirebaseDatabase.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAuth = FirebaseAuth.getInstance();//

        lbl_name = v.findViewById(R.id.lbl_name_stud_adp);
        lbl_gender = v.findViewById(R.id.lbl_gender_stud_adp);
        lbl_address = v.findViewById(R.id.lbl_address_stud_adp);

        btn_logout = v.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() == null){
                            Intent intent = new Intent(FragmentAccount.this.getActivity(),MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                        }
                    }
                };
                mAuth.addAuthStateListener(authStateListener);
                mAuth.signOut();

            }
        });

        dbStudent.getReference("student/"+uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student = snapshot.getValue(Student.class);
                lbl_name.setText(student.getName());
                lbl_gender.setText(student.getGender());
                lbl_address.setText(student.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }

        );



        return v;
    }
}


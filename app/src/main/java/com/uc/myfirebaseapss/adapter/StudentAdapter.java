package com.uc.myfirebaseapss.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uc.myfirebaseapss.AddLecturer;
import com.uc.myfirebaseapss.BottomNavigationActivity;
import com.uc.myfirebaseapss.LecturerData;
import com.uc.myfirebaseapss.LecturerDetail;
import com.uc.myfirebaseapss.LoginStudent;
import com.uc.myfirebaseapss.R;
import com.uc.myfirebaseapss.RegisterStudent;
import com.uc.myfirebaseapss.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.CardViewViewHolder>{


    private Context context;
    private ArrayList<Student> listStudent;
    private ArrayList<Student> getListStudent() {
        return listStudent;
    }
    public void setListStudent(ArrayList<Student> listStudent) {
        this.listStudent = listStudent;
    }
    public StudentAdapter(Context context) {
        this.context = context;
    }
    DatabaseReference dbStudent = FirebaseDatabase.getInstance().getReference("student");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @NonNull
    @Override
    public StudentAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_adapter, parent, false);
        return new StudentAdapter.CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final StudentAdapter.CardViewViewHolder holder, int position) {

        final Student student = getListStudent().get(position);
        holder.lbl_name.setText(student.getName());
        holder.lbl_gender.setText(student.getGender());
        holder.lbl_address.setText(student.getAddress());

        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.startAnimation(klik);
                new AlertDialog.Builder(context)
                        .setTitle("Konfirmasi")
                        .setIcon(R.drawable.ic_android_goldtrans_24dp)
                        .setMessage("Are you sure to delete "+student.getName()+" data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
//                                dialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
//                                        dialog.cancel();
                                        mAuth.signInWithEmailAndPassword(student.getEmail(), student.getPassword() )
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                            user.delete();
                                                        } else {

                                                        }

                                                    }
                                                });

                                        dbStudent.child(student.getUid()).removeValue(new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//
                                            }
                                        });

                                    }
                                }, 2000);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, RegisterStudent.class);
                in.putExtra("action", "edit");
                in.putExtra("edit_data_stud", student);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(in);
            }
        });
    }
    @Override
    public int getItemCount() {
        return getListStudent().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder{
        TextView lbl_name, lbl_gender, lbl_address ;
        ImageButton btn_del , btn_edit;


        CardViewViewHolder(View itemView) {
            super(itemView);
            lbl_name = itemView.findViewById(R.id.lbl_name_stud_adp);
            lbl_gender = itemView.findViewById(R.id.lbl_gender_stud_adp);
            lbl_address = itemView.findViewById(R.id.lbl_address_stud_adp);
            btn_del = itemView.findViewById(R.id.ib_del);
            btn_edit = itemView.findViewById(R.id.ib_edit);

        }
    }
}

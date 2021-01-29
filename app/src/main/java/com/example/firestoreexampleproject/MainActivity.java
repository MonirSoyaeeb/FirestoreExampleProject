package com.example.firestoreexampleproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView textViewData;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
    private DocumentReference noteRef = db.collection("Notebook").document("My first note");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        textViewData = findViewById(R.id.text_view_data);
    }

    public void addNote(View view) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        Note note = new Note(title,description);

        notebookRef.add(note);
    }


    @Override
    protected void onStart() {
        super.onStart();
        notebookRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            String data = "";

            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }else {
                    for(QueryDocumentSnapshot documentSnapshot : value){
                        Note note = documentSnapshot.toObject(Note.class);
                        note.setDocumentID(documentSnapshot.getId());

                        String documentId = note.getDocumentID();
                        String title = note.getTitle();
                        String description = note.getDescription();

                        data += "DocumentId :"+documentId+ "\nTitle :"+title+"\n"+"Description: "+description+"\n\n";

                    }
                    textViewData.setText(data);
                }
            }
        });

    }

    public void loadNotes(View view) {
        notebookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";

                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Note note = documentSnapshot.toObject(Note.class);
                    note.setDocumentID(documentSnapshot.getId());

                    String documentID = note.getDocumentID();
                    String title = note.getTitle();
                    String description = note.getDescription();

                    data += "DocumentId :"+documentID+ "\nTitle :"+title+"\n"+"Description: "+description+"\n\n";

                }
                textViewData.setText(data);
            }
        });
    }
}
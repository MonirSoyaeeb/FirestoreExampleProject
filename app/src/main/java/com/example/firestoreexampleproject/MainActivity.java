package com.example.firestoreexampleproject;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPriority;
    private TextView textViewData;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        textViewData = findViewById(R.id.text_view_data);
        editTextPriority = findViewById(R.id.edit_text_priority);
    }

    public void addNote(View view) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = Integer.parseInt(editTextPriority.getText().toString());

        if (editTextPriority.length() == 0) {
            editTextPriority.setText("0");
        }

        Note note = new Note(title, description, priority);
        notebookRef.add(note);
    }

    public void loadNotes(View view) {
       notebookRef.orderBy("priority").startAt(3).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               String data = "";
               for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                   Note note = documentSnapshot.toObject(Note.class);
                   note.setDocumentID(documentSnapshot.getId());

                   String documentId = note.getDocumentID();
                   String title = note.getTitle();
                   String description = note.getDescription();
                   int priority = note.getPriority();

                   data+= "ID"+documentId+"\nTitle"+title+"\nDescription"+description+"\nPriority"+priority+"\n\n";
               }
               textViewData.setText(data);
           }
       });
    }
}
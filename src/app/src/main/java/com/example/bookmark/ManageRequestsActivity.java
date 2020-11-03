package com.example.bookmark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bookmark.adapters.RequestList;
import com.example.bookmark.models.Book;
import com.example.bookmark.models.Borrower;
import com.example.bookmark.models.Owner;
import com.example.bookmark.models.Request;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * TODO: Description of class.
 *
 * @author Nayan Prakash.
 */
public class ManageRequestsActivity extends BackButtonActivity {

    private Book book;
    private ListView requestList;
    private ArrayAdapter<Request> requestAdapter;
    private ArrayList<Request> requestDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_requests);

        // TODO: make sure this matches with what Konrad is doing
        SharedPreferences mPrefs = getSharedPreferences("Key", MODE_PRIVATE);
        String username = mPrefs.getString("username", null);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String bookTitle = "Book Title";

        if (bundle != null) {
            book = (Book) bundle.getSerializable("Book");
            bookTitle = book.getTitle();
        }

        TextView bookName = findViewById(R.id.book_name);
        bookName.setText(bookTitle);

        requestList = findViewById(R.id.request_list);
        requestDataList = new ArrayList<>();
        requestAdapter = new RequestList(this, requestDataList);
        requestList.setAdapter(requestAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Requests");

        collectionReference
            .whereEqualTo("Owner", username)
            .whereEqualTo("BookTitle", book.getTitle())
            .addSnapshotListener((queryDocumentSnapshots, e) -> {

            requestDataList.clear();
            for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                // TODO: clarify once Firebase is working as expected
                Owner requestOwner = (Owner) doc.getData().get("Owner");
                Book requestBook = (Book) doc.getData().get("Book");
                if (requestOwner.getUsername().equals(username) && requestBook.equals(book)) {
                    Borrower borrower = (Borrower) doc.getData().get("Requester");
                    String requestDate = doc.getData().get("Request Date").toString();
                    requestDataList.add(new Request(requestBook, borrower, null));
                }
            }
            requestAdapter.notifyDataSetChanged();
        });

    }

    public void accept_request(View v) {
//        FirebaseProvider.getInstance().acceptRequest(request);
        Intent i = new Intent(this, AcceptRequestsActivity.class);
        startActivity(i);
    }

    public void reject_request(View v) {
//        FirebaseProvider.getInstance().rejectRequest(request);
    }
}

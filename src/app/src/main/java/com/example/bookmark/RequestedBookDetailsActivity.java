package com.example.bookmark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookmark.abstracts.ListingBooksActivity;
import com.example.bookmark.models.Book;

/**
 *  This activity shows the details of a book.
 *
 * @author Nayan Prakash.
 */
public class RequestedBookDetailsActivity extends BackButtonActivity {

    String isbn;
    String title;
    String author;
    String description;
    String ownedBy;
    String status;
    // TODO: Image image;

    private TextView titleTextView;
    private TextView authorTextView;
    private TextView isbnTextView;
    private TextView descriptionTextView;
    private ImageView imageView;
    private TextView ownedByTextView;
    private TextView statusTextView;

    private Book book;

    /**
     * This function creates the RequestedBookDetails view and retrieves the book object from the
     * intent, and sets all the views of the activity
     *
     * @param savedInstanceState an instance state that has the state of the BorrowBookActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_book_details);
        getSupportActionBar().setTitle("Book Details");

        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        book = (Book) myBundle.getSerializable(ListingBooksActivity.EXTRA_BOOK);

        titleTextView = findViewById(R.id.requested_details_title_text);
        authorTextView = findViewById(R.id.requested_details_author_text);
        isbnTextView = findViewById(R.id.requested_details_isbn_text);
        descriptionTextView = findViewById(R.id.requested_details_description_text);
        imageView = findViewById(R.id.requested_details_book_image);
        ownedByTextView = findViewById(R.id.requested_details_owned_by);
        statusTextView = findViewById(R.id.requested_details_book_status_text);

        setBookDetails();
        fillBookDetails();
    }

    /**
     * This function handles retrieving data from the Book object
     */
    private void setBookDetails() {
        isbn = book.getIsbn();
        author = book.getAuthor();
        title = book.getTitle();
        description = book.getDescription();
        ownedBy = book.getOwnerId().toString();
        status = book.getStatus().toString();
    }

    /**
     * This function handles filling the text fields and image with the book data
     */
    private void fillBookDetails() {
        titleTextView.setText(title);
        authorTextView.setText(author);
        isbnTextView.setText("ISBN: " + isbn);
        descriptionTextView.setText("Description: " + description);
        //imageView.setImageBitmap();
        ownedByTextView.setText("Owned by: " + ownedBy);
        statusTextView.setText("Status: " + status);
    }
}

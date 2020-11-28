package com.example.bookmark;

import com.example.bookmark.abstracts.ListingBooksActivity;
import com.example.bookmark.models.Book;
import com.example.bookmark.server.StorageServiceProvider;
import com.example.bookmark.util.DialogUtil;
import com.example.bookmark.util.UserUtil;

/**
 * This activity shows a user a list of books that they have pending requests
 * for. They can select a book which takes them to the
 * RequestedBookDetailsActivity where they can see the books details.
 * TODO what else do we want here?
 *
 * @author Ryan Kortbeek.
 */
public class PendingRequestsActivity extends ListingBooksActivity {

    protected void setActivityTitle() {
        activityTitle = "Pending Requests";
    }

    protected void setBookOwnerAndStatusVisibility() {
        showOwner = true;
        showStatus = true;
    }

    protected void setIntentStartingPoint() {
        intentStartingPoint = this;
    }

    protected void setIntentDestination() {
        intentDestination = ExploreBookDetailsActivity.class;
    }

    protected void getBooks() {
        String username = UserUtil.getLoggedInUser(this);
        StorageServiceProvider.getStorageService().retrieveUserByUsername(username, user -> {
            StorageServiceProvider.getStorageService().retrieveBooksByRequester(user,
                books -> {
                    relevantBooks.clear();
                    visibleBooks.clear();
                    for (Book book : books) {
                        if (book.getStatus() == Book.Status.REQUESTED) {
                            relevantBooks.add(book);
                        }
                    }
                    visibleBooks.addAll(relevantBooks);
                    visibleBooksAdapter.notifyDataSetChanged();
                    System.out.println(visibleBooks);
                }, e -> {
                    DialogUtil.showErrorDialog(this, e);
                });
        }, e -> {
            DialogUtil.showErrorDialog(this, e);
        });

        // System.out.println("visible " + visibleBooks.toString());
    }
}

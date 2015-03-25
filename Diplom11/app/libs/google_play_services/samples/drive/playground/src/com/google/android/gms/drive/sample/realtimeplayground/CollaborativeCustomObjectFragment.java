// Copyright 2014 Google Inc. All Rights Reserved.

package com.google.android.gms.drive.sample.realtimeplayground;

import com.google.android.gms.drive.realtime.CustomCollaborativeObject;
import com.google.android.gms.drive.realtime.CustomCollaborativeObject.FieldChangedEvent;
import com.google.android.gms.drive.realtime.RealtimeDocument;
import com.google.android.gms.drive.realtime.RealtimeEvent.Listener;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class CollaborativeCustomObjectFragment extends PlaygroundFragment {
    private RealtimeDocument mRealtimeDocument;
    private Movie mCustomMovie;

    private EditText mTitleEditText;
    private EditText mDirectorNameEditText;
    private EditText mNotesEditText;
    private EditText mStarRatingEditText;

    private Listener<FieldChangedEvent> mFieldChangedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(
                R.layout.fragment_collaborative_custom_object, container, false);

        mTitleEditText = (EditText) view.findViewById(R.id.movie_title_edit_text);
        mDirectorNameEditText = (EditText) view.findViewById(R.id.movie_director_name_edit_text);
        mNotesEditText = (EditText) view.findViewById(R.id.movie_notes_edit_text);
        mStarRatingEditText = (EditText) view.findViewById(R.id.movie_star_rating_edit_text);

        setEditTextTextWatcher(mTitleEditText);
        setEditTextTextWatcher(mDirectorNameEditText);
        setEditTextTextWatcher(mNotesEditText);
        setEditTextTextWatcher(mStarRatingEditText);

        disableViewUntilLoaded(mTitleEditText);
        disableViewUntilLoaded(mDirectorNameEditText);
        disableViewUntilLoaded(mNotesEditText);
        disableViewUntilLoaded(mStarRatingEditText);

        return view;
    }

    /**
     * Add TextWatcher to EditTexts so changes automatically update the Movie object. This is used
     * instead of a CollaborativeString, to keep the custom object simple, or a save button to
     * update in realtime. It is, however, slow because it's saving after each character.
     */
    private void setEditTextTextWatcher(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void afterTextChanged(Editable editable) {
                switch (editText.getId()) {
                    case R.id.movie_title_edit_text:
                        mCustomMovie.setTitle(editable.toString());
                        break;
                    case R.id.movie_director_name_edit_text:
                        mCustomMovie.setDirectorName(editable.toString());
                        break;
                    case R.id.movie_notes_edit_text:
                        mCustomMovie.setNotes(editable.toString());
                        break;
                    case R.id.movie_star_rating_edit_text:
                        mCustomMovie.setStarRating(editable.toString());
                        break;
                    default:
                        break;
                }
                editText.setSelection(editable.length());
            }
        });
    }

    @Override
    String getTitle() {
        return "Collaborative Custom Object";
    }

    @Override
    void onLoaded(RealtimeDocument document) {
        mRealtimeDocument = document;
        mCustomMovie = new Movie((CustomCollaborativeObject) mRealtimeDocument.getModel().getRoot()
                .get(PlaygroundDocumentActivity.COLLAB_CUSTOM_OBJ_NAME));
        mTitleEditText.setText(mCustomMovie.getTitle());
        mDirectorNameEditText.setText(mCustomMovie.getDirectorName());
        mNotesEditText.setText(mCustomMovie.getNotes());
        mStarRatingEditText.setText(mCustomMovie.getStarRating());

        mFieldChangedListener = new Listener<FieldChangedEvent>() {
            @Override
            public void onEvent(FieldChangedEvent event) {
                String fieldName = event.getFieldName();
                if (fieldName.equals("name")) {
                    mTitleEditText.setText(mCustomMovie.getTitle());
                } else if (fieldName.equals("directory")) {
                    mDirectorNameEditText.setText(mCustomMovie.getDirectorName());
                } else if (fieldName.equals("notes")) {
                    mNotesEditText.setText(mCustomMovie.getNotes());
                } else if (fieldName.equals("rating")) {
                    mStarRatingEditText.setText(mCustomMovie.getStarRating());
                }
            }
        };

        mCustomMovie.addTitleChangedListener(mFieldChangedListener);
        mCustomMovie.addDirectorNameChangedListener(mFieldChangedListener);
        mCustomMovie.addNotesChangedListener(mFieldChangedListener);
        mCustomMovie.addStarRatingChangedListener(mFieldChangedListener);
    }

    public static class Movie {
        private CustomCollaborativeObject movie;

        Movie(CustomCollaborativeObject obj) {
            this.movie = obj;
        }

        CustomCollaborativeObject getCustomObject() {
            return movie;
        }

        String getTitle() {
            return (String) movie.get("name");
        }

        void setTitle(String title) {
            movie.set("name", title);
        }

        void addTitleChangedListener(Listener<FieldChangedEvent> listener) {
            movie.addFieldChangedListener(listener);
        }

        String getDirectorName() {
            return (String) movie.get("director");
        }

        void setDirectorName(String directorName) {
            movie.set("director", directorName);
        }

        void addDirectorNameChangedListener(Listener<FieldChangedEvent> listener) {
            movie.addFieldChangedListener(listener);
        }

        String getNotes() {
            return (String) movie.get("notes");
        }

        void setNotes(String notes) {
            movie.set("notes", notes);
        }

        void addNotesChangedListener(Listener<FieldChangedEvent> listener) {
            movie.addFieldChangedListener(listener);
        }

        String getStarRating() {
            return (String) movie.get("rating");
        }

        void setStarRating(String rating) {
            movie.set("rating", rating);
        }

        void addStarRatingChangedListener(Listener<FieldChangedEvent> listener) {
            movie.addFieldChangedListener(listener);
        }
    }
}

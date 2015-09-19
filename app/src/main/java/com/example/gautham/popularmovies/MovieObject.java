package com.example.gautham.popularmovies;

/**
 * Created by gautham on 19/09/2015.
 */
public class MovieObject {
    public String mBackDropPath, mTitle, mDescription, mReleaseDate, mUserRating;

    public MovieObject(String BackDropPath, String Title, String Description, String ReleaseDate, String UserRating) {

        this.mBackDropPath = BackDropPath;
        this.mTitle = Title;
        this.mDescription = Description;
        this.mReleaseDate = ReleaseDate;
        this.mUserRating = UserRating;
    }
    public String getBackDropPath() {
        return mBackDropPath;
    }
}

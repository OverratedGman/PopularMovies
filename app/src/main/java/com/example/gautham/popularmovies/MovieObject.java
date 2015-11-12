package com.example.gautham.popularmovies;

/**
 * Created by gautham on 19/09/2015.
 */
public class MovieObject {
    public String mBackDropPath, mTitle, mDescription, mReleaseDate, mUserRating,mID,mTrailer,mReview;

    public MovieObject(String BackDropPath, String Title, String Description, String ReleaseDate, String UserRating,String ID/*,String Trailer, String Review*/) {

        this.mBackDropPath = BackDropPath;
        this.mTitle = Title;
        this.mDescription = Description;
        this.mReleaseDate = ReleaseDate;
        this.mUserRating = UserRating;
        this.mID = ID;
       // this.mTrailer = Trailer;
       // this.mReview = Review;
    }
    public String getBackDropPath() {
        return mBackDropPath;
    }
    public String getTitle(){return mTitle;}
    public String getReleaseDate(){return mReleaseDate;}
    public String getUserRating(){return mUserRating;}
    public String getDescription(){return mDescription;}
    public String getID(){return mID;}
    //public String getTrailer(){return mTrailer;}
   // public String getReview(){return mReview;}

}

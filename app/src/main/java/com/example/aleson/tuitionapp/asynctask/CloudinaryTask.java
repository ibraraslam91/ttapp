package com.example.aleson.tuitionapp.asynctask;

import android.os.AsyncTask;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Aleson on 1/21/2017.
 */

public class CloudinaryTask extends AsyncTask<File, Void, Map> {

    public final static String profileUploadTag = "ProfileUpload";
    CTcallBack mListner;
    Map uploadResult;
    String userID;
    String catagory;


    public CloudinaryTask(CTcallBack Listner, String catagary, String userID) {
        this.mListner = Listner;
        this.catagory = catagary;
        this.userID = userID;
    }

    @Override
    protected Map doInBackground(File... files) {
        Cloudinary cloudinary = new Cloudinary("cloudinary://721316732552831:J3AfrRzFrmcwIgFzMzRNbD4pCnQ@dymx8oply");
        if (catagory.equals(profileUploadTag)) {
            uploadResult = profileUpload(cloudinary, files[0]);
        }
        return uploadResult;
    }

    @Override
    protected void onPostExecute(Map map) {
        super.onPostExecute(map);
        mListner.onUploadComplete(map);
    }

    public Map profileUpload(Cloudinary cloudinary, File uploadFile) {
        Map options, result;
        result = null;
        try {
            options = ObjectUtils.asMap("public_id", userID, "transformation", new Transformation().width(120).height(120).crop("thumb").gravity("face").fetchFormat("png"));
            result = cloudinary.uploader().upload(uploadFile, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public interface CTcallBack {
        void onUploadComplete(Map map);
    }

}

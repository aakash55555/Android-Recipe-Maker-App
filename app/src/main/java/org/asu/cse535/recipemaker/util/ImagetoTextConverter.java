package org.asu.cse535.recipemaker.util;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import org.asu.cse535.recipemaker.activity.FoodListActivity;
import org.asu.cse535.recipemaker.activity.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ImagetoTextConverter {


    public static List<String> foodList = null;
    public static MainActivity mainActivity = null;
    public static boolean compress = false;

    public static List<String> getTextListFromImage(String path, VisionRequestInitializer requestInitializer, MainActivity activity, boolean compressMain) {

        foodList = null;
        mainActivity = activity;
        compress = compressMain;
        performCloudVisionRequest(path, requestInitializer);
        //Comment below line to see the error
        //foodList = new ArrayList<>(asList("Pizza", "Pasta", "Chicken Noddle", "Sushi"));
        return foodList;
    }

    public static void performCloudVisionRequest(String path, VisionRequestInitializer requestInitializer) {
        if (path != "") {
            try {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = resizeBitmap(BitmapFactory.decodeFile(path, bmOptions), 1024);
                if (compress) bitmap = resizeBitmap(BitmapFactory.decodeFile(path, bmOptions), 512);
                callCloudVision(bitmap, requestInitializer);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private static void callCloudVision(final Bitmap bitmap, final VisionRequestInitializer requestInitializer) throws IOException {

        new AsyncTask<Object, Void, BatchAnnotateImagesResponse>() {
            @Override
            protected BatchAnnotateImagesResponse doInBackground(Object... params) {
                try {

                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    Vision.Builder builder = new Vision.Builder
                            (httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);
                    Vision vision = builder.build();

                    List<Feature> featureList = new ArrayList<>();
                    Feature labelDetection = new Feature();
                    labelDetection.setType("LABEL_DETECTION");
                    labelDetection.setMaxResults(10);
                    featureList.add(labelDetection);


                    List<AnnotateImageRequest> imageList = new ArrayList<>();
                    AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();
                    Image base64EncodedImage = getBase64EncodedJpeg(bitmap);
                    annotateImageRequest.setImage(base64EncodedImage);
                    annotateImageRequest.setFeatures(featureList);
                    imageList.add(annotateImageRequest);

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(imageList);

                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(batchAnnotateImagesRequest);
                    annotateRequest.setDisableGZipContent(true);
                    Log.d(TAG, "Sending request to Google Cloud");

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return response;

                } catch (GoogleJsonResponseException e) {
                    Log.e(TAG, "Request error: " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "Request error: " + e.getMessage());
                }
                return null;
            }

            protected void onPostExecute(BatchAnnotateImagesResponse response) {
                foodList = getDetectedTexts(response);
                foodList.remove("Food");
                foodList.remove("Dish");
                foodList.remove("Cuisine");
                foodList.remove("Ingredient");
                foodList.remove("Produce");
                Log.d(TAG, String.valueOf(foodList));
                mainActivity.progressDialog.dismiss();
                mainActivity.navigate(foodList);
            }

        }.execute();
    }

    private static ArrayList<String> getDetectedTexts(BatchAnnotateImagesResponse response) {
        ArrayList<String> message = new ArrayList();
        List<EntityAnnotation> texts = response.getResponses().get(0)
                .getLabelAnnotations();
        if (texts != null) {
            for (EntityAnnotation text : texts) {
                message.add(text.getDescription());
            }
        } else {
            message.add("Food not found\n");
        }

        return message;
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int dimension) {

        int maxDimension = dimension;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    public static Image getBase64EncodedJpeg(Bitmap bitmap) {
        Image image = new Image();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        image.encodeContent(imageBytes);
        return image;
    }

}

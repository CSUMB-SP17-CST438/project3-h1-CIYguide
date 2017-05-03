package com.ciy;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

<<<<<<< HEAD:app/src/main/java/com/ciy/ClarifaiPrediction.java
import com.ciy.SearchRecipeScreen;

=======
<<<<<<< HEAD
=======
import com.ciy.SearchRecipeScreen;

>>>>>>> 07e229fdf9f0cfa29465066f091f74361c000b53
>>>>>>> 4980d0008b4cf9330220c8cba53fb9dbf35cb2fb:app/src/main/java/com/example/ciyguide/ciyguide/ClarifaiPrediction.java
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by jason on 5/2/2017.
 */

public class ClarifaiPrediction extends AsyncTask<Void, Void, ClarifaiResponse<List<ClarifaiOutput<Concept>>>> {

    private SearchRecipeScreen activity;
    private ArrayList<String> list = new ArrayList<String>();
    private List<Concept> predictionlist = new ArrayList<Concept>();

    public ClarifaiPrediction(SearchRecipeScreen activity)
    {
        this.activity = activity;

    }

    @Override
    protected ClarifaiResponse<List<ClarifaiOutput<Concept>>> doInBackground(Void... params) {
        // The default Clarifai model that identifies concepts in images
        final ConceptModel generalModel = activity.client.getDefaultModels().foodModel();

        // Use this model to predict, with the image that the user just selected as the input
        return generalModel.predict()
                .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(activity.jpegImage)))
                .executeSync();
    }

    @Override protected void onPostExecute(ClarifaiResponse<List<ClarifaiOutput<Concept>>> response) {
        if (!response.isSuccessful()) {
            return;
        }
        final List<ClarifaiOutput<Concept>> listofPredictions = response.get();
        if (listofPredictions.isEmpty()) {
            return;
        }
        predictionlist = listofPredictions.get(0).data();
        for(int ii = 0; ii < predictionlist.size(); ii++)
        {
            list.add(predictionlist.get(ii).name());
        }
        activity.setList(list);
    }


}

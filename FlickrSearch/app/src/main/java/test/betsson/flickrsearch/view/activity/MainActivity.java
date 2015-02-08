package test.betsson.flickrsearch.view.activity;

import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import test.betsson.flickrsearch.R;
import test.betsson.flickrsearch.service.ImagesService;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImagesService.initiateImageSearch(getApplicationContext(), "monkey");
    }
}
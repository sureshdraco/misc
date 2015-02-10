package test.betsson.flickrsearch.view.activity;

import test.betsson.flickrsearch.R;
import test.betsson.flickrsearch.view.custom.ActivityProgressIndicator;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ImageSearchActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;

		switch (id) {
		case ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER:
			dialog = new ActivityProgressIndicator(this, R.style.TransparentDialog);
			break;
		}
		return dialog;
	}

}
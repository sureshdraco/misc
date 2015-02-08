package test.betsson.flickrsearch.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import test.betsson.flickrsearch.R;
import test.betsson.flickrsearch.service.ImagesService;

public class SearchFragment extends Fragment {

    private ListView searchResultListView;
    private Button searchBtn;
    private EditText searchText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        searchResultListView = (ListView) view.findViewById(R.id.searchResultListView);
        searchText = (EditText) view.findViewById(R.id.searchText);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    ImagesService.initiateImageSearch(getActivity(), String.valueOf(searchText.getText()));
                }
            }
        });
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(String.valueOf(searchText.getText()))) {
            searchText.setError(getResources().getString(R.string.search_text_missing));
            return false;
        }
        return true;
    }
}
